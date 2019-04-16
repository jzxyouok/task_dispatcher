package com.hptpd.taskdispatcherserver.domain;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Objects;

/**
 * \* Created with IntelliJ IDEA.
 * \* Date: 2019-04-16 10:56
 * \*
 * \* Description: 专家
 * \
 *
 * @author longwei
 */
@Entity
@Table(name = "expert")
@Data
public class Expert {
    @Id
    @GenericGenerator(name = "uuid", strategy = "uuid")
    @GeneratedValue(generator = "uuid")
    @Column(name = "expert_id")
    private String id;

    @Column(name = "name")
    private String name;

    @ManyToOne
    private User user;


    @ManyToOne
    private Task task;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Expert expert = (Expert) o;
        return Objects.equals(id, expert.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
