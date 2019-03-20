package com.hptpd.taskdispatcherserver.repository;

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
 }
