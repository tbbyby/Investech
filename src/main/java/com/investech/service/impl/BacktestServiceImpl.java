package com.investech.service.impl;

import com.investech.entity.Backtest;
import com.investech.mapper.BacktestMapper;
import com.investech.service.BacktestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class BacktestServiceImpl implements BacktestService {
    
    @Autowired
    private BacktestMapper backtestMapper;
    
    @Override
    public List<Backtest> findAll() {
        return backtestMapper.findAll();
    }
    
    @Override
    public Backtest findByBacktestCode(String backtestCode) {
        return backtestMapper.findByBacktestCode(backtestCode);
    }
    
    @Override
    public Backtest findById(Long id) {
        return backtestMapper.findById(id);
    }
    
    @Override
    public List<Backtest> findByStrategyCode(String strategyCode) {
        return backtestMapper.findByStrategyCode(strategyCode);
    }
    
    @Override
    public List<Backtest> findByCreatorId(Long creatorId) {
        return backtestMapper.findByCreatorId(creatorId);
    }
    
    @Override
    public List<Backtest> findByStatus(String status) {
        return backtestMapper.findByStatus(status);
    }
    
    @Override
    public List<Backtest> searchBacktests(String backtestCode, String backtestName, String strategyCode, String status, Long creatorId) {
        return backtestMapper.findByCondition(backtestCode, backtestName, strategyCode, status, creatorId);
    }
    
    @Override
    public boolean createBacktest(Backtest backtest) {
        return backtestMapper.insert(backtest) > 0;
    }
    
    @Override
    public boolean updateBacktest(Backtest backtest) {
        return backtestMapper.update(backtest) > 0;
    }
    
    @Override
    public boolean deleteBacktest(Long id) {
        return backtestMapper.deleteById(id) > 0;
    }
} 