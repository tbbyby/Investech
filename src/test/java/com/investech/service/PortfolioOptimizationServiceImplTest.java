package com.investech.service;

import com.investech.entity.PortfolioOptimization;
import com.investech.mapper.PortfolioOptimizationMapper;
import com.investech.service.impl.PortfolioOptimizationServiceImpl;
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
class PortfolioOptimizationServiceImplTest {

    @Mock
    private PortfolioOptimizationMapper portfolioOptimizationMapper;

    @InjectMocks
    private PortfolioOptimizationServiceImpl portfolioOptimizationService;

    private PortfolioOptimization testOptimization;

    @BeforeEach
    void setUp() {
        testOptimization = new PortfolioOptimization();
        testOptimization.setId(1L);
        testOptimization.setClientId(1001L);
        testOptimization.setOptimizationName("2025Q2投资组合优化");
        testOptimization.setOptimizationMethod("马科维茨");
        testOptimization.setFundPool("[\"FUND001\", \"FUND002\", \"FUND003\"]");
        testOptimization.setTargetReturn(8.5);
        testOptimization.setMaxRisk(15.0);
        testOptimization.setOptimizedWeights("{\"FUND001\": 0.5, \"FUND002\": 0.3, \"FUND003\": 0.2}");
        testOptimization.setExpectedReturn(9.2);
        testOptimization.setExpectedRisk(14.5);
        testOptimization.setSharpeRatio(0.63);
        testOptimization.setConstraints("{\"最小持仓\": 0.1, \"最大持仓\": 0.5}");
        testOptimization.setStatus(1);
        testOptimization.setCreateTime(LocalDateTime.now());
        testOptimization.setUpdateTime(LocalDateTime.now());
    }

    @Test
    void getAllOptimizations_shouldCallMapperOnce() {
        List<PortfolioOptimization> optimizations = Arrays.asList(testOptimization);
        when(portfolioOptimizationMapper.selectAll()).thenReturn(optimizations);

        List<PortfolioOptimization> result = portfolioOptimizationService.getAllOptimizations();

        verify(portfolioOptimizationMapper, times(1)).selectAll();
        assertNotNull(result);
        assertEquals(1, result.size());
    }

