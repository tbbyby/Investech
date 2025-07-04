package com.investech.controller;

import com.investech.common.Result;
import com.investech.entity.PortfolioOptimization;
import com.investech.service.PortfolioOptimizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/portfolio-optimization")
@CrossOrigin
public class PortfolioOptimizationController {
    
    @Autowired
    private PortfolioOptimizationService portfolioOptimizationService;
    
    @GetMapping("/list")
    public Result<List<PortfolioOptimization>> getAllOptimizations() {
        try {
            List<PortfolioOptimization> optimizations = portfolioOptimizationService.getAllOptimizations();
            return Result.success(optimizations);
        } catch (Exception e) {
            return Result.error("获取投资组合优化列表失败：" + e.getMessage());
        }
    }
    
    @GetMapping("/{id}")
    public Result<PortfolioOptimization> getOptimizationById(@PathVariable Long id) {
        try {
            PortfolioOptimization optimization = portfolioOptimizationService.getOptimizationById(id);
            if (optimization != null) {
                return Result.success(optimization);
            } else {
                return Result.error("投资组合优化不存在");
            }
        } catch (Exception e) {
            return Result.error("获取投资组合优化失败：" + e.getMessage());
        }
    }
    
    @GetMapping("/client/{clientId}")
    public Result<List<PortfolioOptimization>> getOptimizationsByClientId(@PathVariable Long clientId) {
        try {
            List<PortfolioOptimization> optimizations = portfolioOptimizationService.getOptimizationsByClientId(clientId);
            return Result.success(optimizations);
        } catch (Exception e) {
            return Result.error("获取客户投资组合优化失败：" + e.getMessage());
        }
    }
    
    @PostMapping("/search")
    public Result<List<PortfolioOptimization>> searchOptimizations(@RequestBody PortfolioOptimization optimization) {
        try {
            List<PortfolioOptimization> optimizations = portfolioOptimizationService.getOptimizationsByCondition(optimization);
            return Result.success(optimizations);
        } catch (Exception e) {
            return Result.error("搜索投资组合优化失败：" + e.getMessage());
        }
    }
    
    @PostMapping("/add")
    public Result<String> addOptimization(@RequestBody PortfolioOptimization optimization) {
        try {
            if (portfolioOptimizationService.addOptimization(optimization)) {
                return Result.success("添加投资组合优化成功");
            } else {
                return Result.error("添加投资组合优化失败");
            }
        } catch (Exception e) {
            return Result.error("添加投资组合优化失败：" + e.getMessage());
        }
    }
    
    @PutMapping("/update")
    public Result<String> updateOptimization(@RequestBody PortfolioOptimization optimization) {
        try {
            if (portfolioOptimizationService.updateOptimization(optimization)) {
                return Result.success("更新投资组合优化成功");
            } else {
                return Result.error("更新投资组合优化失败");
            }
        } catch (Exception e) {
            return Result.error("更新投资组合优化失败：" + e.getMessage());
        }
    }
    
    @DeleteMapping("/{id}")
    public Result<String> deleteOptimization(@PathVariable Long id) {
        try {
            if (portfolioOptimizationService.deleteOptimization(id)) {
                return Result.success("删除投资组合优化成功");
            } else {
                return Result.error("删除投资组合优化失败");
            }
        } catch (Exception e) {
            return Result.error("删除投资组合优化失败：" + e.getMessage());
        }
    }
    
    @PutMapping("/{id}/status")
    public Result<String> updateOptimizationStatus(@PathVariable Long id, @RequestParam Integer status) {
        try {
            if (portfolioOptimizationService.updateOptimizationStatus(id, status)) {
                return Result.success("更新投资组合优化状态成功");
            } else {
                return Result.error("更新投资组合优化状态失败");
            }
        } catch (Exception e) {
            return Result.error("更新投资组合优化状态失败：" + e.getMessage());
        }
    }
} 