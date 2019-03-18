package com.hptpd.taskdispatcherserver.service;


import com.hptpd.taskdispatcherserver.component.Result;
import com.hptpd.taskdispatcherserver.domain.vo.TaskVo;

public interface BaseTaskService {


    Result DispatchTask(TaskVo taskVo);

}
