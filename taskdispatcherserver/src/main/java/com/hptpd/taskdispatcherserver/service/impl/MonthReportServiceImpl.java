package com.hptpd.taskdispatcherserver.service.impl;

import com.google.common.collect.Lists;
import com.hptpd.taskdispatcherserver.common.util.JsonUtil;
import com.hptpd.taskdispatcherserver.component.Result;
import com.hptpd.taskdispatcherserver.domain.MonthReportProjectStatistics;
import com.hptpd.taskdispatcherserver.domain.vo.MonthReportProjectStatisticsVo;
import com.hptpd.taskdispatcherserver.repository.MonthReportProjectStatisticsRep;
import com.hptpd.taskdispatcherserver.service.IMonthReportService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * \* Created with IntelliJ IDEA.
 * \* Date: 2019-05-06 17:22
 * \*
 * \* Description:
 * \
 * @author walter_long
 */
@Service("iMonthReportService")
public class MonthReportServiceImpl implements IMonthReportService {

    private Logger logger = LoggerFactory.getLogger(MonthReportServiceImpl.class);

    @Resource(name = "monthReportProjectStatisticsRep")
    private MonthReportProjectStatisticsRep monthReportProjectStatisticsRep;

    @Override
    public Result getMonthReport(String time) {
        if (null == time || time.isEmpty()) {
            return Result.setResult(Result.ERROR, "时间参数不对");
        }
        List<MonthReportProjectStatisticsVo> monthReportProjectStatisticsVos = MonthReportProjectStatisticsVo.convert2Vo(monthReportProjectStatisticsRep.findByTimeAndValidityDaysGreaterThan(time, 0F));
        return Result.setResult(Result.SUCCESS, "获取成功", JsonUtil.objectToJson(monthReportProjectStatisticsVos));
    }

    @Override
    public Result generateMonthReport() {
        return Result.setResult(Result.ERROR, "执行异常");
    }
}
