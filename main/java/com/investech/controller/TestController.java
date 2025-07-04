package com.investech.controller;

import com.investech.entity.AssetAllocation;
import com.investech.service.AssetAllocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/test")
public class TestController {
    
    @Autowired
    private AssetAllocationService assetAllocationService;
    
    @GetMapping("/asset-allocation")
    public String testAssetAllocation() {
        try {
            List<AssetAllocation> list = assetAllocationService.getAllAllocations();
            return "查询成功，数据条数: " + list.size() + ", 数据: " + list;
        } catch (Exception e) {
            return "查询失败: " + e.getMessage();
        }
    }
} 