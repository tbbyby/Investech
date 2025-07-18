package com.investech.controller;

import com.investech.entity.ComplianceCheck;
import com.investech.service.ComplianceCheckService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/compliance-check")
@CrossOrigin(origins = "*")
public class ComplianceCheckController {
    
    @Autowired
    private ComplianceCheckService complianceCheckService;
    
    @GetMapping("/list")
    public Map<String, Object> getAllChecks() {
        Map<String, Object> result = new HashMap<>();
        try {
            List<ComplianceCheck> checks = complianceCheckService.getAllChecks();
            result.put("success", true);
            result.put("data", checks);
            result.put("message", "查询成功");
        } catch (Exception e) {
            result.put("success", false);
            result.put("message", "查询失败: " + e.getMessage());
        }
        return result;
    }
    
    @GetMapping("/{id}")
    public Map<String, Object> getCheckById(@PathVariable Long id) {
        Map<String, Object> result = new HashMap<>();
        try {
            ComplianceCheck check = complianceCheckService.getCheckById(id);
            result.put("success", true);
            result.put("data", check);
            result.put("message", "查询成功");
        } catch (Exception e) {
            result.put("success", false);
            result.put("message", "查询失败: " + e.getMessage());
        }
        return result;
    }
    
    @PostMapping("/search")
    public Map<String, Object> searchChecks(@RequestBody ComplianceCheck check) {
        Map<String, Object> result = new HashMap<>();
        try {
            List<ComplianceCheck> checks = complianceCheckService.getChecksByCondition(check);
            result.put("success", true);
            result.put("data", checks);
            result.put("message", "查询成功");
        } catch (Exception e) {
            result.put("success", false);
            result.put("message", "查询失败: " + e.getMessage());
        }
        return result;
    }
    
    @PostMapping("/add")
    public Map<String, Object> addCheck(@RequestBody ComplianceCheck check) {
        Map<String, Object> result = new HashMap<>();
        try {
            boolean success = complianceCheckService.addCheck(check);
            result.put("success", success);
            result.put("message", success ? "添加成功" : "添加失败");
        } catch (Exception e) {
            result.put("success", false);
            result.put("message", "添加失败: " + e.getMessage());
        }
        return result;
    }
    
    @PutMapping("/update")
    public Map<String, Object> updateCheck(@RequestBody ComplianceCheck check) {
        Map<String, Object> result = new HashMap<>();
        try {
            boolean success = complianceCheckService.updateCheck(check);
            result.put("success", success);
            result.put("message", success ? "更新成功" : "更新失败");
        } catch (Exception e) {
            result.put("success", false);
            result.put("message", "更新失败: " + e.getMessage());
        }
        return result;
    }
    
    @DeleteMapping("/{id}")
    public Map<String, Object> deleteCheck(@PathVariable Long id) {
        Map<String, Object> result = new HashMap<>();
        try {
            boolean success = complianceCheckService.deleteCheck(id);
            result.put("success", success);
            result.put("message", success ? "删除成功" : "删除失败");
        } catch (Exception e) {
            result.put("success", false);
            result.put("message", "删除失败: " + e.getMessage());
        }
        return result;
    }
    
    @PutMapping("/{id}/status")
    public Map<String, Object> updateCheckStatus(@PathVariable Long id, @RequestParam Integer status) {
        Map<String, Object> result = new HashMap<>();
        try {
            boolean success = complianceCheckService.updateCheckStatus(id, status);
            result.put("success", success);
            result.put("message", success ? "状态更新成功" : "状态更新失败");
        } catch (Exception e) {
            result.put("success", false);
            result.put("message", "状态更新失败: " + e.getMessage());
        }
        return result;
    }
} 