package com.hptpd.taskdispatcherserver.service.impl;

import com.hptpd.taskdispatcherserver.component.Result;
import com.hptpd.taskdispatcherserver.domain.User;
import com.hptpd.taskdispatcherserver.domain.vo.UserVo;
import com.hptpd.taskdispatcherserver.repository.UserRep;
import com.hptpd.taskdispatcherserver.service.BaseTaskService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;


/**
 * \* Created with IntelliJ IDEA.
 * \* Date: 2019-03-14 17:04
 * \*
 * \* Description:
 * \
 *
 * @author liucheng
 */
@Service("baseTaskService")
public class BaseTaskServiceImpl implements BaseTaskService {

    private Logger logger = LoggerFactory.getLogger(BaseTaskServiceImpl.class);

    @Resource(name = "userRep")
    private UserRep userRep;
    /**
     * 获取所以用户列表
     */
    @Override
    public List<UserVo> getUsers() {

        List<User> users =userRep.findAll();

        return UserVo.userToVo(users);
    }
}
