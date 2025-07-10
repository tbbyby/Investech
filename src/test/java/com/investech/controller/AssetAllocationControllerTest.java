package com.investech.controller;

import com.investech.common.Result;
import com.investech.entity.AssetAllocation;
import com.investech.service.AssetAllocationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AssetAllocationControllerTest {

    @Mock
    private AssetAllocationService assetAllocationService;

    @InjectMocks
    private AssetAllocationController assetAllocationController;

    private AssetAllocation testAllocation;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        // 初始化测试数据
        testAllocation = new AssetAllocation();
        testAllocation.setId(1L);
        testAllocation.setClientId(1001L);
        testAllocation.setAllocationName("Test Allocation");
        testAllocation.setAssetClass("股票");
        testAllocation.setAllocationRatio(60.0);
        testAllocation.setCurrentValue(100000.0);
        testAllocation.setTargetValue(120000.0);
        testAllocation.setFundCodes("FUND001,FUND002");
        testAllocation.setDescription("Test description");
        testAllocation.setCreateTime(LocalDateTime.now());
        testAllocation.setUpdateTime(LocalDateTime.now());
        testAllocation.setStatus(1);
    }

    @Test
    public void getAllAllocations_success() {
        // 准备测试数据
        List<AssetAllocation> allocations = Arrays.asList(testAllocation);

        // 模拟服务层返回
        when(assetAllocationService.getAllAllocations()).thenReturn(allocations);

        // 调用控制器方法
        Result<List<AssetAllocation>> result = assetAllocationController.getAllAllocations();

        // 验证结果
        assertNotNull(result);
        assertEquals(200, result.getCode().intValue());
        assertEquals("成功", result.getMessage());
        assertEquals(allocations, result.getData());
        verify(assetAllocationService, times(1)).getAllAllocations();
    }

    @Test
    public void getAllAllocations_failure() {
        // 模拟异常
        when(assetAllocationService.getAllAllocations()).thenThrow(new RuntimeException("Database error"));

        // 调用控制器方法
        Result<List<AssetAllocation>> result = assetAllocationController.getAllAllocations();

        // 验证结果
        assertNotNull(result);
        assertEquals(500, result.getCode().intValue());
        assertTrue(result.getMessage().contains("获取资产配置列表失败"));
        assertNull(result.getData());
        verify(assetAllocationService, times(1)).getAllAllocations();
    }

    @Test
    public void getAllocationById_success() {
        // 模拟服务层返回
        when(assetAllocationService.getAllocationById(1L)).thenReturn(testAllocation);

        // 调用控制器方法
        Result<AssetAllocation> result = assetAllocationController.getAllocationById(1L);

        // 验证结果
        assertNotNull(result);
        assertEquals(200, result.getCode().intValue());
        assertEquals("成功", result.getMessage());
        assertEquals(testAllocation, result.getData());
        verify(assetAllocationService, times(1)).getAllocationById(1L);
    }

    @Test
    public void getAllocationById_notFound() {
        // 模拟服务层返回null
        when(assetAllocationService.getAllocationById(1L)).thenReturn(null);

        // 调用控制器方法
        Result<AssetAllocation> result = assetAllocationController.getAllocationById(1L);

        // 验证结果
        assertNotNull(result);
        assertEquals(500, result.getCode().intValue());
        assertEquals("资产配置不存在", result.getMessage());
        assertNull(result.getData());
        verify(assetAllocationService, times(1)).getAllocationById(1L);
    }

    @Test
    public void getAllocationById_failure() {
        // 模拟异常
        when(assetAllocationService.getAllocationById(1L)).thenThrow(new RuntimeException("Database error"));

        // 调用控制器方法
        Result<AssetAllocation> result = assetAllocationController.getAllocationById(1L);

        // 验证结果
        assertNotNull(result);
        assertEquals(500, result.getCode().intValue());
        assertTrue(result.getMessage().contains("获取资产配置失败"));
        assertNull(result.getData());
        verify(assetAllocationService, times(1)).getAllocationById(1L);
    }

    @Test
    public void getAllocationsByClientId_success() {
        // 准备测试数据
        List<AssetAllocation> allocations = Arrays.asList(testAllocation);

        // 模拟服务层返回
        when(assetAllocationService.getAllocationsByClientId(1001L)).thenReturn(allocations);

        // 调用控制器方法
        Result<List<AssetAllocation>> result = assetAllocationController.getAllocationsByClientId(1001L);

        // 验证结果
        assertNotNull(result);
        assertEquals(200, result.getCode().intValue());
        assertEquals("成功", result.getMessage());
        assertEquals(allocations, result.getData());
        verify(assetAllocationService, times(1)).getAllocationsByClientId(1001L);
    }

    @Test
    public void getAllocationsByClientId_failure() {
        // 模拟异常
        when(assetAllocationService.getAllocationsByClientId(1001L)).thenThrow(new RuntimeException("Database error"));

        // 调用控制器方法
        Result<List<AssetAllocation>> result = assetAllocationController.getAllocationsByClientId(1001L);

        // 验证结果
        assertNotNull(result);
        assertEquals(500, result.getCode().intValue());
        assertTrue(result.getMessage().contains("获取客户资产配置失败"));
        assertNull(result.getData());
        verify(assetAllocationService, times(1)).getAllocationsByClientId(1001L);
    }

    @Test
    public void searchAllocations_success() {
        // 准备测试数据
        List<AssetAllocation> allocations = Arrays.asList(testAllocation);

        // 模拟服务层返回
        when(assetAllocationService.getAllocationsByCondition(testAllocation)).thenReturn(allocations);

        // 调用控制器方法
        Result<List<AssetAllocation>> result = assetAllocationController.searchAllocations(testAllocation);

        // 验证结果
        assertNotNull(result);
        assertEquals(200, result.getCode().intValue());
        assertEquals("成功", result.getMessage());
        assertEquals(allocations, result.getData());
        verify(assetAllocationService, times(1)).getAllocationsByCondition(testAllocation);
    }

    @Test
    public void searchAllocations_failure() {
        // 模拟异常
        when(assetAllocationService.getAllocationsByCondition(testAllocation)).thenThrow(new RuntimeException("Database error"));

        // 调用控制器方法
        Result<List<AssetAllocation>> result = assetAllocationController.searchAllocations(testAllocation);

        // 验证结果
        assertNotNull(result);
        assertEquals(500, result.getCode().intValue());
        assertTrue(result.getMessage().contains("搜索资产配置失败"));
        assertNull(result.getData());
        verify(assetAllocationService, times(1)).getAllocationsByCondition(testAllocation);
    }

    @Test
    public void addAllocation_success() {
        // 模拟服务层返回true
        when(assetAllocationService.addAllocation(testAllocation)).thenReturn(true);

        // 调用控制器方法
        Result<String> result = assetAllocationController.addAllocation(testAllocation);

        // 验证结果
        assertNotNull(result);
        assertEquals(200, result.getCode().intValue());
        assertEquals("成功", result.getMessage());
        assertEquals("添加资产配置成功", result.getData());
        verify(assetAllocationService, times(1)).addAllocation(testAllocation);
    }

    @Test
    public void addAllocation_failure() {
        // 模拟服务层返回false
        when(assetAllocationService.addAllocation(testAllocation)).thenReturn(false);

        // 调用控制器方法
        Result<String> result = assetAllocationController.addAllocation(testAllocation);

        // 验证结果
        assertNotNull(result);
        assertEquals(500, result.getCode().intValue());
        assertEquals("添加资产配置失败", result.getMessage());
        assertNull(result.getData());
        verify(assetAllocationService, times(1)).addAllocation(testAllocation);
    }

    @Test
    public void addAllocation_serviceException() {
        // 模拟异常
        when(assetAllocationService.addAllocation(testAllocation)).thenThrow(new RuntimeException("Database error"));

        // 调用控制器方法
        Result<String> result = assetAllocationController.addAllocation(testAllocation);

        // 验证结果
        assertNotNull(result);
        assertEquals(500, result.getCode().intValue());
        assertTrue(result.getMessage().contains("添加资产配置失败"));
        assertNull(result.getData());
        verify(assetAllocationService, times(1)).addAllocation(testAllocation);
    }

    @Test
    public void updateAllocation_success() {
        // 模拟服务层返回true
        when(assetAllocationService.updateAllocation(testAllocation)).thenReturn(true);

        // 调用控制器方法
        Result<String> result = assetAllocationController.updateAllocation(testAllocation);

        // 验证结果
        assertNotNull(result);
        assertEquals(200, result.getCode().intValue());
        assertEquals("成功", result.getMessage());
        assertEquals("更新资产配置成功", result.getData());
        verify(assetAllocationService, times(1)).updateAllocation(testAllocation);
    }

    @Test
    public void updateAllocation_failure() {
        // 模拟服务层返回false
        when(assetAllocationService.updateAllocation(testAllocation)).thenReturn(false);

        // 调用控制器方法
        Result<String> result = assetAllocationController.updateAllocation(testAllocation);

        // 验证结果
        assertNotNull(result);
        assertEquals(500, result.getCode().intValue());
        assertEquals("更新资产配置失败", result.getMessage());
        assertNull(result.getData());
        verify(assetAllocationService, times(1)).updateAllocation(testAllocation);
    }

    @Test
    public void updateAllocation_serviceException() {
        // 模拟异常
        when(assetAllocationService.updateAllocation(testAllocation)).thenThrow(new RuntimeException("Database error"));

        // 调用控制器方法
        Result<String> result = assetAllocationController.updateAllocation(testAllocation);

        // 验证结果
        assertNotNull(result);
        assertEquals(500, result.getCode().intValue());
        assertTrue(result.getMessage().contains("更新资产配置失败"));
        assertNull(result.getData());
        verify(assetAllocationService, times(1)).updateAllocation(testAllocation);
    }

    @Test
    public void deleteAllocation_success() {
        // 模拟服务层返回true
        when(assetAllocationService.deleteAllocation(1L)).thenReturn(true);

        // 调用控制器方法
        Result<String> result = assetAllocationController.deleteAllocation(1L);

        // 验证结果
        assertNotNull(result);
        assertEquals(200, result.getCode().intValue());
        assertEquals("成功", result.getMessage());
        assertEquals("删除资产配置成功", result.getData());
        verify(assetAllocationService, times(1)).deleteAllocation(1L);
    }

    @Test
    public void deleteAllocation_failure() {
        // 模拟服务层返回false
        when(assetAllocationService.deleteAllocation(1L)).thenReturn(false);

        // 调用控制器方法
        Result<String> result = assetAllocationController.deleteAllocation(1L);

        // 验证结果
        assertNotNull(result);
        assertEquals(500, result.getCode().intValue());
        assertEquals("删除资产配置失败", result.getMessage());
        assertNull(result.getData());
        verify(assetAllocationService, times(1)).deleteAllocation(1L);
    }

    @Test
    public void deleteAllocation_serviceException() {
        // 模拟异常
        when(assetAllocationService.deleteAllocation(1L)).thenThrow(new RuntimeException("Database error"));

        // 调用控制器方法
        Result<String> result = assetAllocationController.deleteAllocation(1L);

        // 验证结果
        assertNotNull(result);
        assertEquals(500, result.getCode().intValue());
        assertTrue(result.getMessage().contains("删除资产配置失败"));
        assertNull(result.getData());
        verify(assetAllocationService, times(1)).deleteAllocation(1L);
    }

    @Test
    public void updateAllocationStatus_success() {
        // 模拟服务层返回true
        when(assetAllocationService.updateAllocationStatus(1L, 0)).thenReturn(true);

        // 调用控制器方法
        Result<String> result = assetAllocationController.updateAllocationStatus(1L, 0);

        // 验证结果
        assertNotNull(result);
        assertEquals(200, result.getCode().intValue());
        assertEquals("成功", result.getMessage());
        assertEquals("更新资产配置状态成功", result.getData());
        verify(assetAllocationService, times(1)).updateAllocationStatus(1L, 0);
    }

    @Test
    public void updateAllocationStatus_failure() {
        // 模拟服务层返回false
        when(assetAllocationService.updateAllocationStatus(1L, 0)).thenReturn(false);

        // 调用控制器方法
        Result<String> result = assetAllocationController.updateAllocationStatus(1L, 0);

        // 验证结果
        assertNotNull(result);
        assertEquals(500, result.getCode().intValue());
        assertEquals("更新资产配置状态失败", result.getMessage());
        assertNull(result.getData());
        verify(assetAllocationService, times(1)).updateAllocationStatus(1L, 0);
    }

    @Test
    public void updateAllocationStatus_serviceException() {
        // 模拟异常
        when(assetAllocationService.updateAllocationStatus(1L, 0)).thenThrow(new RuntimeException("Database error"));

        // 调用控制器方法
        Result<String> result = assetAllocationController.updateAllocationStatus(1L, 0);

        // 验证结果
        assertNotNull(result);
        assertEquals(500, result.getCode().intValue());
        assertTrue(result.getMessage().contains("更新资产配置状态失败"));
        assertNull(result.getData());
        verify(assetAllocationService, times(1)).updateAllocationStatus(1L, 0);
    }
}
