package com.hptpd.taskdispatcherserver.domain;

import com.google.common.collect.Sets;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Objects;
import java.util.Set;

/**
 * \* Created with IntelliJ IDEA.
 * \* Date: 2019-03-14 16:19
 * \*
 * \* Description:
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
    private String Name;

    @Column(name = "department")
    private String department;

    @OneToMany(mappedBy = "project", cascade ={CascadeType.PERSIST,CascadeType.REMOVE}, fetch = FetchType.LAZY)
    private Set<Task> tasks = Sets.newLinkedHashSet();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Project)) return false;
        Project project = (Project) o;
        return Objects.equals(getId(), project.getId()) &&
                Objects.equals(getTasks(), project.getTasks());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getTasks());
    }
}
