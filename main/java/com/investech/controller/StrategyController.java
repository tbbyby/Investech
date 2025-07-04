package com.investech.controller;

import com.investech.common.Result;
import com.investech.entity.Strategy;
import com.investech.service.StrategyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/strategy")
@CrossOrigin(origins = "*")
public class StrategyController {
    
    @Autowired
    private StrategyService strategyService;
    
    @GetMapping("/list")
    public Result<List<Strategy>> getAllStrategies() {
        List<Strategy> strategies = strategyService.findAll();
        return Result.success(strategies);
    }
    
    @GetMapping("/{id}")
    public Result<Strategy> getStrategyById(@PathVariable Long id) {
        Strategy strategy = strategyService.findById(id);
        if (strategy != null) {
            return Result.success(strategy);
        } else {
            return Result.error("策略不存在");
        }
    }
    
    @GetMapping("/code/{strategyCode}")
    public Result<Strategy> getStrategyByCode(@PathVariable String strategyCode) {
        Strategy strategy = strategyService.findByStrategyCode(strategyCode);
        if (strategy != null) {
            return Result.success(strategy);
        } else {
            return Result.error("策略不存在");
        }
    }
    
    @GetMapping("/creator/{creatorId}")
    public Result<List<Strategy>> getStrategiesByCreator(@PathVariable Long creatorId) {
        List<Strategy> strategies = strategyService.findByCreatorId(creatorId);
        return Result.success(strategies);
    }
    
    @GetMapping("/type/{strategyType}")
    public Result<List<Strategy>> getStrategiesByType(@PathVariable String strategyType) {
        List<Strategy> strategies = strategyService.findByType(strategyType);
        return Result.success(strategies);
    }
    
    @GetMapping("/style/{investmentStyle}")
    public Result<List<Strategy>> getStrategiesByStyle(@PathVariable String investmentStyle) {
        List<Strategy> strategies = strategyService.findByInvestmentStyle(investmentStyle);
        return Result.success(strategies);
    }
    
    @GetMapping("/risk/{riskLevel}")
    public Result<List<Strategy>> getStrategiesByRiskLevel(@PathVariable String riskLevel) {
        List<Strategy> strategies = strategyService.findByRiskLevel(riskLevel);
        return Result.success(strategies);
    }
    
    @GetMapping("/search")
    public Result<List<Strategy>> searchStrategies(
            @RequestParam(required = false) String strategyCode,
            @RequestParam(required = false) String strategyName,
            @RequestParam(required = false) String strategyType,
            @RequestParam(required = false) String investmentStyle,
            @RequestParam(required = false) String riskLevel,
            @RequestParam(required = false) Long creatorId) {
        
        List<Strategy> strategies = strategyService.searchStrategies(strategyCode, strategyName, 
                                                                    strategyType, investmentStyle, riskLevel, creatorId);
        return Result.success(strategies);
    }
    
    @PostMapping("/create")
    public Result<String> createStrategy(@RequestBody Strategy strategy) {
        if (strategyService.createStrategy(strategy)) {
            return Result.success("创建成功", null);
        } else {
            return Result.error("创建失败");
        }
    }
    
    @PutMapping("/update")
    public Result<String> updateStrategy(@RequestBody Strategy strategy) {
        if (strategyService.updateStrategy(strategy)) {
            return Result.success("更新成功", null);
        } else {
            return Result.error("更新失败");
        }
    }
    
    @DeleteMapping("/{id}")
    public Result<String> deleteStrategy(@PathVariable Long id) {
        if (strategyService.deleteStrategy(id)) {
            return Result.success("删除成功", null);
        } else {
            return Result.error("删除失败");
        }
    }
} 