package com.hptpd.taskdispatcherserver.service.impl;


import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.google.common.collect.Lists;
import com.hptpd.taskdispatcherserver.common.util.AbstractMyBeanUtils;
import com.hptpd.taskdispatcherserver.component.Result;
import com.hptpd.taskdispatcherserver.domain.*;
import com.hptpd.taskdispatcherserver.domain.vo.*;
import com.hptpd.taskdispatcherserver.repository.*;
import com.hptpd.taskdispatcherserver.service.BaseTaskService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
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
        task.setStaffs(staffList);

        task.setProposer(proposer);
        task.setAuditor(auditor);

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
        List<Label> labels =labelRep.findAll();
        return LabelVo.labelToVo(labels);
    }

    /**
     * 用户激活
     * 判断User.telephone且User.name在数据库里是否有对应已录数据，有则存入User.weChat，无则激活失败		--激活过程
     *
     * @param userVo
     * @return
     */
    @Override
    public Result activateUser(UserVo userVo) {

        String telephone =userVo.getTelephone();
        User user =userRep.findByTelephone(telephone);
        if (user != null){
           user.setWeChat(userVo.getWeChat());
           userRep.save(user);
           return Result.setResult(Result.SUCCESS,"用户激活成功");
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
        List<Task> tasks=taskRep.findByOrient(TaskVo.UN_ORIENT,sort);

        return TaskVo.convertTask(tasks);
    }

    /**
     * 短信验证
     *
     * @param session
     * @return
     */
    @Override
    public Result getMsgCode(HttpSession session,String phone) {

        session.removeAttribute(phone);
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
            System.out.println(response.getData());
            session.setAttribute(phone, random);

            final Timer timer = new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    session.removeAttribute(phone);
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
            return Result.setResult(Result.SUCCESS,"该用户已激活");
        }else {
            return Result.setResult(Result.ERROR,"该用户不存在");
        }

    }


}
