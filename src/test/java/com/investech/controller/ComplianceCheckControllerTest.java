package com.investech.controller;

import com.investech.entity.ComplianceCheck;
import com.investech.service.ComplianceCheckService;
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
public class ComplianceCheckControllerTest {

    @Mock
    private ComplianceCheckService complianceCheckService;

    @InjectMocks
    private ComplianceCheckController complianceCheckController;

    private ComplianceCheck testCheck;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        // 初始化测试数据
        testCheck = new ComplianceCheck();
        testCheck.setId(1L);
        testCheck.setCheckName("投资限制检查");
        testCheck.setCheckType("投资限制");
        testCheck.setCheckRule("单只基金持仓不超过10%");
        testCheck.setCheckResult("通过");
        testCheck.setViolationType("无");
        testCheck.setViolationDescription("未发现违规");
        testCheck.setActionRequired("无需行动");
        testCheck.setResponsiblePerson("张三");
        testCheck.setCheckTime(LocalDateTime.now());
        testCheck.setResolveTime(LocalDateTime.now().plusDays(7));
        testCheck.setStatus("已检查");
        testCheck.setCreateTime(LocalDateTime.now());
        testCheck.setUpdateTime(LocalDateTime.now());
        testCheck.setStatusCode(1);
    }

    @Test
    public void getAllChecks_success() {
        // 准备测试数据
        List<ComplianceCheck> checks = Arrays.asList(testCheck);

        // 模拟服务层返回
        when(complianceCheckService.getAllChecks()).thenReturn(checks);

        // 调用控制器方法
        Map<String, Object> result = complianceCheckController.getAllChecks();

        // 验证结果
        assertNotNull(result);
        assertTrue((Boolean) result.get("success"));
        assertEquals("查询成功", result.get("message"));
        assertEquals(checks, result.get("data"));
        verify(complianceCheckService, times(1)).getAllChecks();
    }

    @Test
    public void getAllChecks_emptyList() {
        // 模拟服务层返回空列表
        when(complianceCheckService.getAllChecks()).thenReturn(Arrays.asList());

        // 调用控制器方法
        Map<String, Object> result = complianceCheckController.getAllChecks();

        // 验证结果
        assertNotNull(result);
        assertTrue((Boolean) result.get("success"));
        assertEquals("查询成功", result.get("message"));
        assertTrue(((List<?>) result.get("data")).isEmpty());
        verify(complianceCheckService, times(1)).getAllChecks();
    }

    @Test
    public void getAllChecks_failure() {
        // 模拟异常
        when(complianceCheckService.getAllChecks()).thenThrow(new RuntimeException("Database error"));

        // 调用控制器方法
        Map<String, Object> result = complianceCheckController.getAllChecks();

        // 验证结果
        assertNotNull(result);
        assertFalse((Boolean) result.get("success"));
        assertTrue(result.get("message").toString().contains("查询失败"));
        assertNull(result.get("data"));
        verify(complianceCheckService, times(1)).getAllChecks();
    }

    @Test
    public void getCheckById_success() {
        // 模拟服务层返回
        when(complianceCheckService.getCheckById(1L)).thenReturn(testCheck);

        // 调用控制器方法
        Map<String, Object> result = complianceCheckController.getCheckById(1L);

        // 验证结果
        assertNotNull(result);
        assertTrue((Boolean) result.get("success"));
        assertEquals("查询成功", result.get("message"));
        assertEquals(testCheck, result.get("data"));
        verify(complianceCheckService, times(1)).getCheckById(1L);
    }

    @Test
    public void getCheckById_notFound() {
        // 模拟服务层返回null
        when(complianceCheckService.getCheckById(1L)).thenReturn(null);

        // 调用控制器方法
        Map<String, Object> result = complianceCheckController.getCheckById(1L);

        // 验证结果 - 注意：根据控制器实现，当找不到时仍返回"查询成功"消息
        assertNotNull(result);
        assertTrue((Boolean) result.get("success"));
        assertEquals("查询成功", result.get("message"));
        assertNull(result.get("data"));
        verify(complianceCheckService, times(1)).getCheckById(1L);
    }

    @Test
    public void getCheckById_failure() {
        // 模拟异常
        when(complianceCheckService.getCheckById(1L)).thenThrow(new RuntimeException("Database error"));

        // 调用控制器方法
        Map<String, Object> result = complianceCheckController.getCheckById(1L);

        // 验证结果
        assertNotNull(result);
        assertFalse((Boolean) result.get("success"));
        assertTrue(result.get("message").toString().contains("查询失败"));
        assertNull(result.get("data"));
        verify(complianceCheckService, times(1)).getCheckById(1L);
    }

    @Test
    public void searchChecks_success() {
        // 准备测试数据
        List<ComplianceCheck> checks = Arrays.asList(testCheck);

        // 模拟服务层返回
        when(complianceCheckService.getChecksByCondition(testCheck)).thenReturn(checks);

        // 调用控制器方法
        Map<String, Object> result = complianceCheckController.searchChecks(testCheck);

        // 验证结果
        assertNotNull(result);
        assertTrue((Boolean) result.get("success"));
        assertEquals("查询成功", result.get("message"));
        assertEquals(checks, result.get("data"));
        verify(complianceCheckService, times(1)).getChecksByCondition(testCheck);
    }

    @Test
    public void searchChecks_emptyList() {
        // 模拟服务层返回空列表
        when(complianceCheckService.getChecksByCondition(testCheck)).thenReturn(Arrays.asList());

        // 调用控制器方法
        Map<String, Object> result = complianceCheckController.searchChecks(testCheck);

        // 验证结果
        assertNotNull(result);
        assertTrue((Boolean) result.get("success"));
        assertEquals("查询成功", result.get("message"));
        assertTrue(((List<?>) result.get("data")).isEmpty());
        verify(complianceCheckService, times(1)).getChecksByCondition(testCheck);
    }

    @Test
    public void searchChecks_failure() {
        // 模拟异常
        when(complianceCheckService.getChecksByCondition(testCheck)).thenThrow(new RuntimeException("Database error"));

        // 调用控制器方法
        Map<String, Object> result = complianceCheckController.searchChecks(testCheck);

        // 验证结果
        assertNotNull(result);
        assertFalse((Boolean) result.get("success"));
        assertTrue(result.get("message").toString().contains("查询失败"));
        assertNull(result.get("data"));
        verify(complianceCheckService, times(1)).getChecksByCondition(testCheck);
    }

    @Test
    public void addCheck_success() {
        // 模拟服务层返回true
        when(complianceCheckService.addCheck(testCheck)).thenReturn(true);

        // 调用控制器方法
        Map<String, Object> result = complianceCheckController.addCheck(testCheck);

        // 验证结果
        assertNotNull(result);
        assertTrue((Boolean) result.get("success"));
        assertEquals("添加成功", result.get("message"));
        assertNull(result.get("data"));
        verify(complianceCheckService, times(1)).addCheck(testCheck);
    }

    @Test
    public void addCheck_failure() {
        // 模拟服务层返回false
        when(complianceCheckService.addCheck(testCheck)).thenReturn(false);

        // 调用控制器方法
        Map<String, Object> result = complianceCheckController.addCheck(testCheck);

        // 验证结果
        assertNotNull(result);
        assertFalse((Boolean) result.get("success"));
        assertEquals("添加失败", result.get("message"));
        assertNull(result.get("data"));
        verify(complianceCheckService, times(1)).addCheck(testCheck);
    }

    @Test
    public void addCheck_serviceException() {
        // 模拟异常
        when(complianceCheckService.addCheck(testCheck)).thenThrow(new RuntimeException("Database error"));

        // 调用控制器方法
        Map<String, Object> result = complianceCheckController.addCheck(testCheck);

        // 验证结果
        assertNotNull(result);
        assertFalse((Boolean) result.get("success"));
        assertTrue(result.get("message").toString().contains("添加失败"));
        assertNull(result.get("data"));
        verify(complianceCheckService, times(1)).addCheck(testCheck);
    }

    @Test
    public void updateCheck_success() {
        // 模拟服务层返回true
        when(complianceCheckService.updateCheck(testCheck)).thenReturn(true);

        // 调用控制器方法
        Map<String, Object> result = complianceCheckController.updateCheck(testCheck);

        // 验证结果
        assertNotNull(result);
        assertTrue((Boolean) result.get("success"));
        assertEquals("更新成功", result.get("message"));
        assertNull(result.get("data"));
        verify(complianceCheckService, times(1)).updateCheck(testCheck);
    }

    @Test
    public void updateCheck_failure() {
        // 模拟服务层返回false
        when(complianceCheckService.updateCheck(testCheck)).thenReturn(false);

        // 调用控制器方法
        Map<String, Object> result = complianceCheckController.updateCheck(testCheck);

        // 验证结果
        assertNotNull(result);
        assertFalse((Boolean) result.get("success"));
        assertEquals("更新失败", result.get("message"));
        assertNull(result.get("data"));
        verify(complianceCheckService, times(1)).updateCheck(testCheck);
    }

    @Test
    public void updateCheck_serviceException() {
        // 模拟异常
        when(complianceCheckService.updateCheck(testCheck)).thenThrow(new RuntimeException("Database error"));

        // 调用控制器方法
        Map<String, Object> result = complianceCheckController.updateCheck(testCheck);

        // 验证结果
        assertNotNull(result);
        assertFalse((Boolean) result.get("success"));
        assertTrue(result.get("message").toString().contains("更新失败"));
        assertNull(result.get("data"));
        verify(complianceCheckService, times(1)).updateCheck(testCheck);
    }

    @Test
    public void deleteCheck_success() {
        // 模拟服务层返回true
        when(complianceCheckService.deleteCheck(1L)).thenReturn(true);

        // 调用控制器方法
        Map<String, Object> result = complianceCheckController.deleteCheck(1L);

        // 验证结果
        assertNotNull(result);
        assertTrue((Boolean) result.get("success"));
        assertEquals("删除成功", result.get("message"));
        assertNull(result.get("data"));
        verify(complianceCheckService, times(1)).deleteCheck(1L);
    }

    @Test
    public void deleteCheck_failure() {
        // 模拟服务层返回false
        when(complianceCheckService.deleteCheck(1L)).thenReturn(false);

        // 调用控制器方法
        Map<String, Object> result = complianceCheckController.deleteCheck(1L);

        // 验证结果
        assertNotNull(result);
        assertFalse((Boolean) result.get("success"));
        assertEquals("删除失败", result.get("message"));
        assertNull(result.get("data"));
        verify(complianceCheckService, times(1)).deleteCheck(1L);
    }

    @Test
    public void deleteCheck_serviceException() {
        // 模拟异常
        when(complianceCheckService.deleteCheck(1L)).thenThrow(new RuntimeException("Database error"));

        // 调用控制器方法
        Map<String, Object> result = complianceCheckController.deleteCheck(1L);

        // 验证结果
        assertNotNull(result);
        assertFalse((Boolean) result.get("success"));
        assertTrue(result.get("message").toString().contains("删除失败"));
        assertNull(result.get("data"));
        verify(complianceCheckService, times(1)).deleteCheck(1L);
    }

    @Test
    public void updateCheckStatus_success() {
        // 模拟服务层返回true
        when(complianceCheckService.updateCheckStatus(1L, 0)).thenReturn(true);

        // 调用控制器方法
        Map<String, Object> result = complianceCheckController.updateCheckStatus(1L, 0);

        // 验证结果
        assertNotNull(result);
        assertTrue((Boolean) result.get("success"));
        assertEquals("状态更新成功", result.get("message"));
        assertNull(result.get("data"));
        verify(complianceCheckService, times(1)).updateCheckStatus(1L, 0);
    }

    @Test
    public void updateCheckStatus_failure() {
        // 模拟服务层返回false
        when(complianceCheckService.updateCheckStatus(1L, 0)).thenReturn(false);

        // 调用控制器方法
        Map<String, Object> result = complianceCheckController.updateCheckStatus(1L, 0);

        // 验证结果
        assertNotNull(result);
        assertFalse((Boolean) result.get("success"));
        assertEquals("状态更新失败", result.get("message"));
        assertNull(result.get("data"));
        verify(complianceCheckService, times(1)).updateCheckStatus(1L, 0);
    }

    @Test
    public void updateCheckStatus_serviceException() {
        // 模拟异常
        when(complianceCheckService.updateCheckStatus(1L, 0)).thenThrow(new RuntimeException("Database error"));

        // 调用控制器方法
        Map<String, Object> result = complianceCheckController.updateCheckStatus(1L, 0);

        // 验证结果
        assertNotNull(result);
        assertFalse((Boolean) result.get("success"));
        assertTrue(result.get("message").toString().contains("状态更新失败"));
        assertNull(result.get("data"));
        verify(complianceCheckService, times(1)).updateCheckStatus(1L, 0);
    }
}
