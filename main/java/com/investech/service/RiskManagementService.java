package com.investech.service;

import com.investech.entity.RiskManagement;
import java.util.List;

public interface RiskManagementService {
    List<RiskManagement> findAll();
    
    RiskManagement findByRiskCode(String riskCode);
    
    RiskManagement findById(Long id);
    
    List<RiskManagement> findByRiskType(String riskType);
    
    List<RiskManagement> findByRiskLevel(String riskLevel);
    
    List<RiskManagement> findByCreatorId(Long creatorId);
    
    List<RiskManagement> searchRisks(String riskCode, String riskName, String riskType, String riskLevel, Long creatorId);
    
    boolean createRisk(RiskManagement riskManagement);
    
    boolean updateRisk(RiskManagement riskManagement);
    
    boolean deleteRisk(Long id);
} 