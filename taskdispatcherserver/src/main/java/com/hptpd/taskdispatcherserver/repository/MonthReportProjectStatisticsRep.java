package com.hptpd.taskdispatcherserver.repository;

import com.hptpd.taskdispatcherserver.domain.MonthReportProjectStatistics;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author lw
 */
@Repository("monthReportProjectStatisticsRep")
public interface MonthReportProjectStatisticsRep extends JpaRepository<MonthReportProjectStatistics,String> {
    /**
     * @param time String
     * @return List
     */
    List<MonthReportProjectStatistics> findByTimeAndValidityDaysGreaterThan(String time, Float validityDays);
}
