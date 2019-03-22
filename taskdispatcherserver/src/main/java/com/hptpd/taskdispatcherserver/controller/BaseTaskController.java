package com.hptpd.taskdispatcherserver.controller;

import com.hptpd.taskdispatcherserver.component.Result;
import com.hptpd.taskdispatcherserver.domain.User;
import com.hptpd.taskdispatcherserver.domain.vo.LabelVo;
import com.hptpd.taskdispatcherserver.domain.vo.ProjectVo;
import com.hptpd.taskdispatcherserver.domain.vo.TaskVo;
import com.hptpd.taskdispatcherserver.domain.vo.UserVo;
import com.hptpd.taskdispatcherserver.service.BaseTaskService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
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
    public Result getMsgCode( HttpSession session){

        return baseTaskService.getMsgCode(session,"18627082367");
    }
}
