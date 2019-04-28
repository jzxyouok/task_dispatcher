package com.hptpd.taskdispatcherserver.service.impl;


import com.alibaba.fastjson.JSONObject;
import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.hptpd.taskdispatcherserver.common.util.AbstractMyBeanUtils;
import com.hptpd.taskdispatcherserver.common.util.HttpUtil;
import com.hptpd.taskdispatcherserver.common.util.JsonUtil;
import com.hptpd.taskdispatcherserver.component.RedisService;
import com.hptpd.taskdispatcherserver.component.Result;
import com.hptpd.taskdispatcherserver.component.ServerConfig;
import com.hptpd.taskdispatcherserver.domain.*;
import com.hptpd.taskdispatcherserver.domain.vo.*;
import com.hptpd.taskdispatcherserver.repository.*;
import com.hptpd.taskdispatcherserver.service.BaseTaskService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.*;


/**
 * \* Created with IntelliJ IDEA.
 * \* Date: 2019-03-14 17:04
 * \*
 * \* Description:
 * \
 *
 * @author liucheng
 */
@Service("baseTaskService")
public class BaseTaskServiceImpl implements BaseTaskService {

    private Logger logger = LoggerFactory.getLogger(BaseTaskServiceImpl.class);

    @Autowired
    private RedisService redisService;

    @Resource(name = "taskRep")
    private TaskRep taskRep;

    @Resource(name = "userRep")
    private UserRep userRep;

    @Resource(name = "proposerRep")
    private ProposerRep proposerRep;

    @Resource(name = "auditorRep")
    private AuditorRep auditorRep;

    @Resource(name = "staffRep")
    private StaffRep staffRep;

    @Resource(name = "projectRep")
    private ProjectRep projectRep;

    @Resource(name = "labelRep")
    private LabelRep labelRep;

    @Override
    public Result dispatchTask(TaskVo taskVo) {

        Task task =new Task();
        AbstractMyBeanUtils.copyProperties(taskVo,task);

        ProposerVo proposerVo =taskVo.getProposerVo();
        Proposer proposer =new Proposer();
        UserVo userVo =proposerVo.getUserVo();
        Optional<User> optionalUser =userRep.findById(userVo.getId());
        proposer.setUser(optionalUser.get());
        proposer.setTask(task);


        AuditorVo auditorVo =taskVo.getAuditorVo();
        Auditor auditor =new Auditor();
        logger.info(auditorVo.toString());
        Optional<User> optionalUser1 =userRep.findById(auditorVo.getUserVo().getId());
        auditor.setUser(optionalUser1.get());
        auditor.setTask(task);

        List<LabelVo> labelVos = taskVo.getLabelVos();
        Set<Label> labelList = Sets.newLinkedHashSet();
        for (LabelVo labelVo : labelVos) {
            Label label = null;
            if ((labelVo.getId().length() >= LabelVo.NEW_LABEL_TAG.length()) && (labelVo.getId().substring(0, LabelVo.NEW_LABEL_TAG.length()).equals(LabelVo.NEW_LABEL_TAG))) {
                label = new Label();
                AbstractMyBeanUtils.copyProperties(labelVo, label);
                label.setId(null);
                label.setCreatTime(new Date());
                label.setCreatUser(proposerVo.getUserVo().getId());
                label.setFrequency(1L);
                Set<Task> taskList = Sets.newLinkedHashSet();
                taskList.add(task);
                label.setTasks(taskList);
            } else {
                label = labelRep.findById(labelVo.getId()).get();
                label.setFrequency(label.getFrequency() + 1);
                label.getTasks().add(task);
            }
            labelList.add(label);
        }

        List<StaffVo> staffVos =taskVo.getStaffVos();

        List<Staff> staffList =Lists.newArrayList();
        for (StaffVo staffVo:staffVos){
            Staff staff =new Staff();
            UserVo userVo1 =staffVo.getUserVo();
            Optional<User> optionalUser2 =userRep.findById(userVo1.getId());
            staff.setUser(optionalUser2.get());
            staff.setTask(task);
            staffList.add(staff);
        }

        ProjectVo projVo = taskVo.getProjectVo();
        Optional<Project> optionalProj = projectRep.findById(projVo.getId());
        task.setProject(optionalProj.get());

        task.setLabels(labelList);
        task.setStaffs(staffList);

        task.setProposer(proposer);
        task.setAuditor(auditor);
        task.setCreatTime(new Date());
        task.setTaskState(taskVo.getTaskState());

        taskRep.save(task);
        return Result.setResult(Result.SUCCESS,"任务发布成功");

    }


