package com.hptpd.taskdispatcherserver.repository;

import com.hptpd.taskdispatcherserver.domain.Auditor;
import com.hptpd.taskdispatcherserver.domain.Proposer;
import com.hptpd.taskdispatcherserver.domain.Staff;
import com.hptpd.taskdispatcherserver.domain.Task;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * \* Created with IntelliJ IDEA.
 * \* Date: 2019-03-14 15:59
 * \*
 * \* Description:
 * \
 *
 * @author liucheng
 */

@Repository("taskRep")
public interface TaskRep extends JpaRepository<Task,String> {


    /**
     * 查询 排序
     * @param orient
     * @param sort
     * @return
     */
    List<Task> findByOrient(boolean orient, Sort sort);

    List<Task> findByOrientAndTaskStateAndStaffsOrderByCreatTimeDesc(boolean orient, String taskState, List<Staff> staffs);
    /**
     * 申请角色的任务
     * @param proposer
     * @return
     */
    Task findByProposer(Proposer proposer);


    /**
     * 审核者 角色的任务
     * @param auditor
     * @return
     */
    Task findByAuditor(Auditor auditor);

    /**
     * 处于成员角色的任务
     * @param staff
     * @return
     */
    Task findByStaffs(Staff staff);


 }
