package com.investech.service.impl;

import com.investech.entity.FundPortfolio;
import com.investech.mapper.FundPortfolioMapper;
import com.investech.service.FundPortfolioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class FundPortfolioServiceImpl implements FundPortfolioService {
    
    @Autowired
    private FundPortfolioMapper fundPortfolioMapper;
    
    @Override
    public List<FundPortfolio> findAll() {
        return fundPortfolioMapper.findAll();
    }
    
    @Override
    public FundPortfolio findById(Long id) {
        return fundPortfolioMapper.findById(id);
    }
    
    @Override
    public List<FundPortfolio> findByUserId(Long userId) {
        return fundPortfolioMapper.findByUserId(userId);
    }
    
    @Override
    public boolean createPortfolio(FundPortfolio portfolio) {
        portfolio.setStatus(1);
        LocalDateTime now = LocalDateTime.now();
        portfolio.setCreateTime(now);
        portfolio.setUpdateTime(now);
        return fundPortfolioMapper.insert(portfolio) > 0;
    }
    
    @Override
    public boolean updatePortfolio(FundPortfolio portfolio) {
        portfolio.setUpdateTime(LocalDateTime.now());
        return fundPortfolioMapper.update(portfolio) > 0;
    }
    
    @Override
    public boolean deletePortfolio(Long id) {
        return fundPortfolioMapper.deleteById(id) > 0;
    }
} 