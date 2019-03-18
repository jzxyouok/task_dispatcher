package com.hptpd.taskdispatcherserver.domain;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;
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
public class Role implements Serializable {

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


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "task_id")
    private Task task;


    @ManyToMany(cascade = {CascadeType.PERSIST})
    private List<User> users = Lists.newArrayList();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Role)) return false;
        Role role = (Role) o;
        return getId().equals(role.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }

    @Override
    public String toString() {
        return "Role{" +
                "id='" + id + '\'' +
                ", roleInfo='" + roleInfo + '\'' +
                ", task=" + task +
                ", users=" + users +
                '}';
    }
}
