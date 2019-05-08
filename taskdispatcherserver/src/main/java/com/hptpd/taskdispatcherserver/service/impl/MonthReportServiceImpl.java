package com.hptpd.taskdispatcherserver.service.impl;

import com.google.common.collect.Lists;
import com.hptpd.taskdispatcherserver.common.util.DateUtil;
import com.hptpd.taskdispatcherserver.common.util.JsonUtil;
import com.hptpd.taskdispatcherserver.component.Result;
import com.hptpd.taskdispatcherserver.domain.*;
import com.hptpd.taskdispatcherserver.domain.vo.MonthReportProjectStatisticsVo;
import com.hptpd.taskdispatcherserver.domain.vo.TaskVo;
import com.hptpd.taskdispatcherserver.repository.MonthReportProjectStatisticsRep;
import com.hptpd.taskdispatcherserver.repository.ProjectRep;
import com.hptpd.taskdispatcherserver.repository.TaskRep;
import com.hptpd.taskdispatcherserver.repository.UserRep;
import com.hptpd.taskdispatcherserver.service.IMonthReportService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
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
    @Resource(name = "projectRep")
    private ProjectRep projectRep;
    @Resource(name = "userRep")
    private UserRep userRep;
    @Resource(name = "taskRep")
    private TaskRep taskRep;

    @Override
    public Result getMonthReport(String time) {
        if (null == time || time.isEmpty()) {
            return Result.setResult(Result.ERROR, "时间参数不对");
        }
        List<MonthReportProjectStatisticsVo> monthReportProjectStatisticsVos = MonthReportProjectStatisticsVo.convert2Vo(monthReportProjectStatisticsRep.findByTimeAndValidityDaysGreaterThan(time, 0F));
        return Result.setResult(Result.SUCCESS, "获取成功", JsonUtil.objectToJson(monthReportProjectStatisticsVos));
    }

    @Override
    @Transactional
    public Result generateMonthReport() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, -1);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM");
        String reportMonth = simpleDateFormat.format(calendar.getTime());
        List<Project> projects = projectRep.findAll();
        List<User> users = userRep.findByIdNot("0");
        float[] totalWorkloadArr = new float[users.size()];
        MonthReportProjectStatistics totalMonthReportProjectStatistics = new MonthReportProjectStatistics();
        List<MonthReportProjectStatistics> monthReportProjectStatisticss = Lists.newLinkedList();
        float sumValidityDays = 0F;
        for (Project project : projects) {
            float[] workloadArr = new float[users.size()];
            float validityDays = 0F;
            List<Task> tasks = taskRep.findByProjectAndTaskStateAndDoneTimeIsBetween(project, TaskVo.DONE, DateUtil.getMonthBegin(calendar.getTime()), DateUtil.getMonthEnd(calendar.getTime()));
            //遍历计算每个人的每个项目的总工作量存到workloadArr
            for (Task task : tasks) {
                for (Staff staff : task.getStaffs()) {
                    int userIndex = 0;
                    for (User user : users) {
                        if (user.equals(staff.getUser())) {
                            float workload = Float.parseFloat(task.getRealWorkload());
                            workloadArr[userIndex] += workload;
                            totalWorkloadArr[userIndex] += workload;
                            validityDays += workload;
                            sumValidityDays += workload;
                            break;
                        }
                        ++userIndex;
                    }
                }
            }
            MonthReportProjectStatistics monthReportProjectStatistics = new MonthReportProjectStatistics();
            monthReportProjectStatistics.setProject(project);
            monthReportProjectStatistics.setProjectName(project.getName());
            monthReportProjectStatistics.setSubtotal(validityDays);
            monthReportProjectStatistics.setValidityDays(validityDays);
            monthReportProjectStatistics.setTime(reportMonth);

            List<MonthReportStaffWorkload> monthReportStaffWorkloads = mapMonthReportStaffWorkload(users, workloadArr, monthReportProjectStatistics);

            monthReportProjectStatistics.setMonthReportStaffWorkloads(monthReportStaffWorkloads);
            monthReportProjectStatisticss.add(monthReportProjectStatistics);
        }

        List<MonthReportStaffWorkload> totalMonthReportStaffWorkloads = mapMonthReportStaffWorkload(users, totalWorkloadArr, totalMonthReportProjectStatistics);

        for (MonthReportProjectStatistics monthReportProjectStatistics : monthReportProjectStatisticss) {
            if (sumValidityDays == 0F) {
                monthReportProjectStatistics.setProjectWeight(1F / projects.size());
            } else {
                monthReportProjectStatistics.setProjectWeight(monthReportProjectStatistics.getValidityDays() / sumValidityDays);
            }
        }

        totalMonthReportProjectStatistics.setProjectWeight(1F);
        totalMonthReportProjectStatistics.setProject(null);
        totalMonthReportProjectStatistics.setProjectName("合计");
        totalMonthReportProjectStatistics.setValidityDays(sumValidityDays);
        totalMonthReportProjectStatistics.setSubtotal(sumValidityDays);
        totalMonthReportProjectStatistics.setMonthReportStaffWorkloads(totalMonthReportStaffWorkloads);
        totalMonthReportProjectStatistics.setTime(reportMonth);

        monthReportProjectStatisticss.add(totalMonthReportProjectStatistics);

        monthReportProjectStatisticsRep.saveAll(monthReportProjectStatisticss);

        return Result.setResult(Result.SUCCESS, "执行成功");
    }

    private List<MonthReportStaffWorkload> mapMonthReportStaffWorkload(List<User> users, float[] workloadArr, MonthReportProjectStatistics monthReportProjectStatistics) {
        int index = 0;
        List<MonthReportStaffWorkload> monthReportStaffWorkloads = Lists.newLinkedList();
        for (User user : users) {
            MonthReportStaffWorkload monthReportStaffWorkload = new MonthReportStaffWorkload();
            monthReportStaffWorkload.setMonthReportProjectStatistics(monthReportProjectStatistics);
            monthReportStaffWorkload.setUser(user);
            monthReportStaffWorkload.setUserName(user.getName());
            monthReportStaffWorkload.setWorkload(workloadArr[index]);
            monthReportStaffWorkloads.add(monthReportStaffWorkload);
            ++index;
        }
        return monthReportStaffWorkloads;
    }

}
