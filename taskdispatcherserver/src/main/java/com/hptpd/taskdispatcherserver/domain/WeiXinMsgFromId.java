package com.hptpd.taskdispatcherserver.domain;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;

/**
 * \* Created with IntelliJ IDEA.
 * \* Date: 2019-05-09 10:56
 * \*
 * \* Description: 微信发送模板消息要用的fromId
 * \
 *
 * @author longwei
 */
@Entity
@Table(name = "weixin_msg_fromid")
@Data
public class WeiXinMsgFromId {
    @Id
    @GenericGenerator(name = "uuid", strategy = "uuid")
    @GeneratedValue(generator = "uuid")
    @Column(name = "id")
    private String id;

    /**
     * 发送模板消息要用的fromId
     */
    @Column(name = "fromid")
    private String fromId;

    /**
     * 创建时间
     */
    @Column(name = "time")
    private Date time;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof WeiXinMsgFromId)) {
            return false;
        }
        WeiXinMsgFromId that = (WeiXinMsgFromId) o;
        return Objects.equals(getId(), that.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }
}
