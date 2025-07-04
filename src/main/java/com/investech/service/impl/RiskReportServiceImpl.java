package com.investech.service.impl;

import com.investech.entity.RiskReport;
import com.investech.mapper.RiskReportMapper;
import com.investech.service.RiskReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RiskReportServiceImpl implements RiskReportService {
    
    @Autowired
    private RiskReportMapper riskReportMapper;
    
    @Override
    public List<RiskReport> getAllReports() {
        return riskReportMapper.selectAll();
    }
    
    @Override
    public RiskReport getReportById(Long id) {
        return riskReportMapper.selectById(id);
    }
    
    @Override
    public List<RiskReport> getReportsByCondition(RiskReport report) {
        return riskReportMapper.selectByCondition(report);
    }
    
    @Override
    public boolean addReport(RiskReport report) {
        return riskReportMapper.insert(report) > 0;
    }
    
    @Override
    public boolean updateReport(RiskReport report) {
        return riskReportMapper.update(report) > 0;
    }
    
    @Override
    public boolean deleteReport(Long id) {
        return riskReportMapper.deleteById(id) > 0;
    }
    
    @Override
    public boolean updateReportStatus(Long id, Integer status) {
        return riskReportMapper.updateStatus(id, status) > 0;
    }
} 