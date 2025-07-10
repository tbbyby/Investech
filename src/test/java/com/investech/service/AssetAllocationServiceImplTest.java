package com.investech.service;

import com.investech.entity.AssetAllocation;
import com.investech.mapper.AssetAllocationMapper;
import com.investech.service.impl.AssetAllocationServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AssetAllocationServiceImplTest {

    @Mock
    private AssetAllocationMapper assetAllocationMapper;

    @InjectMocks
    private AssetAllocationServiceImpl assetAllocationService;

    private AssetAllocation testAllocation;

    @BeforeEach
    void setUp() {
        testAllocation = new AssetAllocation();
        testAllocation.setId(1L);
        testAllocation.setClientId(1001L);
        testAllocation.setStatus(1);
    }

    @Test
    void getAllAllocations_shouldCallMapperOnce() {
        List<AssetAllocation> allocations = Arrays.asList(testAllocation);
        when(assetAllocationMapper.selectAll()).thenReturn(allocations);

        List<AssetAllocation> result = assetAllocationService.getAllAllocations();

        verify(assetAllocationMapper, times(1)).selectAll();
        assertNotNull(result);
        assertEquals(1, result.size());
    }

    @Test
    void getAllAllocations_whenMapperReturnsEmptyList() {
        when(assetAllocationMapper.selectAll()).thenReturn(Arrays.asList());

        List<AssetAllocation> result = assetAllocationService.getAllAllocations();

        verify(assetAllocationMapper, times(1)).selectAll();
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    void getAllocationById_shouldCallSelectByIdOnce() {
        when(assetAllocationMapper.selectById(1L)).thenReturn(testAllocation);

        AssetAllocation result = assetAllocationService.getAllocationById(1L);

        verify(assetAllocationMapper, times(1)).selectById(1L);
        assertNotNull(result);
        assertEquals(1L, result.getId());
    }

    @Test
    void getAllocationById_whenAllocationNotFound() {
        when(assetAllocationMapper.selectById(1L)).thenReturn(null);

        AssetAllocation result = assetAllocationService.getAllocationById(1L);

        verify(assetAllocationMapper, times(1)).selectById(1L);
        assertNull(result);
    }

    @Test
    void getAllocationsByClientId_shouldCallSelectByClientIdOnce() {
        List<AssetAllocation> allocations = Arrays.asList(testAllocation);
        when(assetAllocationMapper.selectByClientId(1001L)).thenReturn(allocations);

        List<AssetAllocation> result = assetAllocationService.getAllocationsByClientId(1001L);

        verify(assetAllocationMapper, times(1)).selectByClientId(1001L);
        assertNotNull(result);
        assertEquals(1, result.size());
    }

    @Test
    void getAllocationsByClientId_whenNoAllocationsFound() {
        when(assetAllocationMapper.selectByClientId(999L)).thenReturn(Arrays.asList());

        List<AssetAllocation> result = assetAllocationService.getAllocationsByClientId(999L);

        verify(assetAllocationMapper, times(1)).selectByClientId(999L);
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    void getAllocationsByCondition_shouldCallSelectByConditionOnce() {
        List<AssetAllocation> allocations = Arrays.asList(testAllocation);
        when(assetAllocationMapper.selectByCondition(testAllocation)).thenReturn(allocations);

        List<AssetAllocation> result = assetAllocationService.getAllocationsByCondition(testAllocation);

        verify(assetAllocationMapper, times(1)).selectByCondition(testAllocation);
        assertNotNull(result);
        assertEquals(1, result.size());
    }

    @Test
    void getAllocationsByCondition_whenNoMatchingAllocations() {
        when(assetAllocationMapper.selectByCondition(testAllocation)).thenReturn(Arrays.asList());

        List<AssetAllocation> result = assetAllocationService.getAllocationsByCondition(testAllocation);

        verify(assetAllocationMapper, times(1)).selectByCondition(testAllocation);
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    void addAllocation_shouldCallInsertOnce() {
        when(assetAllocationMapper.insert(testAllocation)).thenReturn(1);

        boolean result = assetAllocationService.addAllocation(testAllocation);

        verify(assetAllocationMapper, times(1)).insert(testAllocation);
        assertTrue(result);
    }

    @Test
    void addAllocation_whenInsertFails() {
        when(assetAllocationMapper.insert(testAllocation)).thenReturn(0);

        boolean result = assetAllocationService.addAllocation(testAllocation);

        verify(assetAllocationMapper, times(1)).insert(testAllocation);
        assertFalse(result);
    }

    @Test
    void deleteAllocation_shouldCallDeleteOnce() {
        when(assetAllocationMapper.deleteById(1L)).thenReturn(1);

        boolean result = assetAllocationService.deleteAllocation(1L);

        verify(assetAllocationMapper, times(1)).deleteById(1L);
        assertTrue(result);
    }

    @Test
    void deleteAllocation_whenDeleteFails() {
        when(assetAllocationMapper.deleteById(1L)).thenReturn(0);

        boolean result = assetAllocationService.deleteAllocation(1L);

        verify(assetAllocationMapper, times(1)).deleteById(1L);
        assertFalse(result);
    }

    @Test
    void updateAllocationStatus_shouldCallUpdateOnce() {
        when(assetAllocationMapper.updateStatus(1L, 0)).thenReturn(1);

        boolean result = assetAllocationService.updateAllocationStatus(1L, 0);

        verify(assetAllocationMapper, times(1)).updateStatus(1L, 0);
        assertTrue(result);
    }

    @Test
    void updateAllocationStatus_whenUpdateFails() {
        when(assetAllocationMapper.updateStatus(1L, 0)).thenReturn(0);

        boolean result = assetAllocationService.updateAllocationStatus(1L, 0);

        verify(assetAllocationMapper, times(1)).updateStatus(1L, 0);
        assertFalse(result);
    }

    @Test
    void updateAllocation_shouldCallUpdateOnce() {
        when(assetAllocationMapper.update(testAllocation)).thenReturn(1);

        boolean result = assetAllocationService.updateAllocation(testAllocation);

        verify(assetAllocationMapper, times(1)).update(testAllocation);
        assertTrue(result);
    }

    @Test
    void updateAllocation_whenUpdateFails() {
        when(assetAllocationMapper.update(testAllocation)).thenReturn(0);

        boolean result = assetAllocationService.updateAllocation(testAllocation);

        verify(assetAllocationMapper, times(1)).update(testAllocation);
        assertFalse(result);
    }
}