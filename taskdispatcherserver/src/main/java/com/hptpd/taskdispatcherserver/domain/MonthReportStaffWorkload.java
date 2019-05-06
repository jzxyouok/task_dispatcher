package com.hptpd.taskdispatcherserver.domain;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Objects;

/**
 * \* Created with IntelliJ IDEA.
 * \* Date: 2019-05-06 10:56
 * \*
 * \* Description: 月报，人员工作量
 * \
 *
 * @author longwei
 */
@Entity
@Table(name = "month_report_staff_workload")
@Data
public class MonthReportStaffWorkload {
    @Id
    @GenericGenerator(name = "uuid", strategy = "uuid")
    @GeneratedValue(generator = "uuid")
    @Column(name = "id")
    private String id;

    @Column(name = "user_name")
    private String userName;

    @Column(name = "workload", nullable=false, columnDefinition="FLOAT default 0.0")
    private Float workload;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    private MonthReportProjectStatistics monthReportProjectStatistics;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MonthReportStaffWorkload)) return false;
        MonthReportStaffWorkload that = (MonthReportStaffWorkload) o;
        return Objects.equals(getId(), that.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }
}
