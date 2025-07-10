package com.investech.controller;

import com.investech.common.Result;
import com.investech.entity.Backtest;
import com.investech.service.BacktestService;
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
public class BacktestControllerTest {

    @Mock
    private BacktestService backtestService;

    @InjectMocks
    private BacktestController backtestController;

    private Backtest testBacktest;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        // 初始化测试数据
        testBacktest = new Backtest();
        testBacktest.setId(1L);
        testBacktest.setBacktestCode("BTCODE001");
        testBacktest.setBacktestName("Test Backtest");
        testBacktest.setStrategyCode("STRAT001");
        testBacktest.setStartDate(LocalDateTime.now());
        testBacktest.setEndDate(LocalDateTime.now().plusDays(30));
        testBacktest.setInitialCapital(new BigDecimal("1000000.00"));
        testBacktest.setFinalCapital(new BigDecimal("1100000.00"));
        testBacktest.setTotalReturn(new BigDecimal("10.00"));
        testBacktest.setAnnualReturn(new BigDecimal("12.50"));
        testBacktest.setMaxDrawdown(new BigDecimal("5.00"));
        testBacktest.setSharpeRatio(new BigDecimal("1.50"));
        testBacktest.setVolatility(new BigDecimal("8.00"));
        testBacktest.setWinRate(new BigDecimal("60.00"));
        testBacktest.setProfitLossRatio(new BigDecimal("2.00"));
        testBacktest.setBenchmarkReturn("沪深300指数");
        testBacktest.setBenchmarkMaxDrawdown("7.50%");
        testBacktest.setBenchmarkSharpeRatio("1.20");
        testBacktest.setStatus("已完成");
        testBacktest.setResultData("{\"returns\": [\"0.01\", \"0.02\"]}");
        testBacktest.setCreatorId(1001L);
        testBacktest.setCreateTime(LocalDateTime.now());
        testBacktest.setUpdateTime(LocalDateTime.now());
    }

    @Test
    public void getAllBacktests_success() {
        // 准备测试数据
        List<Backtest> backtests = Arrays.asList(testBacktest);

        // 模拟服务层返回
        when(backtestService.findAll()).thenReturn(backtests);

        // 调用控制器方法
        Result<List<Backtest>> result = backtestController.getAllBacktests();

        // 验证结果
        assertNotNull(result);
        assertEquals(200, result.getCode().intValue());
        assertEquals("成功", result.getMessage());
        assertEquals(backtests, result.getData());
        verify(backtestService, times(1)).findAll();
    }

    @Test
    public void getAllBacktests_emptyList() {
        // 模拟服务层返回空列表
        when(backtestService.findAll()).thenReturn(Arrays.asList());

        // 调用控制器方法
        Result<List<Backtest>> result = backtestController.getAllBacktests();

        // 验证结果
        assertNotNull(result);
        assertEquals(200, result.getCode().intValue());
        assertEquals("成功", result.getMessage());
        assertTrue(result.getData().isEmpty());
        verify(backtestService, times(1)).findAll();
    }

    @Test
    public void getBacktestById_success() {
        // 模拟服务层返回
        when(backtestService.findById(1L)).thenReturn(testBacktest);

        // 调用控制器方法
        Result<Backtest> result = backtestController.getBacktestById(1L);

        // 验证结果
        assertNotNull(result);
        assertEquals(200, result.getCode().intValue());
        assertEquals("成功", result.getMessage());
        assertEquals(testBacktest, result.getData());
        verify(backtestService, times(1)).findById(1L);
    }

    @Test
    public void getBacktestById_notFound() {
        // 模拟服务层返回null
        when(backtestService.findById(1L)).thenReturn(null);

        // 调用控制器方法
        Result<Backtest> result = backtestController.getBacktestById(1L);

        // 验证结果
        assertNotNull(result);
        assertEquals(500, result.getCode().intValue());
        assertEquals("回测不存在", result.getMessage());
        assertNull(result.getData());
        verify(backtestService, times(1)).findById(1L);
    }

    @Test
    public void getBacktestsByStrategy_success() {
        // 准备测试数据
        List<Backtest> backtests = Arrays.asList(testBacktest);

        // 模拟服务层返回
        when(backtestService.findByStrategyCode("STRAT001")).thenReturn(backtests);

        // 调用控制器方法
        Result<List<Backtest>> result = backtestController.getBacktestsByStrategy("STRAT001");

        // 验证结果
        assertNotNull(result);
        assertEquals(200, result.getCode().intValue());
        assertEquals("成功", result.getMessage());
        assertEquals(backtests, result.getData());
        verify(backtestService, times(1)).findByStrategyCode("STRAT001");
    }

    @Test
    public void getBacktestsByStrategy_emptyList() {
        // 模拟服务层返回空列表
        when(backtestService.findByStrategyCode("STRAT001")).thenReturn(Arrays.asList());

        // 调用控制器方法
        Result<List<Backtest>> result = backtestController.getBacktestsByStrategy("STRAT001");

        // 验证结果
        assertNotNull(result);
        assertEquals(200, result.getCode().intValue());
        assertEquals("成功", result.getMessage());
        assertTrue(result.getData().isEmpty());
        verify(backtestService, times(1)).findByStrategyCode("STRAT001");
    }

    @Test
    public void createBacktest_success() {
        // 模拟服务层返回true
        when(backtestService.createBacktest(testBacktest)).thenReturn(true);

        // 调用控制器方法
        Result<String> result = backtestController.createBacktest(testBacktest);

        // 验证结果
        assertNotNull(result);
        assertEquals(200, result.getCode().intValue());
        assertEquals("创建成功", result.getMessage());
        assertNull(result.getData());
        verify(backtestService, times(1)).createBacktest(testBacktest);
    }

    @Test
    public void createBacktest_failure() {
        // 模拟服务层返回false
        when(backtestService.createBacktest(testBacktest)).thenReturn(false);

        // 调用控制器方法
        Result<String> result = backtestController.createBacktest(testBacktest);

        // 验证结果
        assertNotNull(result);
        assertEquals(500, result.getCode().intValue());
        assertEquals("创建失败", result.getMessage());
        assertNull(result.getData());
        verify(backtestService, times(1)).createBacktest(testBacktest);
    }

    @Test
    public void updateBacktest_success() {
        // 模拟服务层返回true
        when(backtestService.updateBacktest(testBacktest)).thenReturn(true);

        // 调用控制器方法
        Result<String> result = backtestController.updateBacktest(testBacktest);

        // 验证结果
        assertNotNull(result);
        assertEquals(200, result.getCode().intValue());
        assertEquals("更新成功", result.getMessage());
        assertNull(result.getData());
        verify(backtestService, times(1)).updateBacktest(testBacktest);
    }

    @Test
    public void updateBacktest_failure() {
        // 模拟服务层返回false
        when(backtestService.updateBacktest(testBacktest)).thenReturn(false);

        // 调用控制器方法
        Result<String> result = backtestController.updateBacktest(testBacktest);

        // 验证结果
        assertNotNull(result);
        assertEquals(500, result.getCode().intValue());
        assertEquals("更新失败", result.getMessage());
        assertNull(result.getData());
        verify(backtestService, times(1)).updateBacktest(testBacktest);
    }

    @Test
    public void deleteBacktest_success() {
        // 模拟服务层返回true
        when(backtestService.deleteBacktest(1L)).thenReturn(true);

        // 调用控制器方法
        Result<String> result = backtestController.deleteBacktest(1L);

        // 验证结果
        assertNotNull(result);
        assertEquals(200, result.getCode().intValue());
        assertEquals("删除成功", result.getMessage());
        assertNull(result.getData());
        verify(backtestService, times(1)).deleteBacktest(1L);
    }

    @Test
    public void deleteBacktest_failure() {
        // 模拟服务层返回false
        when(backtestService.deleteBacktest(1L)).thenReturn(false);

        // 调用控制器方法
        Result<String> result = backtestController.deleteBacktest(1L);

        // 验证结果
        assertNotNull(result);
        assertEquals(500, result.getCode().intValue());
        assertEquals("删除失败", result.getMessage());
        assertNull(result.getData());
        verify(backtestService, times(1)).deleteBacktest(1L);
    }
}
