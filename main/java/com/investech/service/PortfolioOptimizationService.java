package com.investech.service;

import com.investech.entity.PortfolioOptimization;
import java.util.List;

public interface PortfolioOptimizationService {
    List<PortfolioOptimization> getAllOptimizations();
    PortfolioOptimization getOptimizationById(Long id);
    List<PortfolioOptimization> getOptimizationsByClientId(Long clientId);
    List<PortfolioOptimization> getOptimizationsByCondition(PortfolioOptimization optimization);
    boolean addOptimization(PortfolioOptimization optimization);
    boolean updateOptimization(PortfolioOptimization optimization);
    boolean deleteOptimization(Long id);
    boolean updateOptimizationStatus(Long id, Integer status);
} 