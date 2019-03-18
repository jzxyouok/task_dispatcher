package com.hptpd.taskdispatcherserver.service.impl;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.hptpd.taskdispatcherserver.common.util.AbstractMyBeanUtils;
import com.hptpd.taskdispatcherserver.component.Result;
import com.hptpd.taskdispatcherserver.domain.Role;
import com.hptpd.taskdispatcherserver.domain.Task;
import com.hptpd.taskdispatcherserver.domain.User;
import com.hptpd.taskdispatcherserver.domain.vo.RoleVo;
import com.hptpd.taskdispatcherserver.domain.vo.TaskVo;
import com.hptpd.taskdispatcherserver.domain.vo.UserVo;
import com.hptpd.taskdispatcherserver.repository.RoleRep;
import com.hptpd.taskdispatcherserver.repository.TaskRep;
import com.hptpd.taskdispatcherserver.repository.UserRep;
import com.hptpd.taskdispatcherserver.service.BaseTaskService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Optional;
import java.util.Set;


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

    @Resource(name = "taskRep")
    private TaskRep taskRep;

    @Resource(name = "roleRep")
    private RoleRep roleRep;

    /**
     * 获取所以用户列表
     */
    @Override
    public List<UserVo> getUsers() {

        List<User> users =userRep.findAll();

        return UserVo.userToVo(users);
    }

    /**
     * 发布任务接口
     */
    @Override
    public Result dispatchTask(TaskVo taskVo) {


        logger.info(taskVo.toString());
        Task task = new Task();
        AbstractMyBeanUtils.copyProperties(taskVo,task);

        //的到任务的角色
        List<RoleVo> roleVos =taskVo.getRoleVos();
        List<Role> roles =Lists.newArrayList();

        task.setRoles(roles);

        for (RoleVo roleVo:roleVos){
            //获取角色的成员
            logger.info(roleVo.toString());
            List<User> users = Lists.newArrayList();
            List<UserVo> userVos =roleVo.getUserVos();
            Role role =new Role();
            //角色
            role.setRoleInfo(roleVo.getRoleInfo());

            for (UserVo userVo:userVos){
//                User user =new User();
                logger.info(userVo.toString());
//                AbstractMyBeanUtils.copyProperties(userVo,user);
                User user =userRep.findByWeChat(userVo.getWeChat());
//                user.setRoles(roles);
                logger.info(user.getId());
//                userRep.save(user);
                users.add(user);
            }
            role.setUsers(users);
            role.setTask(task);
//            roleRep.save(role);

            roles.add(role);
            roleRep.saveAll(roles);


        }


        return Result.setResult(0,"发布任务成功");
    }
}
