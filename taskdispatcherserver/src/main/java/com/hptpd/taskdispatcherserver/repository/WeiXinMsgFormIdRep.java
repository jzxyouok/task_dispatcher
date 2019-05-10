package com.hptpd.taskdispatcherserver.repository;


import com.hptpd.taskdispatcherserver.domain.WeiXinMsgFromId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * \* Created with IntelliJ IDEA.
 * \* Date: 2019-05-09 11:50
 * \*
 * \* Description:
 * \
 *
 * @author longwei
 */
@Repository("weiXinMsgFormIdRep")
public interface WeiXinMsgFormIdRep extends JpaRepository<WeiXinMsgFromId, String> {

}
