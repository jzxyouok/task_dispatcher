package com.hptpd.taskdispatcherserver.service;

import com.hptpd.taskdispatcherserver.component.Result;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MonthReportServiceTest {
    @Resource(name = "iMonthReportService")
    private IMonthReportService iMonthReportService;

    @Test
    public void generateMonthReport() {
        Result result = iMonthReportService.generateMonthReport();
        System.out.println(result.getMsg());
    }
}
