package com.investech.service;

import com.investech.entity.Factor;
import com.investech.mapper.FactorMapper;
import com.investech.service.impl.FactorServiceImpl;
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
class FactorServiceImplTest {

    @Mock
    private FactorMapper factorMapper;

    @InjectMocks
    private FactorServiceImpl factorService;

    private Factor testFactor;

    @BeforeEach
    void setUp() {
        testFactor = new Factor();
        testFactor.setId(1L);
        testFactor.setFactorCode("F001");
        testFactor.setFactorName("市盈率");
        testFactor.setFactorDesc("衡量股票估值水平的重要指标");
        testFactor.setFactorType("基础因子");
        testFactor.setDataSource("Wind资讯");
        testFactor.setCalculationMethod("股价/每股收益");
        testFactor.setUnit("倍");
        testFactor.setCategory("估值指标");
        testFactor.setParentCode("NULL");
        testFactor.setLevel(1);
        testFactor.setSortOrder(1);
        testFactor.setStatus(1);
        testFactor.setCreateTime(LocalDateTime.now());
        testFactor.setUpdateTime(LocalDateTime.now());
    }

    @Test
    void findAll_shouldCallMapperOnce() {
        List<Factor> factors = Arrays.asList(testFactor);
        when(factorMapper.findAll()).thenReturn(factors);

        List<Factor> result = factorService.findAll();

        verify(factorMapper, times(1)).findAll();
        assertNotNull(result);
        assertEquals(1, result.size());
    }
    
