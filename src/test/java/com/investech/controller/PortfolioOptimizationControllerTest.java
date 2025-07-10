package com.investech.controller;

import com.investech.common.Result;
import com.investech.entity.PortfolioOptimization;
import com.investech.service.PortfolioOptimizationService;
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
public class PortfolioOptimizationControllerTest {

    @Mock
    private PortfolioOptimizationService portfolioOptimizationService;

    @InjectMocks
    private PortfolioOptimizationController portfolioOptimizationController;

    private PortfolioOptimization testOptimization;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        // 初始化测试数据
        testOptimization = new PortfolioOptimization();
        testOptimization.setId(1L);
        testOptimization.setClientId(1001L);
        testOptimization.setOptimizationName("2025Q3组合优化");
        testOptimization.setOptimizationMethod("马科维茨");
        testOptimization.setFundPool("[\"FUND001\", \"FUND002\", \"FUND003\"]");
        testOptimization.setTargetReturn(8.5);
        testOptimization.setMaxRisk(15.0);
        testOptimization.setOptimizedWeights("{\"FUND001\": 0.4, \"FUND002\": 0.3, \"FUND003\": 0.3}");
        testOptimization.setExpectedReturn(9.2);
        testOptimization.setExpectedRisk(14.5);
        testOptimization.setSharpeRatio(0.63);
        testOptimization.setConstraints("{\"minWeight\": 0.1, \"maxWeight\": 0.5}");
        testOptimization.setStatus(1);
        testOptimization.setCreateTime(LocalDateTime.now());
        testOptimization.setUpdateTime(LocalDateTime.now());
    }

    @Test
    public void getAllOptimizations_success() {
        // 准备测试数据
        List<PortfolioOptimization> optimizations = Arrays.asList(testOptimization);

        // 模拟服务层返回
        when(portfolioOptimizationService.getAllOptimizations()).thenReturn(optimizations);

        // 调用控制器方法
        Result<List<PortfolioOptimization>> result = portfolioOptimizationController.getAllOptimizations();

        // 验证结果
        assertNotNull(result);
        assertEquals(200, result.getCode().intValue());
        assertEquals("成功", result.getMessage());
        assertEquals(optimizations, result.getData());
        verify(portfolioOptimizationService, times(1)).getAllOptimizations();
    }

    @Test
    public void getAllOptimizations_emptyList() {
        // 模拟服务层返回空列表
        when(portfolioOptimizationService.getAllOptimizations()).thenReturn(Arrays.asList());

        // 调用控制器方法
        Result<List<PortfolioOptimization>> result = portfolioOptimizationController.getAllOptimizations();

        // 验证结果
        assertNotNull(result);
        assertEquals(200, result.getCode().intValue());
        assertEquals("成功", result.getMessage());
        assertTrue(result.getData().isEmpty());
        verify(portfolioOptimizationService, times(1)).getAllOptimizations();
    }

    @Test
    public void getAllOptimizations_serviceException() {
        // 模拟服务层抛出异常
        when(portfolioOptimizationService.getAllOptimizations()).thenThrow(new RuntimeException("Database error"));

        // 调用控制器方法
        Result<List<PortfolioOptimization>> result = portfolioOptimizationController.getAllOptimizations();

        // 验证结果
        assertNotNull(result);
        assertEquals(500, result.getCode().intValue());
        assertTrue(result.getMessage().contains("获取投资组合优化列表失败"));
        assertNull(result.getData());
        verify(portfolioOptimizationService, times(1)).getAllOptimizations();
    }

    @Test
    public void getOptimizationById_success() {
        // 模拟服务层返回
        when(portfolioOptimizationService.getOptimizationById(1L)).thenReturn(testOptimization);

        // 调用控制器方法
        Result<PortfolioOptimization> result = portfolioOptimizationController.getOptimizationById(1L);

        // 验证结果
        assertNotNull(result);
        assertEquals(200, result.getCode().intValue());
        assertEquals("成功", result.getMessage());
        assertEquals(testOptimization, result.getData());
        verify(portfolioOptimizationService, times(1)).getOptimizationById(1L);
    }

    @Test
    public void getOptimizationById_notFound() {
        // 模拟服务层返回null
        when(portfolioOptimizationService.getOptimizationById(1L)).thenReturn(null);

        // 调用控制器方法
        Result<PortfolioOptimization> result = portfolioOptimizationController.getOptimizationById(1L);

        // 验证结果
        assertNotNull(result);
        assertEquals(500, result.getCode().intValue());
        assertEquals("投资组合优化不存在", result.getMessage());
        assertNull(result.getData());
        verify(portfolioOptimizationService, times(1)).getOptimizationById(1L);
    }

    @Test
    public void getOptimizationById_serviceException() {
        // 模拟服务层抛出异常
        when(portfolioOptimizationService.getOptimizationById(1L)).thenThrow(new RuntimeException("Database error"));

        // 调用控制器方法
        Result<PortfolioOptimization> result = portfolioOptimizationController.getOptimizationById(1L);

        // 验证结果
        assertNotNull(result);
        assertEquals(500, result.getCode().intValue());
        assertTrue(result.getMessage().contains("获取投资组合优化失败"));
        assertNull(result.getData());
        verify(portfolioOptimizationService, times(1)).getOptimizationById(1L);
    }

    @Test
    public void getOptimizationsByClientId_success() {
        // 准备测试数据
        List<PortfolioOptimization> optimizations = Arrays.asList(testOptimization);

        // 模拟服务层返回
        when(portfolioOptimizationService.getOptimizationsByClientId(1001L)).thenReturn(optimizations);

        // 调用控制器方法
        Result<List<PortfolioOptimization>> result = portfolioOptimizationController.getOptimizationsByClientId(1001L);

        // 验证结果
        assertNotNull(result);
        assertEquals(200, result.getCode().intValue());
        assertEquals("成功", result.getMessage());
        assertEquals(optimizations, result.getData());
        verify(portfolioOptimizationService, times(1)).getOptimizationsByClientId(1001L);
    }

    @Test
    public void getOptimizationsByClientId_emptyList() {
        // 模拟服务层返回空列表
        when(portfolioOptimizationService.getOptimizationsByClientId(1001L)).thenReturn(Arrays.asList());

        // 调用控制器方法
        Result<List<PortfolioOptimization>> result = portfolioOptimizationController.getOptimizationsByClientId(1001L);

        // 验证结果
        assertNotNull(result);
        assertEquals(200, result.getCode().intValue());
        assertEquals("成功", result.getMessage());
        assertTrue(result.getData().isEmpty());
        verify(portfolioOptimizationService, times(1)).getOptimizationsByClientId(1001L);
    }

    @Test
    public void getOptimizationsByClientId_serviceException() {
        // 模拟服务层抛出异常
        when(portfolioOptimizationService.getOptimizationsByClientId(1001L)).thenThrow(new RuntimeException("Database error"));

        // 调用控制器方法
        Result<List<PortfolioOptimization>> result = portfolioOptimizationController.getOptimizationsByClientId(1001L);

        // 验证结果
        assertNotNull(result);
        assertEquals(500, result.getCode().intValue());
        assertTrue(result.getMessage().contains("获取客户投资组合优化失败"));
        assertNull(result.getData());
        verify(portfolioOptimizationService, times(1)).getOptimizationsByClientId(1001L);
    }

    @Test
    public void searchOptimizations_success() {
        // 准备测试数据
        List<PortfolioOptimization> optimizations = Arrays.asList(testOptimization);

        // 模拟服务层返回
        when(portfolioOptimizationService.getOptimizationsByCondition(testOptimization)).thenReturn(optimizations);

        // 调用控制器方法
        Result<List<PortfolioOptimization>> result = portfolioOptimizationController.searchOptimizations(testOptimization);

        // 验证结果
        assertNotNull(result);
        assertEquals(200, result.getCode().intValue());
        assertEquals("成功", result.getMessage());
        assertEquals(optimizations, result.getData());
        verify(portfolioOptimizationService, times(1)).getOptimizationsByCondition(testOptimization);
    }

    @Test
    public void searchOptimizations_emptyList() {
        // 模拟服务层返回空列表
        when(portfolioOptimizationService.getOptimizationsByCondition(testOptimization)).thenReturn(Arrays.asList());

        // 调用控制器方法
        Result<List<PortfolioOptimization>> result = portfolioOptimizationController.searchOptimizations(testOptimization);

        // 验证结果
        assertNotNull(result);
        assertEquals(200, result.getCode().intValue());
        assertEquals("成功", result.getMessage());
        assertTrue(result.getData().isEmpty());
        verify(portfolioOptimizationService, times(1)).getOptimizationsByCondition(testOptimization);
    }

    @Test
    public void searchOptimizations_serviceException() {
        // 模拟服务层抛出异常
        when(portfolioOptimizationService.getOptimizationsByCondition(testOptimization)).thenThrow(new RuntimeException("Database error"));

        // 调用控制器方法
        Result<List<PortfolioOptimization>> result = portfolioOptimizationController.searchOptimizations(testOptimization);

        // 验证结果
        assertNotNull(result);
        assertEquals(500, result.getCode().intValue());
        assertTrue(result.getMessage().contains("搜索投资组合优化失败"));
        assertNull(result.getData());
        verify(portfolioOptimizationService, times(1)).getOptimizationsByCondition(testOptimization);
    }

    @Test
    public void addOptimization_success() {
        // 模拟服务层返回true
        when(portfolioOptimizationService.addOptimization(testOptimization)).thenReturn(true);

        // 调用控制器方法
        Result<String> result = portfolioOptimizationController.addOptimization(testOptimization);

        // 验证结果
        assertNotNull(result);
        assertEquals(200, result.getCode().intValue());
        assertEquals("成功", result.getMessage());
        assertEquals("添加投资组合优化成功", result.getData());

        verify(portfolioOptimizationService, times(1)).addOptimization(testOptimization);
    }

    @Test
    public void addOptimization_failure() {
        // 模拟服务层返回false
        when(portfolioOptimizationService.addOptimization(testOptimization)).thenReturn(false);

        // 调用控制器方法
        Result<String> result = portfolioOptimizationController.addOptimization(testOptimization);

        // 验证结果
        assertNotNull(result);
        assertEquals(500, result.getCode().intValue());
        assertEquals("添加投资组合优化失败", result.getMessage());
        assertNull(result.getData());
        verify(portfolioOptimizationService, times(1)).addOptimization(testOptimization);
    }

    @Test
    public void addOptimization_serviceException() {
        // 模拟服务层抛出异常
        when(portfolioOptimizationService.addOptimization(testOptimization)).thenThrow(new RuntimeException("Database error"));

        // 调用控制器方法
        Result<String> result = portfolioOptimizationController.addOptimization(testOptimization);

        // 验证结果
        assertNotNull(result);
        assertEquals(500, result.getCode().intValue());
        assertTrue(result.getMessage().contains("添加投资组合优化失败"));
        assertNull(result.getData());
        verify(portfolioOptimizationService, times(1)).addOptimization(testOptimization);
    }

    @Test
    public void updateOptimization_success() {
        // 模拟服务层返回true
        when(portfolioOptimizationService.updateOptimization(testOptimization)).thenReturn(true);

        // 调用控制器方法
        Result<String> result = portfolioOptimizationController.updateOptimization(testOptimization);

        // 验证结果
        assertNotNull(result);
        assertEquals(200, result.getCode().intValue());
        assertEquals("成功", result.getMessage());
        assertEquals("更新投资组合优化成功", result.getData());
        verify(portfolioOptimizationService, times(1)).updateOptimization(testOptimization);
    }

    @Test
    public void updateOptimization_failure() {
        // 模拟服务层返回false
        when(portfolioOptimizationService.updateOptimization(testOptimization)).thenReturn(false);

        // 调用控制器方法
        Result<String> result = portfolioOptimizationController.updateOptimization(testOptimization);

        // 验证结果
        assertNotNull(result);
        assertEquals(500, result.getCode().intValue());
        assertEquals("更新投资组合优化失败", result.getMessage());
        assertNull(result.getData());
        verify(portfolioOptimizationService, times(1)).updateOptimization(testOptimization);
    }

    @Test
    public void updateOptimization_serviceException() {
        // 模拟服务层抛出异常
        when(portfolioOptimizationService.updateOptimization(testOptimization)).thenThrow(new RuntimeException("Database error"));

        // 调用控制器方法
        Result<String> result = portfolioOptimizationController.updateOptimization(testOptimization);

        // 验证结果
        assertNotNull(result);
        assertEquals(500, result.getCode().intValue());
        assertTrue(result.getMessage().contains("更新投资组合优化失败"));
        assertNull(result.getData());
        verify(portfolioOptimizationService, times(1)).updateOptimization(testOptimization);
    }

    @Test
    public void deleteOptimization_success() {
        // 模拟服务层返回true
        when(portfolioOptimizationService.deleteOptimization(1L)).thenReturn(true);

        // 调用控制器方法
        Result<String> result = portfolioOptimizationController.deleteOptimization(1L);

        // 验证结果
        assertNotNull(result);
        assertEquals(200, result.getCode().intValue());
        assertEquals("成功", result.getMessage());
        assertEquals("删除投资组合优化成功", result.getData());

        verify(portfolioOptimizationService, times(1)).deleteOptimization(1L);
    }

    @Test
    public void deleteOptimization_failure() {
        // 模拟服务层返回false
        when(portfolioOptimizationService.deleteOptimization(1L)).thenReturn(false);

        // 调用控制器方法
        Result<String> result = portfolioOptimizationController.deleteOptimization(1L);

        // 验证结果
        assertNotNull(result);
        assertEquals(500, result.getCode().intValue());
        assertEquals("删除投资组合优化失败", result.getMessage());
        assertNull(result.getData());
        verify(portfolioOptimizationService, times(1)).deleteOptimization(1L);
    }

    @Test
    public void deleteOptimization_serviceException() {
        // 模拟服务层抛出异常
        when(portfolioOptimizationService.deleteOptimization(1L)).thenThrow(new RuntimeException("Database error"));

        // 调用控制器方法
        Result<String> result = portfolioOptimizationController.deleteOptimization(1L);

        // 验证结果
        assertNotNull(result);
        assertEquals(500, result.getCode().intValue());
        assertTrue(result.getMessage().contains("删除投资组合优化失败"));
        assertNull(result.getData());
        verify(portfolioOptimizationService, times(1)).deleteOptimization(1L);
    }

    @Test
    public void updateOptimizationStatus_success() {
        // 模拟服务层返回true
        when(portfolioOptimizationService.updateOptimizationStatus(1L, 0)).thenReturn(true);

        // 调用控制器方法
        Result<String> result = portfolioOptimizationController.updateOptimizationStatus(1L, 0);

        // 验证结果
        assertNotNull(result);
        assertEquals(200, result.getCode().intValue());
        assertEquals("更新投资组合优化状态成功", result.getData());
        assertEquals("成功", result.getMessage());
        verify(portfolioOptimizationService, times(1)).updateOptimizationStatus(1L, 0);
    }

    @Test
    public void updateOptimizationStatus_failure() {
        // 模拟服务层返回false
        when(portfolioOptimizationService.updateOptimizationStatus(1L, 0)).thenReturn(false);

        // 调用控制器方法
        Result<String> result = portfolioOptimizationController.updateOptimizationStatus(1L, 0);

        // 验证结果
        assertNotNull(result);
        assertEquals(500, result.getCode().intValue());
        assertEquals("更新投资组合优化状态失败", result.getMessage());
        assertNull(result.getData());
        verify(portfolioOptimizationService, times(1)).updateOptimizationStatus(1L, 0);
    }

    @Test
    public void updateOptimizationStatus_serviceException() {
        // 模拟服务层抛出异常
        when(portfolioOptimizationService.updateOptimizationStatus(1L, 0)).thenThrow(new RuntimeException("Database error"));

        // 调用控制器方法
        Result<String> result = portfolioOptimizationController.updateOptimizationStatus(1L, 0);

        // 验证结果
        assertNotNull(result);
        assertEquals(500, result.getCode().intValue());
        assertTrue(result.getMessage().contains("更新投资组合优化状态失败"));
        assertNull(result.getData());
        verify(portfolioOptimizationService, times(1)).updateOptimizationStatus(1L, 0);
    }
}
