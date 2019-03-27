package com.hptpd.taskdispatcherserver.repository;

import com.hptpd.taskdispatcherserver.domain.Staff;
import com.hptpd.taskdispatcherserver.domain.Task;
import com.hptpd.taskdispatcherserver.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * \* Created with IntelliJ IDEA.
 * \* Date: 2019-03-18 14:15
 * \*
 * \* Description:
 * \
 *
 * @author liucheng
 */
@Repository("staffRep")
public interface StaffRep extends JpaRepository<Staff, String> {

    /**
     * 通过任务查找 成员
     * @param task
     * @return
     */
    List<Staff> findByTask(Task task);


    /**
     * 通过用户查询
     * @param user
     * @return
     */
    List<Staff> findByUser(User user);
}