    @Test
    void findAll_shouldHandleEmptyList() {
        when(factorMapper.findAll()).thenReturn(Collections.emptyList());

        List<Factor> result = factorService.findAll();

        verify(factorMapper, times(1)).findAll();
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    void findByFactorCode_shouldCallMapperOnce() {
        when(factorMapper.findByFactorCode("F001")).thenReturn(testFactor);

        Factor result = factorService.findByFactorCode("F001");

        verify(factorMapper, times(1)).findByFactorCode("F001");
        assertNotNull(result);
        assertEquals("市盈率", result.getFactorName());
    }
    
    @Test
    void findByFactorCode_shouldReturnNullWhenNotFound() {
        when(factorMapper.findByFactorCode("F001")).thenReturn(null);

        Factor result = factorService.findByFactorCode("F001");

        verify(factorMapper, times(1)).findByFactorCode("F001");
        assertNull(result);
    }

    @Test
    void findById_shouldCallMapperOnce() {
        when(factorMapper.findById(1L)).thenReturn(testFactor);

        Factor result = factorService.findById(1L);

        verify(factorMapper, times(1)).findById(1L);
        assertNotNull(result);
        assertEquals("F001", result.getFactorCode());
    }
    
    @Test
    void findById_shouldReturnNullWhenNotFound() {
        when(factorMapper.findById(1L)).thenReturn(null);

        Factor result = factorService.findById(1L);

        verify(factorMapper, times(1)).findById(1L);
        assertNull(result);
    }

    @Test
    void findByType_shouldCallMapperOnce() {
        List<Factor> factors = Arrays.asList(testFactor);
        when(factorMapper.findByType("基础因子")).thenReturn(factors);

        List<Factor> result = factorService.findByType("基础因子");

        verify(factorMapper, times(1)).findByType("基础因子");
        assertNotNull(result);
        assertEquals(1, result.size());
    }
    
    @Test
    void findByType_shouldHandleEmptyList() {
        when(factorMapper.findByType("基础因子")).thenReturn(Collections.emptyList());

        List<Factor> result = factorService.findByType("基础因子");

        verify(factorMapper, times(1)).findByType("基础因子");
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    void findByCategory_shouldCallMapperOnce() {
        List<Factor> factors = Arrays.asList(testFactor);
        when(factorMapper.findByCategory("估值指标")).thenReturn(factors);

        List<Factor> result = factorService.findByCategory("估值指标");

        verify(factorMapper, times(1)).findByCategory("估值指标");
        assertNotNull(result);
        assertEquals(1, result.size());
    }
    
    @Test
    void findByCategory_shouldHandleEmptyList() {
        when(factorMapper.findByCategory("估值指标")).thenReturn(Collections.emptyList());

        List<Factor> result = factorService.findByCategory("估值指标");

        verify(factorMapper, times(1)).findByCategory("估值指标");
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    void findByParentCode_shouldCallMapperOnce() {
        List<Factor> factors = Arrays.asList(testFactor);
        when(factorMapper.findByParentCode("NULL")).thenReturn(factors);

        List<Factor> result = factorService.findByParentCode("NULL");

        verify(factorMapper, times(1)).findByParentCode("NULL");
        assertNotNull(result);
        assertEquals(1, result.size());
    }
    
    @Test
    void findByParentCode_shouldHandleEmptyList() {
        when(factorMapper.findByParentCode("NULL")).thenReturn(Collections.emptyList());

        List<Factor> result = factorService.findByParentCode("NULL");

        verify(factorMapper, times(1)).findByParentCode("NULL");
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    void searchFactors_shouldCallMapperOnce() {
        List<Factor> factors = Arrays.asList(testFactor);
        when(factorMapper.findByCondition("F001", "市盈率", "基础因子", "估值指标")).thenReturn(factors);

        List<Factor> result = factorService.searchFactors("F001", "市盈率", "基础因子", "估值指标");

        verify(factorMapper, times(1)).findByCondition("F001", "市盈率", "基础因子", "估值指标");
        assertNotNull(result);
        assertEquals(1, result.size());
    }
    
    @Test
    void searchFactors_shouldHandleEmptyList() {
        when(factorMapper.findByCondition("F001", "市盈率", "基础因子", "估值指标")).thenReturn(Collections.emptyList());

        List<Factor> result = factorService.searchFactors("F001", "市盈率", "基础因子", "估值指标");

        verify(factorMapper, times(1)).findByCondition("F001", "市盈率", "基础因子", "估值指标");
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    void addFactor_shouldCallInsertOnce() {
        when(factorMapper.insert(testFactor)).thenReturn(1);

        boolean result = factorService.addFactor(testFactor);

        verify(factorMapper, times(1)).insert(testFactor);
        assertTrue(result);
        assertEquals(1, testFactor.getStatus().intValue());
        assertNotNull(testFactor.getCreateTime());
        assertNotNull(testFactor.getUpdateTime());
    }
    
    @Test
    void addFactor_shouldReturnFalseWhenInsertFails() {
        when(factorMapper.insert(testFactor)).thenReturn(0);

        boolean result = factorService.addFactor(testFactor);

        verify(factorMapper, times(1)).insert(testFactor);
        assertFalse(result);
    }

    @Test
    void updateFactor_shouldCallUpdateOnce() {
        LocalDateTime createTime = LocalDateTime.now().minusDays(1);
        testFactor.setCreateTime(createTime);

        when(factorMapper.update(testFactor)).thenReturn(1);

        boolean result = factorService.updateFactor(testFactor);

        verify(factorMapper, times(1)).update(testFactor);
        assertTrue(result);
        assertEquals(createTime, testFactor.getCreateTime());
        assertTrue(testFactor.getUpdateTime().isAfter(createTime));
    }
    
    @Test
    void updateFactor_shouldReturnFalseWhenUpdateFails() {
        LocalDateTime createTime = LocalDateTime.now().minusDays(1);
        testFactor.setCreateTime(createTime);

        when(factorMapper.update(testFactor)).thenReturn(0);

        boolean result = factorService.updateFactor(testFactor);

        verify(factorMapper, times(1)).update(testFactor);
        assertFalse(result);
        assertEquals(createTime, testFactor.getCreateTime());
        assertTrue(testFactor.getUpdateTime().isAfter(createTime));
    }

    @Test
    void deleteFactor_shouldCallDeleteOnce() {
        when(factorMapper.deleteById(1L)).thenReturn(1);

        boolean result = factorService.deleteFactor(1L);

        verify(factorMapper, times(1)).deleteById(1L);
        assertTrue(result);
    }
    
    @Test
    void deleteFactor_shouldReturnFalseWhenDeleteFails() {
        when(factorMapper.deleteById(1L)).thenReturn(0);

        boolean result = factorService.deleteFactor(1L);

        verify(factorMapper, times(1)).deleteById(1L);
        assertFalse(result);
    }
}