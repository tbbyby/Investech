package com.investech.mapper;

import com.investech.entity.Backtest;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;

@Mapper
public interface BacktestMapper {
    Backtest findByBacktestCode(String backtestCode);
    
    Backtest findById(Long id);
    
    List<Backtest> findAll();
    
    List<Backtest> findByStrategyCode(String strategyCode);
    
    List<Backtest> findByCreatorId(Long creatorId);
    
    List<Backtest> findByStatus(String status);
    
    List<Backtest> findByCondition(@Param("backtestCode") String backtestCode,
                                   @Param("backtestName") String backtestName,
                                   @Param("strategyCode") String strategyCode,
                                   @Param("status") String status,
                                   @Param("creatorId") Long creatorId);
    
    int insert(Backtest backtest);
    
    int update(Backtest backtest);
    
    int deleteById(Long id);
} 