package com.hptpd.taskdispatcherserver.repository;

import com.hptpd.taskdispatcherserver.domain.Auditor;
import com.hptpd.taskdispatcherserver.domain.Proposer;
import com.hptpd.taskdispatcherserver.domain.Staff;
import com.hptpd.taskdispatcherserver.domain.Task;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Map;

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

    /**
     * 获取任务，通过承接者与任务状态
     * @param staffs List<Staff>
     * @param taskStates List<String>
     * @return List<Task>
     */
    List<Task> findByStaffsInAndTaskStateIn(List<Staff> staffs, List<String> taskStates);

    /**
     * fdsfad
     * @return List<Map<String, Object>>
     */
    @Query(nativeQuery = true, value = "select sum(t1.real_workload) as workloadSum, t1.user_user_id as userId  from (select t1.*,t2.* from task t1 left join staff t2 on t1.task_id = t2.task_task_id where t1.task_state = :taskState and t1.done_time >= :minTime and t1.done_time <= :maxTime) t1 group by user_user_id order by workloadsum desc")
    List<Map<String, Object>> countAllUserWorkloadByMonth(@Param("taskState") String taskState, @Param("minTime") Date minTime, @Param("maxTime") Date maxTime);
 }
