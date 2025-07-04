package com.investech.controller;

import com.investech.common.Result;
import com.investech.entity.FundPortfolio;
import com.investech.service.FundPortfolioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/portfolio")
@CrossOrigin(origins = "*")
public class FundPortfolioController {
    
    @Autowired
    private FundPortfolioService fundPortfolioService;
    
    @GetMapping("/list")
    public Result<List<FundPortfolio>> getAllPortfolios() {
        List<FundPortfolio> portfolios = fundPortfolioService.findAll();
        return Result.success(portfolios);
    }
    
    @GetMapping("/{id}")
    public Result<FundPortfolio> getPortfolioById(@PathVariable Long id) {
        FundPortfolio portfolio = fundPortfolioService.findById(id);
        if (portfolio != null) {
            return Result.success(portfolio);
        } else {
            return Result.error("组合不存在");
        }
    }
    
    @GetMapping("/user/{userId}")
    public Result<List<FundPortfolio>> getPortfoliosByUserId(@PathVariable Long userId) {
        List<FundPortfolio> portfolios = fundPortfolioService.findByUserId(userId);
        return Result.success(portfolios);
    }
    
    @PostMapping("/create")
    public Result<String> createPortfolio(@RequestBody FundPortfolio portfolio) {
        if (fundPortfolioService.createPortfolio(portfolio)) {
            return Result.success("创建成功", null);
        } else {
            return Result.error("创建失败");
        }
    }
    
    @PutMapping("/update")
    public Result<String> updatePortfolio(@RequestBody FundPortfolio portfolio) {
        if (fundPortfolioService.updatePortfolio(portfolio)) {
            return Result.success("更新成功", null);
        } else {
            return Result.error("更新失败");
        }
    }
    
    @DeleteMapping("/{id}")
    public Result<String> deletePortfolio(@PathVariable Long id) {
        if (fundPortfolioService.deletePortfolio(id)) {
            return Result.success("删除成功", null);
        } else {
            return Result.error("删除失败");
        }
    }
} 