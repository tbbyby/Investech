package com.investech.service.impl;

import com.investech.entity.Strategy;
import com.investech.mapper.StrategyMapper;
import com.investech.service.StrategyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class StrategyServiceImpl implements StrategyService {
    
    @Autowired
    private StrategyMapper strategyMapper;
    
    @Override
    public List<Strategy> findAll() {
        return strategyMapper.findAll();
    }
    
    @Override
    public Strategy findByStrategyCode(String strategyCode) {
        return strategyMapper.findByStrategyCode(strategyCode);
    }
    
    @Override
    public Strategy findById(Long id) {
        return strategyMapper.findById(id);
    }
    
    @Override
    public List<Strategy> findByCreatorId(Long creatorId) {
        return strategyMapper.findByCreatorId(creatorId);
    }
    
    @Override
    public List<Strategy> findByType(String strategyType) {
        return strategyMapper.findByType(strategyType);
    }
    
    @Override
    public List<Strategy> findByInvestmentStyle(String investmentStyle) {
        return strategyMapper.findByInvestmentStyle(investmentStyle);
    }
    
    @Override
    public List<Strategy> findByRiskLevel(String riskLevel) {
        return strategyMapper.findByRiskLevel(riskLevel);
    }
    
    @Override
    public List<Strategy> searchStrategies(String strategyCode, String strategyName, String strategyType, 
                                          String investmentStyle, String riskLevel, Long creatorId) {
        return strategyMapper.findByCondition(strategyCode, strategyName, strategyType, 
                                             investmentStyle, riskLevel, creatorId);
    }
    
    @Override
    public boolean createStrategy(Strategy strategy) {
        strategy.setStatus(1);
        return strategyMapper.insert(strategy) > 0;
    }
    
    @Override
    public boolean updateStrategy(Strategy strategy) {
        return strategyMapper.update(strategy) > 0;
    }
    
    @Override
    public boolean deleteStrategy(Long id) {
        return strategyMapper.deleteById(id) > 0;
    }
} 