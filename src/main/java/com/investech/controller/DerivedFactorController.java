package com.investech.controller;

import com.investech.common.Result;
import com.investech.entity.DerivedFactor;
import com.investech.service.DerivedFactorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/derived-factor")
@CrossOrigin(origins = "*")
public class DerivedFactorController {
    
    @Autowired
    private DerivedFactorService derivedFactorService;
    
    @GetMapping("/list")
    public Result<List<DerivedFactor>> getAllFactors() {
        List<DerivedFactor> factors = derivedFactorService.findAll();
        return Result.success(factors);
    }
    
    @GetMapping("/{id}")
    public Result<DerivedFactor> getFactorById(@PathVariable Long id) {
        DerivedFactor factor = derivedFactorService.findById(id);
        if (factor != null) {
            return Result.success(factor);
        } else {
            return Result.error("衍生因子不存在");
        }
    }
    
    @GetMapping("/code/{factorCode}")
    public Result<DerivedFactor> getFactorByCode(@PathVariable String factorCode) {
        DerivedFactor factor = derivedFactorService.findByFactorCode(factorCode);
        if (factor != null) {
            return Result.success(factor);
        } else {
            return Result.error("衍生因子不存在");
        }
    }
    
    @GetMapping("/creator/{creatorId}")
    public Result<List<DerivedFactor>> getFactorsByCreator(@PathVariable Long creatorId) {
        List<DerivedFactor> factors = derivedFactorService.findByCreatorId(creatorId);
        return Result.success(factors);
    }
    
    @GetMapping("/category/{category}")
    public Result<List<DerivedFactor>> getFactorsByCategory(@PathVariable String category) {
        List<DerivedFactor> factors = derivedFactorService.findByCategory(category);
        return Result.success(factors);
    }
    
    @GetMapping("/search")
    public Result<List<DerivedFactor>> searchFactors(
            @RequestParam(required = false) String factorCode,
            @RequestParam(required = false) String factorName,
            @RequestParam(required = false) String category,
            @RequestParam(required = false) Long creatorId) {
        
        List<DerivedFactor> factors = derivedFactorService.searchFactors(factorCode, factorName, category, creatorId);
        return Result.success(factors);
    }
    
    @PostMapping("/create")
    public Result<String> createFactor(@RequestBody DerivedFactor derivedFactor) {
        if (derivedFactorService.createFactor(derivedFactor)) {
            return Result.success("创建成功", null);
        } else {
            return Result.error("创建失败");
        }
    }
    
    @PutMapping("/update")
    public Result<String> updateFactor(@RequestBody DerivedFactor derivedFactor) {
        if (derivedFactorService.updateFactor(derivedFactor)) {
            return Result.success("更新成功", null);
        } else {
            return Result.error("更新失败");
        }
    }
    
    @DeleteMapping("/{id}")
    public Result<String> deleteFactor(@PathVariable Long id) {
        if (derivedFactorService.deleteFactor(id)) {
            return Result.success("删除成功", null);
        } else {
            return Result.error("删除失败");
        }
    }
} 