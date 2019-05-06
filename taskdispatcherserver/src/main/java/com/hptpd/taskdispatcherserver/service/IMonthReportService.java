package com.hptpd.taskdispatcherserver.service;

import com.hptpd.taskdispatcherserver.component.Result;

/**
 * \* Created with IntelliJ IDEA.
 * \* Date: 2019-05-06 17:22
 * \*
 * \* Description:
 * \
 *
 * @author walter_long
 */
public interface IMonthReportService {
    /**
     * 获取月报
     * @param time 2019-01
     * @return Result
     */
    Result getMonthReport(String time);
}
