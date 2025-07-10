package com.investech.service;

import com.investech.entity.RiskReport;
import com.investech.mapper.RiskReportMapper;
import com.investech.service.impl.RiskReportServiceImpl;
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
class RiskReportServiceImplTest {

    @Mock
    private RiskReportMapper riskReportMapper;

    @InjectMocks
    private RiskReportServiceImpl riskReportService;

    private RiskReport testReport;

    @BeforeEach
    void setUp() {
        testReport = new RiskReport();
        testReport.setId(1L);
        testReport.setReportName("2025年第二季度风险报告");
        testReport.setReportType("季报");
        testReport.setReportPeriod("2025-04 至 2025-06");
        testReport.setRiskSummary("本季度市场波动较大，需关注流动性风险");
        testReport.setMarketRisk("股票市场波动率增加，建议降低仓位");
        testReport.setCreditRisk("债券市场信用利差扩大");
        testReport.setLiquidityRisk("部分资产变现能力减弱");
        testReport.setOperationalRisk("系统升级导致交易延迟");
        testReport.setRiskMetrics("{\"波动率\": \"15.5%\", \"VaR\": \"2.5%\"}");
        testReport.setRecommendations("调整组合配置，增加现金持有比例");
        testReport.setAuthor("张伟");
        testReport.setReviewer("李娜");
        testReport.setReportDate(LocalDateTime.now());
        testReport.setStatus("已发布");
        testReport.setCreateTime(LocalDateTime.now());
        testReport.setUpdateTime(LocalDateTime.now());
        testReport.setStatusCode(1);
    }

    @Test
    void getAllReports_shouldCallMapperOnce() {
        List<RiskReport> reports = Arrays.asList(testReport);
        when(riskReportMapper.selectAll()).thenReturn(reports);

        List<RiskReport> result = riskReportService.getAllReports();

        verify(riskReportMapper, times(1)).selectAll();
        assertNotNull(result);
        assertEquals(1, result.size());
    }

    @Test
    void getAllReports_whenNoData_returnsEmptyList() {
        when(riskReportMapper.selectAll()).thenReturn(List.of());

        List<RiskReport> result = riskReportService.getAllReports();

        verify(riskReportMapper, times(1)).selectAll();
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    void getReportById_shouldCallMapperOnce() {
        when(riskReportMapper.selectById(1L)).thenReturn(testReport);

        RiskReport result = riskReportService.getReportById(1L);

        verify(riskReportMapper, times(1)).selectById(1L);
        assertNotNull(result);
        assertEquals("2025年第二季度风险报告", result.getReportName());
    }

    @Test
    void getReportById_whenIdNotFound_returnsNull() {
        when(riskReportMapper.selectById(999L)).thenReturn(null);

        RiskReport result = riskReportService.getReportById(999L);

        verify(riskReportMapper, times(1)).selectById(999L);
        assertNull(result);
    }

    @Test
    void getReportsByCondition_shouldCallMapperOnce() {
        List<RiskReport> reports = Arrays.asList(testReport);
        when(riskReportMapper.selectByCondition(testReport)).thenReturn(reports);

        List<RiskReport> result = riskReportService.getReportsByCondition(testReport);

        verify(riskReportMapper, times(1)).selectByCondition(testReport);
        assertNotNull(result);
        assertEquals(1, result.size());
    }

    @Test
    void getReportsByCondition_whenNoMatch_returnsEmptyList() {
        when(riskReportMapper.selectByCondition(any(RiskReport.class))).thenReturn(List.of());

        RiskReport condition = new RiskReport();
        condition.setReportType("年报");

        List<RiskReport> result = riskReportService.getReportsByCondition(condition);

        verify(riskReportMapper, times(1)).selectByCondition(condition);
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    void addReport_shouldCallInsertOnce() {
        LocalDateTime createTime = LocalDateTime.now().minusDays(1);
        testReport.setCreateTime(createTime);

        when(riskReportMapper.insert(testReport)).thenReturn(1);

        boolean result = riskReportService.addReport(testReport);

        verify(riskReportMapper, times(1)).insert(testReport);
        assertTrue(result);
        assertEquals(createTime, testReport.getCreateTime());
        assertTrue(testReport.getUpdateTime().isAfter(createTime));
    }

    @Test
    void addReport_whenInsertFails_returnsFalse() {
        LocalDateTime createTime = LocalDateTime.now().minusDays(1);
        testReport.setCreateTime(createTime);

        when(riskReportMapper.insert(testReport)).thenReturn(0);

        boolean result = riskReportService.addReport(testReport);

        verify(riskReportMapper, times(1)).insert(testReport);
        assertFalse(result);
        assertEquals(createTime, testReport.getCreateTime());
        assertTrue(testReport.getUpdateTime().isAfter(createTime));
    }

    @Test
    void updateReport_shouldCallUpdateOnce() {
        LocalDateTime createTime = LocalDateTime.now().minusDays(1);
        testReport.setCreateTime(createTime);

        when(riskReportMapper.update(testReport)).thenReturn(1);

        boolean result = riskReportService.updateReport(testReport);

        verify(riskReportMapper, times(1)).update(testReport);
        assertTrue(result);
        assertEquals(createTime, testReport.getCreateTime());
        assertTrue(testReport.getUpdateTime().isAfter(createTime));
    }

    @Test
    void updateReport_whenUpdateFails_returnsFalse() {
        LocalDateTime createTime = LocalDateTime.now().minusDays(1);
        testReport.setCreateTime(createTime);

        when(riskReportMapper.update(testReport)).thenReturn(0);

        boolean result = riskReportService.updateReport(testReport);

        verify(riskReportMapper, times(1)).update(testReport);
        assertFalse(result);
        assertEquals(createTime, testReport.getCreateTime());
        assertTrue(testReport.getUpdateTime().isAfter(createTime));
    }

    @Test
    void deleteReport_shouldCallDeleteOnce() {
        when(riskReportMapper.deleteById(1L)).thenReturn(1);

        boolean result = riskReportService.deleteReport(1L);

        verify(riskReportMapper, times(1)).deleteById(1L);
        assertTrue(result);
    }

    @Test
    void deleteReport_whenIdNotFound_returnsFalse() {
        when(riskReportMapper.deleteById(999L)).thenReturn(0);

        boolean result = riskReportService.deleteReport(999L);

        verify(riskReportMapper, times(1)).deleteById(999L);
        assertFalse(result);
    }

    @Test
    void updateReportStatus_shouldCallUpdateStatusOnce() {
        when(riskReportMapper.updateStatus(1L, 0)).thenReturn(1);

        boolean result = riskReportService.updateReportStatus(1L, 0);

        verify(riskReportMapper, times(1)).updateStatus(1L, 0);
        assertTrue(result);
    }

    @Test
    void updateReportStatus_whenInvalidId_returnsFalse() {
        when(riskReportMapper.updateStatus(999L, 0)).thenReturn(0);

        boolean result = riskReportService.updateReportStatus(999L, 0);

        verify(riskReportMapper, times(1)).updateStatus(999L, 0);
        assertFalse(result);
    }
}
