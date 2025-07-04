package com.investech.mapper;

import com.investech.entity.PerformanceEvaluation;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;

@Mapper
public interface PerformanceEvaluationMapper {
    PerformanceEvaluation findByEvaluationCode(String evaluationCode);
    
    PerformanceEvaluation findById(Long id);
    
    List<PerformanceEvaluation> findAll();
    
    List<PerformanceEvaluation> findByStrategyCode(String strategyCode);
    
    List<PerformanceEvaluation> findByPortfolioCode(String portfolioCode);
    
    List<PerformanceEvaluation> findByEvaluatorId(Long evaluatorId);
    
    List<PerformanceEvaluation> findByEvaluationResult(String evaluationResult);
    
    List<PerformanceEvaluation> findByCondition(@Param("evaluationCode") String evaluationCode,
                                                 @Param("evaluationName") String evaluationName,
                                                 @Param("strategyCode") String strategyCode,
                                                 @Param("portfolioCode") String portfolioCode,
                                                 @Param("evaluationResult") String evaluationResult,
                                                 @Param("evaluatorId") Long evaluatorId);
    
    int insert(PerformanceEvaluation performanceEvaluation);
    
    int update(PerformanceEvaluation performanceEvaluation);
    
    int deleteById(Long id);
} 