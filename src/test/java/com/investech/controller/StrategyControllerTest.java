package com.investech.controller;

import com.investech.common.Result;
import com.investech.entity.Strategy;
import com.investech.service.StrategyService;
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
public class StrategyControllerTest {

    @Mock
    private StrategyService strategyService;

    @InjectMocks
    private StrategyController strategyController;

    private Strategy testStrategy;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        // 初始化测试数据
        testStrategy = new Strategy();
        testStrategy.setId(1L);
        testStrategy.setStrategyCode("STRATEGY001");
        testStrategy.setStrategyName("价值投资策略");
        testStrategy.setStrategyDesc("基于基本面分析的价值投资策略");
        testStrategy.setStrategyType("股票策略");
        testStrategy.setInvestmentStyle("价值型");
        testStrategy.setRiskLevel("中风险");
        testStrategy.setTargetReturn("8%-10%");
        testStrategy.setMaxDrawdown("15%");
        testStrategy.setBenchmark("沪深300指数");
        testStrategy.setFactorCodes("[\"PE\",\"PB\",\"ROE\"]");
        testStrategy.setParameters("{\"holdingPeriod\": \"长期\", \"sector\": \"金融\"}");
        testStrategy.setCreatorId(1001L);
        testStrategy.setStatus(1);
        testStrategy.setCreateTime(LocalDateTime.now());
        testStrategy.setUpdateTime(LocalDateTime.now());
    }

    @Test
    public void getAllStrategies_success() {
        // 准备测试数据
        List<Strategy> strategies = Arrays.asList(testStrategy);

        // 模拟服务层返回
        when(strategyService.findAll()).thenReturn(strategies);

        // 调用控制器方法
        Result<List<Strategy>> result = strategyController.getAllStrategies();

        // 验证结果
        assertNotNull(result);
        assertEquals(200, result.getCode().intValue());
        assertEquals("成功", result.getMessage());
        assertEquals(strategies, result.getData());
        verify(strategyService, times(1)).findAll();
    }

    @Test
    public void getAllStrategies_emptyList() {
        // 模拟服务层返回空列表
        when(strategyService.findAll()).thenReturn(Arrays.asList());

        // 调用控制器方法
        Result<List<Strategy>> result = strategyController.getAllStrategies();

        // 验证结果
        assertNotNull(result);
        assertEquals(200, result.getCode().intValue());
        assertEquals("成功", result.getMessage());
        assertTrue(result.getData().isEmpty());
        verify(strategyService, times(1)).findAll();
    }

    @Test
    public void getStrategyById_success() {
        // 模拟服务层返回
        when(strategyService.findById(1L)).thenReturn(testStrategy);

        // 调用控制器方法
        Result<Strategy> result = strategyController.getStrategyById(1L);

        // 验证结果
        assertNotNull(result);
        assertEquals(200, result.getCode().intValue());
        assertEquals("成功", result.getMessage());
        assertEquals(testStrategy, result.getData());
        verify(strategyService, times(1)).findById(1L);
    }

    @Test
    public void getStrategyById_notFound() {
        // 模拟服务层返回null
        when(strategyService.findById(1L)).thenReturn(null);

        // 调用控制器方法
        Result<Strategy> result = strategyController.getStrategyById(1L);

        // 验证结果
        assertNotNull(result);
        assertEquals(500, result.getCode().intValue());
        assertEquals("策略不存在", result.getMessage());
        assertNull(result.getData());
        verify(strategyService, times(1)).findById(1L);
    }

    @Test
    public void getStrategyByCode_success() {
        // 模拟服务层返回
        when(strategyService.findByStrategyCode("STRATEGY001")).thenReturn(testStrategy);

        // 调用控制器方法
        Result<Strategy> result = strategyController.getStrategyByCode("STRATEGY001");

        // 验证结果
        assertNotNull(result);
        assertEquals(200, result.getCode().intValue());
        assertEquals("成功", result.getMessage());
        assertEquals(testStrategy, result.getData());
        verify(strategyService, times(1)).findByStrategyCode("STRATEGY001");
    }

    @Test
    public void getStrategyByCode_notFound() {
        // 模拟服务层返回null
        when(strategyService.findByStrategyCode("STRATEGY001")).thenReturn(null);

        // 调用控制器方法
        Result<Strategy> result = strategyController.getStrategyByCode("STRATEGY001");

        // 验证结果
        assertNotNull(result);
        assertEquals(500, result.getCode().intValue());
        assertEquals("策略不存在", result.getMessage());
        assertNull(result.getData());
        verify(strategyService, times(1)).findByStrategyCode("STRATEGY001");
    }

    @Test
    public void getStrategiesByCreator_success() {
        // 准备测试数据
        List<Strategy> strategies = Arrays.asList(testStrategy);

        // 模拟服务层返回
        when(strategyService.findByCreatorId(1001L)).thenReturn(strategies);

        // 调用控制器方法
        Result<List<Strategy>> result = strategyController.getStrategiesByCreator(1001L);

        // 验证结果
        assertNotNull(result);
        assertEquals(200, result.getCode().intValue());
        assertEquals("成功", result.getMessage());
        assertEquals(strategies, result.getData());
        verify(strategyService, times(1)).findByCreatorId(1001L);
    }

    @Test
    public void getStrategiesByCreator_emptyList() {
        // 模拟服务层返回空列表
        when(strategyService.findByCreatorId(1001L)).thenReturn(Arrays.asList());

        // 调用控制器方法
        Result<List<Strategy>> result = strategyController.getStrategiesByCreator(1001L);

        // 验证结果
        assertNotNull(result);
        assertEquals(200, result.getCode().intValue());
        assertEquals("成功", result.getMessage());
        assertTrue(result.getData().isEmpty());
        verify(strategyService, times(1)).findByCreatorId(1001L);
    }

    @Test
    public void getStrategiesByType_success() {
        // 准备测试数据
        List<Strategy> strategies = Arrays.asList(testStrategy);

        // 模拟服务层返回
        when(strategyService.findByType("股票策略")).thenReturn(strategies);

        // 调用控制器方法
        Result<List<Strategy>> result = strategyController.getStrategiesByType("股票策略");

        // 验证结果
        assertNotNull(result);
        assertEquals(200, result.getCode().intValue());
        assertEquals("成功", result.getMessage());
        assertEquals(strategies, result.getData());
        verify(strategyService, times(1)).findByType("股票策略");
    }

    @Test
    public void getStrategiesByType_emptyList() {
        // 模拟服务层返回空列表
        when(strategyService.findByType("股票策略")).thenReturn(Arrays.asList());

        // 调用控制器方法
        Result<List<Strategy>> result = strategyController.getStrategiesByType("股票策略");

        // 验证结果
        assertNotNull(result);
        assertEquals(200, result.getCode().intValue());
        assertEquals("成功", result.getMessage());
        assertTrue(result.getData().isEmpty());
        verify(strategyService, times(1)).findByType("股票策略");
    }

    @Test
    public void getStrategiesByStyle_success() {
        // 准备测试数据
        List<Strategy> strategies = Arrays.asList(testStrategy);

        // 模拟服务层返回
        when(strategyService.findByInvestmentStyle("价值型")).thenReturn(strategies);

        // 调用控制器方法
        Result<List<Strategy>> result = strategyController.getStrategiesByStyle("价值型");

        // 验证结果
        assertNotNull(result);
        assertEquals(200, result.getCode().intValue());
        assertEquals("成功", result.getMessage());
        assertEquals(strategies, result.getData());
        verify(strategyService, times(1)).findByInvestmentStyle("价值型");
    }

    @Test
    public void getStrategiesByStyle_emptyList() {
        // 模拟服务层返回空列表
        when(strategyService.findByInvestmentStyle("价值型")).thenReturn(Arrays.asList());

        // 调用控制器方法
        Result<List<Strategy>> result = strategyController.getStrategiesByStyle("价值型");

        // 验证结果
        assertNotNull(result);
        assertEquals(200, result.getCode().intValue());
        assertEquals("成功", result.getMessage());
        assertTrue(result.getData().isEmpty());
        verify(strategyService, times(1)).findByInvestmentStyle("价值型");
    }

    @Test
    public void getStrategiesByRiskLevel_success() {
        // 准备测试数据
        List<Strategy> strategies = Arrays.asList(testStrategy);

        // 模拟服务层返回
        when(strategyService.findByRiskLevel("中风险")).thenReturn(strategies);

        // 调用控制器方法
        Result<List<Strategy>> result = strategyController.getStrategiesByRiskLevel("中风险");

        // 验证结果
        assertNotNull(result);
        assertEquals(200, result.getCode().intValue());
        assertEquals("成功", result.getMessage());
        assertEquals(strategies, result.getData());
        verify(strategyService, times(1)).findByRiskLevel("中风险");
    }

    @Test
    public void getStrategiesByRiskLevel_emptyList() {
        // 模拟服务层返回空列表
        when(strategyService.findByRiskLevel("中风险")).thenReturn(Arrays.asList());

        // 调用控制器方法
        Result<List<Strategy>> result = strategyController.getStrategiesByRiskLevel("中风险");

        // 验证结果
        assertNotNull(result);
        assertEquals(200, result.getCode().intValue());
        assertEquals("成功", result.getMessage());
        assertTrue(result.getData().isEmpty());
        verify(strategyService, times(1)).findByRiskLevel("中风险");
    }

    @Test
    public void searchStrategies_success() {
        // 准备测试数据
        List<Strategy> strategies = Arrays.asList(testStrategy);

        // 模拟服务层返回
        when(strategyService.searchStrategies(
                "STRATEGY001",
                "价值投资策略",
                "股票策略",
                "价值型",
                "中风险",
                1001L))
                .thenReturn(strategies);

        // 调用控制器方法
        Result<List<Strategy>> result = strategyController.searchStrategies(
                "STRATEGY001",
                "价值投资策略",
                "股票策略",
                "价值型",
                "中风险",
                1001L);

        // 验证结果
        assertNotNull(result);
        assertEquals(200, result.getCode().intValue());
        assertEquals("成功", result.getMessage());
        assertEquals(strategies, result.getData());
        verify(strategyService, times(1)).searchStrategies(
                "STRATEGY001",
                "价值投资策略",
                "股票策略",
                "价值型",
                "中风险",
                1001L);
    }

    @Test
    public void searchStrategies_emptyList() {
        // 模拟服务层返回空列表
        when(strategyService.searchStrategies(
                "STRATEGY001",
                "价值投资策略",
                "股票策略",
                "价值型",
                "中风险",
                1001L))
                .thenReturn(Arrays.asList());

        // 调用控制器方法
        Result<List<Strategy>> result = strategyController.searchStrategies(
                "STRATEGY001",
                "价值投资策略",
                "股票策略",
                "价值型",
                "中风险",
                1001L);

        // 验证结果
        assertNotNull(result);
        assertEquals(200, result.getCode().intValue());
        assertEquals("成功", result.getMessage());
        assertTrue(result.getData().isEmpty());
        verify(strategyService, times(1)).searchStrategies(
                "STRATEGY001",
                "价值投资策略",
                "股票策略",
                "价值型",
                "中风险",
                1001L);
    }

    @Test
    public void createStrategy_success() {
        // 模拟服务层返回true
        when(strategyService.createStrategy(testStrategy)).thenReturn(true);

        // 调用控制器方法
        Result<String> result = strategyController.createStrategy(testStrategy);

        // 验证结果
        assertNotNull(result);
        assertEquals(200, result.getCode().intValue());
        assertEquals("创建成功", result.getMessage());
        assertNull(result.getData());
        verify(strategyService, times(1)).createStrategy(testStrategy);
    }

    @Test
    public void createStrategy_failure() {
        // 模拟服务层返回false
        when(strategyService.createStrategy(testStrategy)).thenReturn(false);

        // 调用控制器方法
        Result<String> result = strategyController.createStrategy(testStrategy);

        // 验证结果
        assertNotNull(result);
        assertEquals(500, result.getCode().intValue());
        assertEquals("创建失败", result.getMessage());
        assertNull(result.getData());
        verify(strategyService, times(1)).createStrategy(testStrategy);
    }

    @Test
    public void updateStrategy_success() {
        // 模拟服务层返回true
        when(strategyService.updateStrategy(testStrategy)).thenReturn(true);

        // 调用控制器方法
        Result<String> result = strategyController.updateStrategy(testStrategy);

        // 验证结果
        assertNotNull(result);
        assertEquals(200, result.getCode().intValue());
        assertEquals("更新成功", result.getMessage());
        assertNull(result.getData());
        verify(strategyService, times(1)).updateStrategy(testStrategy);
    }

    @Test
    public void updateStrategy_failure() {
        // 模拟服务层返回false
        when(strategyService.updateStrategy(testStrategy)).thenReturn(false);

        // 调用控制器方法
        Result<String> result = strategyController.updateStrategy(testStrategy);

        // 验证结果
        assertNotNull(result);
        assertEquals(500, result.getCode().intValue());
        assertEquals("更新失败", result.getMessage());
        assertNull(result.getData());
        verify(strategyService, times(1)).updateStrategy(testStrategy);
    }

    @Test
    public void deleteStrategy_success() {
        // 模拟服务层返回true
        when(strategyService.deleteStrategy(1L)).thenReturn(true);

        // 调用控制器方法
        Result<String> result = strategyController.deleteStrategy(1L);

        // 验证结果
        assertNotNull(result);
        assertEquals(200, result.getCode().intValue());
        assertEquals("删除成功", result.getMessage());
        assertNull(result.getData());
        verify(strategyService, times(1)).deleteStrategy(1L);
    }

    @Test
    public void deleteStrategy_failure() {
        // 模拟服务层返回false
        when(strategyService.deleteStrategy(1L)).thenReturn(false);

        // 调用控制器方法
        Result<String> result = strategyController.deleteStrategy(1L);

        // 验证结果
        assertNotNull(result);
        assertEquals(500, result.getCode().intValue());
        assertEquals("删除失败", result.getMessage());
        assertNull(result.getData());
        verify(strategyService, times(1)).deleteStrategy(1L);
    }
}
