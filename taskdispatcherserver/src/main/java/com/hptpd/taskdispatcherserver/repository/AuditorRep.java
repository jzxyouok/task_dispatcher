package com.hptpd.taskdispatcherserver.repository;

import com.hptpd.taskdispatcherserver.domain.Auditor;
import com.hptpd.taskdispatcherserver.domain.Task;
import com.hptpd.taskdispatcherserver.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * \* Created with IntelliJ IDEA.
 * \* Date: 2019-03-18 14:14
 * \*
 * \* Description:
 * \
 *
 * @author liucheng
 */
@Repository("auditorRep")
public interface AuditorRep extends JpaRepository<Auditor,String> {

    /**
     * 通过用户查询
     * @param user
     * @return
     */
    List<Auditor> findByUser(User user);


    /**
     * 通过 任务查找
     * @param task
     * @return
     */
    Auditor findByTask(Task task);

}