    @Test
    void getAllOptimizations_whenNoData_returnsEmptyList() {
        when(portfolioOptimizationMapper.selectAll()).thenReturn(List.of());

        List<PortfolioOptimization> result = portfolioOptimizationService.getAllOptimizations();

        verify(portfolioOptimizationMapper, times(1)).selectAll();
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    void getOptimizationById_shouldCallMapperOnce() {
        when(portfolioOptimizationMapper.selectById(1L)).thenReturn(testOptimization);

        PortfolioOptimization result = portfolioOptimizationService.getOptimizationById(1L);

        verify(portfolioOptimizationMapper, times(1)).selectById(1L);
        assertNotNull(result);
        assertEquals("2025Q2投资组合优化", result.getOptimizationName());
    }

    @Test
    void getOptimizationById_whenNotFound_returnsNull() {
        when(portfolioOptimizationMapper.selectById(1L)).thenReturn(null);

        PortfolioOptimization result = portfolioOptimizationService.getOptimizationById(1L);

        verify(portfolioOptimizationMapper, times(1)).selectById(1L);
        assertNull(result);
    }

    @Test
    void getOptimizationsByClientId_shouldCallMapperOnce() {
        List<PortfolioOptimization> optimizations = Arrays.asList(testOptimization);
        when(portfolioOptimizationMapper.selectByClientId(1001L)).thenReturn(optimizations);

        List<PortfolioOptimization> result = portfolioOptimizationService.getOptimizationsByClientId(1001L);

        verify(portfolioOptimizationMapper, times(1)).selectByClientId(1001L);
        assertNotNull(result);
        assertEquals(1, result.size());
    }

    @Test
    void getOptimizationsByClientId_whenNoResult_returnsEmptyList() {
        when(portfolioOptimizationMapper.selectByClientId(999L)).thenReturn(List.of());

        List<PortfolioOptimization> result = portfolioOptimizationService.getOptimizationsByClientId(999L);

        verify(portfolioOptimizationMapper, times(1)).selectByClientId(999L);
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    void getOptimizationsByCondition_shouldCallMapperOnce() {
        List<PortfolioOptimization> optimizations = Arrays.asList(testOptimization);
        when(portfolioOptimizationMapper.selectByCondition(testOptimization)).thenReturn(optimizations);

        List<PortfolioOptimization> result = portfolioOptimizationService.getOptimizationsByCondition(testOptimization);

        verify(portfolioOptimizationMapper, times(1)).selectByCondition(testOptimization);
        assertNotNull(result);
        assertEquals(1, result.size());
    }

    @Test
    void getOptimizationsByCondition_whenNoMatch_returnsEmptyList() {
        when(portfolioOptimizationMapper.selectByCondition(any(PortfolioOptimization.class))).thenReturn(List.of());

        PortfolioOptimization condition = new PortfolioOptimization();
        condition.setClientId(999L);

        List<PortfolioOptimization> result = portfolioOptimizationService.getOptimizationsByCondition(condition);

        verify(portfolioOptimizationMapper, times(1)).selectByCondition(condition);
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    void addOptimization_shouldCallInsertOnce() {
        LocalDateTime createTime = LocalDateTime.now().minusDays(1);
        testOptimization.setCreateTime(createTime);

        when(portfolioOptimizationMapper.insert(testOptimization)).thenReturn(1);

        boolean result = portfolioOptimizationService.addOptimization(testOptimization);

        verify(portfolioOptimizationMapper, times(1)).insert(testOptimization);
        assertTrue(result);
        assertEquals(createTime, testOptimization.getCreateTime());
        assertTrue(testOptimization.getUpdateTime().isAfter(createTime));
    }

    @Test
    void addOptimization_whenInsertFails_returnsFalse() {
        when(portfolioOptimizationMapper.insert(testOptimization)).thenReturn(0);

        boolean result = portfolioOptimizationService.addOptimization(testOptimization);

        verify(portfolioOptimizationMapper, times(1)).insert(testOptimization);
        assertFalse(result);
    }

    @Test
    void updateOptimization_shouldCallUpdateOnce() {
        LocalDateTime createTime = LocalDateTime.now().minusDays(1);
        testOptimization.setCreateTime(createTime);

        when(portfolioOptimizationMapper.update(testOptimization)).thenReturn(1);

        boolean result = portfolioOptimizationService.updateOptimization(testOptimization);

        verify(portfolioOptimizationMapper, times(1)).update(testOptimization);
        assertTrue(result);
        assertEquals(createTime, testOptimization.getCreateTime());
        assertTrue(testOptimization.getUpdateTime().isAfter(createTime));
    }

    @Test
    void updateOptimization_whenUpdateFails_returnsFalse() {
        when(portfolioOptimizationMapper.update(testOptimization)).thenReturn(0);

        boolean result = portfolioOptimizationService.updateOptimization(testOptimization);

        verify(portfolioOptimizationMapper, times(1)).update(testOptimization);
        assertFalse(result);
    }

    @Test
    void deleteOptimization_shouldCallDeleteOnce() {
        when(portfolioOptimizationMapper.deleteById(1L)).thenReturn(1);

        boolean result = portfolioOptimizationService.deleteOptimization(1L);

        verify(portfolioOptimizationMapper, times(1)).deleteById(1L);
        assertTrue(result);
    }

    @Test
    void deleteOptimization_whenDeleteFails_returnsFalse() {
        when(portfolioOptimizationMapper.deleteById(1L)).thenReturn(0);

        boolean result = portfolioOptimizationService.deleteOptimization(1L);

        verify(portfolioOptimizationMapper, times(1)).deleteById(1L);
        assertFalse(result);
    }

    @Test
    void updateOptimizationStatus_shouldCallUpdateStatusOnce() {
        when(portfolioOptimizationMapper.updateStatus(1L, 2)).thenReturn(1);

        boolean result = portfolioOptimizationService.updateOptimizationStatus(1L, 2);

        verify(portfolioOptimizationMapper, times(1)).updateStatus(1L, 2);
        assertTrue(result);
    }

    @Test
    void updateOptimizationStatus_whenUpdateFails_returnsFalse() {
        when(portfolioOptimizationMapper.updateStatus(1L, 2)).thenReturn(0);

        boolean result = portfolioOptimizationService.updateOptimizationStatus(1L, 2);

        verify(portfolioOptimizationMapper, times(1)).updateStatus(1L, 2);
        assertFalse(result);
    }
}
