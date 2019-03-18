package com.hptpd.taskdispatcherserver.repository;

import com.hptpd.taskdispatcherserver.domain.Auditor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

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
}
