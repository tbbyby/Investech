package com.investech.service;

import com.investech.entity.FactorTree;
import com.investech.mapper.FactorTreeMapper;
import com.investech.service.impl.FactorTreeServiceImpl;
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
class FactorTreeServiceImplTest {

    @Mock
    private FactorTreeMapper factorTreeMapper;

    @InjectMocks
    private FactorTreeServiceImpl factorTreeService;

    private FactorTree testTree;

    @BeforeEach
    void setUp() {
        testTree = new FactorTree();
        testTree.setId(1L);
        testTree.setTreeName("因子树1");
        testTree.setTreeDesc("基金研究因子树");
        testTree.setBusinessScene("基金研究");
        testTree.setFactorCodes("[\"F001\", \"F002\", \"F003\"]");
        testTree.setStatus(1);
        testTree.setCreateTime(LocalDateTime.now());
        testTree.setUpdateTime(LocalDateTime.now());
    }

    @Test
    void findAll_shouldCallMapperOnce() {
        List<FactorTree> trees = Arrays.asList(testTree);
        when(factorTreeMapper.findAll()).thenReturn(trees);

        List<FactorTree> result = factorTreeService.findAll();

        verify(factorTreeMapper, times(1)).findAll();
        assertNotNull(result);
        assertEquals(1, result.size());
    }
    
    @Test
    void findAll_shouldHandleEmptyList() {
        when(factorTreeMapper.findAll()).thenReturn(Collections.emptyList());

        List<FactorTree> result = factorTreeService.findAll();

        verify(factorTreeMapper, times(1)).findAll();
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    void findById_shouldCallMapperOnce() {
        when(factorTreeMapper.findById(1L)).thenReturn(testTree);

        FactorTree result = factorTreeService.findById(1L);

        verify(factorTreeMapper, times(1)).findById(1L);
        assertNotNull(result);
        assertEquals("因子树1", result.getTreeName());
    }
    
    @Test
    void findById_shouldReturnNullWhenNotFound() {
        when(factorTreeMapper.findById(1L)).thenReturn(null);

        FactorTree result = factorTreeService.findById(1L);

        verify(factorTreeMapper, times(1)).findById(1L);
        assertNull(result);
    }

    @Test
    void findByBusinessScene_shouldCallMapperOnce() {
        List<FactorTree> trees = Arrays.asList(testTree);
        when(factorTreeMapper.findByBusinessScene("基金研究")).thenReturn(trees);

        List<FactorTree> result = factorTreeService.findByBusinessScene("基金研究");

        verify(factorTreeMapper, times(1)).findByBusinessScene("基金研究");
        assertNotNull(result);
        assertEquals(1, result.size());
    }
    
    @Test
    void findByBusinessScene_shouldHandleEmptyList() {
        when(factorTreeMapper.findByBusinessScene("基金研究")).thenReturn(Collections.emptyList());

        List<FactorTree> result = factorTreeService.findByBusinessScene("基金研究");

        verify(factorTreeMapper, times(1)).findByBusinessScene("基金研究");
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    void searchTrees_shouldCallMapperOnce() {
        List<FactorTree> trees = Arrays.asList(testTree);
        when(factorTreeMapper.findByCondition("因子树1", "基金研究")).thenReturn(trees);

        List<FactorTree> result = factorTreeService.searchTrees("因子树1", "基金研究");

        verify(factorTreeMapper, times(1)).findByCondition("因子树1", "基金研究");
        assertNotNull(result);
        assertEquals(1, result.size());
    }
    
    @Test
    void searchTrees_shouldHandleEmptyList() {
        when(factorTreeMapper.findByCondition("因子树1", "基金研究")).thenReturn(Collections.emptyList());

        List<FactorTree> result = factorTreeService.searchTrees("因子树1", "基金研究");

        verify(factorTreeMapper, times(1)).findByCondition("因子树1", "基金研究");
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    void createTree_shouldCallInsertOnce() {
        when(factorTreeMapper.insert(testTree)).thenReturn(1);

        boolean result = factorTreeService.createTree(testTree);

        verify(factorTreeMapper, times(1)).insert(testTree);
        assertTrue(result);
        assertEquals(1, testTree.getStatus().intValue());
    }
    
    @Test
    void createTree_shouldReturnFalseWhenInsertFails() {
        when(factorTreeMapper.insert(testTree)).thenReturn(0);

        boolean result = factorTreeService.createTree(testTree);

        verify(factorTreeMapper, times(1)).insert(testTree);
        assertFalse(result);
    }

    @Test
    void updateTree_shouldCallUpdateOnce() {
        LocalDateTime createTime = LocalDateTime.now().minusDays(1);
        testTree.setCreateTime(createTime);

        when(factorTreeMapper.update(testTree)).thenReturn(1);

        boolean result = factorTreeService.updateTree(testTree);

        verify(factorTreeMapper, times(1)).update(testTree);
        assertTrue(result);
        assertEquals(createTime, testTree.getCreateTime());
        assertTrue(testTree.getUpdateTime().isAfter(createTime));
    }
    
    @Test
    void updateTree_shouldReturnFalseWhenUpdateFails() {
        LocalDateTime createTime = LocalDateTime.now().minusDays(1);
        testTree.setCreateTime(createTime);

        when(factorTreeMapper.update(testTree)).thenReturn(0);

        boolean result = factorTreeService.updateTree(testTree);

        verify(factorTreeMapper, times(1)).update(testTree);
        assertFalse(result);
        assertEquals(createTime, testTree.getCreateTime());
        assertTrue(testTree.getUpdateTime().isAfter(createTime));
    }

    @Test
    void deleteTree_shouldCallDeleteOnce() {
        when(factorTreeMapper.deleteById(1L)).thenReturn(1);

        boolean result = factorTreeService.deleteTree(1L);

        verify(factorTreeMapper, times(1)).deleteById(1L);
        assertTrue(result);
    }
    
    @Test
    void deleteTree_shouldReturnFalseWhenDeleteFails() {
        when(factorTreeMapper.deleteById(1L)).thenReturn(0);

        boolean result = factorTreeService.deleteTree(1L);

        verify(factorTreeMapper, times(1)).deleteById(1L);
        assertFalse(result);
    }
}