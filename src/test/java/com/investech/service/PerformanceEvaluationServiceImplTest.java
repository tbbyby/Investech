package com.investech.service;

import com.investech.entity.PerformanceEvaluation;
import com.investech.mapper.PerformanceEvaluationMapper;
import com.investech.service.impl.PerformanceEvaluationServiceImpl;
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
class PerformanceEvaluationServiceImplTest {

    @Mock
    private PerformanceEvaluationMapper performanceEvaluationMapper;

    @InjectMocks
    private PerformanceEvaluationServiceImpl performanceEvaluationService;

    private PerformanceEvaluation testEvaluation;

    @BeforeEach
    void setUp() {
        testEvaluation = new PerformanceEvaluation();
        testEvaluation.setId(1L);
        testEvaluation.setEvaluationCode("EVAL001");
        testEvaluation.setEvaluationName("2025Q2策略评估");
        testEvaluation.setStrategyCode("STRAT001");
        testEvaluation.setPortfolioCode("PORTFOLIO001");
        testEvaluation.setEvaluationDate(LocalDateTime.now());
        testEvaluation.setTotalReturn(new BigDecimal("15.50"));
        testEvaluation.setAnnualReturn(new BigDecimal("20.75"));
        testEvaluation.setBenchmarkReturn(new BigDecimal("12.25"));
        testEvaluation.setExcessReturn(new BigDecimal("3.25"));
        testEvaluation.setSharpeRatio(new BigDecimal("1.85"));
        testEvaluation.setSortinoRatio(new BigDecimal("2.10"));
        testEvaluation.setCalmarRatio(new BigDecimal("3.50"));
        testEvaluation.setInformationRatio(new BigDecimal("1.20"));
        testEvaluation.setMaxDrawdown(new BigDecimal("10.25"));
        testEvaluation.setVolatility(new BigDecimal("12.50"));
        testEvaluation.setBeta(new BigDecimal("1.05"));
        testEvaluation.setAlpha(new BigDecimal("2.75"));
        testEvaluation.setTreynorRatio(new BigDecimal("15.20"));
        testEvaluation.setJensenAlpha(new BigDecimal("1.50"));
        testEvaluation.setRiskAdjustedReturn("{\"夏普比率\": \"优秀\", \"索提诺比率\": \"良好\"}");
        testEvaluation.setPerformanceRanking("前10%");
        testEvaluation.setEvaluationResult("优秀");
        testEvaluation.setEvaluationReport("{\"收益分析\": \"高于基准3.25%\", \"风险分析\": \"低于平均水平\"}");
        testEvaluation.setEvaluatorId(2001L);
        testEvaluation.setCreateTime(LocalDateTime.now());
        testEvaluation.setUpdateTime(LocalDateTime.now());
    }

    @Test
    void findAll_shouldCallMapperOnce() {
        List<PerformanceEvaluation> evaluations = Arrays.asList(testEvaluation);
        when(performanceEvaluationMapper.findAll()).thenReturn(evaluations);

        List<PerformanceEvaluation> result = performanceEvaluationService.findAll();

        verify(performanceEvaluationMapper, times(1)).findAll();
        assertNotNull(result);
        assertEquals(1, result.size());
    }

