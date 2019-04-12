package com.hptpd.taskdispatcherserver.domain;


import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Set;

/**
 * \* Created with IntelliJ IDEA.
 * \* Date: 2019-03-14 09:15
 * \*
 * \* Description: 任务实体
 * \
 *
 * @author liucheng
 */
@Entity
@Table(name = "task")
@Data
public class Task {



    @Id
    @GenericGenerator(name = "system_uuid", strategy = "uuid")
    @GeneratedValue(generator = "system_uuid")
    @Column(name = "task_id")
    private String id;

    /**
     *  任务名称
     */
    @Column(name = "task_name")
    private String taskName;

    /**
     *   任务描述
     */
    @Column(name = "task_description")
    private String taskDescription;


    /**
     *  任务开始时间
     */
    @Column(name = "start_time")
    private Date startTime;

    /**
     *  任务 结束时间
     */
    @Column(name="end_time")
    private Date endTime;

    /**
     * 创建时间
     */
    @Column(name = "creat_time")
    private Date creatTime;

    /**
     *  任务是否定向
     */
    @Column(name="orient")
    private boolean orient;

    /**
     *  工作量
     * （人天数）
     */
    @Column(name = "workload")
    private String workload;


    /**
     * 任务状态
     */
    @Column(name="task_state")
    private String  taskState;

    /**
     * 点评
     */
    @Column(name="comment")
    private String comment;

    /**
     * 评审意见
     */
    @Column(name="expertComment")
    private String expertComment;

    /**
     * 提审原因
     */
    @Column(name="reviewReason")
    private String reviewReason;

    @ManyToMany(mappedBy = "tasks", cascade ={CascadeType.ALL,CascadeType.REMOVE}, fetch = FetchType.LAZY)
    private Set<Label> labels =Sets.newLinkedHashSet();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_id")
    private Project project;

    @OneToOne(mappedBy = "task", cascade = {CascadeType.PERSIST,CascadeType.REMOVE}, fetch = FetchType.LAZY)
    private Proposer proposer;


    @OneToOne(mappedBy = "task", cascade = {CascadeType.PERSIST,CascadeType.REMOVE}, fetch = FetchType.LAZY)
    private Auditor auditor;

    @OneToMany(mappedBy = "task", cascade = {CascadeType.PERSIST,CascadeType.REMOVE}, fetch = FetchType.LAZY)
    private List<Staff> staffs =Lists.newArrayList();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Task task = (Task) o;
        return Objects.equals(id, task.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
