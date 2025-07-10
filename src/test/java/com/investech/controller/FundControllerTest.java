package com.investech.controller;

import com.investech.common.Result;
import com.investech.entity.Fund;
import com.investech.service.FundService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class FundControllerTest {

    @Mock
    private FundService fundService;

    @InjectMocks
    private FundController fundController;

    private Fund testFund;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        // 初始化测试数据
        testFund = new Fund();
        testFund.setId(1L);
        testFund.setFundCode("FUND001");
        testFund.setFundName("稳健增长基金");
        testFund.setFundType("混合型");
        testFund.setFundCompany("华夏基金");
        testFund.setFundManager("张伟");
        testFund.setNetValue(new BigDecimal("1.25"));
        testFund.setNetValueDate(LocalDate.now());
        testFund.setTotalAssets(new BigDecimal("500000000.00"));
        testFund.setDailyReturn(new BigDecimal("0.15"));
        testFund.setWeeklyReturn(new BigDecimal("1.20"));
        testFund.setMonthlyReturn(new BigDecimal("3.50"));
        testFund.setYearlyReturn(new BigDecimal("12.50"));
        testFund.setMaxDrawdown(new BigDecimal("8.25"));
        testFund.setSharpeRatio(new BigDecimal("1.8"));
        testFund.setRiskLevel("中等风险");
        testFund.setTags("{\"category\": \"成长型\", \"style\": \"价值投资\"}");
        testFund.setStatus(1);
        testFund.setCreateTime(LocalDate.now());
        testFund.setUpdateTime(LocalDate.now());
    }

    @Test
    public void getAllFunds_success() {
        // 准备测试数据
        List<Fund> funds = Arrays.asList(testFund);

        // 模拟服务层返回
        when(fundService.findAll()).thenReturn(funds);

        // 调用控制器方法
        Result<List<Fund>> result = fundController.getAllFunds();

        // 验证结果
        assertNotNull(result);
        assertEquals(200, result.getCode().intValue());
        assertEquals("成功", result.getMessage());
        assertEquals(funds, result.getData());
        verify(fundService, times(1)).findAll();
    }

    @Test
    public void getAllFunds_emptyList() {
        // 模拟服务层返回空列表
        when(fundService.findAll()).thenReturn(Arrays.asList());

        // 调用控制器方法
        Result<List<Fund>> result = fundController.getAllFunds();

        // 验证结果
        assertNotNull(result);
        assertEquals(200, result.getCode().intValue());
        assertEquals("成功", result.getMessage());
        assertTrue(result.getData().isEmpty());
        verify(fundService, times(1)).findAll();
    }

    @Test
    public void getFundById_success() {
        // 模拟服务层返回
        when(fundService.findById(1L)).thenReturn(testFund);

        // 调用控制器方法
        Result<Fund> result = fundController.getFundById(1L);

        // 验证结果
        assertNotNull(result);
        assertEquals(200, result.getCode().intValue());
        assertEquals("成功", result.getMessage());
        assertEquals(testFund, result.getData());
        verify(fundService, times(1)).findById(1L);
    }

    @Test
    public void getFundById_notFound() {
        // 模拟服务层返回null
        when(fundService.findById(1L)).thenReturn(null);

        // 调用控制器方法
        Result<Fund> result = fundController.getFundById(1L);

        // 验证结果
        assertNotNull(result);
        assertEquals(500, result.getCode().intValue());
        assertEquals("基金不存在", result.getMessage());
        assertNull(result.getData());
        verify(fundService, times(1)).findById(1L);
    }

    @Test
    public void getFundByCode_success() {
        // 模拟服务层返回
        when(fundService.findByFundCode("FUND001")).thenReturn(testFund);

        // 调用控制器方法
        Result<Fund> result = fundController.getFundByCode("FUND001");

        // 验证结果
        assertNotNull(result);
        assertEquals(200, result.getCode().intValue());
        assertEquals("成功", result.getMessage());
        assertEquals(testFund, result.getData());
        verify(fundService, times(1)).findByFundCode("FUND001");
    }

    @Test
    public void getFundByCode_notFound() {
        // 模拟服务层返回null
        when(fundService.findByFundCode("FUND001")).thenReturn(null);

        // 调用控制器方法
        Result<Fund> result = fundController.getFundByCode("FUND001");

        // 验证结果
        assertNotNull(result);
        assertEquals(500, result.getCode().intValue());
        assertEquals("基金不存在", result.getMessage());
        assertNull(result.getData());
        verify(fundService, times(1)).findByFundCode("FUND001");
    }

    @Test
    public void getFundsByCompany_success() {
        // 准备测试数据
        List<Fund> funds = Arrays.asList(testFund);

        // 模拟服务层返回
        when(fundService.findByCompany("华夏基金")).thenReturn(funds);

        // 调用控制器方法
        Result<List<Fund>> result = fundController.getFundsByCompany("华夏基金");

        // 验证结果
        assertNotNull(result);
        assertEquals(200, result.getCode().intValue());
        assertEquals("成功", result.getMessage());
        assertEquals(funds, result.getData());
        verify(fundService, times(1)).findByCompany("华夏基金");
    }

    @Test
    public void getFundsByCompany_emptyList() {
        // 模拟服务层返回空列表
        when(fundService.findByCompany("华夏基金")).thenReturn(Arrays.asList());

        // 调用控制器方法
        Result<List<Fund>> result = fundController.getFundsByCompany("华夏基金");

        // 验证结果
        assertNotNull(result);
        assertEquals(200, result.getCode().intValue());
        assertEquals("成功", result.getMessage());
        assertTrue(result.getData().isEmpty());
        verify(fundService, times(1)).findByCompany("华夏基金");
    }

    @Test
    public void getFundsByManager_success() {
        // 准备测试数据
        List<Fund> funds = Arrays.asList(testFund);

        // 模拟服务层返回
        when(fundService.findByManager("张伟")).thenReturn(funds);

        // 调用控制器方法
        Result<List<Fund>> result = fundController.getFundsByManager("张伟");

        // 验证结果
        assertNotNull(result);
        assertEquals(200, result.getCode().intValue());
        assertEquals("成功", result.getMessage());
        assertEquals(funds, result.getData());
        verify(fundService, times(1)).findByManager("张伟");
    }

    @Test
    public void getFundsByManager_emptyList() {
        // 模拟服务层返回空列表
        when(fundService.findByManager("张伟")).thenReturn(Arrays.asList());

        // 调用控制器方法
        Result<List<Fund>> result = fundController.getFundsByManager("张伟");

        // 验证结果
        assertNotNull(result);
        assertEquals(200, result.getCode().intValue());
        assertEquals("成功", result.getMessage());
        assertTrue(result.getData().isEmpty());
        verify(fundService, times(1)).findByManager("张伟");
    }

    @Test
    public void searchFunds_withAllParams_success() {
        // 准备测试数据
        List<Fund> funds = Arrays.asList(testFund);

        // 模拟服务层返回
        when(fundService.searchFunds("FUND001", "稳健增长", "华夏基金",
                "张伟", "混合型", "中等风险", "{\"category\": \"成长型\"}"))
                .thenReturn(funds);

        // 调用控制器方法
        Result<List<Fund>> result = fundController.searchFunds("FUND001", "稳健增长", "华夏基金",
                "张伟", "混合型", "中等风险", "{\"category\": \"成长型\"}");

        // 验证结果
        assertNotNull(result);
        assertEquals(200, result.getCode().intValue());
        assertEquals("成功", result.getMessage());
        assertEquals(funds, result.getData());
        verify(fundService, times(1)).searchFunds("FUND001", "稳健增长", "华夏基金",
                "张伟", "混合型", "中等风险", "{\"category\": \"成长型\"}");
    }

    @Test
    public void searchFunds_withNoParams_success() {
        // 准备测试数据
        List<Fund> funds = Arrays.asList(testFund);

        // 模拟服务层返回
        when(fundService.searchFunds(null, null, null, null, null, null, null))
                .thenReturn(funds);

        // 调用控制器方法
        Result<List<Fund>> result = fundController.searchFunds(null, null, null, null, null, null, null);

        // 验证结果
        assertNotNull(result);
        assertEquals(200, result.getCode().intValue());
        assertEquals("成功", result.getMessage());
        assertEquals(funds, result.getData());
        verify(fundService, times(1)).searchFunds(null, null, null, null, null, null, null);
    }

    @Test
    public void addFund_success() {
        // 模拟服务层返回true
        when(fundService.addFund(testFund)).thenReturn(true);

        // 调用控制器方法
        Result<String> result = fundController.addFund(testFund);

        // 验证结果
        assertNotNull(result);
        assertEquals(200, result.getCode().intValue());
        assertEquals("添加成功", result.getMessage());
        assertNull(result.getData());
        verify(fundService, times(1)).addFund(testFund);
    }

    @Test
    public void addFund_failure() {
        // 模拟服务层返回false
        when(fundService.addFund(testFund)).thenReturn(false);

        // 调用控制器方法
        Result<String> result = fundController.addFund(testFund);

        // 验证结果
        assertNotNull(result);
        assertEquals(500, result.getCode().intValue());
        assertEquals("添加失败", result.getMessage());
        assertNull(result.getData());
        verify(fundService, times(1)).addFund(testFund);
    }

    @Test
    public void updateFund_success() {
        // 模拟服务层返回true
        when(fundService.updateFund(testFund)).thenReturn(true);

        // 调用控制器方法
        Result<String> result = fundController.updateFund(testFund);

        // 验证结果
        assertNotNull(result);
        assertEquals(200, result.getCode().intValue());
        assertEquals("更新成功", result.getMessage());
        assertNull(result.getData());
        verify(fundService, times(1)).updateFund(testFund);
    }

    @Test
    public void updateFund_failure() {
        // 模拟服务层返回false
        when(fundService.updateFund(testFund)).thenReturn(false);

        // 调用控制器方法
        Result<String> result = fundController.updateFund(testFund);

        // 验证结果
        assertNotNull(result);
        assertEquals(500, result.getCode().intValue());
        assertEquals("更新失败", result.getMessage());
        assertNull(result.getData());
        verify(fundService, times(1)).updateFund(testFund);
    }

    @Test
    public void deleteFund_success() {
        // 模拟服务层返回true
        when(fundService.deleteFund(1L)).thenReturn(true);

        // 调用控制器方法
        Result<String> result = fundController.deleteFund(1L);

        // 验证结果
        assertNotNull(result);
        assertEquals(200, result.getCode().intValue());
        assertEquals("删除成功", result.getMessage());
        assertNull(result.getData());
        verify(fundService, times(1)).deleteFund(1L);
    }

    @Test
    public void deleteFund_failure() {
        // 模拟服务层返回false
        when(fundService.deleteFund(1L)).thenReturn(false);

        // 调用控制器方法
        Result<String> result = fundController.deleteFund(1L);

        // 验证结果
        assertNotNull(result);
        assertEquals(500, result.getCode().intValue());
        assertEquals("删除失败", result.getMessage());
        assertNull(result.getData());
        verify(fundService, times(1)).deleteFund(1L);
    }
}
