package com.investech.controller;

import com.investech.common.Result;
import com.investech.entity.Factor;
import com.investech.service.FactorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/factor")
@CrossOrigin(origins = "*")
public class FactorController {
    
    @Autowired
    private FactorService factorService;
    
    @GetMapping("/list")
    public Result<List<Factor>> getAllFactors() {
        List<Factor> factors = factorService.findAll();
        return Result.success(factors);
    }
    
    @GetMapping("/{id}")
    public Result<Factor> getFactorById(@PathVariable Long id) {
        Factor factor = factorService.findById(id);
        if (factor != null) {
            return Result.success(factor);
        } else {
            return Result.error("因子不存在");
        }
    }
    
    @GetMapping("/code/{factorCode}")
    public Result<Factor> getFactorByCode(@PathVariable String factorCode) {
        Factor factor = factorService.findByFactorCode(factorCode);
        if (factor != null) {
            return Result.success(factor);
        } else {
            return Result.error("因子不存在");
        }
    }
    
    @GetMapping("/type/{factorType}")
    public Result<List<Factor>> getFactorsByType(@PathVariable String factorType) {
        List<Factor> factors = factorService.findByType(factorType);
        return Result.success(factors);
    }
    
    @GetMapping("/category/{category}")
    public Result<List<Factor>> getFactorsByCategory(@PathVariable String category) {
        List<Factor> factors = factorService.findByCategory(category);
        return Result.success(factors);
    }
    
    @GetMapping("/parent/{parentCode}")
    public Result<List<Factor>> getFactorsByParent(@PathVariable String parentCode) {
        List<Factor> factors = factorService.findByParentCode(parentCode);
        return Result.success(factors);
    }
    
    @GetMapping("/search")
    public Result<List<Factor>> searchFactors(
            @RequestParam(required = false) String factorCode,
            @RequestParam(required = false) String factorName,
            @RequestParam(required = false) String factorType,
            @RequestParam(required = false) String category) {
        
        List<Factor> factors = factorService.searchFactors(factorCode, factorName, factorType, category);
        return Result.success(factors);
    }
    
    @PostMapping("/add")
    public Result<String> addFactor(@RequestBody Factor factor) {
        if (factorService.addFactor(factor)) {
            return Result.success("添加成功", null);
        } else {
            return Result.error("添加失败");
        }
    }
    
    @PutMapping("/update")
    public Result<String> updateFactor(@RequestBody Factor factor) {
        if (factorService.updateFactor(factor)) {
            return Result.success("更新成功", null);
        } else {
            return Result.error("更新失败");
        }
    }
    
    @DeleteMapping("/{id}")
    public Result<String> deleteFactor(@PathVariable Long id) {
        if (factorService.deleteFactor(id)) {
            return Result.success("删除成功", null);
        } else {
            return Result.error("删除失败");
        }
    }
} 