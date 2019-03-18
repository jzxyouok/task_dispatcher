package com.hptpd.taskdispatcherserver.repository;

import com.hptpd.taskdispatcherserver.domain.Staff;
import com.hptpd.taskdispatcherserver.domain.Task;
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

    List<Staff> findByTask(Task task);
}
