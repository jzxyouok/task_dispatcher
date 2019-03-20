package com.hptpd.taskdispatcherserver.repository;

import com.hptpd.taskdispatcherserver.domain.Label;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


/**
 * @author lc
 */
@Repository("labelRep")
public interface LabelRep extends JpaRepository<Label,String> {
}
