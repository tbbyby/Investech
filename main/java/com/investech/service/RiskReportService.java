package com.investech.service;

import com.investech.entity.RiskReport;
import java.util.List;

public interface RiskReportService {
    List<RiskReport> getAllReports();
    RiskReport getReportById(Long id);
    List<RiskReport> getReportsByCondition(RiskReport report);
    boolean addReport(RiskReport report);
    boolean updateReport(RiskReport report);
    boolean deleteReport(Long id);
    boolean updateReportStatus(Long id, Integer status);
} 