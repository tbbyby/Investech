package com.investech.service;

import com.investech.entity.Strategy;
import com.investech.mapper.StrategyMapper;
import com.investech.service.impl.StrategyServiceImpl;
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
class StrategyServiceImplTest {

    @Mock
    private StrategyMapper strategyMapper;

    @InjectMocks
    private StrategyServiceImpl strategyService;

    private Strategy testStrategy;

    @BeforeEach
    void setUp() {
        testStrategy = new Strategy();
        testStrategy.setId(1L);
        testStrategy.setStrategyCode("STRAT001");
        testStrategy.setStrategyName("成长型股票策略");
        testStrategy.setStrategyDesc("专注于高增长潜力的股票投资");
        testStrategy.setStrategyType("股票策略");
        testStrategy.setInvestmentStyle("成长型");
        testStrategy.setRiskLevel("中风险");
        testStrategy.setTargetReturn("年化收益15%");
        testStrategy.setMaxDrawdown("最大回撤20%");
        testStrategy.setBenchmark("沪深300指数");
        testStrategy.setFactorCodes("[\"F001\", \"F002\"]");
        testStrategy.setParameters("{\"换仓周期\": \"季度\", \"仓位控制\": \"80%-100%\"}");
        testStrategy.setCreatorId(1001L);
        testStrategy.setStatus(1);
        testStrategy.setCreateTime(LocalDateTime.now());
        testStrategy.setUpdateTime(LocalDateTime.now());
    }

    @Test
    void findAll_shouldCallMapperOnce() {
        List<Strategy> strategies = Arrays.asList(testStrategy);
        when(strategyMapper.findAll()).thenReturn(strategies);

        List<Strategy> result = strategyService.findAll();

        verify(strategyMapper, times(1)).findAll();
        assertNotNull(result);
        assertEquals(1, result.size());
    }