    @Test
    void findAll_whenNoData_returnsEmptyList() {
        when(performanceEvaluationMapper.findAll()).thenReturn(List.of());

        List<PerformanceEvaluation> result = performanceEvaluationService.findAll();

        verify(performanceEvaluationMapper, times(1)).findAll();
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    void findByEvaluationCode_shouldCallMapperOnce() {
        when(performanceEvaluationMapper.findByEvaluationCode("EVAL001")).thenReturn(testEvaluation);

        PerformanceEvaluation result = performanceEvaluationService.findByEvaluationCode("EVAL001");

        verify(performanceEvaluationMapper, times(1)).findByEvaluationCode("EVAL001");
        assertNotNull(result);
        assertEquals("2025Q2策略评估", result.getEvaluationName());
    }

    @Test
    void findByEvaluationCode_whenNotFound_returnsNull() {
        when(performanceEvaluationMapper.findByEvaluationCode("NON_EXISTENT")).thenReturn(null);

        PerformanceEvaluation result = performanceEvaluationService.findByEvaluationCode("NON_EXISTENT");

        verify(performanceEvaluationMapper, times(1)).findByEvaluationCode("NON_EXISTENT");
        assertNull(result);
    }

    @Test
    void findById_shouldCallMapperOnce() {
        when(performanceEvaluationMapper.findById(1L)).thenReturn(testEvaluation);

        PerformanceEvaluation result = performanceEvaluationService.findById(1L);

        verify(performanceEvaluationMapper, times(1)).findById(1L);
        assertNotNull(result);
        assertEquals("EVAL001", result.getEvaluationCode());
    }

    @Test
    void findById_whenNotFound_returnsNull() {
        when(performanceEvaluationMapper.findById(999L)).thenReturn(null);

        PerformanceEvaluation result = performanceEvaluationService.findById(999L);

        verify(performanceEvaluationMapper, times(1)).findById(999L);
        assertNull(result);
    }

    @Test
    void findByStrategyCode_shouldCallMapperOnce() {
        List<PerformanceEvaluation> evaluations = Arrays.asList(testEvaluation);
        when(performanceEvaluationMapper.findByStrategyCode("STRAT001")).thenReturn(evaluations);

        List<PerformanceEvaluation> result = performanceEvaluationService.findByStrategyCode("STRAT001");

        verify(performanceEvaluationMapper, times(1)).findByStrategyCode("STRAT001");
        assertNotNull(result);
        assertEquals(1, result.size());
    }

    @Test
    void findByStrategyCode_whenNoMatchingData_returnsEmptyList() {
        when(performanceEvaluationMapper.findByStrategyCode("UNKNOWN_STRATEGY")).thenReturn(List.of());

        List<PerformanceEvaluation> result = performanceEvaluationService.findByStrategyCode("UNKNOWN_STRATEGY");

        verify(performanceEvaluationMapper, times(1)).findByStrategyCode("UNKNOWN_STRATEGY");
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    void findByPortfolioCode_shouldCallMapperOnce() {
        List<PerformanceEvaluation> evaluations = Arrays.asList(testEvaluation);
        when(performanceEvaluationMapper.findByPortfolioCode("PORTFOLIO001")).thenReturn(evaluations);

        List<PerformanceEvaluation> result = performanceEvaluationService.findByPortfolioCode("PORTFOLIO001");

        verify(performanceEvaluationMapper, times(1)).findByPortfolioCode("PORTFOLIO001");
        assertNotNull(result);
        assertEquals(1, result.size());
    }

    @Test
    void findByPortfolioCode_whenNoMatchingData_returnsEmptyList() {
        when(performanceEvaluationMapper.findByPortfolioCode("UNKNOWN_PORTFOLIO")).thenReturn(List.of());

        List<PerformanceEvaluation> result = performanceEvaluationService.findByPortfolioCode("UNKNOWN_PORTFOLIO");

        verify(performanceEvaluationMapper, times(1)).findByPortfolioCode("UNKNOWN_PORTFOLIO");
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    void findByEvaluatorId_shouldCallMapperOnce() {
        List<PerformanceEvaluation> evaluations = Arrays.asList(testEvaluation);
        when(performanceEvaluationMapper.findByEvaluatorId(2001L)).thenReturn(evaluations);

        List<PerformanceEvaluation> result = performanceEvaluationService.findByEvaluatorId(2001L);

        verify(performanceEvaluationMapper, times(1)).findByEvaluatorId(2001L);
        assertNotNull(result);
        assertEquals(1, result.size());
    }

    @Test
    void findByEvaluatorId_whenNoMatchingData_returnsEmptyList() {
        when(performanceEvaluationMapper.findByEvaluatorId(9999L)).thenReturn(List.of());

        List<PerformanceEvaluation> result = performanceEvaluationService.findByEvaluatorId(9999L);

        verify(performanceEvaluationMapper, times(1)).findByEvaluatorId(9999L);
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    void findByEvaluationResult_shouldCallMapperOnce() {
        List<PerformanceEvaluation> evaluations = Arrays.asList(testEvaluation);
        when(performanceEvaluationMapper.findByEvaluationResult("优秀")).thenReturn(evaluations);

        List<PerformanceEvaluation> result = performanceEvaluationService.findByEvaluationResult("优秀");

        verify(performanceEvaluationMapper, times(1)).findByEvaluationResult("优秀");
        assertNotNull(result);
        assertEquals(1, result.size());
    }

    @Test
    void findByEvaluationResult_whenNoMatchingData_returnsEmptyList() {
        when(performanceEvaluationMapper.findByEvaluationResult("不存在的结果")).thenReturn(List.of());

        List<PerformanceEvaluation> result = performanceEvaluationService.findByEvaluationResult("不存在的结果");

        verify(performanceEvaluationMapper, times(1)).findByEvaluationResult("不存在的结果");
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    void searchEvaluations_shouldCallMapperOnce() {
        List<PerformanceEvaluation> evaluations = Arrays.asList(testEvaluation);
        when(performanceEvaluationMapper.findByCondition(
                "EVAL001", "2025Q2策略评估", "STRAT001", "PORTFOLIO001", "优秀", 2001L))
                .thenReturn(evaluations);

        List<PerformanceEvaluation> result = performanceEvaluationService.searchEvaluations(
                "EVAL001", "2025Q2策略评估", "STRAT001", "PORTFOLIO001", "优秀", 2001L);

        verify(performanceEvaluationMapper, times(1)).findByCondition(
                "EVAL001", "2025Q2策略评估", "STRAT001", "PORTFOLIO001", "优秀", 2001L);
        assertNotNull(result);
        assertEquals(1, result.size());
    }

    @Test
    void searchEvaluations_whenNoMatches_returnsEmptyList() {
        when(performanceEvaluationMapper.findByCondition(
                "WRONG_CODE", "不存在的名称", "错误策略", "无效组合", "未知结果", 0L))
                .thenReturn(List.of());

        List<PerformanceEvaluation> result = performanceEvaluationService.searchEvaluations(
                "WRONG_CODE", "不存在的名称", "错误策略", "无效组合", "未知结果", 0L);

        verify(performanceEvaluationMapper, times(1)).findByCondition(
                "WRONG_CODE", "不存在的名称", "错误策略", "无效组合", "未知结果", 0L);
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    void createEvaluation_shouldCallInsertOnce() {
        LocalDateTime createTime = LocalDateTime.now().minusDays(1);
        testEvaluation.setCreateTime(createTime);

        when(performanceEvaluationMapper.insert(testEvaluation)).thenReturn(1);

        boolean result = performanceEvaluationService.createEvaluation(testEvaluation);

        verify(performanceEvaluationMapper, times(1)).insert(testEvaluation);
        assertTrue(result);
        assertEquals(createTime, testEvaluation.getCreateTime());
        assertTrue(testEvaluation.getUpdateTime().isAfter(createTime));
    }

    @Test
    void createEvaluation_whenInsertFails_returnsFalse() {
        when(performanceEvaluationMapper.insert(testEvaluation)).thenReturn(0);

        boolean result = performanceEvaluationService.createEvaluation(testEvaluation);

        verify(performanceEvaluationMapper, times(1)).insert(testEvaluation);
        assertFalse(result);
    }

    @Test
    void updateEvaluation_shouldCallUpdateOnce() {
        LocalDateTime createTime = LocalDateTime.now().minusDays(1);
        testEvaluation.setCreateTime(createTime);

        when(performanceEvaluationMapper.update(testEvaluation)).thenReturn(1);

        boolean result = performanceEvaluationService.updateEvaluation(testEvaluation);

        verify(performanceEvaluationMapper, times(1)).update(testEvaluation);
        assertTrue(result);
        assertEquals(createTime, testEvaluation.getCreateTime());
        assertTrue(testEvaluation.getUpdateTime().isAfter(createTime));
    }

    @Test
    void updateEvaluation_whenUpdateFails_returnsFalse() {
        when(performanceEvaluationMapper.update(testEvaluation)).thenReturn(0);

        boolean result = performanceEvaluationService.updateEvaluation(testEvaluation);

        verify(performanceEvaluationMapper, times(1)).update(testEvaluation);
        assertFalse(result);
    }

    @Test
    void deleteEvaluation_shouldCallDeleteOnce() {
        when(performanceEvaluationMapper.deleteById(1L)).thenReturn(1);

        boolean result = performanceEvaluationService.deleteEvaluation(1L);

        verify(performanceEvaluationMapper, times(1)).deleteById(1L);
        assertTrue(result);
    }

    @Test
    void deleteEvaluation_whenDeleteFails_returnsFalse() {
        when(performanceEvaluationMapper.deleteById(999L)).thenReturn(0);

        boolean result = performanceEvaluationService.deleteEvaluation(999L);

        verify(performanceEvaluationMapper, times(1)).deleteById(999L);
        assertFalse(result);
    }
}
