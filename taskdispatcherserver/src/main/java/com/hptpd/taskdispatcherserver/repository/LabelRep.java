package com.hptpd.taskdispatcherserver.repository;

import com.hptpd.taskdispatcherserver.domain.Label;
import com.hptpd.taskdispatcherserver.domain.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * @author lc
 */
@Repository("labelRep")
public interface LabelRep extends JpaRepository<Label,String> {
    /**
     * 通过任务查询
     * @param task
     * @return
     */
    List<Label> findByTasks(Task task);
}
