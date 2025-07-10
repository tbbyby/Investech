package com.investech.service;

import com.investech.entity.FundPortfolio;
import com.investech.mapper.FundPortfolioMapper;
import com.investech.service.impl.FundPortfolioServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class FundPortfolioServiceImplTest {

    @Mock
    private FundPortfolioMapper fundPortfolioMapper;

    @InjectMocks
    private FundPortfolioServiceImpl fundPortfolioService;

    private FundPortfolio testPortfolio;

    @BeforeEach
    void setUp() {
        testPortfolio = new FundPortfolio();
        testPortfolio.setId(1L);
        testPortfolio.setPortfolioName("成长型组合");
        testPortfolio.setPortfolioDesc("高风险高收益的成长型基金组合");
        testPortfolio.setUserId(1001L);
        testPortfolio.setFundCodes("FUND001,FUND002,FUND003");
        testPortfolio.setWeights("{\"FUND001\": 0.5, \"FUND002\": 0.3, \"FUND003\": 0.2}");
        testPortfolio.setStatus(1);
        testPortfolio.setCreateTime(LocalDateTime.now());
        testPortfolio.setUpdateTime(LocalDateTime.now());
    }

    @Test
    void findAll_shouldCallMapperOnce() {
        List<FundPortfolio> portfolios = Arrays.asList(testPortfolio);
        when(fundPortfolioMapper.findAll()).thenReturn(portfolios);

        List<FundPortfolio> result = fundPortfolioService.findAll();

        verify(fundPortfolioMapper, times(1)).findAll();
        assertNotNull(result);
        assertEquals(1, result.size());
    }
    
    @Test
    void findAll_shouldHandleEmptyList() {
        when(fundPortfolioMapper.findAll()).thenReturn(Collections.emptyList());

        List<FundPortfolio> result = fundPortfolioService.findAll();

        verify(fundPortfolioMapper, times(1)).findAll();
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    void findById_shouldCallMapperOnce() {
        when(fundPortfolioMapper.findById(1L)).thenReturn(testPortfolio);

        FundPortfolio result = fundPortfolioService.findById(1L);

        verify(fundPortfolioMapper, times(1)).findById(1L);
        assertNotNull(result);
        assertEquals("成长型组合", result.getPortfolioName());
    }
    
    @Test
    void findById_shouldReturnNullWhenNotFound() {
        when(fundPortfolioMapper.findById(1L)).thenReturn(null);

        FundPortfolio result = fundPortfolioService.findById(1L);

        verify(fundPortfolioMapper, times(1)).findById(1L);
        assertNull(result);
    }

    @Test
    void findByUserId_shouldCallMapperOnce() {
        List<FundPortfolio> portfolios = Arrays.asList(testPortfolio);
        when(fundPortfolioMapper.findByUserId(1001L)).thenReturn(portfolios);

        List<FundPortfolio> result = fundPortfolioService.findByUserId(1001L);

        verify(fundPortfolioMapper, times(1)).findByUserId(1001L);
        assertNotNull(result);
        assertEquals(1, result.size());
    }
    
    @Test
    void findByUserId_shouldHandleEmptyList() {
        when(fundPortfolioMapper.findByUserId(1001L)).thenReturn(Collections.emptyList());

        List<FundPortfolio> result = fundPortfolioService.findByUserId(1001L);

        verify(fundPortfolioMapper, times(1)).findByUserId(1001L);
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    void createPortfolio_shouldCallInsertOnce() {
        when(fundPortfolioMapper.insert(testPortfolio)).thenReturn(1);

        boolean result = fundPortfolioService.createPortfolio(testPortfolio);

        verify(fundPortfolioMapper, times(1)).insert(testPortfolio);
        assertTrue(result);
        assertEquals(1, testPortfolio.getStatus().intValue());
        assertNotNull(testPortfolio.getCreateTime());
        assertNotNull(testPortfolio.getUpdateTime());
    }
    
    @Test
    void createPortfolio_shouldReturnFalseWhenInsertFails() {
        when(fundPortfolioMapper.insert(testPortfolio)).thenReturn(0);

        boolean result = fundPortfolioService.createPortfolio(testPortfolio);

        verify(fundPortfolioMapper, times(1)).insert(testPortfolio);
        assertFalse(result);
    }

    @Test
    void updatePortfolio_shouldCallUpdateOnce() {
        LocalDateTime createTime = LocalDateTime.now().minusDays(1);
        testPortfolio.setCreateTime(createTime);

        when(fundPortfolioMapper.update(testPortfolio)).thenReturn(1);

        boolean result = fundPortfolioService.updatePortfolio(testPortfolio);

        verify(fundPortfolioMapper, times(1)).update(testPortfolio);
        assertTrue(result);
        assertEquals(createTime, testPortfolio.getCreateTime());
        assertTrue(testPortfolio.getUpdateTime().isAfter(createTime));
    }
    
    @Test
    void updatePortfolio_shouldReturnFalseWhenUpdateFails() {
        LocalDateTime createTime = LocalDateTime.now().minusDays(1);
        testPortfolio.setCreateTime(createTime);

        when(fundPortfolioMapper.update(testPortfolio)).thenReturn(0);

        boolean result = fundPortfolioService.updatePortfolio(testPortfolio);

        verify(fundPortfolioMapper, times(1)).update(testPortfolio);
        assertFalse(result);
        assertEquals(createTime, testPortfolio.getCreateTime());
        assertTrue(testPortfolio.getUpdateTime().isAfter(createTime));
    }

    @Test
    void deletePortfolio_shouldCallDeleteOnce() {
        when(fundPortfolioMapper.deleteById(1L)).thenReturn(1);

        boolean result = fundPortfolioService.deletePortfolio(1L);

        verify(fundPortfolioMapper, times(1)).deleteById(1L);
        assertTrue(result);
    }
    
    @Test
    void deletePortfolio_shouldReturnFalseWhenDeleteFails() {
        when(fundPortfolioMapper.deleteById(1L)).thenReturn(0);

        boolean result = fundPortfolioService.deletePortfolio(1L);

        verify(fundPortfolioMapper, times(1)).deleteById(1L);
        assertFalse(result);
    }
}