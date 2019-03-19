package com.hptpd.taskdispatcherserver.controller;

import com.hptpd.taskdispatcherserver.component.Result;
import com.hptpd.taskdispatcherserver.domain.User;
import com.hptpd.taskdispatcherserver.domain.vo.TaskVo;
import com.hptpd.taskdispatcherserver.domain.vo.UserVo;
import com.hptpd.taskdispatcherserver.service.BaseTaskService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

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
}
