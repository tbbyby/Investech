package com.investech.controller;

import com.investech.common.Result;
import com.investech.entity.RiskManagement;
import com.investech.service.RiskManagementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/risk")
@CrossOrigin(origins = "*")
public class RiskManagementController {
    
    @Autowired
    private RiskManagementService riskManagementService;
    
    @GetMapping("/list")
    public Result<List<RiskManagement>> getAllRisks() {
        List<RiskManagement> risks = riskManagementService.findAll();
        return Result.success(risks);
    }
    
    @GetMapping("/{id}")
    public Result<RiskManagement> getRiskById(@PathVariable Long id) {
        RiskManagement risk = riskManagementService.findById(id);
        if (risk != null) {
            return Result.success(risk);
        } else {
            return Result.error("风险记录不存在");
        }
    }
    
    @GetMapping("/type/{riskType}")
    public Result<List<RiskManagement>> getRisksByType(@PathVariable String riskType) {
        List<RiskManagement> risks = riskManagementService.findByRiskType(riskType);
        return Result.success(risks);
    }
    
    @GetMapping("/level/{riskLevel}")
    public Result<List<RiskManagement>> getRisksByLevel(@PathVariable String riskLevel) {
        List<RiskManagement> risks = riskManagementService.findByRiskLevel(riskLevel);
        return Result.success(risks);
    }
    
    @PostMapping("/create")
    public Result<String> createRisk(@RequestBody RiskManagement riskManagement) {
        if (riskManagementService.createRisk(riskManagement)) {
            return Result.success("创建成功", null);
        } else {
            return Result.error("创建失败");
        }
    }
    
    @PutMapping("/update")
    public Result<String> updateRisk(@RequestBody RiskManagement riskManagement) {
        if (riskManagementService.updateRisk(riskManagement)) {
            return Result.success("更新成功", null);
        } else {
            return Result.error("更新失败");
        }
    }
    
    @DeleteMapping("/{id}")
    public Result<String> deleteRisk(@PathVariable Long id) {
        if (riskManagementService.deleteRisk(id)) {
            return Result.success("删除成功", null);
        } else {
            return Result.error("删除失败");
        }
    }
} 