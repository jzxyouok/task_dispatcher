package com.hptpd.taskdispatcherserver.repository;

import com.hptpd.taskdispatcherserver.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * \* Created with IntelliJ IDEA.
 * \* Date: 2019-03-14 17:08
 * \*
 * \* Description:
 * \
 *
 * @author liucheng
 */
@Repository("userRep")
public interface UserRep extends JpaRepository<User,String> {

    User findByWeChat(String weChat);

}
