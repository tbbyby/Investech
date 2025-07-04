package com.investech.service.impl;

import com.investech.entity.PortfolioOptimization;
import com.investech.mapper.PortfolioOptimizationMapper;
import com.investech.service.PortfolioOptimizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class PortfolioOptimizationServiceImpl implements PortfolioOptimizationService {
    
    @Autowired
    private PortfolioOptimizationMapper portfolioOptimizationMapper;
    
    @Override
    public List<PortfolioOptimization> getAllOptimizations() {
        return portfolioOptimizationMapper.selectAll();
    }
    
    @Override
    public PortfolioOptimization getOptimizationById(Long id) {
        return portfolioOptimizationMapper.selectById(id);
    }
    
    @Override
    public List<PortfolioOptimization> getOptimizationsByClientId(Long clientId) {
        return portfolioOptimizationMapper.selectByClientId(clientId);
    }
    
    @Override
    public List<PortfolioOptimization> getOptimizationsByCondition(PortfolioOptimization optimization) {
        return portfolioOptimizationMapper.selectByCondition(optimization);
    }
    
    @Override
    public boolean addOptimization(PortfolioOptimization optimization) {
        return portfolioOptimizationMapper.insert(optimization) > 0;
    }
    
    @Override
    public boolean updateOptimization(PortfolioOptimization optimization) {
        return portfolioOptimizationMapper.update(optimization) > 0;
    }
    
    @Override
    public boolean deleteOptimization(Long id) {
        return portfolioOptimizationMapper.deleteById(id) > 0;
    }
    
    @Override
    public boolean updateOptimizationStatus(Long id, Integer status) {
        return portfolioOptimizationMapper.updateStatus(id, status) > 0;
    }
} 