package com.hptpd.taskdispatcherserver.component;

import com.hptpd.taskdispatcherserver.service.IMonthReportService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Calendar;
import java.util.Date;

/**
 * 定时任务类
 * @author walter_long
 */
@Component
@EnableScheduling
@EnableAsync
public class ScheduledService {

    private Logger logger = LoggerFactory.getLogger(ScheduledService.class);

    @Resource(name = "iMonthReportService")
    private IMonthReportService iMonthReportService;

    @Async
    @Scheduled(cron = "0 0 1 1 * ?")
    public void generateMonthReport() {
        logger.info("生成月报开始 : " + new Date().toLocaleString());
        Result result = iMonthReportService.generateMonthReport();
        logger.info("生成月报结束 : " + new Date().toLocaleString() + "  msg:" + result.getMsg());
    }

    @Async
    public void second() {
        System.out.println("第二个定时任务开始 : " + new Date().toLocaleString() + "\r\n线程 : " + Thread.currentThread().getName());
        System.out.println();
    }
}
