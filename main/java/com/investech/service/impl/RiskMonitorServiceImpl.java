package com.investech.service.impl;

import com.investech.entity.RiskMonitor;
import com.investech.mapper.RiskMonitorMapper;
import com.investech.service.RiskMonitorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RiskMonitorServiceImpl implements RiskMonitorService {
    
    @Autowired
    private RiskMonitorMapper riskMonitorMapper;
    
    @Override
    public List<RiskMonitor> getAllMonitors() {
        return riskMonitorMapper.selectAll();
    }
    
    @Override
    public RiskMonitor getMonitorById(Long id) {
        return riskMonitorMapper.selectById(id);
    }
    
    @Override
    public List<RiskMonitor> getMonitorsByCondition(RiskMonitor monitor) {
        return riskMonitorMapper.selectByCondition(monitor);
    }
    
    @Override
    public boolean addMonitor(RiskMonitor monitor) {
        return riskMonitorMapper.insert(monitor) > 0;
    }
    
    @Override
    public boolean updateMonitor(RiskMonitor monitor) {
        return riskMonitorMapper.update(monitor) > 0;
    }
    
    @Override
    public boolean deleteMonitor(Long id) {
        return riskMonitorMapper.deleteById(id) > 0;
    }
    
    @Override
    public boolean updateMonitorStatus(Long id, Integer status) {
        return riskMonitorMapper.updateStatus(id, status) > 0;
    }
} 