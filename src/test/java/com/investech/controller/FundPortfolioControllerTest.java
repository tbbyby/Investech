package com.investech.controller;

import com.investech.common.Result;
import com.investech.entity.FundPortfolio;
import com.investech.service.FundPortfolioService;
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
public class FundPortfolioControllerTest {

    @Mock
    private FundPortfolioService fundPortfolioService;

    @InjectMocks
    private FundPortfolioController fundPortfolioController;

    private FundPortfolio testPortfolio;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        // 初始化测试数据
        testPortfolio = new FundPortfolio();
        testPortfolio.setId(1L);
        testPortfolio.setPortfolioName("稳健增长组合");
        testPortfolio.setPortfolioDesc("包含5只低风险基金的平衡型投资组合");
        testPortfolio.setUserId(1001L);
        testPortfolio.setFundCodes("FUND001,FUND002,FUND003,FUND004,FUND005");
        testPortfolio.setWeights("{\"FUND001\": 0.2, \"FUND002\": 0.2, \"FUND003\": 0.2, \"FUND004\": 0.2, \"FUND005\": 0.2}");
        testPortfolio.setStatus(1);
        testPortfolio.setCreateTime(LocalDateTime.now());
        testPortfolio.setUpdateTime(LocalDateTime.now());
    }

    @Test
    public void getAllPortfolios_success() {
        // 准备测试数据
        List<FundPortfolio> portfolios = Arrays.asList(testPortfolio);

        // 模拟服务层返回
        when(fundPortfolioService.findAll()).thenReturn(portfolios);

        // 调用控制器方法
        Result<List<FundPortfolio>> result = fundPortfolioController.getAllPortfolios();

        // 验证结果
        assertNotNull(result);
        assertEquals(200, result.getCode().intValue());
        assertEquals("成功", result.getMessage());
        assertEquals(portfolios, result.getData());
        verify(fundPortfolioService, times(1)).findAll();
    }

    @Test
    public void getAllPortfolios_emptyList() {
        // 模拟服务层返回空列表
        when(fundPortfolioService.findAll()).thenReturn(Arrays.asList());

        // 调用控制器方法
        Result<List<FundPortfolio>> result = fundPortfolioController.getAllPortfolios();

        // 验证结果
        assertNotNull(result);
        assertEquals(200, result.getCode().intValue());
        assertEquals("成功", result.getMessage());
        assertTrue(result.getData().isEmpty());
        verify(fundPortfolioService, times(1)).findAll();
    }

    @Test
    public void getPortfolioById_success() {
        // 模拟服务层返回
        when(fundPortfolioService.findById(1L)).thenReturn(testPortfolio);

        // 调用控制器方法
        Result<FundPortfolio> result = fundPortfolioController.getPortfolioById(1L);

        // 验证结果
        assertNotNull(result);
        assertEquals(200, result.getCode().intValue());
        assertEquals("成功", result.getMessage());
        assertEquals(testPortfolio, result.getData());
        verify(fundPortfolioService, times(1)).findById(1L);
    }

    @Test
    public void getPortfolioById_notFound() {
        // 模拟服务层返回null
        when(fundPortfolioService.findById(1L)).thenReturn(null);

        // 调用控制器方法
        Result<FundPortfolio> result = fundPortfolioController.getPortfolioById(1L);

        // 验证结果
        assertNotNull(result);
        assertEquals(500, result.getCode().intValue());
        assertEquals("组合不存在", result.getMessage());
        assertNull(result.getData());
        verify(fundPortfolioService, times(1)).findById(1L);
    }

    @Test
    public void getPortfoliosByUserId_success() {
        // 准备测试数据
        List<FundPortfolio> portfolios = Arrays.asList(testPortfolio);

        // 模拟服务层返回
        when(fundPortfolioService.findByUserId(1001L)).thenReturn(portfolios);

        // 调用控制器方法
        Result<List<FundPortfolio>> result = fundPortfolioController.getPortfoliosByUserId(1001L);

        // 验证结果
        assertNotNull(result);
        assertEquals(200, result.getCode().intValue());
        assertEquals("成功", result.getMessage());
        assertEquals(portfolios, result.getData());
        verify(fundPortfolioService, times(1)).findByUserId(1001L);
    }

    @Test
    public void getPortfoliosByUserId_emptyList() {
        // 模拟服务层返回空列表
        when(fundPortfolioService.findByUserId(1001L)).thenReturn(Arrays.asList());

        // 调用控制器方法
        Result<List<FundPortfolio>> result = fundPortfolioController.getPortfoliosByUserId(1001L);

        // 验证结果
        assertNotNull(result);
        assertEquals(200, result.getCode().intValue());
        assertEquals("成功", result.getMessage());
        assertTrue(result.getData().isEmpty());
        verify(fundPortfolioService, times(1)).findByUserId(1001L);
    }

    @Test
    public void createPortfolio_success() {
        // 模拟服务层返回true
        when(fundPortfolioService.createPortfolio(testPortfolio)).thenReturn(true);

        // 调用控制器方法
        Result<String> result = fundPortfolioController.createPortfolio(testPortfolio);

        // 验证结果
        assertNotNull(result);
        assertEquals(200, result.getCode().intValue());
        assertEquals("创建成功", result.getMessage());
        assertNull(result.getData());
        verify(fundPortfolioService, times(1)).createPortfolio(testPortfolio);
    }

    @Test
    public void createPortfolio_failure() {
        // 模拟服务层返回false
        when(fundPortfolioService.createPortfolio(testPortfolio)).thenReturn(false);

        // 调用控制器方法
        Result<String> result = fundPortfolioController.createPortfolio(testPortfolio);

        // 验证结果
        assertNotNull(result);
        assertEquals(500, result.getCode().intValue());
        assertEquals("创建失败", result.getMessage());
        assertNull(result.getData());
        verify(fundPortfolioService, times(1)).createPortfolio(testPortfolio);
    }

    @Test
    public void updatePortfolio_success() {
        // 模拟服务层返回true
        when(fundPortfolioService.updatePortfolio(testPortfolio)).thenReturn(true);

        // 调用控制器方法
        Result<String> result = fundPortfolioController.updatePortfolio(testPortfolio);

        // 验证结果
        assertNotNull(result);
        assertEquals(200, result.getCode().intValue());
        assertEquals("更新成功", result.getMessage());
        assertNull(result.getData());
        verify(fundPortfolioService, times(1)).updatePortfolio(testPortfolio);
    }

    @Test
    public void updatePortfolio_failure() {
        // 模拟服务层返回false
        when(fundPortfolioService.updatePortfolio(testPortfolio)).thenReturn(false);

        // 调用控制器方法
        Result<String> result = fundPortfolioController.updatePortfolio(testPortfolio);

        // 验证结果
        assertNotNull(result);
        assertEquals(500, result.getCode().intValue());
        assertEquals("更新失败", result.getMessage());
        assertNull(result.getData());
        verify(fundPortfolioService, times(1)).updatePortfolio(testPortfolio);
    }

    @Test
    public void deletePortfolio_success() {
        // 模拟服务层返回true
        when(fundPortfolioService.deletePortfolio(1L)).thenReturn(true);

        // 调用控制器方法
        Result<String> result = fundPortfolioController.deletePortfolio(1L);

        // 验证结果
        assertNotNull(result);
        assertEquals(200, result.getCode().intValue());
        assertEquals("删除成功", result.getMessage());
        assertNull(result.getData());
        verify(fundPortfolioService, times(1)).deletePortfolio(1L);
    }

    @Test
    public void deletePortfolio_failure() {
        // 模拟服务层返回false
        when(fundPortfolioService.deletePortfolio(1L)).thenReturn(false);

        // 调用控制器方法
        Result<String> result = fundPortfolioController.deletePortfolio(1L);

        // 验证结果
        assertNotNull(result);
        assertEquals(500, result.getCode().intValue());
        assertEquals("删除失败", result.getMessage());
        assertNull(result.getData());
        verify(fundPortfolioService, times(1)).deletePortfolio(1L);
    }
}
