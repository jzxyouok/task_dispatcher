package com.hptpd.taskdispatcherserver.repository;


import com.hptpd.taskdispatcherserver.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * \* Created with IntelliJ IDEA.
 * \* Date: 2019-03-18 11:50
 * \*
 * \* Description:
 * \
 *
 * @author liucheng
 */
@Repository("userRep")
public interface UserRep extends JpaRepository<User,String> {

    /**
     * 通过电话查询
     * @param phone
     * @return
     */
    User findByTelephone(String phone);

    /**
     * 通过openId 查询
     * @param weChat
     * @return
     */
    User findByWeChat(String weChat);

    /**
     * @param id String
     * @return List<User>
     */
    List<User> findByIdNot(String id);
}
