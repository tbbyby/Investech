package com.investech.controller;

import com.investech.common.Result;
import com.investech.entity.Fund;
import com.investech.service.FundService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/fund")
@CrossOrigin(origins = "*")
public class FundController {
    
    @Autowired
    private FundService fundService;
    
    @GetMapping("/list")
    public Result<List<Fund>> getAllFunds() {
        List<Fund> funds = fundService.findAll();
        return Result.success(funds);
    }
    
    @GetMapping("/{id}")
    public Result<Fund> getFundById(@PathVariable Long id) {
        Fund fund = fundService.findById(id);
        if (fund != null) {
            return Result.success(fund);
        } else {
            return Result.error("基金不存在");
        }
    }
    
    @GetMapping("/code/{fundCode}")
    public Result<Fund> getFundByCode(@PathVariable String fundCode) {
        Fund fund = fundService.findByFundCode(fundCode);
        if (fund != null) {
            return Result.success(fund);
        } else {
            return Result.error("基金不存在");
        }
    }
    
    @GetMapping("/search")
    public Result<List<Fund>> searchFunds(
            @RequestParam(required = false) String fundCode,
            @RequestParam(required = false) String fundName,
            @RequestParam(required = false) String fundCompany,
            @RequestParam(required = false) String fundManager,
            @RequestParam(required = false) String fundType,
            @RequestParam(required = false) String riskLevel,
            @RequestParam(required = false) String tags) {
        
        List<Fund> funds = fundService.searchFunds(fundCode, fundName, fundCompany, 
                                                  fundManager, fundType, riskLevel, tags);
        return Result.success(funds);
    }
    
    @GetMapping("/company/{fundCompany}")
    public Result<List<Fund>> getFundsByCompany(@PathVariable String fundCompany) {
        List<Fund> funds = fundService.findByCompany(fundCompany);
        return Result.success(funds);
    }
    
    @GetMapping("/manager/{fundManager}")
    public Result<List<Fund>> getFundsByManager(@PathVariable String fundManager) {
        List<Fund> funds = fundService.findByManager(fundManager);
        return Result.success(funds);
    }
    
    @PostMapping("/add")
    public Result<String> addFund(@RequestBody Fund fund) {
        if (fundService.addFund(fund)) {
            return Result.success("添加成功", null);
        } else {
            return Result.error("添加失败");
        }
    }
    
    @PutMapping("/update")
    public Result<String> updateFund(@RequestBody Fund fund) {
        if (fundService.updateFund(fund)) {
            return Result.success("更新成功", null);
        } else {
            return Result.error("更新失败");
        }
    }
    
    @DeleteMapping("/{id}")
    public Result<String> deleteFund(@PathVariable Long id) {
        if (fundService.deleteFund(id)) {
            return Result.success("删除成功", null);
        } else {
            return Result.error("删除失败");
        }
    }
} 