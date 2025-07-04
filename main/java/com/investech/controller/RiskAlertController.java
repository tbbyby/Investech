package com.investech.controller;

import com.investech.entity.RiskAlert;
import com.investech.service.RiskAlertService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/risk-alert")
@CrossOrigin(origins = "*")
public class RiskAlertController {
    
    @Autowired
    private RiskAlertService riskAlertService;
    
    @GetMapping("/list")
    public Map<String, Object> getAllAlerts() {
        Map<String, Object> result = new HashMap<>();
        try {
            List<RiskAlert> alerts = riskAlertService.getAllAlerts();
            result.put("success", true);
            result.put("data", alerts);
            result.put("message", "查询成功");
        } catch (Exception e) {
            result.put("success", false);
            result.put("message", "查询失败: " + e.getMessage());
        }
        return result;
    }
    
    @GetMapping("/{id}")
    public Map<String, Object> getAlertById(@PathVariable Long id) {
        Map<String, Object> result = new HashMap<>();
        try {
            RiskAlert alert = riskAlertService.getAlertById(id);
            result.put("success", true);
            result.put("data", alert);
            result.put("message", "查询成功");
        } catch (Exception e) {
            result.put("success", false);
            result.put("message", "查询失败: " + e.getMessage());
        }
        return result;
    }
    
    @PostMapping("/search")
    public Map<String, Object> searchAlerts(@RequestBody RiskAlert alert) {
        Map<String, Object> result = new HashMap<>();
        try {
            List<RiskAlert> alerts = riskAlertService.getAlertsByCondition(alert);
            result.put("success", true);
            result.put("data", alerts);
            result.put("message", "查询成功");
        } catch (Exception e) {
            result.put("success", false);
            result.put("message", "查询失败: " + e.getMessage());
        }
        return result;
    }
    
    @PostMapping("/add")
    public Map<String, Object> addAlert(@RequestBody RiskAlert alert) {
        Map<String, Object> result = new HashMap<>();
        try {
            boolean success = riskAlertService.addAlert(alert);
            result.put("success", success);
            result.put("message", success ? "添加成功" : "添加失败");
        } catch (Exception e) {
            result.put("success", false);
            result.put("message", "添加失败: " + e.getMessage());
        }
        return result;
    }
    
    @PutMapping("/update")
    public Map<String, Object> updateAlert(@RequestBody RiskAlert alert) {
        Map<String, Object> result = new HashMap<>();
        try {
            boolean success = riskAlertService.updateAlert(alert);
            result.put("success", success);
            result.put("message", success ? "更新成功" : "更新失败");
        } catch (Exception e) {
            result.put("success", false);
            result.put("message", "更新失败: " + e.getMessage());
        }
        return result;
    }
    
    @DeleteMapping("/{id}")
    public Map<String, Object> deleteAlert(@PathVariable Long id) {
        Map<String, Object> result = new HashMap<>();
        try {
            boolean success = riskAlertService.deleteAlert(id);
            result.put("success", success);
            result.put("message", success ? "删除成功" : "删除失败");
        } catch (Exception e) {
            result.put("success", false);
            result.put("message", "删除失败: " + e.getMessage());
        }
        return result;
    }
    
    @PutMapping("/{id}/status")
    public Map<String, Object> updateAlertStatus(@PathVariable Long id, @RequestParam Integer status) {
        Map<String, Object> result = new HashMap<>();
        try {
            boolean success = riskAlertService.updateAlertStatus(id, status);
            result.put("success", success);
            result.put("message", success ? "状态更新成功" : "状态更新失败");
        } catch (Exception e) {
            result.put("success", false);
            result.put("message", "状态更新失败: " + e.getMessage());
        }
        return result;
    }
} 