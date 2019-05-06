package com.hptpd.taskdispatcherserver.domain.vo;

import com.google.common.collect.Lists;
import com.hptpd.taskdispatcherserver.common.util.AbstractMyBeanUtils;
import com.hptpd.taskdispatcherserver.domain.MonthReportProjectStatistics;
import com.hptpd.taskdispatcherserver.domain.MonthReportStaffWorkload;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.ManyToOne;
import java.util.List;

/**
 * \* Created with IntelliJ IDEA.
 * \* Date: 2019-05-06 10:56
 * \*
 * \* Description: 月报，项目统计
 * \
 *
 * @author longwei
 */
@Data
public class MonthReportProjectStatisticsVo {
    private String id;

    private Float validityDays;

    private String projectName;

    private Float projectWeight;

    private Float subtotal;

    private String time;

    private ProjectVo projectVo;

    private List<MonthReportStaffWorkloadVo> monthReportStaffWorkloadVos;

    public static MonthReportProjectStatisticsVo convert2Vo (MonthReportProjectStatistics monthReportProjectStatistics) {
        if (null == monthReportProjectStatistics) {
            return null;
        }
        MonthReportProjectStatisticsVo monthReportProjectStatisticsVo = new MonthReportProjectStatisticsVo();
        AbstractMyBeanUtils.copyProperties(monthReportProjectStatistics, monthReportProjectStatisticsVo);
        monthReportProjectStatisticsVo.setProjectVo(ProjectVo.projectToVo(monthReportProjectStatistics.getProject()));
        List<MonthReportStaffWorkloadVo> monthReportStaffWorkloadVos = Lists.newArrayList();
        for (MonthReportStaffWorkload monthReportStaffWorkload : monthReportProjectStatistics.getMonthReportStaffWorkloads()) {
            monthReportStaffWorkloadVos.add(MonthReportStaffWorkloadVo.convert2Vo(monthReportStaffWorkload));
        }
        monthReportProjectStatisticsVo.setMonthReportStaffWorkloadVos(monthReportStaffWorkloadVos);
        return monthReportProjectStatisticsVo;
    }

    public static List<MonthReportProjectStatisticsVo> convert2Vo (List<MonthReportProjectStatistics> monthReportProjectStatisticss) {
        List<MonthReportProjectStatisticsVo> monthReportProjectStatisticsVos = Lists.newArrayList();
        for (MonthReportProjectStatistics monthReportProjectStatistics : monthReportProjectStatisticss) {
            monthReportProjectStatisticsVos.add(convert2Vo(monthReportProjectStatistics));
        }
        return monthReportProjectStatisticsVos;
    }
}
