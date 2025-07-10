package com.investech.service;

import com.investech.entity.InvestmentAdvice;
import com.investech.mapper.InvestmentAdviceMapper;
import com.investech.service.impl.InvestmentAdviceServiceImpl;
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
class InvestmentAdviceServiceImplTest {

    @Mock
    private InvestmentAdviceMapper investmentAdviceMapper;

    @InjectMocks
    private InvestmentAdviceServiceImpl investmentAdviceService;

    private InvestmentAdvice testAdvice;

    @BeforeEach
    void setUp() {
        testAdvice = new InvestmentAdvice();
        testAdvice.setId(1L);
        testAdvice.setClientId(1001L);
        testAdvice.setAdviceType("资产配置");
        testAdvice.setTitle("2025年第二季度投资建议");
        testAdvice.setContent("建议增加科技行业基金配置比例至30%");
        testAdvice.setRiskLevel("中");
        testAdvice.setExpectedReturn(8.5);
        testAdvice.setMaxDrawdown(15.0);
        testAdvice.setFundRecommendations("[\"FUND001\", \"FUND002\"]");
        testAdvice.setAssetAllocation("{\"股票\": 60, \"债券\": 30, \"现金\": 10}");
        testAdvice.setStatus(1);
        testAdvice.setCreateTime(LocalDateTime.now());
        testAdvice.setUpdateTime(LocalDateTime.now());
    }

    @Test
    void getAllAdvices_shouldCallMapperOnce() {
        List<InvestmentAdvice> advices = Arrays.asList(testAdvice);
        when(investmentAdviceMapper.selectAll()).thenReturn(advices);

        List<InvestmentAdvice> result = investmentAdviceService.getAllAdvices();

        verify(investmentAdviceMapper, times(1)).selectAll();
        assertNotNull(result);
        assertEquals(1, result.size());
    }

