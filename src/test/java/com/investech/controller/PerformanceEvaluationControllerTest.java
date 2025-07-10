package com.investech.controller;

import com.investech.common.Result;
import com.investech.entity.PerformanceEvaluation;
import com.investech.service.PerformanceEvaluationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PerformanceEvaluationControllerTest {

    @Mock
    private PerformanceEvaluationService performanceEvaluationService;

    @InjectMocks
    private PerformanceEvaluationController performanceEvaluationController;

    private PerformanceEvaluation testEvaluation;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        // 初始化测试数据
        testEvaluation = new PerformanceEvaluation();
        testEvaluation.setId(1L);
        testEvaluation.setEvaluationCode("EVAL001");
        testEvaluation.setEvaluationName("2025Q2策略评估");
        testEvaluation.setStrategyCode("STRATEGY001");
        testEvaluation.setPortfolioCode("PORTFOLIO001");
        testEvaluation.setEvaluationDate(LocalDateTime.now());
        testEvaluation.setTotalReturn(new BigDecimal("25.5"));
        testEvaluation.setAnnualReturn(new BigDecimal("12.3"));
        testEvaluation.setBenchmarkReturn(new BigDecimal("8.5"));
        testEvaluation.setExcessReturn(new BigDecimal("4.0"));
        testEvaluation.setSharpeRatio(new BigDecimal("1.8"));
        testEvaluation.setSortinoRatio(new BigDecimal("2.1"));
        testEvaluation.setCalmarRatio(new BigDecimal("1.5"));
        testEvaluation.setInformationRatio(new BigDecimal("0.9"));
        testEvaluation.setMaxDrawdown(new BigDecimal("15.0"));
        testEvaluation.setVolatility(new BigDecimal("10.5"));
        testEvaluation.setBeta(new BigDecimal("1.2"));
        testEvaluation.setAlpha(new BigDecimal("3.5"));
        testEvaluation.setTreynorRatio(new BigDecimal("10.0"));
        testEvaluation.setJensenAlpha(new BigDecimal("2.8"));
        testEvaluation.setRiskAdjustedReturn("优秀");
        testEvaluation.setPerformanceRanking("第1名");
        testEvaluation.setEvaluationResult("优秀");
        testEvaluation.setEvaluationReport("{\"returnAnalysis\": \"高于基准\", \"riskAnalysis\": \"适中\"}");
        testEvaluation.setEvaluatorId(1001L);
        testEvaluation.setCreateTime(LocalDateTime.now());
        testEvaluation.setUpdateTime(LocalDateTime.now());
    }

    @Test
    public void getAllEvaluations_success() {
        // 准备测试数据
        List<PerformanceEvaluation> evaluations = Arrays.asList(testEvaluation);

        // 模拟服务层返回
        when(performanceEvaluationService.findAll()).thenReturn(evaluations);

        // 调用控制器方法
        Result<List<PerformanceEvaluation>> result = performanceEvaluationController.getAllEvaluations();

        // 验证结果
        assertNotNull(result);
        assertEquals(200, result.getCode().intValue());
        assertEquals("成功", result.getMessage());
        assertEquals(evaluations, result.getData());
        verify(performanceEvaluationService, times(1)).findAll();
    }

    @Test
    public void getAllEvaluations_emptyList() {
        // 模拟服务层返回空列表
        when(performanceEvaluationService.findAll()).thenReturn(Arrays.asList());

        // 调用控制器方法
        Result<List<PerformanceEvaluation>> result = performanceEvaluationController.getAllEvaluations();

        // 验证结果
        assertNotNull(result);
        assertEquals(200, result.getCode().intValue());
        assertEquals("成功", result.getMessage());
        assertTrue(result.getData().isEmpty());
        verify(performanceEvaluationService, times(1)).findAll();
    }

    @Test
    public void getEvaluationById_success() {
        // 模拟服务层返回
        when(performanceEvaluationService.findById(1L)).thenReturn(testEvaluation);

        // 调用控制器方法
        Result<PerformanceEvaluation> result = performanceEvaluationController.getEvaluationById(1L);

        // 验证结果
        assertNotNull(result);
        assertEquals(200, result.getCode().intValue());
        assertEquals("成功", result.getMessage());
        assertEquals(testEvaluation, result.getData());
        verify(performanceEvaluationService, times(1)).findById(1L);
    }

    @Test
    public void getEvaluationById_notFound() {
        // 模拟服务层返回null
        when(performanceEvaluationService.findById(1L)).thenReturn(null);

        // 调用控制器方法
        Result<PerformanceEvaluation> result = performanceEvaluationController.getEvaluationById(1L);

        // 验证结果
        assertNotNull(result);
        assertEquals(500, result.getCode().intValue());
        assertEquals("绩效评估记录不存在", result.getMessage());
        assertNull(result.getData());
        verify(performanceEvaluationService, times(1)).findById(1L);
    }

    @Test
    public void getEvaluationsByStrategy_success() {
        // 准备测试数据
        List<PerformanceEvaluation> evaluations = Arrays.asList(testEvaluation);

        // 模拟服务层返回
        when(performanceEvaluationService.findByStrategyCode("STRATEGY001")).thenReturn(evaluations);

        // 调用控制器方法
        Result<List<PerformanceEvaluation>> result = performanceEvaluationController.getEvaluationsByStrategy("STRATEGY001");

        // 验证结果
        assertNotNull(result);
        assertEquals(200, result.getCode().intValue());
        assertEquals("成功", result.getMessage());
        assertEquals(evaluations, result.getData());
        verify(performanceEvaluationService, times(1)).findByStrategyCode("STRATEGY001");
    }

    @Test
    public void getEvaluationsByStrategy_emptyList() {
        // 模拟服务层返回空列表
        when(performanceEvaluationService.findByStrategyCode("STRATEGY001")).thenReturn(Arrays.asList());

        // 调用控制器方法
        Result<List<PerformanceEvaluation>> result = performanceEvaluationController.getEvaluationsByStrategy("STRATEGY001");

        // 验证结果
        assertNotNull(result);
        assertEquals(200, result.getCode().intValue());
        assertEquals("成功", result.getMessage());
        assertTrue(result.getData().isEmpty());
        verify(performanceEvaluationService, times(1)).findByStrategyCode("STRATEGY001");
    }

    @Test
    public void getEvaluationsByPortfolio_success() {
        // 准备测试数据
        List<PerformanceEvaluation> evaluations = Arrays.asList(testEvaluation);

        // 模拟服务层返回
        when(performanceEvaluationService.findByPortfolioCode("PORTFOLIO001")).thenReturn(evaluations);

        // 调用控制器方法
        Result<List<PerformanceEvaluation>> result = performanceEvaluationController.getEvaluationsByPortfolio("PORTFOLIO001");

        // 验证结果
        assertNotNull(result);
        assertEquals(200, result.getCode().intValue());
        assertEquals("成功", result.getMessage());
        assertEquals(evaluations, result.getData());
        verify(performanceEvaluationService, times(1)).findByPortfolioCode("PORTFOLIO001");
    }

    @Test
    public void getEvaluationsByPortfolio_emptyList() {
        // 模拟服务层返回空列表
        when(performanceEvaluationService.findByPortfolioCode("PORTFOLIO001")).thenReturn(Arrays.asList());

        // 调用控制器方法
        Result<List<PerformanceEvaluation>> result = performanceEvaluationController.getEvaluationsByPortfolio("PORTFOLIO001");

        // 验证结果
        assertNotNull(result);
        assertEquals(200, result.getCode().intValue());
        assertEquals("成功", result.getMessage());
        assertTrue(result.getData().isEmpty());
        verify(performanceEvaluationService, times(1)).findByPortfolioCode("PORTFOLIO001");
    }

    @Test
    public void getEvaluationsByResult_success() {
        // 准备测试数据
        List<PerformanceEvaluation> evaluations = Arrays.asList(testEvaluation);

        // 模拟服务层返回
        when(performanceEvaluationService.findByEvaluationResult("优秀")).thenReturn(evaluations);

        // 调用控制器方法
        Result<List<PerformanceEvaluation>> result = performanceEvaluationController.getEvaluationsByResult("优秀");

        // 验证结果
        assertNotNull(result);
        assertEquals(200, result.getCode().intValue());
        assertEquals("成功", result.getMessage());
        assertEquals(evaluations, result.getData());
        verify(performanceEvaluationService, times(1)).findByEvaluationResult("优秀");
    }

    @Test
    public void getEvaluationsByResult_emptyList() {
        // 模拟服务层返回空列表
        when(performanceEvaluationService.findByEvaluationResult("优秀")).thenReturn(Arrays.asList());

        // 调用控制器方法
        Result<List<PerformanceEvaluation>> result = performanceEvaluationController.getEvaluationsByResult("优秀");

        // 验证结果
        assertNotNull(result);
        assertEquals(200, result.getCode().intValue());
        assertEquals("成功", result.getMessage());
        assertTrue(result.getData().isEmpty());
        verify(performanceEvaluationService, times(1)).findByEvaluationResult("优秀");
    }

    @Test
    public void createEvaluation_success() {
        // 模拟服务层返回true
        when(performanceEvaluationService.createEvaluation(testEvaluation)).thenReturn(true);

        // 调用控制器方法
        Result<String> result = performanceEvaluationController.createEvaluation(testEvaluation);

        // 验证结果
        assertNotNull(result);
        assertEquals(200, result.getCode().intValue());
        assertEquals("创建成功", result.getMessage());
        assertNull(result.getData());
        verify(performanceEvaluationService, times(1)).createEvaluation(testEvaluation);
    }

    @Test
    public void createEvaluation_failure() {
        // 模拟服务层返回false
        when(performanceEvaluationService.createEvaluation(testEvaluation)).thenReturn(false);

        // 调用控制器方法
        Result<String> result = performanceEvaluationController.createEvaluation(testEvaluation);

        // 验证结果
        assertNotNull(result);
        assertEquals(500, result.getCode().intValue());
        assertEquals("创建失败", result.getMessage());
        assertNull(result.getData());
        verify(performanceEvaluationService, times(1)).createEvaluation(testEvaluation);
    }

    @Test
    public void updateEvaluation_success() {
        // 模拟服务层返回true
        when(performanceEvaluationService.updateEvaluation(testEvaluation)).thenReturn(true);

        // 调用控制器方法
        Result<String> result = performanceEvaluationController.updateEvaluation(testEvaluation);

        // 验证结果
        assertNotNull(result);
        assertEquals(200, result.getCode().intValue());
        assertEquals("更新成功", result.getMessage());
        assertNull(result.getData());
        verify(performanceEvaluationService, times(1)).updateEvaluation(testEvaluation);
    }

    @Test
    public void updateEvaluation_failure() {
        // 模拟服务层返回false
        when(performanceEvaluationService.updateEvaluation(testEvaluation)).thenReturn(false);

        // 调用控制器方法
        Result<String> result = performanceEvaluationController.updateEvaluation(testEvaluation);

        // 验证结果
        assertNotNull(result);
        assertEquals(500, result.getCode().intValue());
        assertEquals("更新失败", result.getMessage());
        assertNull(result.getData());
        verify(performanceEvaluationService, times(1)).updateEvaluation(testEvaluation);
    }

    @Test
    public void deleteEvaluation_success() {
        // 模拟服务层返回true
        when(performanceEvaluationService.deleteEvaluation(1L)).thenReturn(true);

        // 调用控制器方法
        Result<String> result = performanceEvaluationController.deleteEvaluation(1L);

        // 验证结果
        assertNotNull(result);
        assertEquals(200, result.getCode().intValue());
        assertEquals("删除成功", result.getMessage());
        assertNull(result.getData());
        verify(performanceEvaluationService, times(1)).deleteEvaluation(1L);
    }

    @Test
    public void deleteEvaluation_failure() {
        // 模拟服务层返回false
        when(performanceEvaluationService.deleteEvaluation(1L)).thenReturn(false);

        // 调用控制器方法
        Result<String> result = performanceEvaluationController.deleteEvaluation(1L);

        // 验证结果
        assertNotNull(result);
        assertEquals(500, result.getCode().intValue());
        assertEquals("删除失败", result.getMessage());
        assertNull(result.getData());
        verify(performanceEvaluationService, times(1)).deleteEvaluation(1L);
    }
}
