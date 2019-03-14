package com.hptpd.taskdispatcherserver.domain;

import com.google.common.collect.Sets;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Set;

/**
 * \* Created with IntelliJ IDEA.
 * \* Date: 2019-03-14 15:03
 * \*
 * \* Description: 角色信息表
 * \
 *
 * @author liucheng
 */
@Entity
@Table(name = "role")
@Data
public class Role {

    @Id
    @GenericGenerator(name = "system_uuid", strategy = "uuid")
    @GeneratedValue(generator = "system_uuid")
    @Column(name = "role_id")
    private String id;

    /**
     *  任务角色
     */
    @Column(name = "role_info")
    private String roleInfo;


    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.REMOVE}, fetch = FetchType.LAZY)
    private Set<Task> tasks = Sets.newLinkedHashSet();


    @ManyToMany(mappedBy = "roles",cascade = {CascadeType.PERSIST,CascadeType.REMOVE}, fetch = FetchType.LAZY)
    private Set<User> users =Sets.newLinkedHashSet();

}
