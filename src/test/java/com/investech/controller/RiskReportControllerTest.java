package com.investech.controller;

import com.investech.entity.RiskReport;
import com.investech.service.RiskReportService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class RiskReportControllerTest {

    @Mock
    private RiskReportService riskReportService;

    @InjectMocks
    private RiskReportController riskReportController;

    private RiskReport testReport;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        // 初始化测试数据
        testReport = new RiskReport();
        testReport.setId(1L);
        testReport.setReportName("2025年Q2风险报告");
        testReport.setReportType("季报");
        testReport.setReportPeriod("2025-04 至 2025-06");
        testReport.setRiskSummary("市场风险有所上升，其他风险保持稳定");
        testReport.setMarketRisk("由于国际形势变化，市场波动性增加");
        testReport.setCreditRisk("信用风险维持低位");
        testReport.setLiquidityRisk("流动性风险保持正常");
        testReport.setOperationalRisk("操作风险控制良好");
        testReport.setRiskMetrics("{\"VaR\": \"500万\", \"CVaR\": \"700万\"}");
        testReport.setRecommendations("建议增加对冲头寸，减少市场波动影响");
        testReport.setAuthor("张伟");
        testReport.setReviewer("李娜");
        testReport.setReportDate(LocalDateTime.now());
        testReport.setStatus("已发布");
        testReport.setCreateTime(LocalDateTime.now());
        testReport.setUpdateTime(LocalDateTime.now());
        testReport.setStatusCode(1);
    }

    @Test
    public void getAllReports_success() {
        // 准备测试数据
        List<RiskReport> reports = Arrays.asList(testReport);

        // 模拟服务层返回
        when(riskReportService.getAllReports()).thenReturn(reports);

        // 调用控制器方法
        Map<String, Object> result = riskReportController.getAllReports();

        // 验证结果
        assertNotNull(result);
        assertTrue((Boolean) result.get("success"));
        assertEquals("查询成功", result.get("message"));
        assertEquals(reports, result.get("data"));
        verify(riskReportService, times(1)).getAllReports();
    }

    @Test
    public void getAllReports_emptyList() {
        // 模拟服务层返回空列表
        when(riskReportService.getAllReports()).thenReturn(Arrays.asList());

        // 调用控制器方法
        Map<String, Object> result = riskReportController.getAllReports();

        // 验证结果
        assertNotNull(result);
        assertTrue((Boolean) result.get("success"));
        assertEquals("查询成功", result.get("message"));
        assertTrue(((List<?>) result.get("data")).isEmpty());
        verify(riskReportService, times(1)).getAllReports();
    }

    @Test
    public void getAllReports_serviceException() {
        // 模拟服务层抛出异常
        when(riskReportService.getAllReports()).thenThrow(new RuntimeException("Database error"));

        // 调用控制器方法
        Map<String, Object> result = riskReportController.getAllReports();

        // 验证结果
        assertNotNull(result);
        assertFalse((Boolean) result.get("success"));
        assertTrue(result.get("message").toString().contains("查询失败"));
        assertNull(result.get("data"));
        verify(riskReportService, times(1)).getAllReports();
    }

    @Test
    public void getReportById_success() {
        // 模拟服务层返回
        when(riskReportService.getReportById(1L)).thenReturn(testReport);

        // 调用控制器方法
        Map<String, Object> result = riskReportController.getReportById(1L);

        // 验证结果
        assertNotNull(result);
        assertTrue((Boolean) result.get("success"));
        assertEquals("查询成功", result.get("message"));
        assertEquals(testReport, result.get("data"));
        verify(riskReportService, times(1)).getReportById(1L);
    }

    @Test
    public void getReportById_notFound() {
        // 模拟服务层返回null（注意：实际控制器没有处理null的情况）
        when(riskReportService.getReportById(1L)).thenReturn(null);

        // 调用控制器方法
        Map<String, Object> result = riskReportController.getReportById(1L);

        // 验证结果 - 注意：控制器没有专门处理not found情况
        assertNotNull(result);
        assertTrue((Boolean) result.get("success"));
        assertEquals("查询成功", result.get("message"));
        assertNull(result.get("data"));
        verify(riskReportService, times(1)).getReportById(1L);
    }

    @Test
    public void getReportById_serviceException() {
        // 模拟服务层抛出异常
        when(riskReportService.getReportById(1L)).thenThrow(new RuntimeException("Database error"));

        // 调用控制器方法
        Map<String, Object> result = riskReportController.getReportById(1L);

        // 验证结果
        assertNotNull(result);
        assertFalse((Boolean) result.get("success"));
        assertTrue(result.get("message").toString().contains("查询失败"));
        assertNull(result.get("data"));
        verify(riskReportService, times(1)).getReportById(1L);
    }

    @Test
    public void searchReports_success() {
        // 准备测试数据
        List<RiskReport> reports = Arrays.asList(testReport);

        // 模拟服务层返回
        when(riskReportService.getReportsByCondition(testReport)).thenReturn(reports);

        // 调用控制器方法
        Map<String, Object> result = riskReportController.searchReports(testReport);

        // 验证结果
        assertNotNull(result);
        assertTrue((Boolean) result.get("success"));
        assertEquals("查询成功", result.get("message"));
        assertEquals(reports, result.get("data"));
        verify(riskReportService, times(1)).getReportsByCondition(testReport);
    }

    @Test
    public void searchReports_emptyList() {
        // 模拟服务层返回空列表
        when(riskReportService.getReportsByCondition(testReport)).thenReturn(Arrays.asList());

        // 调用控制器方法
        Map<String, Object> result = riskReportController.searchReports(testReport);

        // 验证结果
        assertNotNull(result);
        assertTrue((Boolean) result.get("success"));
        assertEquals("查询成功", result.get("message"));
        assertTrue(((List<?>) result.get("data")).isEmpty());
        verify(riskReportService, times(1)).getReportsByCondition(testReport);
    }

    @Test
    public void searchReports_serviceException() {
        // 模拟服务层抛出异常
        when(riskReportService.getReportsByCondition(testReport)).thenThrow(new RuntimeException("Database error"));

        // 调用控制器方法
        Map<String, Object> result = riskReportController.searchReports(testReport);

        // 验证结果
        assertNotNull(result);
        assertFalse((Boolean) result.get("success"));
        assertTrue(result.get("message").toString().contains("查询失败"));
        assertNull(result.get("data"));
        verify(riskReportService, times(1)).getReportsByCondition(testReport);
    }

    @Test
    public void addReport_success() {
        // 模拟服务层返回true
        when(riskReportService.addReport(testReport)).thenReturn(true);

        // 调用控制器方法
        Map<String, Object> result = riskReportController.addReport(testReport);

        // 验证结果
        assertNotNull(result);
        assertTrue((Boolean) result.get("success"));
        assertEquals("添加成功", result.get("message"));
        assertNull(result.get("data"));
        verify(riskReportService, times(1)).addReport(testReport);
    }

    @Test
    public void addReport_failure() {
        // 模拟服务层返回false
        when(riskReportService.addReport(testReport)).thenReturn(false);

        // 调用控制器方法
        Map<String, Object> result = riskReportController.addReport(testReport);

        // 验证结果
        assertNotNull(result);
        assertFalse((Boolean) result.get("success"));
        assertEquals("添加失败", result.get("message"));
        assertNull(result.get("data"));
        verify(riskReportService, times(1)).addReport(testReport);
    }

    @Test
    public void addReport_serviceException() {
        // 模拟服务层抛出异常
        when(riskReportService.addReport(testReport)).thenThrow(new RuntimeException("Database error"));

        // 调用控制器方法
        Map<String, Object> result = riskReportController.addReport(testReport);

        // 验证结果
        assertNotNull(result);
        assertFalse((Boolean) result.get("success"));
        assertTrue(result.get("message").toString().contains("添加失败"));
        assertNull(result.get("data"));
        verify(riskReportService, times(1)).addReport(testReport);
    }

    @Test
    public void updateReport_success() {
        // 模拟服务层返回true
        when(riskReportService.updateReport(testReport)).thenReturn(true);

        // 调用控制器方法
        Map<String, Object> result = riskReportController.updateReport(testReport);

        // 验证结果
        assertNotNull(result);
        assertTrue((Boolean) result.get("success"));
        assertEquals("更新成功", result.get("message"));
        assertNull(result.get("data"));
        verify(riskReportService, times(1)).updateReport(testReport);
    }

    @Test
    public void updateReport_failure() {
        // 模拟服务层返回false
        when(riskReportService.updateReport(testReport)).thenReturn(false);

        // 调用控制器方法
        Map<String, Object> result = riskReportController.updateReport(testReport);

        // 验证结果
        assertNotNull(result);
        assertFalse((Boolean) result.get("success"));
        assertEquals("更新失败", result.get("message"));
        assertNull(result.get("data"));
        verify(riskReportService, times(1)).updateReport(testReport);
    }

    @Test
    public void updateReport_serviceException() {
        // 模拟服务层抛出异常
        when(riskReportService.updateReport(testReport)).thenThrow(new RuntimeException("Database error"));

        // 调用控制器方法
        Map<String, Object> result = riskReportController.updateReport(testReport);

        // 验证结果
        assertNotNull(result);
        assertFalse((Boolean) result.get("success"));
        assertTrue(result.get("message").toString().contains("更新失败"));
        assertNull(result.get("data"));
        verify(riskReportService, times(1)).updateReport(testReport);
    }

    @Test
    public void deleteReport_success() {
        // 模拟服务层返回true
        when(riskReportService.deleteReport(1L)).thenReturn(true);

        // 调用控制器方法
        Map<String, Object> result = riskReportController.deleteReport(1L);

        // 验证结果
        assertNotNull(result);
        assertTrue((Boolean) result.get("success"));
        assertEquals("删除成功", result.get("message"));
        assertNull(result.get("data"));
        verify(riskReportService, times(1)).deleteReport(1L);
    }

    @Test
    public void deleteReport_failure() {
        // 模拟服务层返回false
        when(riskReportService.deleteReport(1L)).thenReturn(false);

        // 调用控制器方法
        Map<String, Object> result = riskReportController.deleteReport(1L);

        // 验证结果
        assertNotNull(result);
        assertFalse((Boolean) result.get("success"));
        assertEquals("删除失败", result.get("message"));
        assertNull(result.get("data"));
        verify(riskReportService, times(1)).deleteReport(1L);
    }

    @Test
    public void deleteReport_serviceException() {
        // 模拟服务层抛出异常
        when(riskReportService.deleteReport(1L)).thenThrow(new RuntimeException("Database error"));

        // 调用控制器方法
        Map<String, Object> result = riskReportController.deleteReport(1L);

        // 验证结果
        assertNotNull(result);
        assertFalse((Boolean) result.get("success"));
        assertTrue(result.get("message").toString().contains("删除失败"));
        assertNull(result.get("data"));
        verify(riskReportService, times(1)).deleteReport(1L);
    }

    @Test
    public void updateReportStatus_success() {
        // 模拟服务层返回true
        when(riskReportService.updateReportStatus(1L, 0)).thenReturn(true);

        // 调用控制器方法
        Map<String, Object> result = riskReportController.updateReportStatus(1L, 0);

        // 验证结果
        assertNotNull(result);
        assertTrue((Boolean) result.get("success"));
        assertEquals("状态更新成功", result.get("message"));
        assertNull(result.get("data"));
        verify(riskReportService, times(1)).updateReportStatus(1L, 0);
    }

    @Test
    public void updateReportStatus_failure() {
        // 模拟服务层返回false
        when(riskReportService.updateReportStatus(1L, 0)).thenReturn(false);

        // 调用控制器方法
        Map<String, Object> result = riskReportController.updateReportStatus(1L, 0);

        // 验证结果
        assertNotNull(result);
        assertFalse((Boolean) result.get("success"));
        assertEquals("状态更新失败", result.get("message"));
        assertNull(result.get("data"));
        verify(riskReportService, times(1)).updateReportStatus(1L, 0);
    }

    @Test
    public void updateReportStatus_serviceException() {
        // 模拟服务层抛出异常
        when(riskReportService.updateReportStatus(1L, 0)).thenThrow(new RuntimeException("Database error"));

        // 调用控制器方法
        Map<String, Object> result = riskReportController.updateReportStatus(1L, 0);

        // 验证结果
        assertNotNull(result);
        assertFalse((Boolean) result.get("success"));
        assertTrue(result.get("message").toString().contains("状态更新失败"));
        assertNull(result.get("data"));
        verify(riskReportService, times(1)).updateReportStatus(1L, 0);
    }
}
