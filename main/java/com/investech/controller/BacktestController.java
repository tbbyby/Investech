package com.investech.controller;

import com.investech.common.Result;
import com.investech.entity.Backtest;
import com.investech.service.BacktestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/backtest")
@CrossOrigin(origins = "*")
public class BacktestController {
    
    @Autowired
    private BacktestService backtestService;
    
    @GetMapping("/list")
    public Result<List<Backtest>> getAllBacktests() {
        List<Backtest> backtests = backtestService.findAll();
        return Result.success(backtests);
    }
    
    @GetMapping("/{id}")
    public Result<Backtest> getBacktestById(@PathVariable Long id) {
        Backtest backtest = backtestService.findById(id);
        if (backtest != null) {
            return Result.success(backtest);
        } else {
            return Result.error("回测不存在");
        }
    }
    
    @GetMapping("/strategy/{strategyCode}")
    public Result<List<Backtest>> getBacktestsByStrategy(@PathVariable String strategyCode) {
        List<Backtest> backtests = backtestService.findByStrategyCode(strategyCode);
        return Result.success(backtests);
    }
    
    @PostMapping("/create")
    public Result<String> createBacktest(@RequestBody Backtest backtest) {
        if (backtestService.createBacktest(backtest)) {
            return Result.success("创建成功", null);
        } else {
            return Result.error("创建失败");
        }
    }
    
    @PutMapping("/update")
    public Result<String> updateBacktest(@RequestBody Backtest backtest) {
        if (backtestService.updateBacktest(backtest)) {
            return Result.success("更新成功", null);
        } else {
            return Result.error("更新失败");
        }
    }
    
    @DeleteMapping("/{id}")
    public Result<String> deleteBacktest(@PathVariable Long id) {
        if (backtestService.deleteBacktest(id)) {
            return Result.success("删除成功", null);
        } else {
            return Result.error("删除失败");
        }
    }
} 