package com.investech.controller;

import com.investech.common.Result;
import com.investech.entity.Factor;
import com.investech.service.FactorService;
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
public class FactorControllerTest {

    @Mock
    private FactorService factorService;

    @InjectMocks
    private FactorController factorController;

    private Factor testFactor;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        // 初始化测试数据
        testFactor = new Factor();
        testFactor.setId(1L);
        testFactor.setFactorCode("F001");
        testFactor.setFactorName("夏普比率");
        testFactor.setFactorDesc("衡量基金风险调整后收益的指标");
        testFactor.setFactorType("基础因子");
        testFactor.setDataSource("第三方数据平台");
        testFactor.setCalculationMethod("(Return - RiskFreeRate) / StandardDeviation");
        testFactor.setUnit("%");
        testFactor.setCategory("风险类");
        testFactor.setParentCode(null);
        testFactor.setLevel(1);
        testFactor.setSortOrder(10);
        testFactor.setStatus(1);
        testFactor.setCreateTime(LocalDateTime.now());
        testFactor.setUpdateTime(LocalDateTime.now());
    }

    @Test
    public void getAllFactors_success() {
        // 准备测试数据
        List<Factor> factors = Arrays.asList(testFactor);

        // 模拟服务层返回
        when(factorService.findAll()).thenReturn(factors);

        // 调用控制器方法
        Result<List<Factor>> result = factorController.getAllFactors();

        // 验证结果
        assertNotNull(result);
        assertEquals(200, result.getCode().intValue());
        assertEquals("成功", result.getMessage());
        assertEquals(factors, result.getData());
        verify(factorService, times(1)).findAll();
    }

    @Test
    public void getAllFactors_emptyList() {
        // 模拟服务层返回空列表
        when(factorService.findAll()).thenReturn(Arrays.asList());

        // 调用控制器方法
        Result<List<Factor>> result = factorController.getAllFactors();

        // 验证结果
        assertNotNull(result);
        assertEquals(200, result.getCode().intValue());
        assertEquals("成功", result.getMessage());
        assertTrue(result.getData().isEmpty());
        verify(factorService, times(1)).findAll();
    }

    @Test
    public void getFactorById_success() {
        // 模拟服务层返回
        when(factorService.findById(1L)).thenReturn(testFactor);

        // 调用控制器方法
        Result<Factor> result = factorController.getFactorById(1L);

        // 验证结果
        assertNotNull(result);
        assertEquals(200, result.getCode().intValue());
        assertEquals("成功", result.getMessage());
        assertEquals(testFactor, result.getData());
        verify(factorService, times(1)).findById(1L);
    }

    @Test
    public void getFactorById_notFound() {
        // 模拟服务层返回null
        when(factorService.findById(1L)).thenReturn(null);

        // 调用控制器方法
        Result<Factor> result = factorController.getFactorById(1L);

        // 验证结果
        assertNotNull(result);
        assertEquals(500, result.getCode().intValue());
        assertEquals("因子不存在", result.getMessage());
        assertNull(result.getData());
        verify(factorService, times(1)).findById(1L);
    }

    @Test
    public void getFactorByCode_success() {
        // 模拟服务层返回
        when(factorService.findByFactorCode("F001")).thenReturn(testFactor);

        // 调用控制器方法
        Result<Factor> result = factorController.getFactorByCode("F001");

        // 验证结果
        assertNotNull(result);
        assertEquals(200, result.getCode().intValue());
        assertEquals("成功", result.getMessage());
        assertEquals(testFactor, result.getData());
        verify(factorService, times(1)).findByFactorCode("F001");
    }

    @Test
    public void getFactorByCode_notFound() {
        // 模拟服务层返回null
        when(factorService.findByFactorCode("F001")).thenReturn(null);

        // 调用控制器方法
        Result<Factor> result = factorController.getFactorByCode("F001");

        // 验证结果
        assertNotNull(result);
        assertEquals(500, result.getCode().intValue());
        assertEquals("因子不存在", result.getMessage());
        assertNull(result.getData());
        verify(factorService, times(1)).findByFactorCode("F001");
    }

    @Test
    public void getFactorsByType_success() {
        // 准备测试数据
        List<Factor> factors = Arrays.asList(testFactor);

        // 模拟服务层返回
        when(factorService.findByType("基础因子")).thenReturn(factors);

        // 调用控制器方法
        Result<List<Factor>> result = factorController.getFactorsByType("基础因子");

        // 验证结果
        assertNotNull(result);
        assertEquals(200, result.getCode().intValue());
        assertEquals("成功", result.getMessage());
        assertEquals(factors, result.getData());
        verify(factorService, times(1)).findByType("基础因子");
    }

    @Test
    public void getFactorsByType_emptyList() {
        // 模拟服务层返回空列表
        when(factorService.findByType("基础因子")).thenReturn(Arrays.asList());

        // 调用控制器方法
        Result<List<Factor>> result = factorController.getFactorsByType("基础因子");

        // 验证结果
        assertNotNull(result);
        assertEquals(200, result.getCode().intValue());
        assertEquals("成功", result.getMessage());
        assertTrue(result.getData().isEmpty());
        verify(factorService, times(1)).findByType("基础因子");
    }

    @Test
    public void getFactorsByCategory_success() {
        // 准备测试数据
        List<Factor> factors = Arrays.asList(testFactor);

        // 模拟服务层返回
        when(factorService.findByCategory("风险类")).thenReturn(factors);

        // 调用控制器方法
        Result<List<Factor>> result = factorController.getFactorsByCategory("风险类");

        // 验证结果
        assertNotNull(result);
        assertEquals(200, result.getCode().intValue());
        assertEquals("成功", result.getMessage());
        assertEquals(factors, result.getData());
        verify(factorService, times(1)).findByCategory("风险类");
    }

    @Test
    public void getFactorsByCategory_emptyList() {
        // 模拟服务层返回空列表
        when(factorService.findByCategory("风险类")).thenReturn(Arrays.asList());

        // 调用控制器方法
        Result<List<Factor>> result = factorController.getFactorsByCategory("风险类");

        // 验证结果
        assertNotNull(result);
        assertEquals(200, result.getCode().intValue());
        assertEquals("成功", result.getMessage());
        assertTrue(result.getData().isEmpty());
        verify(factorService, times(1)).findByCategory("风险类");
    }

    @Test
    public void getFactorsByParent_success() {
        // 准备测试数据
        List<Factor> factors = Arrays.asList(testFactor);

        // 模拟服务层返回
        when(factorService.findByParentCode(null)).thenReturn(factors);

        // 调用控制器方法
        Result<List<Factor>> result = factorController.getFactorsByParent(null);

        // 验证结果
        assertNotNull(result);
        assertEquals(200, result.getCode().intValue());
        assertEquals("成功", result.getMessage());
        assertEquals(factors, result.getData());
        verify(factorService, times(1)).findByParentCode(null);
    }

    @Test
    public void getFactorsByParent_emptyList() {
        // 模拟服务层返回空列表
        when(factorService.findByParentCode(null)).thenReturn(Arrays.asList());

        // 调用控制器方法
        Result<List<Factor>> result = factorController.getFactorsByParent(null);

        // 验证结果
        assertNotNull(result);
        assertEquals(200, result.getCode().intValue());
        assertEquals("成功", result.getMessage());
        assertTrue(result.getData().isEmpty());
        verify(factorService, times(1)).findByParentCode(null);
    }

    @Test
    public void searchFactors_withAllParams_success() {
        // 准备测试数据
        List<Factor> factors = Arrays.asList(testFactor);

        // 模拟服务层返回
        when(factorService.searchFactors("F001", "夏普比率", "基础因子", "风险类")).thenReturn(factors);

        // 调用控制器方法
        Result<List<Factor>> result = factorController.searchFactors("F001", "夏普比率", "基础因子", "风险类");

        // 验证结果
        assertNotNull(result);
        assertEquals(200, result.getCode().intValue());
        assertEquals("成功", result.getMessage());
        assertEquals(factors, result.getData());
        verify(factorService, times(1)).searchFactors("F001", "夏普比率", "基础因子", "风险类");
    }

    @Test
    public void searchFactors_withNoParams_success() {
        // 准备测试数据
        List<Factor> factors = Arrays.asList(testFactor);

        // 模拟服务层返回
        when(factorService.searchFactors(null, null, null, null)).thenReturn(factors);

        // 调用控制器方法
        Result<List<Factor>> result = factorController.searchFactors(null, null, null, null);

        // 验证结果
        assertNotNull(result);
        assertEquals(200, result.getCode().intValue());
        assertEquals("成功", result.getMessage());
        assertEquals(factors, result.getData());
        verify(factorService, times(1)).searchFactors(null, null, null, null);
    }

    @Test
    public void addFactor_success() {
        // 模拟服务层返回true
        when(factorService.addFactor(testFactor)).thenReturn(true);

        // 调用控制器方法
        Result<String> result = factorController.addFactor(testFactor);

        // 验证结果
        assertNotNull(result);
        assertEquals(200, result.getCode().intValue());
        assertEquals("添加成功", result.getMessage());
        assertNull(result.getData());
        verify(factorService, times(1)).addFactor(testFactor);
    }

    @Test
    public void addFactor_failure() {
        // 模拟服务层返回false
        when(factorService.addFactor(testFactor)).thenReturn(false);

        // 调用控制器方法
        Result<String> result = factorController.addFactor(testFactor);

        // 验证结果
        assertNotNull(result);
        assertEquals(500, result.getCode().intValue());
        assertEquals("添加失败", result.getMessage());
        assertNull(result.getData());
        verify(factorService, times(1)).addFactor(testFactor);
    }

    @Test
    public void updateFactor_success() {
        // 模拟服务层返回true
        when(factorService.updateFactor(testFactor)).thenReturn(true);

        // 调用控制器方法
        Result<String> result = factorController.updateFactor(testFactor);

        // 验证结果
        assertNotNull(result);
        assertEquals(200, result.getCode().intValue());
        assertEquals("更新成功", result.getMessage());
        assertNull(result.getData());
        verify(factorService, times(1)).updateFactor(testFactor);
    }

    @Test
    public void updateFactor_failure() {
        // 模拟服务层返回false
        when(factorService.updateFactor(testFactor)).thenReturn(false);

        // 调用控制器方法
        Result<String> result = factorController.updateFactor(testFactor);

        // 验证结果
        assertNotNull(result);
        assertEquals(500, result.getCode().intValue());
        assertEquals("更新失败", result.getMessage());
        assertNull(result.getData());
        verify(factorService, times(1)).updateFactor(testFactor);
    }

    @Test
    public void deleteFactor_success() {
        // 模拟服务层返回true
        when(factorService.deleteFactor(1L)).thenReturn(true);

        // 调用控制器方法
        Result<String> result = factorController.deleteFactor(1L);

        // 验证结果
        assertNotNull(result);
        assertEquals(200, result.getCode().intValue());
        assertEquals("删除成功", result.getMessage());
        assertNull(result.getData());
        verify(factorService, times(1)).deleteFactor(1L);
    }

    @Test
    public void deleteFactor_failure() {
        // 模拟服务层返回false
        when(factorService.deleteFactor(1L)).thenReturn(false);

        // 调用控制器方法
        Result<String> result = factorController.deleteFactor(1L);

        // 验证结果
        assertNotNull(result);
        assertEquals(500, result.getCode().intValue());
        assertEquals("删除失败", result.getMessage());
        assertNull(result.getData());
        verify(factorService, times(1)).deleteFactor(1L);
    }
}