    /**
     * 获取所有用户信息
     *
     * @return
     */
    @Override
    public List<UserVo> getAllUsers() {
        Sort sort = new Sort(Sort.Direction.DESC, "name");
        List<User> users =userRep.findAll(sort);
        return UserVo.userToVo(users);
    }


    /**
     * 获取所有项目信息
     *
     * @return
     */
    @Override
    public List<ProjectVo> getAllProjects() {
        List<Project> projs =projectRep.findAll();
        return ProjectVo.projectToVo(projs);
    }

    /**
     * 获取所有标签
     *
     * @return
     */
    @Override
    public List<LabelVo> getAllLabels() {
        Sort sort = new Sort(Sort.Direction.DESC, "frequency");
        List<Label> labels =labelRep.findAll(sort);
        return LabelVo.labelToVo(labels);
    }


    /**
     * 用户激活
     * 判断 手机号与验证码是否对应
     * 判断User.telephone且User.name在数据库里是否有对应已录数据，有则存入User.weChat，无则激活失败		--激活过程
     *
     * @param userVo
     * @return
     */
    @Override
    public Result activateUser( UserVo userVo) {

        String telephone =userVo.getTelephone();
        User user =userRep.findByTelephone(telephone);



        Integer msgCode =userVo.getMsgCode();
        Integer randomCode = (Integer) redisService.get(telephone);
        logger.info(randomCode+"_"+msgCode+"-");


        if (user != null && msgCode.equals(randomCode)){
           user.setWeChat(userVo.getWeChat());
           userRep.save(user);
           UserVo uVo = new UserVo();
           AbstractMyBeanUtils.copyProperties(user, uVo);
           return Result.setResult(Result.SUCCESS,"用户激活成功", JsonUtil.objectToJson(uVo));
        }else {
            return Result.setResult(Result.ERROR,"用户激活失败");
        }

    }

    /**
     * 查询所有未定向任务列表，按任务创建时间排序
     *
     * @return
     */
    @Override
    public List<TaskVo> queryTaskByUnOrient() {
        Sort sort = new Sort(Sort.Direction.DESC, "creatTime");
        List<Staff> staffs = null;
        List<Task> tasks=taskRep.findByOrientAndTaskStateAndStaffsOrderByCreatTimeDesc(TaskVo.UN_ORIENT, TaskVo.WAIT_CLAIM, staffs);

        return TaskVo.convertTask(tasks);
    }

