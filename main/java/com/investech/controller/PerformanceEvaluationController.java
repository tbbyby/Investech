package com.investech.controller;

import com.investech.common.Result;
import com.investech.entity.PerformanceEvaluation;
import com.investech.service.PerformanceEvaluationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/performance")
@CrossOrigin(origins = "*")
public class PerformanceEvaluationController {
    
    @Autowired
    private PerformanceEvaluationService performanceEvaluationService;
    
    @GetMapping("/list")
    public Result<List<PerformanceEvaluation>> getAllEvaluations() {
        List<PerformanceEvaluation> evaluations = performanceEvaluationService.findAll();
        return Result.success(evaluations);
    }
    
    @GetMapping("/{id}")
    public Result<PerformanceEvaluation> getEvaluationById(@PathVariable Long id) {
        PerformanceEvaluation evaluation = performanceEvaluationService.findById(id);
        if (evaluation != null) {
            return Result.success(evaluation);
        } else {
            return Result.error("绩效评估记录不存在");
        }
    }
    
    @GetMapping("/strategy/{strategyCode}")
    public Result<List<PerformanceEvaluation>> getEvaluationsByStrategy(@PathVariable String strategyCode) {
        List<PerformanceEvaluation> evaluations = performanceEvaluationService.findByStrategyCode(strategyCode);
        return Result.success(evaluations);
    }
    
    @GetMapping("/portfolio/{portfolioCode}")
    public Result<List<PerformanceEvaluation>> getEvaluationsByPortfolio(@PathVariable String portfolioCode) {
        List<PerformanceEvaluation> evaluations = performanceEvaluationService.findByPortfolioCode(portfolioCode);
        return Result.success(evaluations);
    }
    
    @GetMapping("/result/{evaluationResult}")
    public Result<List<PerformanceEvaluation>> getEvaluationsByResult(@PathVariable String evaluationResult) {
        List<PerformanceEvaluation> evaluations = performanceEvaluationService.findByEvaluationResult(evaluationResult);
        return Result.success(evaluations);
    }
    
    @PostMapping("/create")
    public Result<String> createEvaluation(@RequestBody PerformanceEvaluation performanceEvaluation) {
        if (performanceEvaluationService.createEvaluation(performanceEvaluation)) {
            return Result.success("创建成功", null);
        } else {
            return Result.error("创建失败");
        }
    }
    
    @PutMapping("/update")
    public Result<String> updateEvaluation(@RequestBody PerformanceEvaluation performanceEvaluation) {
        if (performanceEvaluationService.updateEvaluation(performanceEvaluation)) {
            return Result.success("更新成功", null);
        } else {
            return Result.error("更新失败");
        }
    }
    
    @DeleteMapping("/{id}")
    public Result<String> deleteEvaluation(@PathVariable Long id) {
        if (performanceEvaluationService.deleteEvaluation(id)) {
            return Result.success("删除成功", null);
        } else {
            return Result.error("删除失败");
        }
    }
} 