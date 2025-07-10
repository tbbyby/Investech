package com.investech.service;

import com.investech.entity.RiskAlert;
import com.investech.mapper.RiskAlertMapper;
import com.investech.service.impl.RiskAlertServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RiskAlertServiceImplTest {

    @Mock
    private RiskAlertMapper riskAlertMapper;

    @InjectMocks
    private RiskAlertServiceImpl riskAlertService;

    private RiskAlert testAlert;

    @BeforeEach
    void setUp() {
        testAlert = new RiskAlert();
        testAlert.setId(1L);
        testAlert.setAlertName("市场风险预警");
        testAlert.setAlertType("市场风险");
        testAlert.setAlertLevel("高");
        testAlert.setAlertSource("系统自动检测");
        testAlert.setAlertContent("组合波动率超过警戒值");
        testAlert.setAffectedPortfolio("PORTFOLIO001");
        testAlert.setImpactAssessment("可能导致组合日损失超过5%");
        testAlert.setActionRequired("立即调整组合配置");
        testAlert.setResponsiblePerson("张伟");
        testAlert.setAlertTime(LocalDateTime.now());
        testAlert.setAcknowledgeTime(LocalDateTime.now().plusHours(1));
        testAlert.setResolveTime(LocalDateTime.now().plusDays(1));
        testAlert.setStatus("未确认");
        testAlert.setResolution("已调整组合持仓比例");
        testAlert.setCreateTime(LocalDateTime.now());
        testAlert.setUpdateTime(LocalDateTime.now());
        testAlert.setStatusCode(1);
    }

    @Test
    void getAllAlerts_shouldCallMapperOnce() {
        List<RiskAlert> alerts = Arrays.asList(testAlert);
        when(riskAlertMapper.selectAll()).thenReturn(alerts);

        List<RiskAlert> result = riskAlertService.getAllAlerts();

        verify(riskAlertMapper, times(1)).selectAll();
        assertNotNull(result);
        assertEquals(1, result.size());
    }

    @Test
    void getAllAlerts_whenMapperReturnsEmptyList() {
        when(riskAlertMapper.selectAll()).thenReturn(List.of());

        List<RiskAlert> result = riskAlertService.getAllAlerts();

        verify(riskAlertMapper, times(1)).selectAll();
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    void getAlertById_shouldCallMapperOnce() {
        when(riskAlertMapper.selectById(1L)).thenReturn(testAlert);

        RiskAlert result = riskAlertService.getAlertById(1L);

        verify(riskAlertMapper, times(1)).selectById(1L);
        assertNotNull(result);
        assertEquals("市场风险预警", result.getAlertName());
    }

    @Test
    void getAlertById_whenMapperReturnsNull() {
        when(riskAlertMapper.selectById(1L)).thenReturn(null);

        RiskAlert result = riskAlertService.getAlertById(1L);

        verify(riskAlertMapper, times(1)).selectById(1L);
        assertNull(result);
    }

    @Test
    void getAlertsByCondition_shouldCallMapperOnce() {
        List<RiskAlert> alerts = Arrays.asList(testAlert);
        when(riskAlertMapper.selectByCondition(testAlert)).thenReturn(alerts);

        List<RiskAlert> result = riskAlertService.getAlertsByCondition(testAlert);

        verify(riskAlertMapper, times(1)).selectByCondition(testAlert);
        assertNotNull(result);
        assertEquals(1, result.size());
    }

    @Test
    void getAlertsByCondition_whenMapperReturnsEmptyList() {
        when(riskAlertMapper.selectByCondition(testAlert)).thenReturn(List.of());

        List<RiskAlert> result = riskAlertService.getAlertsByCondition(testAlert);

        verify(riskAlertMapper, times(1)).selectByCondition(testAlert);
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    void addAlert_shouldCallInsertOnce() {
        LocalDateTime createTime = LocalDateTime.now().minusDays(1);
        testAlert.setCreateTime(createTime);

        when(riskAlertMapper.insert(testAlert)).thenReturn(1);

        boolean result = riskAlertService.addAlert(testAlert);

        verify(riskAlertMapper, times(1)).insert(testAlert);
        assertTrue(result);
        assertEquals(createTime, testAlert.getCreateTime());
        assertTrue(testAlert.getUpdateTime().isAfter(createTime));
    }

    @Test
    void addAlert_whenInsertFails() {
        LocalDateTime createTime = LocalDateTime.now().minusDays(1);
        testAlert.setCreateTime(createTime);

        when(riskAlertMapper.insert(testAlert)).thenReturn(0);

        boolean result = riskAlertService.addAlert(testAlert);

        verify(riskAlertMapper, times(1)).insert(testAlert);
        assertFalse(result);
        assertEquals(createTime, testAlert.getCreateTime());
        assertTrue(testAlert.getUpdateTime().isAfter(createTime));
    }

    @Test
    void updateAlert_shouldCallUpdateOnce() {
        LocalDateTime createTime = LocalDateTime.now().minusDays(1);
        testAlert.setCreateTime(createTime);

        when(riskAlertMapper.update(testAlert)).thenReturn(1);

        boolean result = riskAlertService.updateAlert(testAlert);

        verify(riskAlertMapper, times(1)).update(testAlert);
        assertTrue(result);
        assertEquals(createTime, testAlert.getCreateTime());
        assertTrue(testAlert.getUpdateTime().isAfter(createTime));
    }

    @Test
    void updateAlert_whenUpdateFails() {
        LocalDateTime createTime = LocalDateTime.now().minusDays(1);
        testAlert.setCreateTime(createTime);

        when(riskAlertMapper.update(testAlert)).thenReturn(0);

        boolean result = riskAlertService.updateAlert(testAlert);

        verify(riskAlertMapper, times(1)).update(testAlert);
        assertFalse(result);
        assertEquals(createTime, testAlert.getCreateTime());
        assertTrue(testAlert.getUpdateTime().isAfter(createTime));
    }

    @Test
    void deleteAlert_shouldCallDeleteOnce() {
        when(riskAlertMapper.deleteById(1L)).thenReturn(1);

        boolean result = riskAlertService.deleteAlert(1L);

        verify(riskAlertMapper, times(1)).deleteById(1L);
        assertTrue(result);
    }

    @Test
    void deleteAlert_whenDeleteFails() {
        when(riskAlertMapper.deleteById(1L)).thenReturn(0);

        boolean result = riskAlertService.deleteAlert(1L);

        verify(riskAlertMapper, times(1)).deleteById(1L);
        assertFalse(result);
    }

    @Test
    void updateAlertStatus_shouldCallUpdateStatusOnce() {
        when(riskAlertMapper.updateStatus(1L, 0)).thenReturn(1);

        boolean result = riskAlertService.updateAlertStatus(1L, 0);

        verify(riskAlertMapper, times(1)).updateStatus(1L, 0);
        assertTrue(result);
    }

    @Test
    void updateAlertStatus_whenUpdateFails() {
        when(riskAlertMapper.updateStatus(1L, 0)).thenReturn(0);

        boolean result = riskAlertService.updateAlertStatus(1L, 0);

        verify(riskAlertMapper, times(1)).updateStatus(1L, 0);
        assertFalse(result);
    }

}
