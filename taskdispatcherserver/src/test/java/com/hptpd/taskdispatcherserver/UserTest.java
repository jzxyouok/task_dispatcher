package com.hptpd.taskdispatcherserver;

import com.hptpd.taskdispatcherserver.domain.User;
import com.hptpd.taskdispatcherserver.repository.UserRep;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

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


    @Resource(name = "userRep")
    private UserRep userRep;

    @Test
    public void usersAdd(){
        User user =new User();
        user.setPosition("开发");
        user.setName("刘晨");
        user.setWeChat("liu");

        userRep.save(user);
    }
}
