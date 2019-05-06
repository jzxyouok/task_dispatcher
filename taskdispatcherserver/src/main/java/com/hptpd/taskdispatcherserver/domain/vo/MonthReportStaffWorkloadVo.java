package com.hptpd.taskdispatcherserver.domain.vo;

import com.hptpd.taskdispatcherserver.common.util.AbstractMyBeanUtils;
import com.hptpd.taskdispatcherserver.domain.MonthReportStaffWorkload;
import lombok.Data;

/**
 * \* Created with IntelliJ IDEA.
 * \* Date: 2019-05-06 10:56
 * \*
 * \* Description: 月报，人员工作量
 * \
 *
 * @author longwei
 */
@Data
public class MonthReportStaffWorkloadVo {
    private String id;

    private String userName;

    private Float workload;

    private UserVo userVo;

    private MonthReportProjectStatisticsVo monthReportProjectStatisticsVo;

    public static MonthReportStaffWorkloadVo convert2Vo(MonthReportStaffWorkload monthReportStaffWorkload) {
        if (null == monthReportStaffWorkload) {
            return null;
        }
        MonthReportStaffWorkloadVo monthReportStaffWorkloadVo = new MonthReportStaffWorkloadVo();
        AbstractMyBeanUtils.copyProperties(monthReportStaffWorkload, monthReportStaffWorkloadVo);
        monthReportStaffWorkloadVo.setUserVo(UserVo.userToVo(monthReportStaffWorkload.getUser()));
        return monthReportStaffWorkloadVo;
    }
}