    @Test
    void findAll_whenNoData_returnsEmptyList() {
        when(strategyMapper.findAll()).thenReturn(List.of());

        List<Strategy> result = strategyService.findAll();

        verify(strategyMapper, times(1)).findAll();
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    void findByStrategyCode_shouldCallMapperOnce() {
        when(strategyMapper.findByStrategyCode("STRAT001")).thenReturn(testStrategy);

        Strategy result = strategyService.findByStrategyCode("STRAT001");

        verify(strategyMapper, times(1)).findByStrategyCode("STRAT001");
        assertNotNull(result);
        assertEquals("成长型股票策略", result.getStrategyName());
    }

    @Test
    void findByStrategyCode_whenNotFound_returnsNull() {
        when(strategyMapper.findByStrategyCode("STRAT002")).thenReturn(null);

        Strategy result = strategyService.findByStrategyCode("STRAT002");

        verify(strategyMapper, times(1)).findByStrategyCode("STRAT002");
        assertNull(result);
    }

    @Test
    void findById_shouldCallMapperOnce() {
        when(strategyMapper.findById(1L)).thenReturn(testStrategy);

        Strategy result = strategyService.findById(1L);

        verify(strategyMapper, times(1)).findById(1L);
        assertNotNull(result);
        assertEquals("STRAT001", result.getStrategyCode());
    }

    @Test
    void findById_whenNotFound_returnsNull() {
        when(strategyMapper.findById(2L)).thenReturn(null);

        Strategy result = strategyService.findById(2L);

        verify(strategyMapper, times(1)).findById(2L);
        assertNull(result);
    }

    @Test
    void findByCreatorId_shouldCallMapperOnce() {
        List<Strategy> strategies = Arrays.asList(testStrategy);
        when(strategyMapper.findByCreatorId(1001L)).thenReturn(strategies);

        List<Strategy> result = strategyService.findByCreatorId(1001L);

        verify(strategyMapper, times(1)).findByCreatorId(1001L);
        assertNotNull(result);
        assertEquals(1, result.size());
    }

    @Test
    void findByCreatorId_whenNoResults_returnsEmptyList() {
        when(strategyMapper.findByCreatorId(999L)).thenReturn(List.of());

        List<Strategy> result = strategyService.findByCreatorId(999L);

        verify(strategyMapper, times(1)).findByCreatorId(999L);
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    void findByType_shouldCallMapperOnce() {
        List<Strategy> strategies = Arrays.asList(testStrategy);
        when(strategyMapper.findByType("股票策略")).thenReturn(strategies);

        List<Strategy> result = strategyService.findByType("股票策略");

        verify(strategyMapper, times(1)).findByType("股票策略");
        assertNotNull(result);
        assertEquals(1, result.size());
    }

    @Test
    void findByType_whenNoResults_returnsEmptyList() {
        when(strategyMapper.findByType("债券策略")).thenReturn(List.of());

        List<Strategy> result = strategyService.findByType("债券策略");

        verify(strategyMapper, times(1)).findByType("债券策略");
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    void findByInvestmentStyle_shouldCallMapperOnce() {
        List<Strategy> strategies = Arrays.asList(testStrategy);
        when(strategyMapper.findByInvestmentStyle("成长型")).thenReturn(strategies);

        List<Strategy> result = strategyService.findByInvestmentStyle("成长型");

        verify(strategyMapper, times(1)).findByInvestmentStyle("成长型");
        assertNotNull(result);
        assertEquals(1, result.size());
    }

    @Test
    void findByInvestmentStyle_whenNoResults_returnsEmptyList() {
        when(strategyMapper.findByInvestmentStyle("价值型")).thenReturn(List.of());

        List<Strategy> result = strategyService.findByInvestmentStyle("价值型");

        verify(strategyMapper, times(1)).findByInvestmentStyle("价值型");
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    void findByRiskLevel_shouldCallMapperOnce() {
        List<Strategy> strategies = Arrays.asList(testStrategy);
        when(strategyMapper.findByRiskLevel("中风险")).thenReturn(strategies);

        List<Strategy> result = strategyService.findByRiskLevel("中风险");

        verify(strategyMapper, times(1)).findByRiskLevel("中风险");
        assertNotNull(result);
        assertEquals(1, result.size());
    }

    @Test
    void findByRiskLevel_whenNoResults_returnsEmptyList() {
        when(strategyMapper.findByRiskLevel("低风险")).thenReturn(List.of());

        List<Strategy> result = strategyService.findByRiskLevel("低风险");

        verify(strategyMapper, times(1)).findByRiskLevel("低风险");
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    void searchStrategies_shouldCallMapperOnce() {
        List<Strategy> strategies = Arrays.asList(testStrategy);
        when(strategyMapper.findByCondition(
                "STRAT001", "成长型股票策略", "股票策略",
                "成长型", "中风险", 1001L)).thenReturn(strategies);

        List<Strategy> result = strategyService.searchStrategies(
                "STRAT001", "成长型股票策略", "股票策略",
                "成长型", "中风险", 1001L);

        verify(strategyMapper, times(1)).findByCondition(
                "STRAT001", "成长型股票策略", "股票策略",
                "成长型", "中风险", 1001L);
        assertNotNull(result);
        assertEquals(1, result.size());
    }

    @Test
    void searchStrategies_whenNoMatches_returnsEmptyList() {
        when(strategyMapper.findByCondition(
                "STRAT002", null, null,
                null, null, null)).thenReturn(List.of());

        List<Strategy> result = strategyService.searchStrategies(
                "STRAT002", null, null,
                null, null, null);

        verify(strategyMapper, times(1)).findByCondition(
                "STRAT002", null, null,
                null, null, null);
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    void createStrategy_shouldCallInsertOnce() {
        LocalDateTime createTime = LocalDateTime.now().minusDays(1);
        testStrategy.setCreateTime(createTime);

        when(strategyMapper.insert(testStrategy)).thenReturn(1);

        boolean result = strategyService.createStrategy(testStrategy);

        verify(strategyMapper, times(1)).insert(testStrategy);
        assertTrue(result);
        assertEquals(1, testStrategy.getStatus().intValue());
        assertEquals(createTime, testStrategy.getCreateTime());
        assertTrue(testStrategy.getUpdateTime().isAfter(createTime));
    }

    @Test
    void createStrategy_whenInsertFails_returnsFalse() {
        LocalDateTime createTime = LocalDateTime.now().minusDays(1);
        testStrategy.setCreateTime(createTime);

        when(strategyMapper.insert(testStrategy)).thenReturn(0);

        boolean result = strategyService.createStrategy(testStrategy);

        verify(strategyMapper, times(1)).insert(testStrategy);
        assertFalse(result);
        assertEquals(1, testStrategy.getStatus().intValue());
        assertEquals(createTime, testStrategy.getCreateTime());
        assertTrue(testStrategy.getUpdateTime().isAfter(createTime));
    }

    @Test
    void updateStrategy_shouldCallUpdateOnce() {
        LocalDateTime createTime = LocalDateTime.now().minusDays(1);
        testStrategy.setCreateTime(createTime);

        when(strategyMapper.update(testStrategy)).thenReturn(1);

        boolean result = strategyService.updateStrategy(testStrategy);

        verify(strategyMapper, times(1)).update(testStrategy);
        assertTrue(result);
        assertEquals(createTime, testStrategy.getCreateTime());
        assertTrue(testStrategy.getUpdateTime().isAfter(createTime));
    }

    @Test
    void updateStrategy_whenUpdateFails_returnsFalse() {
        LocalDateTime createTime = LocalDateTime.now().minusDays(1);
        testStrategy.setCreateTime(createTime);

        when(strategyMapper.update(testStrategy)).thenReturn(0);

        boolean result = strategyService.updateStrategy(testStrategy);

        verify(strategyMapper, times(1)).update(testStrategy);
        assertFalse(result);
        assertEquals(createTime, testStrategy.getCreateTime());
        assertTrue(testStrategy.getUpdateTime().isAfter(createTime));
    }

    @Test
    void deleteStrategy_shouldCallDeleteOnce() {
        when(strategyMapper.deleteById(1L)).thenReturn(1);

        boolean result = strategyService.deleteStrategy(1L);

        verify(strategyMapper, times(1)).deleteById(1L);
        assertTrue(result);
    }

    @Test
    void deleteStrategy_whenDeleteFails_returnsFalse() {
        when(strategyMapper.deleteById(1L)).thenReturn(0);

        boolean result = strategyService.deleteStrategy(1L);

        verify(strategyMapper, times(1)).deleteById(1L);
        assertFalse(result);
    }
}
