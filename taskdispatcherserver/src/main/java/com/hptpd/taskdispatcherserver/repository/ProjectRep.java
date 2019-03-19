package com.hptpd.taskdispatcherserver.repository;

import com.hptpd.taskdispatcherserver.domain.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * \* Created with IntelliJ IDEA.
 * \* Date: 2019-03-19 18:50
 * \*
 * \* Description:
 * \
 *
 * @author longwei
 */
@Repository("projectRep")
public interface ProjectRep extends JpaRepository<Project,String> {
}