    @Test
    void getAllAdvices_whenMapperReturnsEmptyList() {
        when(investmentAdviceMapper.selectAll()).thenReturn(List.of());

        List<InvestmentAdvice> result = investmentAdviceService.getAllAdvices();

        verify(investmentAdviceMapper, times(1)).selectAll();
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    void getAdviceById_shouldCallMapperOnce() {
        when(investmentAdviceMapper.selectById(1L)).thenReturn(testAdvice);

        InvestmentAdvice result = investmentAdviceService.getAdviceById(1L);

        verify(investmentAdviceMapper, times(1)).selectById(1L);
        assertNotNull(result);
        assertEquals("2025年第二季度投资建议", result.getTitle());
    }

    @Test
    void getAdviceById_whenAdviceNotFound() {
        when(investmentAdviceMapper.selectById(999L)).thenReturn(null);

        InvestmentAdvice result = investmentAdviceService.getAdviceById(999L);

        verify(investmentAdviceMapper, times(1)).selectById(999L);
        assertNull(result);
    }

    @Test
    void getAdvicesByClientId_shouldCallMapperOnce() {
        List<InvestmentAdvice> advices = Arrays.asList(testAdvice);
        when(investmentAdviceMapper.selectByClientId(1001L)).thenReturn(advices);

        List<InvestmentAdvice> result = investmentAdviceService.getAdvicesByClientId(1001L);

        verify(investmentAdviceMapper, times(1)).selectByClientId(1001L);
        assertNotNull(result);
        assertEquals(1, result.size());
    }

    @Test
    void getAdvicesByClientId_whenNoMatchingClient() {
        when(investmentAdviceMapper.selectByClientId(9999L)).thenReturn(List.of());

        List<InvestmentAdvice> result = investmentAdviceService.getAdvicesByClientId(9999L);

        verify(investmentAdviceMapper, times(1)).selectByClientId(9999L);
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    void getAdvicesByCondition_shouldCallMapperOnce() {
        List<InvestmentAdvice> advices = Arrays.asList(testAdvice);
        when(investmentAdviceMapper.selectByCondition(testAdvice)).thenReturn(advices);

        List<InvestmentAdvice> result = investmentAdviceService.getAdvicesByCondition(testAdvice);

        verify(investmentAdviceMapper, times(1)).selectByCondition(testAdvice);
        assertNotNull(result);
        assertEquals(1, result.size());
    }

    @Test
    void getAdvicesByCondition_whenNoMatch() {
        when(investmentAdviceMapper.selectByCondition(any(InvestmentAdvice.class))).thenReturn(List.of());

        List<InvestmentAdvice> result = investmentAdviceService.getAdvicesByCondition(new InvestmentAdvice());

        verify(investmentAdviceMapper, times(1)).selectByCondition(any(InvestmentAdvice.class));
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    void addAdvice_shouldCallInsertOnce() {
        when(investmentAdviceMapper.insert(testAdvice)).thenReturn(1);

        boolean result = investmentAdviceService.addAdvice(testAdvice);

        verify(investmentAdviceMapper, times(1)).insert(testAdvice);
        assertTrue(result);
    }

    @Test
    void addAdvice_whenInsertFails() {
        when(investmentAdviceMapper.insert(testAdvice)).thenReturn(0);

        boolean result = investmentAdviceService.addAdvice(testAdvice);

        verify(investmentAdviceMapper, times(1)).insert(testAdvice);
        assertFalse(result);
    }

    @Test
    void updateAdvice_shouldCallUpdateOnce() {
        LocalDateTime createTime = LocalDateTime.now().minusDays(1);
        testAdvice.setCreateTime(createTime);

        when(investmentAdviceMapper.update(testAdvice)).thenReturn(1);

        boolean result = investmentAdviceService.updateAdvice(testAdvice);

        verify(investmentAdviceMapper, times(1)).update(testAdvice);
        assertTrue(result);
        assertEquals(createTime, testAdvice.getCreateTime());
        assertTrue(testAdvice.getUpdateTime().isAfter(createTime));
    }

    @Test
    void updateAdvice_whenUpdateFails() {
        LocalDateTime createTime = LocalDateTime.now().minusDays(1);
        testAdvice.setCreateTime(createTime);

        when(investmentAdviceMapper.update(testAdvice)).thenReturn(0);

        boolean result = investmentAdviceService.updateAdvice(testAdvice);

        verify(investmentAdviceMapper, times(1)).update(testAdvice);
        assertFalse(result);
        assertEquals(createTime, testAdvice.getCreateTime());
    }

    @Test
    void deleteAdvice_shouldCallDeleteOnce() {
        when(investmentAdviceMapper.deleteById(1L)).thenReturn(1);

        boolean result = investmentAdviceService.deleteAdvice(1L);

        verify(investmentAdviceMapper, times(1)).deleteById(1L);
        assertTrue(result);
    }

    @Test
    void deleteAdvice_whenDeleteFails() {
        when(investmentAdviceMapper.deleteById(999L)).thenReturn(0);

        boolean result = investmentAdviceService.deleteAdvice(999L);

        verify(investmentAdviceMapper, times(1)).deleteById(999L);
        assertFalse(result);
    }

    @Test
    void updateAdviceStatus_shouldCallUpdateStatusOnce() {
        when(investmentAdviceMapper.updateStatus(1L, 2)).thenReturn(1);

        boolean result = investmentAdviceService.updateAdviceStatus(1L, 2);

        verify(investmentAdviceMapper, times(1)).updateStatus(1L, 2);
        assertTrue(result);
    }

    @Test
    void updateAdviceStatus_whenUpdateFails() {
        when(investmentAdviceMapper.updateStatus(999L, 2)).thenReturn(0);

        boolean result = investmentAdviceService.updateAdviceStatus(999L, 2);

        verify(investmentAdviceMapper, times(1)).updateStatus(999L, 2);
        assertFalse(result);
    }
}
