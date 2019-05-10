package com.hptpd.taskdispatcherserver.controller;

import com.google.common.collect.Maps;
import com.hptpd.taskdispatcherserver.common.util.IpUtil;
import com.hptpd.taskdispatcherserver.common.util.JsonUtil;
import com.hptpd.taskdispatcherserver.component.Result;
import com.hptpd.taskdispatcherserver.domain.vo.LabelVo;
import com.hptpd.taskdispatcherserver.domain.vo.ProjectVo;
import com.hptpd.taskdispatcherserver.domain.vo.TaskVo;
import com.hptpd.taskdispatcherserver.domain.vo.UserVo;
import com.hptpd.taskdispatcherserver.service.BaseTaskService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * \* Created with IntelliJ IDEA.
 * \* Date: 2019-03-14 17:22
 * \*
 * \* Description:
 * \
 *
 * @author liucheng
 */
@RestController
@RequestMapping("/base_task")
public class BaseTaskController {

    @Resource(name = "baseTaskService")
   private BaseTaskService baseTaskService;

    @RequestMapping(value = "/users", method = RequestMethod.GET)
    public List<UserVo> getAllUsers(){
        List<UserVo> userVos =baseTaskService.getAllUsers();
        return  userVos;
    }

    /**
     *  发布任务
     * @return
     */

    @RequestMapping(value = "/dispatchTask", method = RequestMethod.POST)
    public Result dispatchTask(@RequestBody TaskVo taskVo){

        Result result =baseTaskService.dispatchTask(taskVo);
        return result;

    }

    /**
     *  获取所有项目信息
     * @return
     */
    @RequestMapping(value = "/projects", method = RequestMethod.GET)
    public List<ProjectVo> getAllProjects(){
        return baseTaskService.getAllProjects();
    }

    /**
     * 获取所有标签信息
     * @return
     */
    @RequestMapping(value = "/labels", method = RequestMethod.GET)
    public List<LabelVo> getAllLabels(){
        return baseTaskService.getAllLabels();
    }

    /**
     * 判断User.telephone且User.name在数据库里是否有对应已录数据，有则存入User.weChat，无则激活失败		--激活过程
     * @return
     */
    @RequestMapping(value = "/activate", method = RequestMethod.POST)
    public Result activateUser(@RequestBody UserVo userVo){

        return  baseTaskService.activateUser(userVo);
    }

    /**
     * 询所有未定向任务列表，按任务创建时间排序
     * @return
     */

    @RequestMapping(value = "/unOrient/tasks", method = RequestMethod.GET)
    public List<TaskVo> taskVos() {
        return baseTaskService.queryTaskByUnOrient();
    }


    @RequestMapping(value = "/msgCode", method = RequestMethod.GET)
    public Result getMsgCode(@RequestParam String phone){

        return baseTaskService.getMsgCode(phone);
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public Result login(@RequestParam String openId){
      return baseTaskService.login(openId);
    }


    /**
     * 通过User.weChat查询用户下所有不同状态的任务数量的统计		--我的页面
     *
     * @param userId
     * @param role ("已发布") （"已审核"）("承接人")
     * @return
     */
    @RequestMapping(value = "/state/tasks", method = RequestMethod.GET)
    public List<TaskVo> getTaskByUserAndState(@RequestParam String userId,String role){
        return baseTaskService.getTaskByUserAndState(userId,role);
    }

    /**
     * 根据User.weChat查询User资料信息
     * @param openId
     * @return
     */
    @RequestMapping(value = "/userInfo", method = RequestMethod.GET)
    public UserVo getUserInfo(@RequestParam String openId){
        return baseTaskService.getUserInfo(openId);

    }

    /**
     * 传入Task.id与User.id进行承接人绑定		--任务认领
     * @param taskId
     * @param userId
     * @return
     */

    @RequestMapping(value = "/binding/task", method = RequestMethod.GET)
    public Result bindingTask(@RequestParam String taskId,String userId){
        return baseTaskService.bindingTask(taskId,userId);
    }

    /**
     * 根据Task.id查询Task资料信息
     * @param taskId
     * @return
     */
    @RequestMapping(value = "/taskInfo", method = RequestMethod.GET)
    public TaskVo getTaskInfo(@RequestParam  String taskId){
        return  baseTaskService.getTaskInfo(taskId);
    }

    /**
     *  更新任务
     * @return
     */
    @RequestMapping(value = "/updateTaskState", method = RequestMethod.POST)
    public Result updateTaskState(@RequestBody TaskVo taskVo){

        Result result =baseTaskService.updateTaskState(taskVo);
        return result;

    }

    @RequestMapping(value = "/getOpenidByWeixinApi", method = RequestMethod.GET)
    public Result getOpenidByWeixinApi(@RequestParam String code) {
        return baseTaskService.getOpenidByWeixinApi(code);
    }

    @RequestMapping(value = "/getUserOutputValue", method = RequestMethod.GET)
    public Result getUserOutputValue(@RequestParam String userId) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM");
        return baseTaskService.getUserOutputValue(userId, simpleDateFormat.format(new Date()));
    }

    /**
     *
     * @param request HttpServletRequest
     * @return Result
     */
    @RequestMapping(value = "/getWeiXinRequestIp", method = RequestMethod.GET)
    public Result getWeiXinRequestIp(HttpServletRequest request) {
        Map<String, Object> destMap = Maps.newLinkedHashMap();
        String referer = request.getHeader("Referer");
        destMap.put("referer", referer);
        destMap.put("requestIp", IpUtil.getRequestIpAddr(request));
        return Result.setResult(Result.SUCCESS, "调用成功", JsonUtil.objectToJson(destMap));
    }

    @RequestMapping(value = "/offerFormId", method = RequestMethod.GET)
    public Result offerFormId(@RequestParam String userId, @RequestParam String formId) {
        return baseTaskService.offerFormId(userId, formId);
    }
}
