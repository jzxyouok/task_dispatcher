package com.hptpd.taskdispatcherserver;

import com.hptpd.taskdispatcherserver.component.Result;
import com.hptpd.taskdispatcherserver.domain.vo.*;
import com.hptpd.taskdispatcherserver.service.BaseTaskService;
import org.assertj.core.util.Lists;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.List;

/**
 * \* Created with IntelliJ IDEA.
 * \* Date: 2019-03-18 11:49
 * \*
 * \* Description:
 * \
 *
 * @author liucheng
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class TaskTest {


    @Resource(name = "baseTaskService")
    private BaseTaskService baseTaskService;

    @Test
    @Ignore
    public void usersAdd(){
        List<UserVo> userVos = Lists.newArrayList();

        UserVo userVo =new UserVo();
        userVo.setId("402880e76990a8b4016990a8c4c60000");
        userVo.setName("lc");
        userVo.setPosition("开发");
        userVo.setTelephone("176");
        userVos.add(userVo);

        UserVo userVo1 =new UserVo();
        userVo1.setId("402880e76990a8b4016990a8c4f20001");
        userVo1.setName("lw");
        userVo1.setPosition("开发");
        userVo1.setTelephone("175");
        userVos.add(userVo1);


        TaskVo taskVo =new TaskVo();
        taskVo.setWorkload("1");
        taskVo.setTaskDescription("任务描述");
        taskVo.setTaskName("名称");


        ProposerVo proposerVo =new ProposerVo();
        proposerVo.setUserVo(userVo1);
        taskVo.setProposerVo(proposerVo);

        AuditorVo auditorVo =new AuditorVo();
        auditorVo.setUserVo(userVo);
        taskVo.setAuditorVo(auditorVo);

        List<StaffVo> staffVos =Lists.newArrayList();
        StaffVo staffVo =new StaffVo();
        staffVo.setUserVo(userVo);
        staffVos.add(staffVo);

        StaffVo staffVo1 =new StaffVo();
        staffVo1.setUserVo(userVo1);
        staffVos.add(staffVo1);
        taskVo.setStaffVos(staffVos);

        baseTaskService.dispatchTask(taskVo);
    }
    @Test
    @Ignore
    public void test(){
        baseTaskService.getTaskByUserAndState("doctor彭",UserVo.AUDITOR);
    }

    @Test
    public void getUserOutputValue() {
        Result result = baseTaskService.getUserOutputValue("14", "2019-04");
        System.out.println(result.getData());
    }
}
