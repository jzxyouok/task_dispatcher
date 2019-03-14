package com.hptpd.taskdispatcherserver.domain;

import com.google.common.collect.Sets;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Set;

/**
 * \* Created with IntelliJ IDEA.
 * \* Date: 2019-03-14 15:06
 * \*
 * \* Description: 员工用户信息表
 * \
 *
 * @author liucheng
 */

@Entity
@Table(name = "user")
@Data
public class User {

    @Id
    @GenericGenerator(name = "system_uuid", strategy = "uuid")
    @GeneratedValue(generator = "system_uuid")
    @Column(name = "weChat_id")
    private String weChat;


    @Column(name="name")
    private String name;


    @Column(name = "telephone")
    private String telephone;


    @Column(name = "position")
    private String position;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.REMOVE}, fetch = FetchType.LAZY)
    private Set<Role> roles = Sets.newLinkedHashSet();
}
