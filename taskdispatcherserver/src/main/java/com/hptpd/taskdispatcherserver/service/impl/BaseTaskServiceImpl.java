package com.hptpd.taskdispatcherserver.service.impl;


import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.hptpd.taskdispatcherserver.common.util.AbstractMyBeanUtils;
import com.hptpd.taskdispatcherserver.component.Result;
import com.hptpd.taskdispatcherserver.domain.*;
import com.hptpd.taskdispatcherserver.domain.vo.*;
import com.hptpd.taskdispatcherserver.repository.*;
import com.hptpd.taskdispatcherserver.service.BaseTaskService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Sort;
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

    @Resource(name = "taskRep")
    private TaskRep taskRep;

    @Resource(name = "userRep")
    private UserRep userRep;

    @Resource(name = "proposerRep")
    private ProposerRep proposerRep;

    @Resource(name = "auditorRep")
    private AuditorRep auditorRep;

    @Resource(name = "staffRep")
    private StaffRep staffRep;

    @Resource(name = "projectRep")
    private ProjectRep projectRep;

    @Override
    public Result dispatchTask(TaskVo taskVo) {

        Task task =new Task();
        AbstractMyBeanUtils.copyProperties(taskVo,task);

        ProposerVo proposerVo =taskVo.getProposerVo();
        Proposer proposer =new Proposer();
        UserVo userVo =proposerVo.getUserVo();
        Optional<User> optionalUser =userRep.findById(userVo.getId());
        proposer.setUser(optionalUser.get());
        proposer.setTask(task);


        AuditorVo auditorVo =taskVo.getAuditorVo();
        Auditor auditor =new Auditor();
        logger.info(auditorVo.toString());
        Optional<User> optionalUser1 =userRep.findById(auditorVo.getUserVo().getId());
        auditor.setUser(optionalUser1.get());
        auditor.setTask(task);


        List<StaffVo> staffVos =taskVo.getStaffVos();

        List<Staff> staffList =Lists.newArrayList();
        for (StaffVo staffVo:staffVos){
            Staff staff =new Staff();
            UserVo userVo1 =staffVo.getUserVo();
            Optional<User> optionalUser2 =userRep.findById(userVo1.getId());
            staff.setUser(optionalUser2.get());
            staff.setTask(task);
            staffList.add(staff);


        }
        task.setStaffs(staffList);

        task.setProposer(proposer);
        task.setAuditor(auditor);

        taskRep.save(task);
        return Result.setResult(Result.SUCCESS,"任务发布成功");

    }


    /**
     * 获取所有用户信息
     *
     * @return
     */
    @Override
    public List<UserVo> getAllUsers() {
        Sort sort = new Sort(Sort.Direction.DESC, "name");
        List<User> users =userRep.findAll(sort);
        return UserVo.userToVo(users);
    }


    /**
     * 获取所有项目信息
     *
     * @return
     */
    @Override
    public List<ProjectVo> getAllProjects() {
        List<Project> projs =projectRep.findAll();
        return ProjectVo.projectToVo(projs);
    }
}
