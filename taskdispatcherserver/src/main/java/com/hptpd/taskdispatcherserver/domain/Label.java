package com.hptpd.taskdispatcherserver.domain;

import com.google.common.collect.Sets;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

/**
 * \* Created with IntelliJ IDEA.
 * \* Date: 2019-03-14 15:59
 * \*
 * \* Description: 标签
 * \
 *
 * @author liucheng
 */
@Entity
@Table(name = "label")
@Data
public class Label {


     @Id
     @GenericGenerator(name = "system_uuid", strategy = "uuid")
     @GeneratedValue(generator = "system_uuid")
     @Column(name = "label_id")
     private String id;


     @Column(name = "label_name")
     private String labelName;

     @Column(name = "creat_time")
     private Date creatTime;

     @Column(name = "creat_user")
     private String creatUser;

     @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.REMOVE}, fetch = FetchType.LAZY)
     private Set<Task> tasks = Sets.newLinkedHashSet();
}
