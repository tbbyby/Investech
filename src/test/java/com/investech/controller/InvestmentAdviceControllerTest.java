package com.investech.controller;

import com.investech.common.Result;
import com.investech.entity.InvestmentAdvice;
import com.investech.service.InvestmentAdviceService;
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
public class InvestmentAdviceControllerTest {

    @Mock
    private InvestmentAdviceService investmentAdviceService;

    @InjectMocks
    private InvestmentAdviceController investmentAdviceController;

    private InvestmentAdvice testAdvice;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        // 初始化测试数据
        testAdvice = new InvestmentAdvice();
        testAdvice.setId(1L);
        testAdvice.setClientId(1001L);
        testAdvice.setAdviceType("资产配置");
        testAdvice.setTitle("2025年Q3投资建议");
        testAdvice.setContent("基于当前市场环境，建议增加科技类基金配置比例...");
        testAdvice.setRiskLevel("中");
        testAdvice.setExpectedReturn(8.5);
        testAdvice.setMaxDrawdown(15.0);
        testAdvice.setFundRecommendations("[\"FUND001\", \"FUND002\", \"FUND003\"]");
        testAdvice.setAssetAllocation("{\"股票\": 60, \"债券\": 30, \"现金\": 10}");
        testAdvice.setStatus(1);
        testAdvice.setCreateTime(LocalDateTime.now());
        testAdvice.setUpdateTime(LocalDateTime.now());
    }

    @Test
    public void getAllAdvices_success() {
        // 准备测试数据
        List<InvestmentAdvice> advices = Arrays.asList(testAdvice);

        // 模拟服务层返回
        when(investmentAdviceService.getAllAdvices()).thenReturn(advices);

        // 调用控制器方法
        Result<List<InvestmentAdvice>> result = investmentAdviceController.getAllAdvices();

        // 验证结果
        assertNotNull(result);
        assertEquals(200, result.getCode().intValue());
        assertEquals("成功", result.getMessage());
        assertEquals(advices, result.getData());
        verify(investmentAdviceService, times(1)).getAllAdvices();
    }

    @Test
    public void getAllAdvices_emptyList() {
        // 模拟服务层返回空列表
        when(investmentAdviceService.getAllAdvices()).thenReturn(Arrays.asList());

        // 调用控制器方法
        Result<List<InvestmentAdvice>> result = investmentAdviceController.getAllAdvices();

        // 验证结果
        assertNotNull(result);
        assertEquals(200, result.getCode().intValue());
        assertEquals("成功", result.getMessage());
        assertTrue(result.getData().isEmpty());
        verify(investmentAdviceService, times(1)).getAllAdvices();
    }

    @Test
    public void getAllAdvices_serviceException() {
        // 模拟服务层抛出异常
        when(investmentAdviceService.getAllAdvices()).thenThrow(new RuntimeException("Database error"));

        // 调用控制器方法
        Result<List<InvestmentAdvice>> result = investmentAdviceController.getAllAdvices();

        // 验证结果
        assertNotNull(result);
        assertEquals(500, result.getCode().intValue());
        assertTrue(result.getMessage().contains("获取投资建议列表失败"));
        assertNull(result.getData());
        verify(investmentAdviceService, times(1)).getAllAdvices();
    }

    @Test
    public void getAdviceById_success() {
        // 模拟服务层返回
        when(investmentAdviceService.getAdviceById(1L)).thenReturn(testAdvice);

        // 调用控制器方法
        Result<InvestmentAdvice> result = investmentAdviceController.getAdviceById(1L);

        // 验证结果
        assertNotNull(result);
        assertEquals(200, result.getCode().intValue());
        assertEquals("成功", result.getMessage());
        assertEquals(testAdvice, result.getData());
        verify(investmentAdviceService, times(1)).getAdviceById(1L);
    }

    @Test
    public void getAdviceById_notFound() {
        // 模拟服务层返回null
        when(investmentAdviceService.getAdviceById(1L)).thenReturn(null);

        // 调用控制器方法
        Result<InvestmentAdvice> result = investmentAdviceController.getAdviceById(1L);

        // 验证结果
        assertNotNull(result);
        assertEquals(500, result.getCode().intValue());
        assertEquals("投资建议不存在", result.getMessage());
        assertNull(result.getData());
        verify(investmentAdviceService, times(1)).getAdviceById(1L);
    }

    @Test
    public void getAdviceById_serviceException() {
        // 模拟服务层抛出异常
        when(investmentAdviceService.getAdviceById(1L)).thenThrow(new RuntimeException("Database error"));

        // 调用控制器方法
        Result<InvestmentAdvice> result = investmentAdviceController.getAdviceById(1L);

        // 验证结果
        assertNotNull(result);
        assertEquals(500, result.getCode().intValue());
        assertTrue(result.getMessage().contains("获取投资建议失败"));
        assertNull(result.getData());
        verify(investmentAdviceService, times(1)).getAdviceById(1L);
    }

    @Test
    public void getAdvicesByClientId_success() {
        // 准备测试数据
        List<InvestmentAdvice> advices = Arrays.asList(testAdvice);

        // 模拟服务层返回
        when(investmentAdviceService.getAdvicesByClientId(1001L)).thenReturn(advices);

        // 调用控制器方法
        Result<List<InvestmentAdvice>> result = investmentAdviceController.getAdvicesByClientId(1001L);

        // 验证结果
        assertNotNull(result);
        assertEquals(200, result.getCode().intValue());
        assertEquals("成功", result.getMessage());
        assertEquals(advices, result.getData());
        verify(investmentAdviceService, times(1)).getAdvicesByClientId(1001L);
    }

    @Test
    public void getAdvicesByClientId_emptyList() {
        // 模拟服务层返回空列表
        when(investmentAdviceService.getAdvicesByClientId(1001L)).thenReturn(Arrays.asList());

        // 调用控制器方法
        Result<List<InvestmentAdvice>> result = investmentAdviceController.getAdvicesByClientId(1001L);

        // 验证结果
        assertNotNull(result);
        assertEquals(200, result.getCode().intValue());
        assertEquals("成功", result.getMessage());
        assertTrue(result.getData().isEmpty());
        verify(investmentAdviceService, times(1)).getAdvicesByClientId(1001L);
    }

    @Test
    public void getAdvicesByClientId_serviceException() {
        // 模拟服务层抛出异常
        when(investmentAdviceService.getAdvicesByClientId(1001L)).thenThrow(new RuntimeException("Database error"));

        // 调用控制器方法
        Result<List<InvestmentAdvice>> result = investmentAdviceController.getAdvicesByClientId(1001L);

        // 验证结果
        assertNotNull(result);
        assertEquals(500, result.getCode().intValue());
        assertTrue(result.getMessage().contains("获取客户投资建议失败"));
        assertNull(result.getData());
        verify(investmentAdviceService, times(1)).getAdvicesByClientId(1001L);
    }

    @Test
    public void searchAdvices_success() {
        // 准备测试数据
        List<InvestmentAdvice> advices = Arrays.asList(testAdvice);

        // 模拟服务层返回
        when(investmentAdviceService.getAdvicesByCondition(testAdvice)).thenReturn(advices);

        // 调用控制器方法
        Result<List<InvestmentAdvice>> result = investmentAdviceController.searchAdvices(testAdvice);

        // 验证结果
        assertNotNull(result);
        assertEquals(200, result.getCode().intValue());
        assertEquals("成功", result.getMessage());
        assertEquals(advices, result.getData());
        verify(investmentAdviceService, times(1)).getAdvicesByCondition(testAdvice);
    }

    @Test
    public void searchAdvices_emptyList() {
        // 模拟服务层返回空列表
        when(investmentAdviceService.getAdvicesByCondition(testAdvice)).thenReturn(Arrays.asList());

        // 调用控制器方法
        Result<List<InvestmentAdvice>> result = investmentAdviceController.searchAdvices(testAdvice);

        // 验证结果
        assertNotNull(result);
        assertEquals(200, result.getCode().intValue());
        assertEquals("成功", result.getMessage());
        assertTrue(result.getData().isEmpty());
        verify(investmentAdviceService, times(1)).getAdvicesByCondition(testAdvice);
    }

    @Test
    public void searchAdvices_serviceException() {
        // 模拟服务层抛出异常
        when(investmentAdviceService.getAdvicesByCondition(testAdvice)).thenThrow(new RuntimeException("Database error"));

        // 调用控制器方法
        Result<List<InvestmentAdvice>> result = investmentAdviceController.searchAdvices(testAdvice);

        // 验证结果
        assertNotNull(result);
        assertEquals(500, result.getCode().intValue());
        assertTrue(result.getMessage().contains("搜索投资建议失败"));
        assertNull(result.getData());
        verify(investmentAdviceService, times(1)).getAdvicesByCondition(testAdvice);
    }

    @Test
    public void addAdvice_success() {
        // 模拟服务层返回true
        when(investmentAdviceService.addAdvice(testAdvice)).thenReturn(true);

        // 调用控制器方法
        Result<String> result = investmentAdviceController.addAdvice(testAdvice);

        // 验证结果
        assertNotNull(result);
        assertEquals(200, result.getCode().intValue());
        assertEquals("成功", result.getMessage());
        assertEquals("添加投资建议成功", result.getData());
        verify(investmentAdviceService, times(1)).addAdvice(testAdvice);
    }

    @Test
    public void addAdvice_failure() {
        // 模拟服务层返回false
        when(investmentAdviceService.addAdvice(testAdvice)).thenReturn(false);

        // 调用控制器方法
        Result<String> result = investmentAdviceController.addAdvice(testAdvice);

        // 验证结果
        assertNotNull(result);
        assertEquals(500, result.getCode().intValue());
        assertEquals("添加投资建议失败", result.getMessage());
        assertNull(result.getData());
        verify(investmentAdviceService, times(1)).addAdvice(testAdvice);
    }

    @Test
    public void addAdvice_serviceException() {
        // 模拟服务层抛出异常
        when(investmentAdviceService.addAdvice(testAdvice)).thenThrow(new RuntimeException("Database error"));

        // 调用控制器方法
        Result<String> result = investmentAdviceController.addAdvice(testAdvice);

        // 验证结果
        assertNotNull(result);
        assertEquals(500, result.getCode().intValue());
        assertTrue(result.getMessage().contains("添加投资建议失败"));
        assertNull(result.getData());
        verify(investmentAdviceService, times(1)).addAdvice(testAdvice);
    }

    @Test
    public void updateAdvice_success() {
        // 模拟服务层返回true
        when(investmentAdviceService.updateAdvice(testAdvice)).thenReturn(true);

        // 调用控制器方法
        Result<String> result = investmentAdviceController.updateAdvice(testAdvice);

        // 验证结果
        assertNotNull(result);
        assertEquals(200, result.getCode().intValue());
        assertEquals("成功", result.getMessage());
        assertEquals("更新投资建议成功", result.getData());
        verify(investmentAdviceService, times(1)).updateAdvice(testAdvice);
    }

    @Test
    public void updateAdvice_failure() {
        // 模拟服务层返回false
        when(investmentAdviceService.updateAdvice(testAdvice)).thenReturn(false);

        // 调用控制器方法
        Result<String> result = investmentAdviceController.updateAdvice(testAdvice);

        // 验证结果
        assertNotNull(result);
        assertEquals(500, result.getCode().intValue());
        assertEquals("更新投资建议失败", result.getMessage());
        assertNull(result.getData());
        verify(investmentAdviceService, times(1)).updateAdvice(testAdvice);
    }

    @Test
    public void updateAdvice_serviceException() {
        // 模拟服务层抛出异常
        when(investmentAdviceService.updateAdvice(testAdvice)).thenThrow(new RuntimeException("Database error"));

        // 调用控制器方法
        Result<String> result = investmentAdviceController.updateAdvice(testAdvice);

        // 验证结果
        assertNotNull(result);
        assertEquals(500, result.getCode().intValue());
        assertTrue(result.getMessage().contains("更新投资建议失败"));
        assertNull(result.getData());
        verify(investmentAdviceService, times(1)).updateAdvice(testAdvice);
    }

    @Test
    public void deleteAdvice_success() {
        // 模拟服务层返回true
        when(investmentAdviceService.deleteAdvice(1L)).thenReturn(true);

        // 调用控制器方法
        Result<String> result = investmentAdviceController.deleteAdvice(1L);

        // 验证结果
        assertNotNull(result);
        assertEquals(200, result.getCode().intValue());
        assertEquals("成功", result.getMessage());
        assertEquals("删除投资建议成功", result.getData());
        verify(investmentAdviceService, times(1)).deleteAdvice(1L);
    }

    @Test
    public void deleteAdvice_failure() {
        // 模拟服务层返回false
        when(investmentAdviceService.deleteAdvice(1L)).thenReturn(false);

        // 调用控制器方法
        Result<String> result = investmentAdviceController.deleteAdvice(1L);

        // 验证结果
        assertNotNull(result);
        assertEquals(500, result.getCode().intValue());
        assertEquals("删除投资建议失败", result.getMessage());
        assertNull(result.getData());
        verify(investmentAdviceService, times(1)).deleteAdvice(1L);
    }

    @Test
    public void deleteAdvice_serviceException() {
        // 模拟服务层抛出异常
        when(investmentAdviceService.deleteAdvice(1L)).thenThrow(new RuntimeException("Database error"));

        // 调用控制器方法
        Result<String> result = investmentAdviceController.deleteAdvice(1L);

        // 验证结果
        assertNotNull(result);
        assertEquals(500, result.getCode().intValue());
        assertTrue(result.getMessage().contains("删除投资建议失败"));
        assertNull(result.getData());
        verify(investmentAdviceService, times(1)).deleteAdvice(1L);
    }

    @Test
    public void updateAdviceStatus_success() {
        // 模拟服务层返回true
        when(investmentAdviceService.updateAdviceStatus(1L, 0)).thenReturn(true);

        // 调用控制器方法
        Result<String> result = investmentAdviceController.updateAdviceStatus(1L, 0);

        // 验证结果
        assertNotNull(result);
        assertEquals(200, result.getCode().intValue());
        assertEquals("成功", result.getMessage());
        assertEquals("更新投资建议状态成功",result.getData());
        verify(investmentAdviceService, times(1)).updateAdviceStatus(1L, 0);
    }

    @Test
    public void updateAdviceStatus_failure() {
        // 模拟服务层返回false
        when(investmentAdviceService.updateAdviceStatus(1L, 0)).thenReturn(false);

        // 调用控制器方法
        Result<String> result = investmentAdviceController.updateAdviceStatus(1L, 0);

        // 验证结果
        assertNotNull(result);
        assertEquals(500, result.getCode().intValue());
        assertEquals("更新投资建议状态失败", result.getMessage());
        assertNull(result.getData());
        verify(investmentAdviceService, times(1)).updateAdviceStatus(1L, 0);
    }

    @Test
    public void updateAdviceStatus_serviceException() {
        // 模拟服务层抛出异常
        when(investmentAdviceService.updateAdviceStatus(1L, 0)).thenThrow(new RuntimeException("Database error"));

        // 调用控制器方法
        Result<String> result = investmentAdviceController.updateAdviceStatus(1L, 0);

        // 验证结果
        assertNotNull(result);
        assertEquals(500, result.getCode().intValue());
        assertTrue(result.getMessage().contains("更新投资建议状态失败"));
        assertNull(result.getData());
        verify(investmentAdviceService, times(1)).updateAdviceStatus(1L, 0);
    }
}
