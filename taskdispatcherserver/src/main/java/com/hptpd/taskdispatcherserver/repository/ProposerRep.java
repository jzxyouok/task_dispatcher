package com.hptpd.taskdispatcherserver.repository;

import com.hptpd.taskdispatcherserver.domain.Proposer;
import com.hptpd.taskdispatcherserver.domain.Task;
import com.hptpd.taskdispatcherserver.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * @author apple
 */
@Repository("proposerRep")
public interface ProposerRep extends JpaRepository<Proposer,String> {

    /**
     * 通过任务查询 申请
     * @param task
     * @return
     */
    Proposer findByTask(Task task);

    /**
     * 通过用户查询
     * @param user
     * @return
     */
    List<Proposer> findByUser(User user);
}
