package com.hptpd.taskdispatcherserver.repository;

import com.hptpd.taskdispatcherserver.domain.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("roleRep")
public interface RoleRep extends JpaRepository<Role,String> {


}