    /**
     * 短信验证
     *
     * @param phone
     * @return
     */
    @Override
    public Result getMsgCode(String phone) {
        redisService.remove(phone);

        DefaultProfile profile = DefaultProfile.getProfile("default", Message.ACCESSKEYID, Message.SECRET);
        IAcsClient client = new DefaultAcsClient(profile);


        CommonRequest request = new CommonRequest();

        request.setMethod(MethodType.POST);
        request.setDomain(Message.DOMAIN);
        request.setVersion("2017-05-25");
        request.setAction(Message.ACTION);
        request.putQueryParameter("PhoneNumbers", phone);
        request.putQueryParameter("SignName", Message.SIGNNAME);
        request.putQueryParameter("TemplateCode", Message.TEMP);

        int random = (int) ((Math.random()*9+1)*100000);
        request.putQueryParameter("TemplateParam", "{ \"code\":\"" + random + "\"}");

        try {
            CommonResponse response = client.getCommonResponse(request);


            redisService.set(phone,random);

            final Timer timer = new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    redisService.remove(phone);
                    timer.cancel();
                }
            }, 5 * 60 * 1000);

        } catch (ServerException e) {
            e.printStackTrace();
        } catch (ClientException e) {
            e.printStackTrace();
        }


        return Result.setResult(Result.SUCCESS,"成功");
    }

    /**
     * 验证登录
     *
     * @param openId
     * @return
     */
    @Override
    public Result login(String openId) {
        User user =userRep.findByWeChat(openId);
        if (user!=null){
            UserVo userVo = new UserVo();
            AbstractMyBeanUtils.copyProperties(user, userVo);
            return Result.setResult(Result.SUCCESS,"该用户已激活", JsonUtil.objectToJson(userVo));
        }else {
            return Result.setResult(Result.ERROR,"该用户不存在");
        }

    }

    /**
     * 通过User.weChat查询用户下所有不同状态的任务数量的统计		--我的页面
     *
     * @param userId
     * @param role ("proposer") （"auditor"） （"staff"）
     * @return
     */
    @Override
    public List<TaskVo> getTaskByUserAndState(String userId, String role) {
        Optional<User> userOpt= userRep.findById(userId);
        if (!userOpt.isPresent()) {
            return Lists.newArrayList();
        }
        User user = userOpt.get();
        List<TaskVo> taskVos =Lists.newArrayList();

        List<Proposer> proposers =proposerRep.findByUser(user);
        List<Auditor> auditors =auditorRep.findByUser(user);
        List<Staff> staffList =staffRep.findByUser(user);
        //查询该用户下处于申请角色下的任务
        if (UserVo.PROPOSER.equals(role)) {
            if (proposers != null && !proposers.isEmpty()) {
                for (Proposer proposer : proposers) {
                    Task task = proposer.getTask();
                    TaskVo taskVo = new TaskVo();
                    AbstractMyBeanUtils.copyProperties(task, taskVo);
                    taskVos.add(taskVo);
                }
            }
        }else if (UserVo.AUDITOR.equals(role)){
                //查询该用户处于 审核角色下的任务
                if (auditors!=null && !auditors.isEmpty()){
                    for (Auditor auditor:auditors){
                        Task task =auditor.getTask();
                        TaskVo taskVo =new TaskVo();
                        AbstractMyBeanUtils.copyProperties(task,taskVo);
                        taskVos.add(taskVo);
                    }
                }
            }else {
                 // 查询该角色处于成员角色下的任务
                if (staffList!=null && !staffList.isEmpty()){
                    for (Staff staff:staffList){
                        Task task = staff.getTask();
                        if (!TaskVo.TASK_DOING.equals(task.getTaskState())
                                && !TaskVo.COMMIT_REJECTED.equals(task.getTaskState())
                                && !TaskVo.EVALUATING.equals(task.getTaskState())
                                && !TaskVo.EXPERT_EVALUATING.equals(task.getTaskState())
                                && !TaskVo.DONE.equals(task.getTaskState())) {
                            continue;
                        }
                        TaskVo taskVo =new TaskVo();
                        AbstractMyBeanUtils.copyProperties(task,taskVo);
                        taskVos.add(taskVo);
                    }
                }
            }
        return taskVos;
    }

    /**
     * 根据User.weChat查询User资料信息
     *
     * @param openId
     * @return
     */
    @Override
    public UserVo getUserInfo(String openId) {
        User user =userRep.findByWeChat(openId);
        UserVo userVo =new UserVo();
        AbstractMyBeanUtils.copyProperties(user,userVo);
        return userVo;
    }

    /**
     * 传入Task.id与User.id进行承接人绑定		--任务认领
     *
     * @param taskId
     * @param userId
     * @return
     */
    @Override
    public Result bindingTask(String taskId, String userId) {
        Optional<Task> optionalTask =taskRep.findById(taskId);
        Optional<User> optionalUser =userRep.findById(userId);

        if (null == taskId || taskId.isEmpty() || !optionalTask.isPresent()) {
            return Result.setResult(Result.ERROR,"任务不存在");
        }
        if (null == userId || userId.isEmpty() || !optionalUser.isPresent()) {
            return Result.setResult(Result.ERROR,"用户不存在");
        }
        Task task = optionalTask.get();
        task.setTaskState(TaskVo.TASK_DOING);
        Staff staff =new Staff();
        staff.setUser(optionalUser.get());
        staff.setTask(task);
        task.getStaffs().add(staff);
        staffRep.save(staff);
        taskRep.save(task);

        return Result.setResult(Result.SUCCESS,"承接任务成功");
    }

    /**
     * 根据Task.id查询Task资料信息
     *
     * @param taskId
     * @return
     */
    @Override
    public TaskVo getTaskInfo(String taskId) {
        Optional<Task> optionalTask =taskRep.findById(taskId);
        Proposer proposer =proposerRep.findByTask(optionalTask.get());
        ProposerVo proposerVo =new ProposerVo();
        UserVo userVo = new UserVo();
        AbstractMyBeanUtils.copyProperties(proposer,proposerVo);
        AbstractMyBeanUtils.copyProperties(proposer.getUser(),userVo);
        proposerVo.setUserVo(userVo);
        Auditor auditor =auditorRep.findByTask(optionalTask.get());
        AuditorVo auditorVo =new AuditorVo();
        userVo = new UserVo();
        AbstractMyBeanUtils.copyProperties(auditor,auditorVo);
        AbstractMyBeanUtils.copyProperties(auditor.getUser(),userVo);
        auditorVo.setUserVo(userVo);
        TaskVo taskVo =new TaskVo();
        Project project =projectRep.findByTasks(optionalTask.get());
        ProjectVo projectVo =new ProjectVo();
        AbstractMyBeanUtils.copyProperties(project,projectVo);
        AbstractMyBeanUtils.copyProperties(optionalTask.get(),taskVo);
        List<Staff> staffList =staffRep.findByTask(optionalTask.get());
        List<StaffVo> staffVos =StaffVo.staffToVo(staffList);
        List<Label> labelList = labelRep.findByTasks(optionalTask.get());
        List<LabelVo> labelVos = LabelVo.labelToVo(labelList);

        taskVo.setProposerVo(proposerVo);
        taskVo.setAuditorVo(auditorVo);
        taskVo.setProjectVo(projectVo);
        taskVo.setStaffVos(staffVos);
        taskVo.setLabelVos(labelVos);


        return taskVo;
    }

    /**
     *  更新任务状态
     * @return
     */
    @Override
    public Result updateTaskState(TaskVo taskVo) {
        Task task = taskRep.findById(taskVo.getId()).get();
        task.setComment(taskVo.getComment());
        task.setExpertComment(taskVo.getExpertComment());
        task.setReviewReason(taskVo.getReviewReason());
        task.setTaskState(taskVo.getTaskState());
        taskRep.save(task);
        return Result.setResult(Result.SUCCESS,"更新任务成功");
    }

    @Override
    public Result getOpenidByWeixinApi(String code) {
        if (null == code || code.isEmpty()) {
            return Result.setResult(Result.ERROR,"code值无效");
        }
        String secretCode = "d9a18d334e4e94c7cd57a29936e55f23";
        String appid = "wx3f3f1f1c1fcdaae3";
        Map<String, Object> paramMap = Maps.newHashMap();
        paramMap.put("js_code", code);
        paramMap.put("grant_type", "authorization_code");
        paramMap.put("secret", secretCode);
        paramMap.put("appid", appid);
        JSONObject jsonObj = HttpUtil.doGet("https://api.weixin.qq.com/sns/jscode2session", paramMap);
        return Result.setResult(Result.SUCCESS,"openid获取成功", jsonObj.toJSONString());
    }
}
