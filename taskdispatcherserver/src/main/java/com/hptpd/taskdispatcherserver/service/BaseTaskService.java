package com.hptpd.taskdispatcherserver.service;

import com.hptpd.taskdispatcherserver.component.Result;
import com.hptpd.taskdispatcherserver.domain.vo.UserVo;

import java.util.List;

public interface BaseTaskService {

    /**
     *  获取所以用户列表
     */
    List<UserVo> getUsers();
}
