package com.hptpd.taskdispatcherserver.repository;

import com.hptpd.taskdispatcherserver.domain.Proposer;
import com.hptpd.taskdispatcherserver.domain.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository("proposerRep")
public interface ProposerRep extends JpaRepository<Proposer,String> {

    Proposer findByTask(Task task);
}
