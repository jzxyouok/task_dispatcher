package com.hptpd.taskdispatcherserver.domain;

import com.google.common.collect.Lists;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

/**
 * \* Created with IntelliJ IDEA.
 * \* Date: 2019-05-06 10:56
 * \*
 * \* Description: 月报，项目统计
 * \
 *
 * @author longwei
 */
@Entity
@Table(name = "month_report_project_statistics")
@Data
public class MonthReportProjectStatistics {
    @Id
    @GenericGenerator(name = "uuid", strategy = "uuid")
    @GeneratedValue(generator = "uuid")
    @Column(name = "id")
    private String id;

    /**
     * 有效天数
     */
    @Column(name = "validity_days", nullable=false, columnDefinition="FLOAT default 0.0")
    private Float validityDays;

    /**
     * 项目名称
     */
    @Column(name = "project_name")
    private String projectName;

    /**
     * 项目权重
     */
    @Column(name = "project_weight", nullable=false, columnDefinition="FLOAT default 0.0")
    private Float projectWeight;

    /**
     * 小计
     */
    @Column(name = "subtotal", nullable=false, columnDefinition="FLOAT default 0.0")
    private Float subtotal;

    /**
     * 统计的哪个月的时间
     * 2018-02
     */
    @Column(name = "time")
    private String time;

    @ManyToOne(fetch = FetchType.LAZY)
    private Project project;

    @OneToMany(mappedBy = "monthReportProjectStatistics", cascade = {CascadeType.PERSIST,CascadeType.REMOVE}, fetch = FetchType.LAZY)
    private List<MonthReportStaffWorkload> monthReportStaffWorkloads = Lists.newArrayList();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MonthReportProjectStatistics)) return false;
        MonthReportProjectStatistics that = (MonthReportProjectStatistics) o;
        return Objects.equals(getId(), that.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }
}
