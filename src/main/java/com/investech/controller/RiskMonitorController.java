package com.investech.controller;

import com.investech.entity.RiskMonitor;
import com.investech.service.RiskMonitorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/risk-monitor")
@CrossOrigin(origins = "*")
public class RiskMonitorController {
    
    @Autowired
    private RiskMonitorService riskMonitorService;
    
    @GetMapping("/list")
    public Map<String, Object> getAllMonitors() {
        Map<String, Object> result = new HashMap<>();
        try {
            List<RiskMonitor> monitors = riskMonitorService.getAllMonitors();
            result.put("success", true);
            result.put("data", monitors);
            result.put("message", "查询成功");
        } catch (Exception e) {
            result.put("success", false);
            result.put("message", "查询失败: " + e.getMessage());
        }
        return result;
    }
    
    @GetMapping("/{id}")
    public Map<String, Object> getMonitorById(@PathVariable Long id) {
        Map<String, Object> result = new HashMap<>();
        try {
            RiskMonitor monitor = riskMonitorService.getMonitorById(id);
            result.put("success", true);
            result.put("data", monitor);
            result.put("message", "查询成功");
        } catch (Exception e) {
            result.put("success", false);
            result.put("message", "查询失败: " + e.getMessage());
        }
        return result;
    }
    
    @PostMapping("/search")
    public Map<String, Object> searchMonitors(@RequestBody RiskMonitor monitor) {
        Map<String, Object> result = new HashMap<>();
        try {
            List<RiskMonitor> monitors = riskMonitorService.getMonitorsByCondition(monitor);
            result.put("success", true);
            result.put("data", monitors);
            result.put("message", "查询成功");
        } catch (Exception e) {
            result.put("success", false);
            result.put("message", "查询失败: " + e.getMessage());
        }
        return result;
    }
    
    @PostMapping("/add")
    public Map<String, Object> addMonitor(@RequestBody RiskMonitor monitor) {
        Map<String, Object> result = new HashMap<>();
        try {
            boolean success = riskMonitorService.addMonitor(monitor);
            result.put("success", success);
            result.put("message", success ? "添加成功" : "添加失败");
        } catch (Exception e) {
            result.put("success", false);
            result.put("message", "添加失败: " + e.getMessage());
        }
        return result;
    }
    
    @PutMapping("/update")
    public Map<String, Object> updateMonitor(@RequestBody RiskMonitor monitor) {
        Map<String, Object> result = new HashMap<>();
        try {
            boolean success = riskMonitorService.updateMonitor(monitor);
            result.put("success", success);
            result.put("message", success ? "更新成功" : "更新失败");
        } catch (Exception e) {
            result.put("success", false);
            result.put("message", "更新失败: " + e.getMessage());
        }
        return result;
    }
    
    @DeleteMapping("/{id}")
    public Map<String, Object> deleteMonitor(@PathVariable Long id) {
        Map<String, Object> result = new HashMap<>();
        try {
            boolean success = riskMonitorService.deleteMonitor(id);
            result.put("success", success);
            result.put("message", success ? "删除成功" : "删除失败");
        } catch (Exception e) {
            result.put("success", false);
            result.put("message", "删除失败: " + e.getMessage());
        }
        return result;
    }
    
    @PutMapping("/{id}/status")
    public Map<String, Object> updateMonitorStatus(@PathVariable Long id, @RequestParam Integer status) {
        Map<String, Object> result = new HashMap<>();
        try {
            boolean success = riskMonitorService.updateMonitorStatus(id, status);
            result.put("success", success);
            result.put("message", success ? "状态更新成功" : "状态更新失败");
        } catch (Exception e) {
            result.put("success", false);
            result.put("message", "状态更新失败: " + e.getMessage());
        }
        return result;
    }
} 