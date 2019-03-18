package com.hptpd.taskdispatcherserver.domain;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;
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
    @GenericGenerator(name = "uuid", strategy = "uuid")//声明策略通用生成器system_uuid，策略为uuid
    @GeneratedValue(generator = "uuid")//用generator属性指定要用的策略生成器
    @Column(name = "user_id")
    private String id;

    @Column(name = "weChat")
    private String weChat;

    @Column(name="name")
    private String name;


    @Column(name = "telephone")
    private String telephone;


    @Column(name = "position")
    private String position;

    @OneToMany(mappedBy = "user", cascade = {CascadeType.PERSIST,CascadeType.REMOVE})
    private Set<Proposer> proposers =Sets.newLinkedHashSet();

    @OneToMany(mappedBy = "user", cascade = {CascadeType.PERSIST,CascadeType.REMOVE})
    private Set<Auditor> auditors =Sets.newLinkedHashSet();

    @OneToMany(mappedBy = "user", cascade = {CascadeType.PERSIST,CascadeType.REMOVE})
    private Set<Staff> staffs =Sets.newLinkedHashSet();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(id, user.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
