package com.investech.service;

import com.investech.entity.DerivedFactor;
import com.investech.mapper.DerivedFactorMapper;
import com.investech.service.impl.DerivedFactorServiceImpl;
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
class DerivedFactorServiceImplTest {

    @Mock
    private DerivedFactorMapper derivedFactorMapper;

    @InjectMocks
    private DerivedFactorServiceImpl derivedFactorService;

    private DerivedFactor testFactor;

    @BeforeEach
    void setUp() {
        testFactor = new DerivedFactor();
        testFactor.setId(1L);
        testFactor.setFactorCode("DF001");
        testFactor.setFactorName("动量因子");
        testFactor.setFactorDesc("计算股票价格动量");
        testFactor.setBaseFactors("[\"price_5d\", \"volume_20d\"]");
        testFactor.setWeights("{\"price_5d\": 0.6, \"volume_20d\": 0.4}");
        testFactor.setFormula("(price_5d * 0.6) + (volume_20d * 0.4)");
        testFactor.setCategory("技术指标");
        testFactor.setCreatorId(1001L);
        testFactor.setStatus(1);
        testFactor.setCreateTime(LocalDateTime.now());
        testFactor.setUpdateTime(LocalDateTime.now());
    }

    @Test
    void findAll_shouldCallMapperOnce() {
        List<DerivedFactor> factors = Arrays.asList(testFactor);
        when(derivedFactorMapper.findAll()).thenReturn(factors);

        List<DerivedFactor> result = derivedFactorService.findAll();

        verify(derivedFactorMapper, times(1)).findAll();
        assertNotNull(result);
        assertEquals(1, result.size());
    }

    @Test
    void findAll_whenNoData_returnsEmptyList() {
        when(derivedFactorMapper.findAll()).thenReturn(List.of());

        List<DerivedFactor> result = derivedFactorService.findAll();

        verify(derivedFactorMapper, times(1)).findAll();
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    void findByFactorCode_shouldCallMapperOnce() {
        when(derivedFactorMapper.findByFactorCode("DF001")).thenReturn(testFactor);

        DerivedFactor result = derivedFactorService.findByFactorCode("DF001");

        verify(derivedFactorMapper, times(1)).findByFactorCode("DF001");
        assertNotNull(result);
        assertEquals("动量因子", result.getFactorName());
    }

    @Test
    void findById_shouldCallMapperOnce() {
        when(derivedFactorMapper.findById(1L)).thenReturn(testFactor);

        DerivedFactor result = derivedFactorService.findById(1L);

        verify(derivedFactorMapper, times(1)).findById(1L);
        assertNotNull(result);
        assertEquals("DF001", result.getFactorCode());
    }

    @Test
    void findByCreatorId_shouldCallMapperOnce() {
        List<DerivedFactor> factors = Arrays.asList(testFactor);
        when(derivedFactorMapper.findByCreatorId(1001L)).thenReturn(factors);

        List<DerivedFactor> result = derivedFactorService.findByCreatorId(1001L);

        verify(derivedFactorMapper, times(1)).findByCreatorId(1001L);
        assertNotNull(result);
        assertEquals(1, result.size());
    }

    @Test
    void findByCategory_shouldCallMapperOnce() {
        List<DerivedFactor> factors = Arrays.asList(testFactor);
        when(derivedFactorMapper.findByCategory("技术指标")).thenReturn(factors);

        List<DerivedFactor> result = derivedFactorService.findByCategory("技术指标");

        verify(derivedFactorMapper, times(1)).findByCategory("技术指标");
        assertNotNull(result);
        assertEquals(1, result.size());
    }

    @Test
    void searchFactors_shouldCallMapperOnce() {
        List<DerivedFactor> factors = Arrays.asList(testFactor);
        when(derivedFactorMapper.findByCondition("DF001", "动量因子", "技术指标", 1001L)).thenReturn(factors);

        List<DerivedFactor> result = derivedFactorService.searchFactors("DF001", "动量因子", "技术指标", 1001L);

        verify(derivedFactorMapper, times(1)).findByCondition("DF001", "动量因子", "技术指标", 1001L);
        assertNotNull(result);
        assertEquals(1, result.size());
    }

    @Test
    void createFactor_shouldCallInsertOnce() {
        when(derivedFactorMapper.insert(testFactor)).thenReturn(1);

        boolean result = derivedFactorService.createFactor(testFactor);

        verify(derivedFactorMapper, times(1)).insert(testFactor);
        assertTrue(result);
        assertEquals(1, testFactor.getStatus().intValue());
    }

    @Test
    void createFactor_whenInsertFails_returnsFalse() {
        when(derivedFactorMapper.insert(testFactor)).thenReturn(0);

        boolean result = derivedFactorService.createFactor(testFactor);

        verify(derivedFactorMapper, times(1)).insert(testFactor);
        assertFalse(result);
    }

    @Test
    void updateFactor_shouldCallUpdateOnce() {
        when(derivedFactorMapper.update(testFactor)).thenReturn(1);

        boolean result = derivedFactorService.updateFactor(testFactor);

        verify(derivedFactorMapper, times(1)).update(testFactor);
        assertTrue(result);
    }

    @Test
    void updateFactor_whenUpdateFails_returnsFalse() {
        when(derivedFactorMapper.update(testFactor)).thenReturn(0);

        boolean result = derivedFactorService.updateFactor(testFactor);

        verify(derivedFactorMapper, times(1)).update(testFactor);
        assertFalse(result);
    }

    @Test
    void deleteFactor_shouldCallDeleteOnce() {
        when(derivedFactorMapper.deleteById(1L)).thenReturn(1);

        boolean result = derivedFactorService.deleteFactor(1L);

        verify(derivedFactorMapper, times(1)).deleteById(1L);
        assertTrue(result);
    }

    @Test
    void deleteFactor_whenDeleteFails_returnsFalse() {
        when(derivedFactorMapper.deleteById(1L)).thenReturn(0);

        boolean result = derivedFactorService.deleteFactor(1L);

        verify(derivedFactorMapper, times(1)).deleteById(1L);
        assertFalse(result);
    }
}
