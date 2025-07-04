package com.investech.service.impl;

import com.investech.entity.RiskManagement;
import com.investech.mapper.RiskManagementMapper;
import com.investech.service.RiskManagementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class RiskManagementServiceImpl implements RiskManagementService {
    
    @Autowired
    private RiskManagementMapper riskManagementMapper;
    
    @Override
    public List<RiskManagement> findAll() {
        return riskManagementMapper.findAll();
    }
    
    @Override
    public RiskManagement findByRiskCode(String riskCode) {
        return riskManagementMapper.findByRiskCode(riskCode);
    }
    
    @Override
    public RiskManagement findById(Long id) {
        return riskManagementMapper.findById(id);
    }
    
    @Override
    public List<RiskManagement> findByRiskType(String riskType) {
        return riskManagementMapper.findByRiskType(riskType);
    }
    
    @Override
    public List<RiskManagement> findByRiskLevel(String riskLevel) {
        return riskManagementMapper.findByRiskLevel(riskLevel);
    }
    
    @Override
    public List<RiskManagement> findByCreatorId(Long creatorId) {
        return riskManagementMapper.findByCreatorId(creatorId);
    }
    
    @Override
    public List<RiskManagement> searchRisks(String riskCode, String riskName, String riskType, String riskLevel, Long creatorId) {
        return riskManagementMapper.findByCondition(riskCode, riskName, riskType, riskLevel, creatorId);
    }
    
    @Override
    public boolean createRisk(RiskManagement riskManagement) {
        riskManagement.setStatus(1);
        return riskManagementMapper.insert(riskManagement) > 0;
    }
    
    @Override
    public boolean updateRisk(RiskManagement riskManagement) {
        return riskManagementMapper.update(riskManagement) > 0;
    }
    
    @Override
    public boolean deleteRisk(Long id) {
        return riskManagementMapper.deleteById(id) > 0;
    }
} 