package com.investech.service.impl;

import com.investech.entity.PerformanceEvaluation;
import com.investech.mapper.PerformanceEvaluationMapper;
import com.investech.service.PerformanceEvaluationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class PerformanceEvaluationServiceImpl implements PerformanceEvaluationService {
    
    @Autowired
    private PerformanceEvaluationMapper performanceEvaluationMapper;
    
    @Override
    public List<PerformanceEvaluation> findAll() {
        return performanceEvaluationMapper.findAll();
    }
    
    @Override
    public PerformanceEvaluation findByEvaluationCode(String evaluationCode) {
        return performanceEvaluationMapper.findByEvaluationCode(evaluationCode);
    }
    
    @Override
    public PerformanceEvaluation findById(Long id) {
        return performanceEvaluationMapper.findById(id);
    }
    
    @Override
    public List<PerformanceEvaluation> findByStrategyCode(String strategyCode) {
        return performanceEvaluationMapper.findByStrategyCode(strategyCode);
    }
    
    @Override
    public List<PerformanceEvaluation> findByPortfolioCode(String portfolioCode) {
        return performanceEvaluationMapper.findByPortfolioCode(portfolioCode);
    }
    
    @Override
    public List<PerformanceEvaluation> findByEvaluatorId(Long evaluatorId) {
        return performanceEvaluationMapper.findByEvaluatorId(evaluatorId);
    }
    
    @Override
    public List<PerformanceEvaluation> findByEvaluationResult(String evaluationResult) {
        return performanceEvaluationMapper.findByEvaluationResult(evaluationResult);
    }
    
    @Override
    public List<PerformanceEvaluation> searchEvaluations(String evaluationCode, String evaluationName, String strategyCode, String portfolioCode, String evaluationResult, Long evaluatorId) {
        return performanceEvaluationMapper.findByCondition(evaluationCode, evaluationName, strategyCode, portfolioCode, evaluationResult, evaluatorId);
    }
    
    @Override
    public boolean createEvaluation(PerformanceEvaluation performanceEvaluation) {
        return performanceEvaluationMapper.insert(performanceEvaluation) > 0;
    }
    
    @Override
    public boolean updateEvaluation(PerformanceEvaluation performanceEvaluation) {
        return performanceEvaluationMapper.update(performanceEvaluation) > 0;
    }
    
    @Override
    public boolean deleteEvaluation(Long id) {
        return performanceEvaluationMapper.deleteById(id) > 0;
    }
} 