package com.investech.controller;

import com.investech.common.Result;
import com.investech.entity.DerivedFactor;
import com.investech.service.DerivedFactorService;
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
public class DerivedFactorControllerTest {

    @Mock
    private DerivedFactorService derivedFactorService;

    @InjectMocks
    private DerivedFactorController derivedFactorController;

    private DerivedFactor testFactor;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        // 初始化测试数据
        testFactor = new DerivedFactor();
        testFactor.setId(1L);
        testFactor.setFactorCode("DF001");
        testFactor.setFactorName("夏普比率因子");
        testFactor.setFactorDesc("基于历史收益计算的夏普比率因子");
        testFactor.setBaseFactors("[\"factor1\", \"factor2\"]");
        testFactor.setWeights("{\"factor1\": 0.6, \"factor2\": 0.4}");
        testFactor.setFormula("(return - riskFreeRate) / volatility");
        testFactor.setCategory("风险调整");
        testFactor.setCreatorId(1001L);
        testFactor.setStatus(1);
        testFactor.setCreateTime(LocalDateTime.now());
        testFactor.setUpdateTime(LocalDateTime.now());
    }

    @Test
    public void getAllFactors_success() {
        // 准备测试数据
        List<DerivedFactor> factors = Arrays.asList(testFactor);

        // 模拟服务层返回
        when(derivedFactorService.findAll()).thenReturn(factors);

        // 调用控制器方法
        Result<List<DerivedFactor>> result = derivedFactorController.getAllFactors();

        // 验证结果
        assertNotNull(result);
        assertEquals(200, result.getCode().intValue());
        assertEquals("成功", result.getMessage());
        assertEquals(factors, result.getData());
        verify(derivedFactorService, times(1)).findAll();
    }

    @Test
    public void getAllFactors_emptyList() {
        // 模拟服务层返回空列表
        when(derivedFactorService.findAll()).thenReturn(Arrays.asList());

        // 调用控制器方法
        Result<List<DerivedFactor>> result = derivedFactorController.getAllFactors();

        // 验证结果
        assertNotNull(result);
        assertEquals(200, result.getCode().intValue());
        assertEquals("成功", result.getMessage());
        assertTrue(result.getData().isEmpty());
        verify(derivedFactorService, times(1)).findAll();
    }

    @Test
    public void getFactorById_success() {
        // 模拟服务层返回
        when(derivedFactorService.findById(1L)).thenReturn(testFactor);

        // 调用控制器方法
        Result<DerivedFactor> result = derivedFactorController.getFactorById(1L);

        // 验证结果
        assertNotNull(result);
        assertEquals(200, result.getCode().intValue());
        assertEquals("成功", result.getMessage());
        assertEquals(testFactor, result.getData());
        verify(derivedFactorService, times(1)).findById(1L);
    }

    @Test
    public void getFactorById_notFound() {
        // 模拟服务层返回null
        when(derivedFactorService.findById(1L)).thenReturn(null);

        // 调用控制器方法
        Result<DerivedFactor> result = derivedFactorController.getFactorById(1L);

        // 验证结果
        assertNotNull(result);
        assertEquals(500, result.getCode().intValue());
        assertEquals("衍生因子不存在", result.getMessage());
        assertNull(result.getData());
        verify(derivedFactorService, times(1)).findById(1L);
    }

    @Test
    public void getFactorByCode_success() {
        // 模拟服务层返回
        when(derivedFactorService.findByFactorCode("DF001")).thenReturn(testFactor);

        // 调用控制器方法
        Result<DerivedFactor> result = derivedFactorController.getFactorByCode("DF001");

        // 验证结果
        assertNotNull(result);
        assertEquals(200, result.getCode().intValue());
        assertEquals("成功", result.getMessage());
        assertEquals(testFactor, result.getData());
        verify(derivedFactorService, times(1)).findByFactorCode("DF001");
    }

    @Test
    public void getFactorByCode_notFound() {
        // 模拟服务层返回null
        when(derivedFactorService.findByFactorCode("DF001")).thenReturn(null);

        // 调用控制器方法
        Result<DerivedFactor> result = derivedFactorController.getFactorByCode("DF001");

        // 验证结果
        assertNotNull(result);
        assertEquals(500, result.getCode().intValue());
        assertEquals("衍生因子不存在", result.getMessage());
        assertNull(result.getData());
        verify(derivedFactorService, times(1)).findByFactorCode("DF001");
    }

    @Test
    public void getFactorsByCreator_success() {
        // 准备测试数据
        List<DerivedFactor> factors = Arrays.asList(testFactor);

        // 模拟服务层返回
        when(derivedFactorService.findByCreatorId(1001L)).thenReturn(factors);

        // 调用控制器方法
        Result<List<DerivedFactor>> result = derivedFactorController.getFactorsByCreator(1001L);

        // 验证结果
        assertNotNull(result);
        assertEquals(200, result.getCode().intValue());
        assertEquals("成功", result.getMessage());
        assertEquals(factors, result.getData());
        verify(derivedFactorService, times(1)).findByCreatorId(1001L);
    }

    @Test
    public void getFactorsByCreator_emptyList() {
        // 模拟服务层返回空列表
        when(derivedFactorService.findByCreatorId(1001L)).thenReturn(Arrays.asList());

        // 调用控制器方法
        Result<List<DerivedFactor>> result = derivedFactorController.getFactorsByCreator(1001L);

        // 验证结果
        assertNotNull(result);
        assertEquals(200, result.getCode().intValue());
        assertEquals("成功", result.getMessage());
        assertTrue(result.getData().isEmpty());
        verify(derivedFactorService, times(1)).findByCreatorId(1001L);
    }

    @Test
    public void getFactorsByCategory_success() {
        // 准备测试数据
        List<DerivedFactor> factors = Arrays.asList(testFactor);

        // 模拟服务层返回
        when(derivedFactorService.findByCategory("风险调整")).thenReturn(factors);

        // 调用控制器方法
        Result<List<DerivedFactor>> result = derivedFactorController.getFactorsByCategory("风险调整");

        // 验证结果
        assertNotNull(result);
        assertEquals(200, result.getCode().intValue());
        assertEquals("成功", result.getMessage());
        assertEquals(factors, result.getData());
        verify(derivedFactorService, times(1)).findByCategory("风险调整");
    }

    @Test
    public void getFactorsByCategory_emptyList() {
        // 模拟服务层返回空列表
        when(derivedFactorService.findByCategory("风险调整")).thenReturn(Arrays.asList());

        // 调用控制器方法
        Result<List<DerivedFactor>> result = derivedFactorController.getFactorsByCategory("风险调整");

        // 验证结果
        assertNotNull(result);
        assertEquals(200, result.getCode().intValue());
        assertEquals("成功", result.getMessage());
        assertTrue(result.getData().isEmpty());
        verify(derivedFactorService, times(1)).findByCategory("风险调整");
    }

    @Test
    public void searchFactors_success() {
        // 准备测试数据
        List<DerivedFactor> factors = Arrays.asList(testFactor);

        // 模拟服务层返回
        when(derivedFactorService.searchFactors("DF001", "夏普比率", "风险调整", 1001L)).thenReturn(factors);

        // 调用控制器方法
        Result<List<DerivedFactor>> result = derivedFactorController.searchFactors("DF001", "夏普比率", "风险调整", 1001L);

        // 验证结果
        assertNotNull(result);
        assertEquals(200, result.getCode().intValue());
        assertEquals("成功", result.getMessage());
        assertEquals(factors, result.getData());
        verify(derivedFactorService, times(1)).searchFactors("DF001", "夏普比率", "风险调整", 1001L);
    }

    @Test
    public void searchFactors_emptyList() {
        // 模拟服务层返回空列表
        when(derivedFactorService.searchFactors("DF001", "夏普比率", "风险调整", 1001L)).thenReturn(Arrays.asList());

        // 调用控制器方法
        Result<List<DerivedFactor>> result = derivedFactorController.searchFactors("DF001", "夏普比率", "风险调整", 1001L);

        // 验证结果
        assertNotNull(result);
        assertEquals(200, result.getCode().intValue());
        assertEquals("成功", result.getMessage());
        assertTrue(result.getData().isEmpty());
        verify(derivedFactorService, times(1)).searchFactors("DF001", "夏普比率", "风险调整", 1001L);
    }

    @Test
    public void createFactor_success() {
        // 模拟服务层返回true
        when(derivedFactorService.createFactor(testFactor)).thenReturn(true);

        // 调用控制器方法
        Result<String> result = derivedFactorController.createFactor(testFactor);

        // 验证结果
        assertNotNull(result);
        assertEquals(200, result.getCode().intValue());
        assertEquals("创建成功", result.getMessage());
        assertNull(result.getData());
        verify(derivedFactorService, times(1)).createFactor(testFactor);
    }

    @Test
    public void createFactor_failure() {
        // 模拟服务层返回false
        when(derivedFactorService.createFactor(testFactor)).thenReturn(false);

        // 调用控制器方法
        Result<String> result = derivedFactorController.createFactor(testFactor);

        // 验证结果
        assertNotNull(result);
        assertEquals(500, result.getCode().intValue());
        assertEquals("创建失败", result.getMessage());
        assertNull(result.getData());
        verify(derivedFactorService, times(1)).createFactor(testFactor);
    }

    @Test
    public void updateFactor_success() {
        // 模拟服务层返回true
        when(derivedFactorService.updateFactor(testFactor)).thenReturn(true);

        // 调用控制器方法
        Result<String> result = derivedFactorController.updateFactor(testFactor);

        // 验证结果
        assertNotNull(result);
        assertEquals(200, result.getCode().intValue());
        assertEquals("更新成功", result.getMessage());
        assertNull(result.getData());
        verify(derivedFactorService, times(1)).updateFactor(testFactor);
    }

    @Test
    public void updateFactor_failure() {
        // 模拟服务层返回false
        when(derivedFactorService.updateFactor(testFactor)).thenReturn(false);

        // 调用控制器方法
        Result<String> result = derivedFactorController.updateFactor(testFactor);

        // 验证结果
        assertNotNull(result);
        assertEquals(500, result.getCode().intValue());
        assertEquals("更新失败", result.getMessage());
        assertNull(result.getData());
        verify(derivedFactorService, times(1)).updateFactor(testFactor);
    }

    @Test
    public void deleteFactor_success() {
        // 模拟服务层返回true
        when(derivedFactorService.deleteFactor(1L)).thenReturn(true);

        // 调用控制器方法
        Result<String> result = derivedFactorController.deleteFactor(1L);

        // 验证结果
        assertNotNull(result);
        assertEquals(200, result.getCode().intValue());
        assertEquals("删除成功", result.getMessage());
        assertNull(result.getData());
        verify(derivedFactorService, times(1)).deleteFactor(1L);
    }

    @Test
    public void deleteFactor_failure() {
        // 模拟服务层返回false
        when(derivedFactorService.deleteFactor(1L)).thenReturn(false);

        // 调用控制器方法
        Result<String> result = derivedFactorController.deleteFactor(1L);

        // 验证结果
        assertNotNull(result);
        assertEquals(500, result.getCode().intValue());
        assertEquals("删除失败", result.getMessage());
        assertNull(result.getData());
        verify(derivedFactorService, times(1)).deleteFactor(1L);
    }
}
