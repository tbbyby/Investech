package com.investech.service;

import com.investech.entity.Strategy;
import java.util.List;

public interface StrategyService {
    List<Strategy> findAll();
    
    Strategy findByStrategyCode(String strategyCode);
    
    Strategy findById(Long id);
    
    List<Strategy> findByCreatorId(Long creatorId);
    
    List<Strategy> findByType(String strategyType);
    
    List<Strategy> findByInvestmentStyle(String investmentStyle);
    
    List<Strategy> findByRiskLevel(String riskLevel);
    
    List<Strategy> searchStrategies(String strategyCode, String strategyName, String strategyType, 
                                   String investmentStyle, String riskLevel, Long creatorId);
    
    boolean createStrategy(Strategy strategy);
    
    boolean updateStrategy(Strategy strategy);
    
    boolean deleteStrategy(Long id);
} 