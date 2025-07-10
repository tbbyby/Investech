package com.investech.controller;

import com.investech.entity.RiskAlert;
import com.investech.service.RiskAlertService;
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
public class RiskAlertControllerTest {

    @Mock
    private RiskAlertService riskAlertService;

    @InjectMocks
    private RiskAlertController riskAlertController;

    private RiskAlert testAlert;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        // 初始化测试数据
        testAlert = new RiskAlert();
        testAlert.setId(1L);
        testAlert.setAlertName("市场风险预警");
        testAlert.setAlertType("市场风险");
        testAlert.setAlertLevel("高");
        testAlert.setAlertSource("系统自动检测");
        testAlert.setAlertContent("某基金净值波动超过警戒线");
        testAlert.setAffectedPortfolio("[\"PORTFOLIO001\", \"PORTFOLIO002\"]");
        testAlert.setImpactAssessment("可能造成组合大幅回撤");
        testAlert.setActionRequired("立即调整组合配置");
        testAlert.setResponsiblePerson("张三");
        testAlert.setAlertTime(LocalDateTime.now());
        testAlert.setStatus("未确认");
        testAlert.setStatusCode(1);
        testAlert.setCreateTime(LocalDateTime.now());
        testAlert.setUpdateTime(LocalDateTime.now());
    }

    @Test
    public void getAllAlerts_success() {
        // 准备测试数据
        List<RiskAlert> alerts = Arrays.asList(testAlert);

        // 模拟服务层返回
        when(riskAlertService.getAllAlerts()).thenReturn(alerts);

        // 调用控制器方法
        Map<String, Object> result = riskAlertController.getAllAlerts();

        // 验证结果
        assertNotNull(result);
        assertTrue((Boolean) result.get("success"));
        assertEquals("查询成功", result.get("message"));
        assertEquals(alerts, result.get("data"));
        verify(riskAlertService, times(1)).getAllAlerts();
    }

    @Test
    public void getAllAlerts_emptyList() {
        // 模拟服务层返回空列表
        when(riskAlertService.getAllAlerts()).thenReturn(Arrays.asList());

        // 调用控制器方法
        Map<String, Object> result = riskAlertController.getAllAlerts();

        // 验证结果
        assertNotNull(result);
        assertTrue((Boolean) result.get("success"));
        assertEquals("查询成功", result.get("message"));
        assertTrue(((List<?>) result.get("data")).isEmpty());
        verify(riskAlertService, times(1)).getAllAlerts();
    }

    @Test
    public void getAllAlerts_serviceException() {
        // 模拟服务层抛出异常
        when(riskAlertService.getAllAlerts()).thenThrow(new RuntimeException("Database error"));

        // 调用控制器方法
        Map<String, Object> result = riskAlertController.getAllAlerts();

        // 验证结果
        assertNotNull(result);
        assertFalse((Boolean) result.get("success"));
        assertTrue(result.get("message").toString().contains("查询失败"));
        assertNull(result.get("data"));
        verify(riskAlertService, times(1)).getAllAlerts();
    }

    @Test
    public void getAlertById_success() {
        // 模拟服务层返回
        when(riskAlertService.getAlertById(1L)).thenReturn(testAlert);

        // 调用控制器方法
        Map<String, Object> result = riskAlertController.getAlertById(1L);

        // 验证结果
        assertNotNull(result);
        assertTrue((Boolean) result.get("success"));
        assertEquals("查询成功", result.get("message"));
        assertEquals(testAlert, result.get("data"));
        verify(riskAlertService, times(1)).getAlertById(1L);
    }

    @Test
    public void getAlertById_notFound() {
        // 模拟服务层返回null（注意：实际控制器没有处理null的情况）
        when(riskAlertService.getAlertById(1L)).thenReturn(null);

        // 调用控制器方法
        Map<String, Object> result = riskAlertController.getAlertById(1L);

        // 验证结果 - 注意：控制器没有专门处理not found情况
        assertNotNull(result);
        assertTrue((Boolean) result.get("success"));
        assertEquals("查询成功", result.get("message"));
        assertNull(result.get("data"));
        verify(riskAlertService, times(1)).getAlertById(1L);
    }

    @Test
    public void getAlertById_serviceException() {
        // 模拟服务层抛出异常
        when(riskAlertService.getAlertById(1L)).thenThrow(new RuntimeException("Database error"));

        // 调用控制器方法
        Map<String, Object> result = riskAlertController.getAlertById(1L);

        // 验证结果
        assertNotNull(result);
        assertFalse((Boolean) result.get("success"));
        assertTrue(result.get("message").toString().contains("查询失败"));
        assertNull(result.get("data"));
        verify(riskAlertService, times(1)).getAlertById(1L);
    }

    @Test
    public void searchAlerts_success() {
        // 准备测试数据
        List<RiskAlert> alerts = Arrays.asList(testAlert);

        // 模拟服务层返回
        when(riskAlertService.getAlertsByCondition(testAlert)).thenReturn(alerts);

        // 调用控制器方法
        Map<String, Object> result = riskAlertController.searchAlerts(testAlert);

        // 验证结果
        assertNotNull(result);
        assertTrue((Boolean) result.get("success"));
        assertEquals("查询成功", result.get("message"));
        assertEquals(alerts, result.get("data"));
        verify(riskAlertService, times(1)).getAlertsByCondition(testAlert);
    }

    @Test
    public void searchAlerts_emptyList() {
        // 模拟服务层返回空列表
        when(riskAlertService.getAlertsByCondition(testAlert)).thenReturn(Arrays.asList());

        // 调用控制器方法
        Map<String, Object> result = riskAlertController.searchAlerts(testAlert);

        // 验证结果
        assertNotNull(result);
        assertTrue((Boolean) result.get("success"));
        assertEquals("查询成功", result.get("message"));
        assertTrue(((List<?>) result.get("data")).isEmpty());
        verify(riskAlertService, times(1)).getAlertsByCondition(testAlert);
    }

    @Test
    public void searchAlerts_serviceException() {
        // 模拟服务层抛出异常
        when(riskAlertService.getAlertsByCondition(testAlert)).thenThrow(new RuntimeException("Database error"));

        // 调用控制器方法
        Map<String, Object> result = riskAlertController.searchAlerts(testAlert);

        // 验证结果
        assertNotNull(result);
        assertFalse((Boolean) result.get("success"));
        assertTrue(result.get("message").toString().contains("查询失败"));
        assertNull(result.get("data"));
        verify(riskAlertService, times(1)).getAlertsByCondition(testAlert);
    }

    @Test
    public void addAlert_success() {
        // 模拟服务层返回true
        when(riskAlertService.addAlert(testAlert)).thenReturn(true);

        // 调用控制器方法
        Map<String, Object> result = riskAlertController.addAlert(testAlert);

        // 验证结果
        assertNotNull(result);
        assertTrue((Boolean) result.get("success"));
        assertEquals("添加成功", result.get("message"));
        assertNull(result.get("data"));
        verify(riskAlertService, times(1)).addAlert(testAlert);
    }

    @Test
    public void addAlert_failure() {
        // 模拟服务层返回false
        when(riskAlertService.addAlert(testAlert)).thenReturn(false);

        // 调用控制器方法
        Map<String, Object> result = riskAlertController.addAlert(testAlert);

        // 验证结果
        assertNotNull(result);
        assertFalse((Boolean) result.get("success"));
        assertEquals("添加失败", result.get("message"));
        assertNull(result.get("data"));
        verify(riskAlertService, times(1)).addAlert(testAlert);
    }

    @Test
    public void addAlert_serviceException() {
        // 模拟服务层抛出异常
        when(riskAlertService.addAlert(testAlert)).thenThrow(new RuntimeException("Database error"));

        // 调用控制器方法
        Map<String, Object> result = riskAlertController.addAlert(testAlert);

        // 验证结果
        assertNotNull(result);
        assertFalse((Boolean) result.get("success"));
        assertTrue(result.get("message").toString().contains("添加失败"));
        assertNull(result.get("data"));
        verify(riskAlertService, times(1)).addAlert(testAlert);
    }

    @Test
    public void updateAlert_success() {
        // 模拟服务层返回true
        when(riskAlertService.updateAlert(testAlert)).thenReturn(true);

        // 调用控制器方法
        Map<String, Object> result = riskAlertController.updateAlert(testAlert);

        // 验证结果
        assertNotNull(result);
        assertTrue((Boolean) result.get("success"));
        assertEquals("更新成功", result.get("message"));
        assertNull(result.get("data"));
        verify(riskAlertService, times(1)).updateAlert(testAlert);
    }

    @Test
    public void updateAlert_failure() {
        // 模拟服务层返回false
        when(riskAlertService.updateAlert(testAlert)).thenReturn(false);

        // 调用控制器方法
        Map<String, Object> result = riskAlertController.updateAlert(testAlert);

        // 验证结果
        assertNotNull(result);
        assertFalse((Boolean) result.get("success"));
        assertEquals("更新失败", result.get("message"));
        assertNull(result.get("data"));
        verify(riskAlertService, times(1)).updateAlert(testAlert);
    }

    @Test
    public void updateAlert_serviceException() {
        // 模拟服务层抛出异常
        when(riskAlertService.updateAlert(testAlert)).thenThrow(new RuntimeException("Database error"));

        // 调用控制器方法
        Map<String, Object> result = riskAlertController.updateAlert(testAlert);

        // 验证结果
        assertNotNull(result);
        assertFalse((Boolean) result.get("success"));
        assertTrue(result.get("message").toString().contains("更新失败"));
        assertNull(result.get("data"));
        verify(riskAlertService, times(1)).updateAlert(testAlert);
    }

    @Test
    public void deleteAlert_success() {
        // 模拟服务层返回true
        when(riskAlertService.deleteAlert(1L)).thenReturn(true);

        // 调用控制器方法
        Map<String, Object> result = riskAlertController.deleteAlert(1L);

        // 验证结果
        assertNotNull(result);
        assertTrue((Boolean) result.get("success"));
        assertEquals("删除成功", result.get("message"));
        assertNull(result.get("data"));
        verify(riskAlertService, times(1)).deleteAlert(1L);
    }

    @Test
    public void deleteAlert_failure() {
        // 模拟服务层返回false
        when(riskAlertService.deleteAlert(1L)).thenReturn(false);

        // 调用控制器方法
        Map<String, Object> result = riskAlertController.deleteAlert(1L);

        // 验证结果
        assertNotNull(result);
        assertFalse((Boolean) result.get("success"));
        assertEquals("删除失败", result.get("message"));
        assertNull(result.get("data"));
        verify(riskAlertService, times(1)).deleteAlert(1L);
    }

    @Test
    public void deleteAlert_serviceException() {
        // 模拟服务层抛出异常
        when(riskAlertService.deleteAlert(1L)).thenThrow(new RuntimeException("Database error"));

        // 调用控制器方法
        Map<String, Object> result = riskAlertController.deleteAlert(1L);

        // 验证结果
        assertNotNull(result);
        assertFalse((Boolean) result.get("success"));
        assertTrue(result.get("message").toString().contains("删除失败"));
        assertNull(result.get("data"));
        verify(riskAlertService, times(1)).deleteAlert(1L);
    }

    @Test
    public void updateAlertStatus_success() {
        // 模拟服务层返回true
        when(riskAlertService.updateAlertStatus(1L, 0)).thenReturn(true);

        // 调用控制器方法
        Map<String, Object> result = riskAlertController.updateAlertStatus(1L, 0);

        // 验证结果
        assertNotNull(result);
        assertTrue((Boolean) result.get("success"));
        assertEquals("状态更新成功", result.get("message"));
        assertNull(result.get("data"));
        verify(riskAlertService, times(1)).updateAlertStatus(1L, 0);
    }

    @Test
    public void updateAlertStatus_failure() {
        // 模拟服务层返回false
        when(riskAlertService.updateAlertStatus(1L, 0)).thenReturn(false);

        // 调用控制器方法
        Map<String, Object> result = riskAlertController.updateAlertStatus(1L, 0);

        // 验证结果
        assertNotNull(result);
        assertFalse((Boolean) result.get("success"));
        assertEquals("状态更新失败", result.get("message"));
        assertNull(result.get("data"));
        verify(riskAlertService, times(1)).updateAlertStatus(1L, 0);
    }

    @Test
    public void updateAlertStatus_serviceException() {
        // 模拟服务层抛出异常
        when(riskAlertService.updateAlertStatus(1L, 0)).thenThrow(new RuntimeException("Database error"));

        // 调用控制器方法
        Map<String, Object> result = riskAlertController.updateAlertStatus(1L, 0);

        // 验证结果
        assertNotNull(result);
        assertFalse((Boolean) result.get("success"));
        assertTrue(result.get("message").toString().contains("状态更新失败"));
        assertNull(result.get("data"));
        verify(riskAlertService, times(1)).updateAlertStatus(1L, 0);
    }
}
