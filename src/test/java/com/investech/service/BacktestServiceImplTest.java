package com.investech.service;

import com.investech.entity.Backtest;
import com.investech.mapper.BacktestMapper;
import com.investech.service.impl.BacktestServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BacktestServiceImplTest {
    
    @Mock
    private BacktestMapper backtestMapper;
    
    @InjectMocks
    private BacktestServiceImpl backtestService;
    
    private Backtest testBacktest;
    
    @BeforeEach
    void setUp() {
        testBacktest = new Backtest();
        testBacktest.setId(1L);
        testBacktest.setBacktestCode("BTC001");
        testBacktest.setStatus("ACTIVE");
    }

    @Test
    void findAll_shouldCallMapperOnce() {
        List<Backtest> backtests = Arrays.asList(testBacktest);
        when(backtestMapper.findAll()).thenReturn(backtests);
        
        List<Backtest> result = backtestService.findAll();
        
        verify(backtestMapper, times(1)).findAll();
        assertNotNull(result);
        assertEquals(1, result.size());
    }

    @Test
    void findById_shouldReturnNullWhenMapperReturnsNull() {
        when(backtestMapper.findById(999L)).thenReturn(null);
        
        Backtest result = backtestService.findById(999L);
        
        verify(backtestMapper, times(1)).findById(999L);
        assertNull(result);
    }

    @Test
    void createBacktest_shouldReturnFalseWhenInsertFails() {
        when(backtestMapper.insert(testBacktest)).thenReturn(0);
        
        boolean result = backtestService.createBacktest(testBacktest);
        
        verify(backtestMapper, times(1)).insert(testBacktest);
        assertFalse(result);
    }

    @Test
    void updateBacktest_shouldReturnFalseWhenUpdateFails() {
        when(backtestMapper.update(testBacktest)).thenReturn(0);
        
        boolean result = backtestService.updateBacktest(testBacktest);
        
        verify(backtestMapper, times(1)).update(testBacktest);
        assertFalse(result);
    }

    @Test
    void deleteBacktest_shouldReturnFalseWhenDeleteFails() {
        when(backtestMapper.deleteById(999L)).thenReturn(0);
        
        boolean result = backtestService.deleteBacktest(999L);
        
        verify(backtestMapper, times(1)).deleteById(999L);
        assertFalse(result);
    }

    @Test
    void findByBacktestCode_shouldReturnNullWhenNotFound() {
        when(backtestMapper.findByBacktestCode("INVALID")).thenReturn(null);
        
        Backtest result = backtestService.findByBacktestCode("INVALID");
        
        verify(backtestMapper, times(1)).findByBacktestCode("INVALID");
        assertNull(result);
    }

    @Test
    void findByStrategyCode_shouldReturnEmptyListWhenNoResults() {
        when(backtestMapper.findByStrategyCode("INVALID")).thenReturn(Collections.emptyList());
        
        List<Backtest> result = backtestService.findByStrategyCode("INVALID");
        
        verify(backtestMapper, times(1)).findByStrategyCode("INVALID");
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    void findByCreatorId_shouldReturnEmptyListWhenNoResults() {
        when(backtestMapper.findByCreatorId(999L)).thenReturn(Collections.emptyList());
        
        List<Backtest> result = backtestService.findByCreatorId(999L);
        
        verify(backtestMapper, times(1)).findByCreatorId(999L);
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    void findByStatus_shouldReturnEmptyListWhenNoResults() {
        when(backtestMapper.findByStatus("INVALID")).thenReturn(Collections.emptyList());
        
        List<Backtest> result = backtestService.findByStatus("INVALID");
        
        verify(backtestMapper, times(1)).findByStatus("INVALID");
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    void searchBacktests_shouldReturnEmptyListWhenNoResults() {
        when(backtestMapper.findByCondition("INVALID", null, null, null, null)).thenReturn(Collections.emptyList());
        
        List<Backtest> result = backtestService.searchBacktests("INVALID", null, null, null, null);
        
        verify(backtestMapper, times(1)).findByCondition("INVALID", null, null, null, null);
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }
}