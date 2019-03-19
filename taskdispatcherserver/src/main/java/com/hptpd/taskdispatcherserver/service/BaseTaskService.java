package com.hptpd.taskdispatcherserver.service;


import com.hptpd.taskdispatcherserver.component.Result;
import com.hptpd.taskdispatcherserver.domain.vo.TaskVo;
import com.hptpd.taskdispatcherserver.domain.vo.UserVo;

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

}
