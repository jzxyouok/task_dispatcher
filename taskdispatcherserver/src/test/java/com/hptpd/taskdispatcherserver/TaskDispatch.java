package com.hptpd.taskdispatcherserver;

import com.hptpd.taskdispatcherserver.domain.User;
import com.hptpd.taskdispatcherserver.domain.vo.RoleVo;
import com.hptpd.taskdispatcherserver.domain.vo.TaskVo;
import com.hptpd.taskdispatcherserver.domain.vo.UserVo;
import com.hptpd.taskdispatcherserver.repository.UserRep;
import com.hptpd.taskdispatcherserver.service.BaseTaskService;
import org.assertj.core.util.Lists;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.List;

/**
 * \* Created with IntelliJ IDEA.
 * \* Date: 2019-03-15 10:38
 * \*
 * \* Description:
 * \
 *
 * @author liucheng
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class TaskDispatch {

    @Resource(name = "baseTaskService")
    private BaseTaskService baseTaskService;

    @Resource(name = "userRep")
    private UserRep userRep;

    @Test
    public void taskDis(){
        List<UserVo> userVos = Lists.newArrayList();
        UserVo userVo =new UserVo();
        userVo.setWeChat("liu");

        userVos.add(userVo);

        List<UserVo> userVoList = Lists.newArrayList();
        UserVo userVo1 =new UserVo();
        userVo1.setWeChat("liu");
        userVo1.setName("刘晨21");

        userVoList.add(userVo1);

//        UserVo userVo2 =new UserVo();
//        userVo2.setWeChat("long");
//        userVo2.setName("龙威");
//        userVoList.add(userVo2);


        List<RoleVo> roleVos =Lists.newArrayList();
        RoleVo roleVo =new RoleVo();
        roleVo.setRoleInfo("审核人");
        roleVo.setUserVos(userVos);
        roleVos.add(roleVo);

        RoleVo roleVo1 =new RoleVo();
        roleVo1.setRoleInfo("承接人");
        roleVo1.setUserVos(userVoList);
        roleVos.add(roleVo1);


        TaskVo taskVo =new TaskVo();
        taskVo.setTaskName("测试任务名称2");
        taskVo.setTaskDescription("任务描述2");
        taskVo.setWorkload("12月");
        taskVo.setRoleVos(roleVos);

       baseTaskService.dispatchTask(taskVo);

    }

//    @Test
//    public void usersAdd(){
//        User user =new User();
//        user.setPosition("开发");
//        user.setName("刘晨");
//        user.setWeChat("liu");
//
//        userRep.save(user);
//    }
}
