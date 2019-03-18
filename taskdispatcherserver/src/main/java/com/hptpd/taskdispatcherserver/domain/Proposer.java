package com.hptpd.taskdispatcherserver.domain;

import com.google.common.collect.Sets;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Objects;
import java.util.Set;

/**
 * \* Created with IntelliJ IDEA.
 * \* Date: 2019-03-18 10:35
 * \*
 * \* Description:
 * \
 *
 * @author liucheng
 */
@Entity
@Table(name = "proposer")
@Data
public class Proposer{


     @Id
     @GenericGenerator(name = "uuid", strategy = "uuid")
     @GeneratedValue(generator = "uuid")
     @Column(name = "proposer_id")
     private String id;

     @Column(name = "name")
     private String name;

     @ManyToOne
     @JoinColumn(name = "user_id")
     private User user;


     @OneToOne(fetch = FetchType.LAZY)
     private Task task;



}
