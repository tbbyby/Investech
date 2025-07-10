package com.investech.service;

import com.investech.entity.RiskMonitor;
import com.investech.mapper.RiskMonitorMapper;
import com.investech.service.impl.RiskMonitorServiceImpl;
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
class RiskMonitorServiceImplTest {

    @Mock
    private RiskMonitorMapper riskMonitorMapper;

    @InjectMocks
    private RiskMonitorServiceImpl riskMonitorService;

    private RiskMonitor testMonitor;

    @BeforeEach
    void setUp() {
        testMonitor = new RiskMonitor();
        testMonitor.setId(1L);
        testMonitor.setMonitorName("组合波动率监控");
        testMonitor.setMonitorType("市场风险");
        testMonitor.setRiskIndicator("波动率");
        testMonitor.setThreshold(5.0);
        testMonitor.setCurrentValue(4.8);
        testMonitor.setRiskLevel("中");
        testMonitor.setStatus("正常");
        testMonitor.setDescription("监控组合每日波动情况");
        testMonitor.setCreateTime(LocalDateTime.now());
        testMonitor.setUpdateTime(LocalDateTime.now());
        testMonitor.setStatusCode(1);
    }

    @Test
    void getAllMonitors_shouldCallMapperOnce() {
        List<RiskMonitor> monitors = Arrays.asList(testMonitor);
        when(riskMonitorMapper.selectAll()).thenReturn(monitors);

        List<RiskMonitor> result = riskMonitorService.getAllMonitors();

        verify(riskMonitorMapper, times(1)).selectAll();
        assertNotNull(result);
        assertEquals(1, result.size());
    }
    
    @Test
    void getAllMonitors_shouldReturnEmptyListWhenNoData() {
        when(riskMonitorMapper.selectAll()).thenReturn(List.of());

        List<RiskMonitor> result = riskMonitorService.getAllMonitors();

        verify(riskMonitorMapper, times(1)).selectAll();
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    void getMonitorById_shouldCallMapperOnce() {
        when(riskMonitorMapper.selectById(1L)).thenReturn(testMonitor);

        RiskMonitor result = riskMonitorService.getMonitorById(1L);

        verify(riskMonitorMapper, times(1)).selectById(1L);
        assertNotNull(result);
        assertEquals("组合波动率监控", result.getMonitorName());
    }
    
    @Test
    void getMonitorById_shouldReturnNullWhenIdNotFound() {
        when(riskMonitorMapper.selectById(999L)).thenReturn(null);

        RiskMonitor result = riskMonitorService.getMonitorById(999L);

        verify(riskMonitorMapper, times(1)).selectById(999L);
        assertNull(result);
    }

    @Test
    void getMonitorsByCondition_shouldCallMapperOnce() {
        List<RiskMonitor> monitors = Arrays.asList(testMonitor);
        when(riskMonitorMapper.selectByCondition(testMonitor)).thenReturn(monitors);

        List<RiskMonitor> result = riskMonitorService.getMonitorsByCondition(testMonitor);

        verify(riskMonitorMapper, times(1)).selectByCondition(testMonitor);
        assertNotNull(result);
        assertEquals(1, result.size());
    }
    
    @Test
    void getMonitorsByCondition_shouldReturnEmptyListWhenNoMatch() {
        when(riskMonitorMapper.selectByCondition(any(RiskMonitor.class))).thenReturn(List.of());

        List<RiskMonitor> result = riskMonitorService.getMonitorsByCondition(new RiskMonitor());

        verify(riskMonitorMapper, times(1)).selectByCondition(any(RiskMonitor.class));
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    void addMonitor_shouldCallInsertOnce() {
        LocalDateTime createTime = LocalDateTime.now().minusDays(1);
        testMonitor.setCreateTime(createTime);

        when(riskMonitorMapper.insert(testMonitor)).thenReturn(1);

        boolean result = riskMonitorService.addMonitor(testMonitor);

        verify(riskMonitorMapper, times(1)).insert(testMonitor);
        assertTrue(result);
        assertEquals(createTime, testMonitor.getCreateTime());
        assertTrue(testMonitor.getUpdateTime().isAfter(createTime));
    }
    
    @Test
    void addMonitor_shouldReturnFalseWhenInsertFails() {
        LocalDateTime createTime = LocalDateTime.now().minusDays(1);
        testMonitor.setCreateTime(createTime);

        when(riskMonitorMapper.insert(testMonitor)).thenReturn(0);

        boolean result = riskMonitorService.addMonitor(testMonitor);

        verify(riskMonitorMapper, times(1)).insert(testMonitor);
        assertFalse(result);
        assertEquals(createTime, testMonitor.getCreateTime());
        assertTrue(testMonitor.getUpdateTime().isAfter(createTime));
    }

    @Test
    void updateMonitor_shouldCallUpdateOnce() {
        LocalDateTime createTime = LocalDateTime.now().minusDays(1);
        testMonitor.setCreateTime(createTime);

        when(riskMonitorMapper.update(testMonitor)).thenReturn(1);

        boolean result = riskMonitorService.updateMonitor(testMonitor);

        verify(riskMonitorMapper, times(1)).update(testMonitor);
        assertTrue(result);
        assertEquals(createTime, testMonitor.getCreateTime());
        assertTrue(testMonitor.getUpdateTime().isAfter(createTime));
    }
    
    @Test
    void updateMonitor_shouldReturnFalseWhenUpdateFails() {
        LocalDateTime createTime = LocalDateTime.now().minusDays(1);
        testMonitor.setCreateTime(createTime);

        when(riskMonitorMapper.update(testMonitor)).thenReturn(0);

        boolean result = riskMonitorService.updateMonitor(testMonitor);

        verify(riskMonitorMapper, times(1)).update(testMonitor);
        assertFalse(result);
        assertEquals(createTime, testMonitor.getCreateTime());
        assertTrue(testMonitor.getUpdateTime().isAfter(createTime));
    }

    @Test
    void deleteMonitor_shouldCallDeleteOnce() {
        when(riskMonitorMapper.deleteById(1L)).thenReturn(1);

        boolean result = riskMonitorService.deleteMonitor(1L);

        verify(riskMonitorMapper, times(1)).deleteById(1L);
        assertTrue(result);
    }
    
    @Test
    void deleteMonitor_shouldReturnFalseWhenDeleteFails() {
        when(riskMonitorMapper.deleteById(999L)).thenReturn(0);

        boolean result = riskMonitorService.deleteMonitor(999L);

        verify(riskMonitorMapper, times(1)).deleteById(999L);
        assertFalse(result);
    }

    @Test
    void updateMonitorStatus_shouldCallUpdateStatusOnce() {
        when(riskMonitorMapper.updateStatus(1L, 0)).thenReturn(1);

        boolean result = riskMonitorService.updateMonitorStatus(1L, 0);

        verify(riskMonitorMapper, times(1)).updateStatus(1L, 0);
        assertTrue(result);
    }
    
    @Test
    void updateMonitorStatus_shouldReturnFalseWhenUpdateFails() {
        when(riskMonitorMapper.updateStatus(999L, 0)).thenReturn(0);

        boolean result = riskMonitorService.updateMonitorStatus(999L, 0);

        verify(riskMonitorMapper, times(1)).updateStatus(999L, 0);
        assertFalse(result);
    }
}
