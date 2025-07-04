package com.investech.service;

import com.investech.entity.PerformanceEvaluation;
import java.util.List;

public interface PerformanceEvaluationService {
    List<PerformanceEvaluation> findAll();
    
    PerformanceEvaluation findByEvaluationCode(String evaluationCode);
    
    PerformanceEvaluation findById(Long id);
    
    List<PerformanceEvaluation> findByStrategyCode(String strategyCode);
    
    List<PerformanceEvaluation> findByPortfolioCode(String portfolioCode);
    
    List<PerformanceEvaluation> findByEvaluatorId(Long evaluatorId);
    
    List<PerformanceEvaluation> findByEvaluationResult(String evaluationResult);
    
    List<PerformanceEvaluation> searchEvaluations(String evaluationCode, String evaluationName, String strategyCode, String portfolioCode, String evaluationResult, Long evaluatorId);
    
    boolean createEvaluation(PerformanceEvaluation performanceEvaluation);
    
    boolean updateEvaluation(PerformanceEvaluation performanceEvaluation);
    
    boolean deleteEvaluation(Long id);
} 