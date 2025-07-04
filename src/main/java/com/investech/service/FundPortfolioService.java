package com.investech.service;

import com.investech.entity.FundPortfolio;
import java.util.List;

public interface FundPortfolioService {
    List<FundPortfolio> findAll();
    
    FundPortfolio findById(Long id);
    
    List<FundPortfolio> findByUserId(Long userId);
    
    boolean createPortfolio(FundPortfolio portfolio);
    
    boolean updatePortfolio(FundPortfolio portfolio);
    
    boolean deletePortfolio(Long id);
} 