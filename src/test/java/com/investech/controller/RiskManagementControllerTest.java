package com.investech.controller;

import com.investech.common.Result;
import com.investech.entity.RiskManagement;
import com.investech.service.RiskManagementService;
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
public class RiskManagementControllerTest {

    @Mock
    private RiskManagementService riskManagementService;

    @InjectMocks
    private RiskManagementController riskManagementController;

    private RiskManagement testRisk;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        // 初始化测试数据
        testRisk = new RiskManagement();
        testRisk.setId(1L);
        testRisk.setRiskCode("RISK001");
        testRisk.setRiskName("市场风险");
        testRisk.setRiskType("市场风险");
        testRisk.setRiskLevel("中");
        testRisk.setRiskValue(new BigDecimal("5.5"));
        testRisk.setRiskDesc("利率波动导致的市场风险");
        testRisk.setMeasurementMethod("VaR模型");
        testRisk.setRiskLimit("{\"value\": \"1000万\", \"currency\": \"CNY\"}");
        testRisk.setAlertThreshold("{\"value\": \"800万\", \"currency\": \"CNY\"}");
        testRisk.setMitigationMeasures("分散投资，对冲策略");
        testRisk.setRelatedStrategy("趋势跟踪策略");
        testRisk.setRelatedPortfolio("[\"PORTFOLIO001\", \"PORTFOLIO002\"]");
        testRisk.setCreatorId(1001L);
        testRisk.setStatus(1);
        testRisk.setCreateTime(LocalDateTime.now());
        testRisk.setUpdateTime(LocalDateTime.now());
    }

    @Test
    public void getAllRisks_success() {
        // 准备测试数据
        List<RiskManagement> risks = Arrays.asList(testRisk);

        // 模拟服务层返回
        when(riskManagementService.findAll()).thenReturn(risks);

        // 调用控制器方法
        Result<List<RiskManagement>> result = riskManagementController.getAllRisks();

        // 验证结果
        assertNotNull(result);
        assertEquals(200, result.getCode().intValue());
        assertEquals("成功", result.getMessage());
        assertEquals(risks, result.getData());
        verify(riskManagementService, times(1)).findAll();
    }

    @Test
    public void getAllRisks_emptyList() {
        // 模拟服务层返回空列表
        when(riskManagementService.findAll()).thenReturn(Arrays.asList());

        // 调用控制器方法
        Result<List<RiskManagement>> result = riskManagementController.getAllRisks();

        // 验证结果
        assertNotNull(result);
        assertEquals(200, result.getCode().intValue());
        assertEquals("成功", result.getMessage());
        assertTrue(result.getData().isEmpty());
        verify(riskManagementService, times(1)).findAll();
    }

    @Test
    public void getRiskById_success() {
        // 模拟服务层返回
        when(riskManagementService.findById(1L)).thenReturn(testRisk);

        // 调用控制器方法
        Result<RiskManagement> result = riskManagementController.getRiskById(1L);

        // 验证结果
        assertNotNull(result);
        assertEquals(200, result.getCode().intValue());
        assertEquals("成功", result.getMessage());
        assertEquals(testRisk, result.getData());
        verify(riskManagementService, times(1)).findById(1L);
    }

    @Test
    public void getRiskById_notFound() {
        // 模拟服务层返回null
        when(riskManagementService.findById(1L)).thenReturn(null);

        // 调用控制器方法
        Result<RiskManagement> result = riskManagementController.getRiskById(1L);

        // 验证结果
        assertNotNull(result);
        assertEquals(500, result.getCode().intValue());
        assertEquals("风险记录不存在", result.getMessage());
        assertNull(result.getData());
        verify(riskManagementService, times(1)).findById(1L);
    }

    @Test
    public void getRisksByType_success() {
        // 准备测试数据
        List<RiskManagement> risks = Arrays.asList(testRisk);

        // 模拟服务层返回
        when(riskManagementService.findByRiskType("市场风险")).thenReturn(risks);

        // 调用控制器方法
        Result<List<RiskManagement>> result = riskManagementController.getRisksByType("市场风险");

        // 验证结果
        assertNotNull(result);
        assertEquals(200, result.getCode().intValue());
        assertEquals("成功", result.getMessage());
        assertEquals(risks, result.getData());
        verify(riskManagementService, times(1)).findByRiskType("市场风险");
    }

    @Test
    public void getRisksByType_emptyList() {
        // 模拟服务层返回空列表
        when(riskManagementService.findByRiskType("市场风险")).thenReturn(Arrays.asList());

        // 调用控制器方法
        Result<List<RiskManagement>> result = riskManagementController.getRisksByType("市场风险");

        // 验证结果
        assertNotNull(result);
        assertEquals(200, result.getCode().intValue());
        assertEquals("成功", result.getMessage());
        assertTrue(result.getData().isEmpty());
        verify(riskManagementService, times(1)).findByRiskType("市场风险");
    }

    @Test
    public void getRisksByLevel_success() {
        // 准备测试数据
        List<RiskManagement> risks = Arrays.asList(testRisk);

        // 模拟服务层返回
        when(riskManagementService.findByRiskLevel("中")).thenReturn(risks);

        // 调用控制器方法
        Result<List<RiskManagement>> result = riskManagementController.getRisksByLevel("中");

        // 验证结果
        assertNotNull(result);
        assertEquals(200, result.getCode().intValue());
        assertEquals("成功", result.getMessage());
        assertEquals(risks, result.getData());
        verify(riskManagementService, times(1)).findByRiskLevel("中");
    }

    @Test
    public void getRisksByLevel_emptyList() {
        // 模拟服务层返回空列表
        when(riskManagementService.findByRiskLevel("中")).thenReturn(Arrays.asList());

        // 调用控制器方法
        Result<List<RiskManagement>> result = riskManagementController.getRisksByLevel("中");

        // 验证结果
        assertNotNull(result);
        assertEquals(200, result.getCode().intValue());
        assertEquals("成功", result.getMessage());
        assertTrue(result.getData().isEmpty());
        verify(riskManagementService, times(1)).findByRiskLevel("中");
    }

    @Test
    public void createRisk_success() {
        // 模拟服务层返回true
        when(riskManagementService.createRisk(testRisk)).thenReturn(true);

        // 调用控制器方法
        Result<String> result = riskManagementController.createRisk(testRisk);

        // 验证结果
        assertNotNull(result);
        assertEquals(200, result.getCode().intValue());
        assertEquals("创建成功", result.getMessage());
        assertNull(result.getData());
        verify(riskManagementService, times(1)).createRisk(testRisk);
    }

    @Test
    public void createRisk_failure() {
        // 模拟服务层返回false
        when(riskManagementService.createRisk(testRisk)).thenReturn(false);

        // 调用控制器方法
        Result<String> result = riskManagementController.createRisk(testRisk);

        // 验证结果
        assertNotNull(result);
        assertEquals(500, result.getCode().intValue());
        assertEquals("创建失败", result.getMessage());
        assertNull(result.getData());
        verify(riskManagementService, times(1)).createRisk(testRisk);
    }

    @Test
    public void updateRisk_success() {
        // 模拟服务层返回true
        when(riskManagementService.updateRisk(testRisk)).thenReturn(true);

        // 调用控制器方法
        Result<String> result = riskManagementController.updateRisk(testRisk);

        // 验证结果
        assertNotNull(result);
        assertEquals(200, result.getCode().intValue());
        assertEquals("更新成功", result.getMessage());
        assertNull(result.getData());
        verify(riskManagementService, times(1)).updateRisk(testRisk);
    }

    @Test
    public void updateRisk_failure() {
        // 模拟服务层返回false
        when(riskManagementService.updateRisk(testRisk)).thenReturn(false);

        // 调用控制器方法
        Result<String> result = riskManagementController.updateRisk(testRisk);

        // 验证结果
        assertNotNull(result);
        assertEquals(500, result.getCode().intValue());
        assertEquals("更新失败", result.getMessage());
        assertNull(result.getData());
        verify(riskManagementService, times(1)).updateRisk(testRisk);
    }

    @Test
    public void deleteRisk_success() {
        // 模拟服务层返回true
        when(riskManagementService.deleteRisk(1L)).thenReturn(true);

        // 调用控制器方法
        Result<String> result = riskManagementController.deleteRisk(1L);

        // 验证结果
        assertNotNull(result);
        assertEquals(200, result.getCode().intValue());
        assertEquals("删除成功", result.getMessage());
        assertNull(result.getData());
        verify(riskManagementService, times(1)).deleteRisk(1L);
    }

    @Test
    public void deleteRisk_failure() {
        // 模拟服务层返回false
        when(riskManagementService.deleteRisk(1L)).thenReturn(false);

        // 调用控制器方法
        Result<String> result = riskManagementController.deleteRisk(1L);

        // 验证结果
        assertNotNull(result);
        assertEquals(500, result.getCode().intValue());
        assertEquals("删除失败", result.getMessage());
        assertNull(result.getData());
        verify(riskManagementService, times(1)).deleteRisk(1L);
    }
}
