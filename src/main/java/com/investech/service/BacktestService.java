package com.investech.service;

import com.investech.entity.Backtest;
import java.util.List;

public interface BacktestService {
    List<Backtest> findAll();
    
    Backtest findByBacktestCode(String backtestCode);
    
    Backtest findById(Long id);
    
    List<Backtest> findByStrategyCode(String strategyCode);
    
    List<Backtest> findByCreatorId(Long creatorId);
    
    List<Backtest> findByStatus(String status);
    
    List<Backtest> searchBacktests(String backtestCode, String backtestName, String strategyCode, String status, Long creatorId);
    
    boolean createBacktest(Backtest backtest);
    
    boolean updateBacktest(Backtest backtest);
    
    boolean deleteBacktest(Long id);
} 