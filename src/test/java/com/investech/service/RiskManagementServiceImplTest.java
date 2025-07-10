package com.investech.service;

import com.investech.entity.RiskManagement;
import com.investech.mapper.RiskManagementMapper;
import com.investech.service.impl.RiskManagementServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RiskManagementServiceImplTest {

    @Mock
    private RiskManagementMapper riskManagementMapper;

    @InjectMocks
    private RiskManagementServiceImpl riskManagementService;

    private RiskManagement testRisk;

    @BeforeEach
    void setUp() {
        testRisk = new RiskManagement();
        testRisk.setId(1L);
        testRisk.setRiskCode("RISK001");
        testRisk.setRiskName("市场风险");
        testRisk.setRiskType("市场风险");
        testRisk.setRiskLevel("中");
        testRisk.setRiskValue(new BigDecimal("0.05"));
        testRisk.setRiskDesc("组合波动率风险");
        testRisk.setMeasurementMethod("VaR模型");
        testRisk.setRiskLimit("组合价值的5%");
        testRisk.setAlertThreshold("组合价值的3%");
        testRisk.setMitigationMeasures("调整组合配置");
        testRisk.setRelatedStrategy("投资策略A");
        testRisk.setRelatedPortfolio("PORTFOLIO001");
        testRisk.setCreatorId(2001L);
        testRisk.setStatus(1);
        testRisk.setCreateTime(LocalDateTime.now());
        testRisk.setUpdateTime(LocalDateTime.now());
    }

    @Test
    void findAll_shouldCallMapperOnce() {
        List<RiskManagement> risks = Arrays.asList(testRisk);
        when(riskManagementMapper.findAll()).thenReturn(risks);

        List<RiskManagement> result = riskManagementService.findAll();

        verify(riskManagementMapper, times(1)).findAll();
        assertNotNull(result);
        assertEquals(1, result.size());
    }

    @Test
    void findByRiskCode_shouldCallMapperOnce() {
        when(riskManagementMapper.findByRiskCode("RISK001")).thenReturn(testRisk);

        RiskManagement result = riskManagementService.findByRiskCode("RISK001");

        verify(riskManagementMapper, times(1)).findByRiskCode("RISK001");
        assertNotNull(result);
        assertEquals("市场风险", result.getRiskName());
    }

    @Test
    void findById_shouldCallMapperOnce() {
        when(riskManagementMapper.findById(1L)).thenReturn(testRisk);

        RiskManagement result = riskManagementService.findById(1L);

        verify(riskManagementMapper, times(1)).findById(1L);
        assertNotNull(result);
        assertEquals("RISK001", result.getRiskCode());
    }

    @Test
    void findByRiskType_shouldCallMapperOnce() {
        List<RiskManagement> risks = Arrays.asList(testRisk);
        when(riskManagementMapper.findByRiskType("市场风险")).thenReturn(risks);

        List<RiskManagement> result = riskManagementService.findByRiskType("市场风险");

        verify(riskManagementMapper, times(1)).findByRiskType("市场风险");
        assertNotNull(result);
        assertEquals(1, result.size());
    }

    @Test
    void findByRiskLevel_shouldCallMapperOnce() {
        List<RiskManagement> risks = Arrays.asList(testRisk);
        when(riskManagementMapper.findByRiskLevel("中")).thenReturn(risks);

        List<RiskManagement> result = riskManagementService.findByRiskLevel("中");

        verify(riskManagementMapper, times(1)).findByRiskLevel("中");
        assertNotNull(result);
        assertEquals(1, result.size());
    }

    @Test
    void findByCreatorId_shouldCallMapperOnce() {
        List<RiskManagement> risks = Arrays.asList(testRisk);
        when(riskManagementMapper.findByCreatorId(2001L)).thenReturn(risks);

        List<RiskManagement> result = riskManagementService.findByCreatorId(2001L);

        verify(riskManagementMapper, times(1)).findByCreatorId(2001L);
        assertNotNull(result);
        assertEquals(1, result.size());
    }

    @Test
    void searchRisks_shouldCallMapperOnce() {
        List<RiskManagement> risks = Arrays.asList(testRisk);
        when(riskManagementMapper.findByCondition(
                "RISK001", "市场风险", "市场风险", "中", 2001L)).thenReturn(risks);

        List<RiskManagement> result = riskManagementService.searchRisks(
                "RISK001", "市场风险", "市场风险", "中", 2001L);

        verify(riskManagementMapper, times(1)).findByCondition(
                "RISK001", "市场风险", "市场风险", "中", 2001L);
        assertNotNull(result);
        assertEquals(1, result.size());
    }

    @Test
    void createRisk_shouldCallInsertOnce() {
        LocalDateTime createTime = LocalDateTime.now().minusDays(1);
        testRisk.setCreateTime(createTime);

        when(riskManagementMapper.insert(testRisk)).thenReturn(1);

        boolean result = riskManagementService.createRisk(testRisk);

        verify(riskManagementMapper, times(1)).insert(testRisk);
        assertTrue(result);
        assertEquals(1, testRisk.getStatus().intValue());
        assertEquals(createTime, testRisk.getCreateTime());
        assertTrue(testRisk.getUpdateTime().isAfter(createTime));
    }

    @Test
    void updateRisk_shouldCallUpdateOnce() {
        LocalDateTime createTime = LocalDateTime.now().minusDays(1);
        testRisk.setCreateTime(createTime);

        when(riskManagementMapper.update(testRisk)).thenReturn(1);

        boolean result = riskManagementService.updateRisk(testRisk);

        verify(riskManagementMapper, times(1)).update(testRisk);
        assertTrue(result);
        assertEquals(createTime, testRisk.getCreateTime());
        assertTrue(testRisk.getUpdateTime().isAfter(createTime));
    }

    @Test
    void deleteRisk_shouldCallDeleteOnce() {
        when(riskManagementMapper.deleteById(1L)).thenReturn(1);

        boolean result = riskManagementService.deleteRisk(1L);

        verify(riskManagementMapper, times(1)).deleteById(1L);
        assertTrue(result);
    }
}
