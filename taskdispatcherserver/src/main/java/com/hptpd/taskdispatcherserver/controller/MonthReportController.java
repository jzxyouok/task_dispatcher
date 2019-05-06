package com.hptpd.taskdispatcherserver.controller;

import com.hptpd.taskdispatcherserver.component.Result;
import com.hptpd.taskdispatcherserver.service.IMonthReportService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * \* Created with IntelliJ IDEA.
 * \* Date: 2019-05-06 17:22
 * \*
 * \* Description:
 * \
 *
 * @author longwei
 */
@RestController
@RequestMapping("/month_report")
public class MonthReportController {
    @Resource(name = "iMonthReportService")
    private IMonthReportService iMonthReportService;

    /**
     * 获得月报
     * @param time 2018-02
     * @return Result
     */
    @RequestMapping(value = "/getMonthReport", method = RequestMethod.GET)
    public Result getMonthReport(@RequestParam String time) {
        return iMonthReportService.getMonthReport(time);
    }
}
