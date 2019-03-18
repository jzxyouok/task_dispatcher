package com.hptpd.taskdispatcherserver;

import com.hptpd.taskdispatcherserver.domain.*;
import com.hptpd.taskdispatcherserver.domain.vo.*;
import com.hptpd.taskdispatcherserver.repository.*;
import com.hptpd.taskdispatcherserver.service.BaseTaskService;
import com.hptpd.taskdispatcherserver.service.impl.BaseTaskServiceImpl;
import org.assertj.core.util.Lists;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.List;
import java.util.Optional;

/**
 * \* Created with IntelliJ IDEA.
 * \* Date: 2019-03-15 21:27
 * \*
 * \* Description:
 * \
 *
 * @author liucheng
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class UserTest {
    private Logger logger = LoggerFactory.getLogger(UserTest.class);

    @Resource(name = "baseTaskService")
    private BaseTaskService baseTaskService;

    @Resource(name = "userRep")
    private UserRep userRep;

    @Resource(name = "proposerRep")
    private ProposerRep proposerRep;

    @Resource(name = "auditorRep")
    private AuditorRep auditorRep;

    @Resource(name = "staffRep")
    private StaffRep staffRep;

    @Resource(name = "taskRep")
    private TaskRep taskRep;

    @Test
    public void usersAdd(){



        List<User> users =Lists.newArrayList();
        User user =new User();
        user.setWeChat("lc");
        user.setName("刘成");
        user.setPosition("开发");

        users.add(user);


        User user1 =new User();
        user1.setWeChat("lw");
        user1.setName("龙威");
        user1.setPosition("开发");
        users.add(user1);

        User user2 =new User();
        user2.setWeChat("ymh");
        user2.setName("要梦回");
        user2.setPosition("开发");
        users.add(user2);

        User user3 =new User();
        user3.setWeChat("doctor彭");
        user3.setName("doctor");
        user3.setPosition("技术");
        users.add(user3);

        userRep.saveAll(users);



    }
//    @Test
//    public void getTest(){
//       Optional<Task> task =taskRep.findById("402809e469908e2a0169908e356b0000");
////       List<Staff> staffList =staffRep.findByTask(task.get());
////       System.out.println(staffList.size()+"");
//
//       Proposer proposer =proposerRep.findByTask(task.get());
//       logger.info(proposer.getName());
//
//    }
}
