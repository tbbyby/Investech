package com.investech.controller;

import com.investech.entity.RiskReport;
import com.investech.service.RiskReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/risk-report")
@CrossOrigin(origins = "*")
public class RiskReportController {
    
    @Autowired
    private RiskReportService riskReportService;
    
    @GetMapping("/list")
    public Map<String, Object> getAllReports() {
        Map<String, Object> result = new HashMap<>();
        try {
            List<RiskReport> reports = riskReportService.getAllReports();
            result.put("success", true);
            result.put("data", reports);
            result.put("message", "查询成功");
        } catch (Exception e) {
            result.put("success", false);
            result.put("message", "查询失败: " + e.getMessage());
        }
        return result;
    }
    
    @GetMapping("/{id}")
    public Map<String, Object> getReportById(@PathVariable Long id) {
        Map<String, Object> result = new HashMap<>();
        try {
            RiskReport report = riskReportService.getReportById(id);
            result.put("success", true);
            result.put("data", report);
            result.put("message", "查询成功");
        } catch (Exception e) {
            result.put("success", false);
            result.put("message", "查询失败: " + e.getMessage());
        }
        return result;
    }
    
    @PostMapping("/search")
    public Map<String, Object> searchReports(@RequestBody RiskReport report) {
        Map<String, Object> result = new HashMap<>();
        try {
            List<RiskReport> reports = riskReportService.getReportsByCondition(report);
            result.put("success", true);
            result.put("data", reports);
            result.put("message", "查询成功");
        } catch (Exception e) {
            result.put("success", false);
            result.put("message", "查询失败: " + e.getMessage());
        }
        return result;
    }
    
    @PostMapping("/add")
    public Map<String, Object> addReport(@RequestBody RiskReport report) {
        Map<String, Object> result = new HashMap<>();
        try {
            boolean success = riskReportService.addReport(report);
            result.put("success", success);
            result.put("message", success ? "添加成功" : "添加失败");
        } catch (Exception e) {
            result.put("success", false);
            result.put("message", "添加失败: " + e.getMessage());
        }
        return result;
    }
    
    @PutMapping("/update")
    public Map<String, Object> updateReport(@RequestBody RiskReport report) {
        Map<String, Object> result = new HashMap<>();
        try {
            boolean success = riskReportService.updateReport(report);
            result.put("success", success);
            result.put("message", success ? "更新成功" : "更新失败");
        } catch (Exception e) {
            result.put("success", false);
            result.put("message", "更新失败: " + e.getMessage());
        }
        return result;
    }
    
    @DeleteMapping("/{id}")
    public Map<String, Object> deleteReport(@PathVariable Long id) {
        Map<String, Object> result = new HashMap<>();
        try {
            boolean success = riskReportService.deleteReport(id);
            result.put("success", success);
            result.put("message", success ? "删除成功" : "删除失败");
        } catch (Exception e) {
            result.put("success", false);
            result.put("message", "删除失败: " + e.getMessage());
        }
        return result;
    }
    
    @PutMapping("/{id}/status")
    public Map<String, Object> updateReportStatus(@PathVariable Long id, @RequestParam Integer status) {
        Map<String, Object> result = new HashMap<>();
        try {
            boolean success = riskReportService.updateReportStatus(id, status);
            result.put("success", success);
            result.put("message", success ? "状态更新成功" : "状态更新失败");
        } catch (Exception e) {
            result.put("success", false);
            result.put("message", "状态更新失败: " + e.getMessage());
        }
        return result;
    }
} 