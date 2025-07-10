package com.investech.controller;

import com.investech.common.Result;
import com.investech.entity.FactorTree;
import com.investech.service.FactorTreeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class FactorTreeControllerTest {

    @Mock
    private FactorTreeService factorTreeService;

    @InjectMocks
    private FactorTreeController factorTreeController;

    private FactorTree testTree;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        // 初始化测试数据
        testTree = new FactorTree();
        testTree.setId(1L);
        testTree.setTreeName("风险因子树");
        testTree.setTreeDesc("包含所有风险类因子的树结构");
        testTree.setBusinessScene("基金研究");
        testTree.setFactorCodes("[\"F001\", \"F002\", \"F003\"]");
        testTree.setStatus(1);
        testTree.setCreateTime(LocalDateTime.now());
        testTree.setUpdateTime(LocalDateTime.now());
    }

    @Test
    public void getAllTrees_success() {
        // 准备测试数据
        List<FactorTree> trees = Arrays.asList(testTree);

        // 模拟服务层返回
        when(factorTreeService.findAll()).thenReturn(trees);

        // 调用控制器方法
        Result<List<FactorTree>> result = factorTreeController.getAllTrees();

        // 验证结果
        assertNotNull(result);
        assertEquals(200, result.getCode().intValue());
        assertEquals("成功", result.getMessage());
        assertEquals(trees, result.getData());
        verify(factorTreeService, times(1)).findAll();
    }

    @Test
    public void getAllTrees_emptyList() {
        // 模拟服务层返回空列表
        when(factorTreeService.findAll()).thenReturn(Arrays.asList());

        // 调用控制器方法
        Result<List<FactorTree>> result = factorTreeController.getAllTrees();

        // 验证结果
        assertNotNull(result);
        assertEquals(200, result.getCode().intValue());
        assertEquals("成功", result.getMessage());
        assertTrue(result.getData().isEmpty());
        verify(factorTreeService, times(1)).findAll();
    }

    @Test
    public void getTreeById_success() {
        // 模拟服务层返回
        when(factorTreeService.findById(1L)).thenReturn(testTree);

        // 调用控制器方法
        Result<FactorTree> result = factorTreeController.getTreeById(1L);

        // 验证结果
        assertNotNull(result);
        assertEquals(200, result.getCode().intValue());
        assertEquals("成功", result.getMessage());
        assertEquals(testTree, result.getData());
        verify(factorTreeService, times(1)).findById(1L);
    }

    @Test
    public void getTreeById_notFound() {
        // 模拟服务层返回null
        when(factorTreeService.findById(1L)).thenReturn(null);

        // 调用控制器方法
        Result<FactorTree> result = factorTreeController.getTreeById(1L);

        // 验证结果
        assertNotNull(result);
        assertEquals(500, result.getCode().intValue());
        assertEquals("因子树不存在", result.getMessage());
        assertNull(result.getData());
        verify(factorTreeService, times(1)).findById(1L);
    }

    @Test
    public void getTreesByScene_success() {
        // 准备测试数据
        List<FactorTree> trees = Arrays.asList(testTree);

        // 模拟服务层返回
        when(factorTreeService.findByBusinessScene("基金研究")).thenReturn(trees);

        // 调用控制器方法
        Result<List<FactorTree>> result = factorTreeController.getTreesByScene("基金研究");

        // 验证结果
        assertNotNull(result);
        assertEquals(200, result.getCode().intValue());
        assertEquals("成功", result.getMessage());
        assertEquals(trees, result.getData());
        verify(factorTreeService, times(1)).findByBusinessScene("基金研究");
    }

    @Test
    public void getTreesByScene_emptyList() {
        // 模拟服务层返回空列表
        when(factorTreeService.findByBusinessScene("基金研究")).thenReturn(Arrays.asList());

        // 调用控制器方法
        Result<List<FactorTree>> result = factorTreeController.getTreesByScene("基金研究");

        // 验证结果
        assertNotNull(result);
        assertEquals(200, result.getCode().intValue());
        assertEquals("成功", result.getMessage());
        assertTrue(result.getData().isEmpty());
        verify(factorTreeService, times(1)).findByBusinessScene("基金研究");
    }

    @Test
    public void searchTrees_withAllParams_success() {
        // 准备测试数据
        List<FactorTree> trees = Arrays.asList(testTree);

        // 模拟服务层返回
        when(factorTreeService.searchTrees("风险因子树", "基金研究")).thenReturn(trees);

        // 调用控制器方法
        Result<List<FactorTree>> result = factorTreeController.searchTrees("风险因子树", "基金研究");

        // 验证结果
        assertNotNull(result);
        assertEquals(200, result.getCode().intValue());
        assertEquals("成功", result.getMessage());
        assertEquals(trees, result.getData());
        verify(factorTreeService, times(1)).searchTrees("风险因子树", "基金研究");
    }

    @Test
    public void searchTrees_withOnlyTreeName_success() {
        // 准备测试数据
        List<FactorTree> trees = Arrays.asList(testTree);

        // 模拟服务层返回
        when(factorTreeService.searchTrees("风险因子树", null)).thenReturn(trees);

        // 调用控制器方法
        Result<List<FactorTree>> result = factorTreeController.searchTrees("风险因子树", null);

        // 验证结果
        assertNotNull(result);
        assertEquals(200, result.getCode().intValue());
        assertEquals("成功", result.getMessage());
        assertEquals(trees, result.getData());
        verify(factorTreeService, times(1)).searchTrees("风险因子树", null);
    }

    @Test
    public void searchTrees_withOnlyScene_success() {
        // 准备测试数据
        List<FactorTree> trees = Arrays.asList(testTree);

        // 模拟服务层返回
        when(factorTreeService.searchTrees(null, "基金研究")).thenReturn(trees);

        // 调用控制器方法
        Result<List<FactorTree>> result = factorTreeController.searchTrees(null, "基金研究");

        // 验证结果
        assertNotNull(result);
        assertEquals(200, result.getCode().intValue());
        assertEquals("成功", result.getMessage());
        assertEquals(trees, result.getData());
        verify(factorTreeService, times(1)).searchTrees(null, "基金研究");
    }

    @Test
    public void searchTrees_withNoParams_success() {
        // 准备测试数据
        List<FactorTree> trees = Arrays.asList(testTree);

        // 模拟服务层返回
        when(factorTreeService.searchTrees(null, null)).thenReturn(trees);

        // 调用控制器方法
        Result<List<FactorTree>> result = factorTreeController.searchTrees(null, null);

        // 验证结果
        assertNotNull(result);
        assertEquals(200, result.getCode().intValue());
        assertEquals("成功", result.getMessage());
        assertEquals(trees, result.getData());
        verify(factorTreeService, times(1)).searchTrees(null, null);
    }

    @Test
    public void createTree_success() {
        // 模拟服务层返回true
        when(factorTreeService.createTree(testTree)).thenReturn(true);

        // 调用控制器方法
        Result<String> result = factorTreeController.createTree(testTree);

        // 验证结果
        assertNotNull(result);
        assertEquals(200, result.getCode().intValue());
        assertEquals("创建成功", result.getMessage());
        assertNull(result.getData());
        verify(factorTreeService, times(1)).createTree(testTree);
    }

    @Test
    public void createTree_failure() {
        // 模拟服务层返回false
        when(factorTreeService.createTree(testTree)).thenReturn(false);

        // 调用控制器方法
        Result<String> result = factorTreeController.createTree(testTree);

        // 验证结果
        assertNotNull(result);
        assertEquals(500, result.getCode().intValue());
        assertEquals("创建失败", result.getMessage());
        assertNull(result.getData());
        verify(factorTreeService, times(1)).createTree(testTree);
    }

    @Test
    public void updateTree_success() {
        // 模拟服务层返回true
        when(factorTreeService.updateTree(testTree)).thenReturn(true);

        // 调用控制器方法
        Result<String> result = factorTreeController.updateTree(testTree);

        // 验证结果
        assertNotNull(result);
        assertEquals(200, result.getCode().intValue());
        assertEquals("更新成功", result.getMessage());
        assertNull(result.getData());
        verify(factorTreeService, times(1)).updateTree(testTree);
    }

    @Test
    public void updateTree_failure() {
        // 模拟服务层返回false
        when(factorTreeService.updateTree(testTree)).thenReturn(false);

        // 调用控制器方法
        Result<String> result = factorTreeController.updateTree(testTree);

        // 验证结果
        assertNotNull(result);
        assertEquals(500, result.getCode().intValue());
        assertEquals("更新失败", result.getMessage());
        assertNull(result.getData());
        verify(factorTreeService, times(1)).updateTree(testTree);
    }

    @Test
    public void deleteTree_success() {
        // 模拟服务层返回true
        when(factorTreeService.deleteTree(1L)).thenReturn(true);

        // 调用控制器方法
        Result<String> result = factorTreeController.deleteTree(1L);

        // 验证结果
        assertNotNull(result);
        assertEquals(200, result.getCode().intValue());
        assertEquals("删除成功", result.getMessage());
        assertNull(result.getData());
        verify(factorTreeService, times(1)).deleteTree(1L);
    }

    @Test
    public void deleteTree_failure() {
        // 模拟服务层返回false
        when(factorTreeService.deleteTree(1L)).thenReturn(false);

        // 调用控制器方法
        Result<String> result = factorTreeController.deleteTree(1L);

        // 验证结果
        assertNotNull(result);
        assertEquals(500, result.getCode().intValue());
        assertEquals("删除失败", result.getMessage());
        assertNull(result.getData());
        verify(factorTreeService, times(1)).deleteTree(1L);
    }
}
