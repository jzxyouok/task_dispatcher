package com.hptpd.taskdispatcherserver.domain;

import com.google.common.collect.Sets;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

/**
 * \* Created with IntelliJ IDEA.
 * \* Date: 2019-03-14 16:19
 * \*
 * \* Description: 处理和基站相关的业务逻辑代码
 * \
 *
 * @author liucheng
 */
@Entity
@Table(name = "project")
@Data
public class Project {

    @Id
    @GenericGenerator(name = "system_uuid", strategy = "uuid")
    @GeneratedValue(generator = "system_uuid")
    @Column(name = "project_id")
    private String id;

    @Column(name = "project_name")
    private String projectName;

    @Column(name = "department")
    private String department;

    @OneToMany(mappedBy = "project", cascade ={CascadeType.PERSIST,CascadeType.REMOVE}, fetch = FetchType.LAZY)
    private Set<Task> tasks = Sets.newLinkedHashSet();
}
