package com.investech.controller;

import com.investech.common.Result;
import com.investech.entity.AssetAllocation;
import com.investech.service.AssetAllocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/asset-allocation")
@CrossOrigin
public class AssetAllocationController {
    
    @Autowired
    private AssetAllocationService assetAllocationService;
    
    @GetMapping("/list")
    public Result<List<AssetAllocation>> getAllAllocations() {
        try {
            List<AssetAllocation> allocations = assetAllocationService.getAllAllocations();
            return Result.success(allocations);
        } catch (Exception e) {
            return Result.error("获取资产配置列表失败：" + e.getMessage());
        }
    }
    
    @GetMapping("/{id}")
    public Result<AssetAllocation> getAllocationById(@PathVariable Long id) {
        try {
            AssetAllocation allocation = assetAllocationService.getAllocationById(id);
            if (allocation != null) {
                return Result.success(allocation);
            } else {
                return Result.error("资产配置不存在");
            }
        } catch (Exception e) {
            return Result.error("获取资产配置失败：" + e.getMessage());
        }
    }
    
    @GetMapping("/client/{clientId}")
    public Result<List<AssetAllocation>> getAllocationsByClientId(@PathVariable Long clientId) {
        try {
            List<AssetAllocation> allocations = assetAllocationService.getAllocationsByClientId(clientId);
            return Result.success(allocations);
        } catch (Exception e) {
            return Result.error("获取客户资产配置失败：" + e.getMessage());
        }
    }
    
    @PostMapping("/search")
    public Result<List<AssetAllocation>> searchAllocations(@RequestBody AssetAllocation allocation) {
        try {
            List<AssetAllocation> allocations = assetAllocationService.getAllocationsByCondition(allocation);
            return Result.success(allocations);
        } catch (Exception e) {
            return Result.error("搜索资产配置失败：" + e.getMessage());
        }
    }
    
    @PostMapping("/add")
    public Result<String> addAllocation(@RequestBody AssetAllocation allocation) {
        try {
            if (assetAllocationService.addAllocation(allocation)) {
                return Result.success("添加资产配置成功");
            } else {
                return Result.error("添加资产配置失败");
            }
        } catch (Exception e) {
            return Result.error("添加资产配置失败：" + e.getMessage());
        }
    }
    
    @PutMapping("/update")
    public Result<String> updateAllocation(@RequestBody AssetAllocation allocation) {
        try {
            if (assetAllocationService.updateAllocation(allocation)) {
                return Result.success("更新资产配置成功");
            } else {
                return Result.error("更新资产配置失败");
            }
        } catch (Exception e) {
            return Result.error("更新资产配置失败：" + e.getMessage());
        }
    }
    
    @DeleteMapping("/{id}")
    public Result<String> deleteAllocation(@PathVariable Long id) {
        try {
            if (assetAllocationService.deleteAllocation(id)) {
                return Result.success("删除资产配置成功");
            } else {
                return Result.error("删除资产配置失败");
            }
        } catch (Exception e) {
            return Result.error("删除资产配置失败：" + e.getMessage());
        }
    }
    
    @PutMapping("/{id}/status")
    public Result<String> updateAllocationStatus(@PathVariable Long id, @RequestParam Integer status) {
        try {
            if (assetAllocationService.updateAllocationStatus(id, status)) {
                return Result.success("更新资产配置状态成功");
            } else {
                return Result.error("更新资产配置状态失败");
            }
        } catch (Exception e) {
            return Result.error("更新资产配置状态失败：" + e.getMessage());
        }
    }
} 