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
     * @return
     */
    Result activateUser(UserVo userVo);


    /**
     * 查询所有未定向任务列表，按任务创建时间排序
     * @return
     */
     List<TaskVo> queryTaskByUnOrient();


    /**
     * 短信验证
     * @return
     */
     Result getMsgCode(HttpSession session,String phone);


}
