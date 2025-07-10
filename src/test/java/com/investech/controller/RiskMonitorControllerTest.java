package com.investech.controller;

import com.investech.entity.RiskMonitor;
import com.investech.service.RiskMonitorService;
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
public class RiskMonitorControllerTest {

    @Mock
    private RiskMonitorService riskMonitorService;

    @InjectMocks
    private RiskMonitorController riskMonitorController;

    private RiskMonitor testMonitor;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        // 初始化测试数据
        testMonitor = new RiskMonitor();
        testMonitor.setId(1L);
        testMonitor.setMonitorName("市场风险监控");
        testMonitor.setMonitorType("市场风险");
        testMonitor.setRiskIndicator("波动率");
        testMonitor.setThreshold(0.8);
        testMonitor.setCurrentValue(0.75);
        testMonitor.setRiskLevel("中");
        testMonitor.setStatus("正常");
        testMonitor.setDescription("监控基金组合的市场风险");
        testMonitor.setCreateTime(LocalDateTime.now());
        testMonitor.setUpdateTime(LocalDateTime.now());
        testMonitor.setStatusCode(1);
    }

    @Test
    public void getAllMonitors_success() {
        // 准备测试数据
        List<RiskMonitor> monitors = Arrays.asList(testMonitor);

        // 模拟服务层返回
        when(riskMonitorService.getAllMonitors()).thenReturn(monitors);

        // 调用控制器方法
        Map<String, Object> result = riskMonitorController.getAllMonitors();

        // 验证结果
        assertNotNull(result);
        assertTrue((Boolean) result.get("success"));
        assertEquals("查询成功", result.get("message"));
        assertEquals(monitors, result.get("data"));
        verify(riskMonitorService, times(1)).getAllMonitors();
    }

    @Test
    public void getAllMonitors_emptyList() {
        // 模拟服务层返回空列表
        when(riskMonitorService.getAllMonitors()).thenReturn(Arrays.asList());

        // 调用控制器方法
        Map<String, Object> result = riskMonitorController.getAllMonitors();

        // 验证结果
        assertNotNull(result);
        assertTrue((Boolean) result.get("success"));
        assertEquals("查询成功", result.get("message"));
        assertTrue(((List<?>) result.get("data")).isEmpty());
        verify(riskMonitorService, times(1)).getAllMonitors();
    }

    @Test
    public void getAllMonitors_serviceException() {
        // 模拟服务层抛出异常
        when(riskMonitorService.getAllMonitors()).thenThrow(new RuntimeException("Database error"));

        // 调用控制器方法
        Map<String, Object> result = riskMonitorController.getAllMonitors();

        // 验证结果
        assertNotNull(result);
        assertFalse((Boolean) result.get("success"));
        assertTrue(result.get("message").toString().contains("查询失败"));
        assertNull(result.get("data"));
        verify(riskMonitorService, times(1)).getAllMonitors();
    }

    @Test
    public void getMonitorById_success() {
        // 模拟服务层返回
        when(riskMonitorService.getMonitorById(1L)).thenReturn(testMonitor);

        // 调用控制器方法
        Map<String, Object> result = riskMonitorController.getMonitorById(1L);

        // 验证结果
        assertNotNull(result);
        assertTrue((Boolean) result.get("success"));
        assertEquals("查询成功", result.get("message"));
        assertEquals(testMonitor, result.get("data"));
        verify(riskMonitorService, times(1)).getMonitorById(1L);
    }

    @Test
    public void getMonitorById_notFound() {
        // 模拟服务层返回null（注意：实际控制器没有处理null的情况）
        when(riskMonitorService.getMonitorById(1L)).thenReturn(null);

        // 调用控制器方法
        Map<String, Object> result = riskMonitorController.getMonitorById(1L);

        // 验证结果 - 注意：控制器没有专门处理not found情况
        assertNotNull(result);
        assertTrue((Boolean) result.get("success"));
        assertEquals("查询成功", result.get("message"));
        assertNull(result.get("data"));
        verify(riskMonitorService, times(1)).getMonitorById(1L);
    }

    @Test
    public void getMonitorById_serviceException() {
        // 模拟服务层抛出异常
        when(riskMonitorService.getMonitorById(1L)).thenThrow(new RuntimeException("Database error"));

        // 调用控制器方法
        Map<String, Object> result = riskMonitorController.getMonitorById(1L);

        // 验证结果
        assertNotNull(result);
        assertFalse((Boolean) result.get("success"));
        assertTrue(result.get("message").toString().contains("查询失败"));
        assertNull(result.get("data"));
        verify(riskMonitorService, times(1)).getMonitorById(1L);
    }

    @Test
    public void searchMonitors_success() {
        // 准备测试数据
        List<RiskMonitor> monitors = Arrays.asList(testMonitor);

        // 模拟服务层返回
        when(riskMonitorService.getMonitorsByCondition(testMonitor)).thenReturn(monitors);

        // 调用控制器方法
        Map<String, Object> result = riskMonitorController.searchMonitors(testMonitor);

        // 验证结果
        assertNotNull(result);
        assertTrue((Boolean) result.get("success"));
        assertEquals("查询成功", result.get("message"));
        assertEquals(monitors, result.get("data"));
        verify(riskMonitorService, times(1)).getMonitorsByCondition(testMonitor);
    }

    @Test
    public void searchMonitors_emptyList() {
        // 模拟服务层返回空列表
        when(riskMonitorService.getMonitorsByCondition(testMonitor)).thenReturn(Arrays.asList());

        // 调用控制器方法
        Map<String, Object> result = riskMonitorController.searchMonitors(testMonitor);

        // 验证结果
        assertNotNull(result);
        assertTrue((Boolean) result.get("success"));
        assertEquals("查询成功", result.get("message"));
        assertTrue(((List<?>) result.get("data")).isEmpty());
        verify(riskMonitorService, times(1)).getMonitorsByCondition(testMonitor);
    }

    @Test
    public void searchMonitors_serviceException() {
        // 模拟服务层抛出异常
        when(riskMonitorService.getMonitorsByCondition(testMonitor)).thenThrow(new RuntimeException("Database error"));

        // 调用控制器方法
        Map<String, Object> result = riskMonitorController.searchMonitors(testMonitor);

        // 验证结果
        assertNotNull(result);
        assertFalse((Boolean) result.get("success"));
        assertTrue(result.get("message").toString().contains("查询失败"));
        assertNull(result.get("data"));
        verify(riskMonitorService, times(1)).getMonitorsByCondition(testMonitor);
    }

    @Test
    public void addMonitor_success() {
        // 模拟服务层返回true
        when(riskMonitorService.addMonitor(testMonitor)).thenReturn(true);

        // 调用控制器方法
        Map<String, Object> result = riskMonitorController.addMonitor(testMonitor);

        // 验证结果
        assertNotNull(result);
        assertTrue((Boolean) result.get("success"));
        assertEquals("添加成功", result.get("message"));
        assertNull(result.get("data"));
        verify(riskMonitorService, times(1)).addMonitor(testMonitor);
    }

    @Test
    public void addMonitor_failure() {
        // 模拟服务层返回false
        when(riskMonitorService.addMonitor(testMonitor)).thenReturn(false);

        // 调用控制器方法
        Map<String, Object> result = riskMonitorController.addMonitor(testMonitor);

        // 验证结果
        assertNotNull(result);
        assertFalse((Boolean) result.get("success"));
        assertEquals("添加失败", result.get("message"));
        assertNull(result.get("data"));
        verify(riskMonitorService, times(1)).addMonitor(testMonitor);
    }

    @Test
    public void addMonitor_serviceException() {
        // 模拟服务层抛出异常
        when(riskMonitorService.addMonitor(testMonitor)).thenThrow(new RuntimeException("Database error"));

        // 调用控制器方法
        Map<String, Object> result = riskMonitorController.addMonitor(testMonitor);

        // 验证结果
        assertNotNull(result);
        assertFalse((Boolean) result.get("success"));
        assertTrue(result.get("message").toString().contains("添加失败"));
        assertNull(result.get("data"));
        verify(riskMonitorService, times(1)).addMonitor(testMonitor);
    }

    @Test
    public void updateMonitor_success() {
        // 模拟服务层返回true
        when(riskMonitorService.updateMonitor(testMonitor)).thenReturn(true);

        // 调用控制器方法
        Map<String, Object> result = riskMonitorController.updateMonitor(testMonitor);

        // 验证结果
        assertNotNull(result);
        assertTrue((Boolean) result.get("success"));
        assertEquals("更新成功", result.get("message"));
        assertNull(result.get("data"));
        verify(riskMonitorService, times(1)).updateMonitor(testMonitor);
    }

    @Test
    public void updateMonitor_failure() {
        // 模拟服务层返回false
        when(riskMonitorService.updateMonitor(testMonitor)).thenReturn(false);

        // 调用控制器方法
        Map<String, Object> result = riskMonitorController.updateMonitor(testMonitor);

        // 验证结果
        assertNotNull(result);
        assertFalse((Boolean) result.get("success"));
        assertEquals("更新失败", result.get("message"));
        assertNull(result.get("data"));
        verify(riskMonitorService, times(1)).updateMonitor(testMonitor);
    }

    @Test
    public void updateMonitor_serviceException() {
        // 模拟服务层抛出异常
        when(riskMonitorService.updateMonitor(testMonitor)).thenThrow(new RuntimeException("Database error"));

        // 调用控制器方法
        Map<String, Object> result = riskMonitorController.updateMonitor(testMonitor);

        // 验证结果
        assertNotNull(result);
        assertFalse((Boolean) result.get("success"));
        assertTrue(result.get("message").toString().contains("更新失败"));
        assertNull(result.get("data"));
        verify(riskMonitorService, times(1)).updateMonitor(testMonitor);
    }

    @Test
    public void deleteMonitor_success() {
        // 模拟服务层返回true
        when(riskMonitorService.deleteMonitor(1L)).thenReturn(true);

        // 调用控制器方法
        Map<String, Object> result = riskMonitorController.deleteMonitor(1L);

        // 验证结果
        assertNotNull(result);
        assertTrue((Boolean) result.get("success"));
        assertEquals("删除成功", result.get("message"));
        assertNull(result.get("data"));
        verify(riskMonitorService, times(1)).deleteMonitor(1L);
    }

    @Test
    public void deleteMonitor_failure() {
        // 模拟服务层返回false
        when(riskMonitorService.deleteMonitor(1L)).thenReturn(false);

        // 调用控制器方法
        Map<String, Object> result = riskMonitorController.deleteMonitor(1L);

        // 验证结果
        assertNotNull(result);
        assertFalse((Boolean) result.get("success"));
        assertEquals("删除失败", result.get("message"));
        assertNull(result.get("data"));
        verify(riskMonitorService, times(1)).deleteMonitor(1L);
    }

    @Test
    public void deleteMonitor_serviceException() {
        // 模拟服务层抛出异常
        when(riskMonitorService.deleteMonitor(1L)).thenThrow(new RuntimeException("Database error"));

        // 调用控制器方法
        Map<String, Object> result = riskMonitorController.deleteMonitor(1L);

        // 验证结果
        assertNotNull(result);
        assertFalse((Boolean) result.get("success"));
        assertTrue(result.get("message").toString().contains("删除失败"));
        assertNull(result.get("data"));
        verify(riskMonitorService, times(1)).deleteMonitor(1L);
    }

    @Test
    public void updateMonitorStatus_success() {
        // 模拟服务层返回true
        when(riskMonitorService.updateMonitorStatus(1L, 0)).thenReturn(true);

        // 调用控制器方法
        Map<String, Object> result = riskMonitorController.updateMonitorStatus(1L, 0);

        // 验证结果
        assertNotNull(result);
        assertTrue((Boolean) result.get("success"));
        assertEquals("状态更新成功", result.get("message"));
        assertNull(result.get("data"));
        verify(riskMonitorService, times(1)).updateMonitorStatus(1L, 0);
    }

    @Test
    public void updateMonitorStatus_failure() {
        // 模拟服务层返回false
        when(riskMonitorService.updateMonitorStatus(1L, 0)).thenReturn(false);

        // 调用控制器方法
        Map<String, Object> result = riskMonitorController.updateMonitorStatus(1L, 0);

        // 验证结果
        assertNotNull(result);
        assertFalse((Boolean) result.get("success"));
        assertEquals("状态更新失败", result.get("message"));
        assertNull(result.get("data"));
        verify(riskMonitorService, times(1)).updateMonitorStatus(1L, 0);
    }

    @Test
    public void updateMonitorStatus_serviceException() {
        // 模拟服务层抛出异常
        when(riskMonitorService.updateMonitorStatus(1L, 0)).thenThrow(new RuntimeException("Database error"));

        // 调用控制器方法
        Map<String, Object> result = riskMonitorController.updateMonitorStatus(1L, 0);

        // 验证结果
        assertNotNull(result);
        assertFalse((Boolean) result.get("success"));
        assertTrue(result.get("message").toString().contains("状态更新失败"));
        assertNull(result.get("data"));
        verify(riskMonitorService, times(1)).updateMonitorStatus(1L, 0);
    }
}
