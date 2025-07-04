package com.investech.service.impl;

import com.investech.entity.RiskAlert;
import com.investech.mapper.RiskAlertMapper;
import com.investech.service.RiskAlertService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RiskAlertServiceImpl implements RiskAlertService {
    
    @Autowired
    private RiskAlertMapper riskAlertMapper;
    
    @Override
    public List<RiskAlert> getAllAlerts() {
        return riskAlertMapper.selectAll();
    }
    
    @Override
    public RiskAlert getAlertById(Long id) {
        return riskAlertMapper.selectById(id);
    }
    
    @Override
    public List<RiskAlert> getAlertsByCondition(RiskAlert alert) {
        return riskAlertMapper.selectByCondition(alert);
    }
    
    @Override
    public boolean addAlert(RiskAlert alert) {
        return riskAlertMapper.insert(alert) > 0;
    }
    
    @Override
    public boolean updateAlert(RiskAlert alert) {
        return riskAlertMapper.update(alert) > 0;
    }
    
    @Override
    public boolean deleteAlert(Long id) {
        return riskAlertMapper.deleteById(id) > 0;
    }
    
    @Override
    public boolean updateAlertStatus(Long id, Integer status) {
        return riskAlertMapper.updateStatus(id, status) > 0;
    }
} 