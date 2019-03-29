package com.hptpd.taskdispatcherserver.service;


import com.hptpd.taskdispatcherserver.component.Result;
import com.hptpd.taskdispatcherserver.domain.Label;
import com.hptpd.taskdispatcherserver.domain.Task;
import com.hptpd.taskdispatcherserver.domain.vo.LabelVo;
import com.hptpd.taskdispatcherserver.domain.vo.ProjectVo;
import com.hptpd.taskdispatcherserver.domain.vo.TaskVo;
import com.hptpd.taskdispatcherserver.domain.vo.UserVo;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 *
 *
 * @author apple
 */

public interface BaseTaskService {

    /**
     *  发布任务
     * @param taskVo
     * @return
     */
    Result dispatchTask(TaskVo taskVo);


    /**
     *  获取所有用户信息
     * @return
     */
    List<UserVo> getAllUsers();

    /**
     *  获取所有项目信息
     * @return
     */
    List<ProjectVo> getAllProjects();

    /**
     *  获取所有标签
     * @return
     */
    List<LabelVo> getAllLabels();

    /**
     *  用户激活
     *  判断User.telephone且User.name在数据库里是否有对应已录数据，有则存入User.weChat，无则激活失败		--激活过程
     * @param userVo
     * @param session
     * @return
     */
    Result activateUser(HttpSession session,UserVo userVo);


    /**
     * 查询所有未定向任务列表，按任务创建时间排序
     * @return
     */
     List<TaskVo> queryTaskByUnOrient();


    /**
     * 短信验证
     * @param phone
     * @param session
     * @return
     */
     Result getMsgCode(HttpSession session,String phone);


    /**
     *  验证登录
     * @param openId
     * @return
     */
     Result login(String openId);

    /**
     * 通过User.weChat查询用户下所有不同状态的任务数量的统计		--我的页面
     *
     * @param userId
     * @param role ("proposer") （"auditor"） （"staff"）
     * @return
     */
     List<TaskVo> getTaskByUserAndState(String userId,String role);


    /**
     * 根据User.weChat查询User资料信息
     * @param openId
     * @return
     */
    UserVo getUserInfo(String openId);


    /**
     * 传入Task.id与User.id进行承接人绑定		--任务认领
     * @param taskId
     * @param userId
     * @return
     */
    Result bindingTask(String taskId,String userId);


    /**
     * 根据Task.id查询Task资料信息
     * @param taskId
     * @return
     */
    TaskVo getTaskInfo(String taskId);

}
