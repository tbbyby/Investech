package com.investech.mapper;

import com.investech.entity.Strategy;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;

@Mapper
public interface StrategyMapper {
    Strategy findByStrategyCode(String strategyCode);
    
    Strategy findById(Long id);
    
    List<Strategy> findAll();
    
    List<Strategy> findByCreatorId(Long creatorId);
    
    List<Strategy> findByType(String strategyType);
    
    List<Strategy> findByInvestmentStyle(String investmentStyle);
    
    List<Strategy> findByRiskLevel(String riskLevel);
    
    List<Strategy> findByCondition(@Param("strategyCode") String strategyCode,
                                  @Param("strategyName") String strategyName,
                                  @Param("strategyType") String strategyType,
                                  @Param("investmentStyle") String investmentStyle,
                                  @Param("riskLevel") String riskLevel,
                                  @Param("creatorId") Long creatorId);
    
    int insert(Strategy strategy);
    
    int update(Strategy strategy);
    
    int deleteById(Long id);
} 