package com.investech.service;

import com.investech.entity.Factor;
import com.investech.mapper.FactorMapper;
import com.investech.service.impl.FactorServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

/**
 * FactorService单元测试类
 * 测试因子管理相关的核心功能
 */
@SpringBootTest
class FactorServiceTest {

    @Mock
    private FactorMapper factorMapper;

    @InjectMocks
    private FactorServiceImpl factorService;

    private Factor testFactor;
    private Factor testFactor2;
    private Factor childFactor;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        
        // 初始化测试数据
        testFactor = new Factor();
        testFactor.setId(1L);
        testFactor.setFactorCode("F001");
        testFactor.setFactorName("市盈率");
        testFactor.setFactorDesc("股票价格与每股收益的比率");
        testFactor.setFactorType("基础因子");
        testFactor.setDataSource("财务数据");
        testFactor.setCalculationMethod("价格/每股收益");
        testFactor.setUnit("倍");
        testFactor.setCategory("估值因子");
        testFactor.setParentCode(null);
        testFactor.setLevel(1);
        testFactor.setSortOrder(1);
        testFactor.setStatus(1);
        testFactor.setCreateTime(LocalDateTime.now());
        testFactor.setUpdateTime(LocalDateTime.now());

        testFactor2 = new Factor();
        testFactor2.setId(2L);
        testFactor2.setFactorCode("F002");
        testFactor2.setFactorName("市净率");
        testFactor2.setFactorDesc("股票价格与每股净资产的比率");
        testFactor2.setFactorType("基础因子");
        testFactor2.setDataSource("财务数据");
        testFactor2.setCalculationMethod("价格/每股净资产");
        testFactor2.setUnit("倍");
        testFactor2.setCategory("估值因子");
        testFactor2.setParentCode(null);
        testFactor2.setLevel(1);
        testFactor2.setSortOrder(2);
        testFactor2.setStatus(1);
        testFactor2.setCreateTime(LocalDateTime.now());
        testFactor2.setUpdateTime(LocalDateTime.now());

        childFactor = new Factor();
        childFactor.setId(3L);
        childFactor.setFactorCode("F003");
        childFactor.setFactorName("PEG比率");
        childFactor.setFactorDesc("市盈率与盈利增长率的比率");
        childFactor.setFactorType("衍生因子");
        childFactor.setDataSource("财务数据");
        childFactor.setCalculationMethod("市盈率/盈利增长率");
        childFactor.setUnit("倍");
        childFactor.setCategory("估值因子");
        childFactor.setParentCode("F001");
        childFactor.setLevel(2);
        childFactor.setSortOrder(3);
        childFactor.setStatus(1);
        childFactor.setCreateTime(LocalDateTime.now());
        childFactor.setUpdateTime(LocalDateTime.now());
    }

    @Test
    @DisplayName("测试查找所有因子")
    void testFindAll() {
        // 准备测试数据
        List<Factor> factorList = Arrays.asList(testFactor, testFactor2, childFactor);
        
        // 模拟Mapper返回因子列表
        when(factorMapper.findAll()).thenReturn(factorList);
        
        // 执行测试
        List<Factor> result = factorService.findAll();
        
        // 验证结果
        assertNotNull(result);
        assertEquals(3, result.size());
        assertEquals("F001", result.get(0).getFactorCode());
        assertEquals("F002", result.get(1).getFactorCode());
        assertEquals("F003", result.get(2).getFactorCode());
        
        // 验证方法调用
        verify(factorMapper, times(1)).findAll();
    }

    @Test
    @DisplayName("测试根据因子代码查找因子")
    void testFindByFactorCode() {
        // 准备测试数据
        String factorCode = "F001";
        
        // 模拟Mapper返回因子
        when(factorMapper.findByFactorCode(factorCode)).thenReturn(testFactor);
        
        // 执行测试
        Factor result = factorService.findByFactorCode(factorCode);
        
        // 验证结果
        assertNotNull(result);
        assertEquals(factorCode, result.getFactorCode());
        assertEquals("市盈率", result.getFactorName());
        assertEquals("基础因子", result.getFactorType());
        assertEquals("估值因子", result.getCategory());
        
        // 验证方法调用
        verify(factorMapper, times(1)).findByFactorCode(factorCode);
    }

    @Test
    @DisplayName("测试根据因子代码查找因子-不存在")
    void testFindByFactorCodeNotFound() {
        // 准备测试数据
        String factorCode = "F999";
        
        // 模拟Mapper返回null
        when(factorMapper.findByFactorCode(factorCode)).thenReturn(null);
        
        // 执行测试
        Factor result = factorService.findByFactorCode(factorCode);
        
        // 验证结果
        assertNull(result);
        
        // 验证方法调用
        verify(factorMapper, times(1)).findByFactorCode(factorCode);
    }

    @Test
    @DisplayName("测试根据ID查找因子")
    void testFindById() {
        // 准备测试数据
        Long factorId = 1L;
        
        // 模拟Mapper返回因子
        when(factorMapper.findById(factorId)).thenReturn(testFactor);
        
        // 执行测试
        Factor result = factorService.findById(factorId);
        
        // 验证结果
        assertNotNull(result);
        assertEquals(factorId, result.getId());
        assertEquals("F001", result.getFactorCode());
        assertEquals("市盈率", result.getFactorName());
        
        // 验证方法调用
        verify(factorMapper, times(1)).findById(factorId);
    }

    @Test
    @DisplayName("测试根据因子类型查找因子")
    void testFindByType() {
        // 准备测试数据
        String factorType = "基础因子";
        List<Factor> typeFactors = Arrays.asList(testFactor, testFactor2);
        
        // 模拟Mapper返回因子列表
        when(factorMapper.findByType(factorType)).thenReturn(typeFactors);
        
        // 执行测试
        List<Factor> result = factorService.findByType(factorType);
        
        // 验证结果
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("基础因子", result.get(0).getFactorType());
        assertEquals("基础因子", result.get(1).getFactorType());
        
        // 验证方法调用
        verify(factorMapper, times(1)).findByType(factorType);
    }

    @Test
    @DisplayName("测试根据因子分类查找因子")
    void testFindByCategory() {
        // 准备测试数据
        String category = "估值因子";
        List<Factor> categoryFactors = Arrays.asList(testFactor, testFactor2, childFactor);
        
        // 模拟Mapper返回因子列表
        when(factorMapper.findByCategory(category)).thenReturn(categoryFactors);
        
        // 执行测试
        List<Factor> result = factorService.findByCategory(category);
        
        // 验证结果
        assertNotNull(result);
        assertEquals(3, result.size());
        assertEquals("估值因子", result.get(0).getCategory());
        assertEquals("估值因子", result.get(1).getCategory());
        assertEquals("估值因子", result.get(2).getCategory());
        
        // 验证方法调用
        verify(factorMapper, times(1)).findByCategory(category);
    }

    @Test
    @DisplayName("测试根据父因子代码查找子因子")
    void testFindByParentCode() {
        // 准备测试数据
        String parentCode = "F001";
        List<Factor> childFactors = Arrays.asList(childFactor);
        
        // 模拟Mapper返回子因子列表
        when(factorMapper.findByParentCode(parentCode)).thenReturn(childFactors);
        
        // 执行测试
        List<Factor> result = factorService.findByParentCode(parentCode);
        
        // 验证结果
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(parentCode, result.get(0).getParentCode());
        assertEquals("PEG比率", result.get(0).getFactorName());
        assertEquals("衍生因子", result.get(0).getFactorType());
        
        // 验证方法调用
        verify(factorMapper, times(1)).findByParentCode(parentCode);
    }

    @Test
    @DisplayName("测试因子搜索功能")
    void testSearchFactors() {
        // 准备测试数据
        String factorCode = "F";
        String factorName = "市盈";
        String factorType = "基础";
        String category = "估值";
        
        List<Factor> searchResult = Arrays.asList(testFactor);
        
        // 模拟Mapper返回搜索结果
        when(factorMapper.findByCondition(factorCode, factorName, factorType, category))
                .thenReturn(searchResult);
        
        // 执行测试
        List<Factor> result = factorService.searchFactors(factorCode, factorName, factorType, category);
        
        // 验证结果
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("F001", result.get(0).getFactorCode());
        assertEquals("市盈率", result.get(0).getFactorName());
        
        // 验证方法调用
        verify(factorMapper, times(1)).findByCondition(factorCode, factorName, factorType, category);
    }

    @Test
    @DisplayName("测试添加因子成功")
    void testAddFactorSuccess() {
        // 准备测试数据
        Factor newFactor = new Factor();
        newFactor.setFactorCode("F004");
        newFactor.setFactorName("ROE");
        newFactor.setFactorDesc("净资产收益率");
        newFactor.setFactorType("基础因子");
        newFactor.setCategory("盈利因子");
        newFactor.setLevel(1);
        newFactor.setSortOrder(4);
        
        // 模拟插入成功
        when(factorMapper.insert(any(Factor.class))).thenReturn(1);
        
        // 执行测试
        boolean result = factorService.addFactor(newFactor);
        
        // 验证结果
        assertTrue(result);
        assertEquals(1, newFactor.getStatus()); // 验证状态被设置为正常
        
        // 验证方法调用
        verify(factorMapper, times(1)).insert(newFactor);
    }

    @Test
    @DisplayName("测试添加因子失败")
    void testAddFactorFailure() {
        // 准备测试数据
        Factor newFactor = new Factor();
        newFactor.setFactorCode("F004");
        newFactor.setFactorName("ROE");
        
        // 模拟插入失败
        when(factorMapper.insert(any(Factor.class))).thenReturn(0);
        
        // 执行测试
        boolean result = factorService.addFactor(newFactor);
        
        // 验证结果
        assertFalse(result);
        assertEquals(1, newFactor.getStatus()); // 验证状态仍被设置
        
        // 验证方法调用
        verify(factorMapper, times(1)).insert(newFactor);
    }

    @Test
    @DisplayName("测试更新因子成功")
    void testUpdateFactorSuccess() {
        // 准备测试数据
        Factor updateFactor = new Factor();
        updateFactor.setId(1L);
        updateFactor.setFactorCode("F001");
        updateFactor.setFactorName("市盈率(更新)");
        updateFactor.setFactorDesc("更新后的市盈率描述");
        
        // 模拟更新成功
        when(factorMapper.update(any(Factor.class))).thenReturn(1);
        
        // 执行测试
        boolean result = factorService.updateFactor(updateFactor);
        
        // 验证结果
        assertTrue(result);
        
        // 验证方法调用
        verify(factorMapper, times(1)).update(updateFactor);
    }

    @Test
    @DisplayName("测试更新因子失败")
    void testUpdateFactorFailure() {
        // 准备测试数据
        Factor updateFactor = new Factor();
        updateFactor.setId(999L);
        updateFactor.setFactorCode("F999");
        updateFactor.setFactorName("不存在的因子");
        
        // 模拟更新失败
        when(factorMapper.update(any(Factor.class))).thenReturn(0);
        
        // 执行测试
        boolean result = factorService.updateFactor(updateFactor);
        
        // 验证结果
        assertFalse(result);
        
        // 验证方法调用
        verify(factorMapper, times(1)).update(updateFactor);
    }

    @Test
    @DisplayName("测试删除因子成功")
    void testDeleteFactorSuccess() {
        // 准备测试数据
        Long factorId = 1L;
        
        // 模拟删除成功
        when(factorMapper.deleteById(factorId)).thenReturn(1);
        
        // 执行测试
        boolean result = factorService.deleteFactor(factorId);
        
        // 验证结果
        assertTrue(result);
        
        // 验证方法调用
        verify(factorMapper, times(1)).deleteById(factorId);
    }

    @Test
    @DisplayName("测试删除因子失败")
    void testDeleteFactorFailure() {
        // 准备测试数据
        Long factorId = 999L;
        
        // 模拟删除失败
        when(factorMapper.deleteById(factorId)).thenReturn(0);
        
        // 执行测试
        boolean result = factorService.deleteFactor(factorId);
        
        // 验证结果
        assertFalse(result);
        
        // 验证方法调用
        verify(factorMapper, times(1)).deleteById(factorId);
    }

    @Test
    @DisplayName("测试边界条件-空因子代码搜索")
    void testSearchFactorsWithEmptyFactorCode() {
        // 准备测试数据
        List<Factor> searchResult = Arrays.asList(testFactor, testFactor2, childFactor);
        
        // 模拟Mapper返回搜索结果
        when(factorMapper.findByCondition("", null, null, null))
                .thenReturn(searchResult);
        
        // 执行测试
        List<Factor> result = factorService.searchFactors("", null, null, null);
        
        // 验证结果
        assertNotNull(result);
        assertEquals(3, result.size());
        
        // 验证方法调用
        verify(factorMapper, times(1)).findByCondition("", null, null, null);
    }

    @Test
    @DisplayName("测试边界条件-空因子类型查找")
    void testFindByTypeWithEmptyType() {
        // 准备测试数据
        List<Factor> typeFactors = Arrays.asList();
        
        // 模拟Mapper返回空列表
        when(factorMapper.findByType("")).thenReturn(typeFactors);
        
        // 执行测试
        List<Factor> result = factorService.findByType("");
        
        // 验证结果
        assertNotNull(result);
        assertEquals(0, result.size());
        
        // 验证方法调用
        verify(factorMapper, times(1)).findByType("");
    }

    @Test
    @DisplayName("测试因子层级关系")
    void testFactorHierarchy() {
        // 准备测试数据
        String parentCode = "F001";
        List<Factor> childFactors = Arrays.asList(childFactor);
        
        // 模拟Mapper返回子因子列表
        when(factorMapper.findByParentCode(parentCode)).thenReturn(childFactors);
        
        // 执行测试
        List<Factor> result = factorService.findByParentCode(parentCode);
        
        // 验证结果
        assertNotNull(result);
        assertEquals(1, result.size());
        
        Factor child = result.get(0);
        assertEquals(parentCode, child.getParentCode());
        assertEquals(2, child.getLevel()); // 验证层级为2
        assertEquals("衍生因子", child.getFactorType()); // 验证为衍生因子
        
        // 验证方法调用
        verify(factorMapper, times(1)).findByParentCode(parentCode);
    }

    @Test
    @DisplayName("测试因子状态管理")
    void testFactorStatusManagement() {
        // 准备测试数据
        Factor newFactor = new Factor();
        newFactor.setFactorCode("F005");
        newFactor.setFactorName("测试因子");
        newFactor.setStatus(0); // 初始状态为禁用
        
        // 模拟插入成功
        when(factorMapper.insert(any(Factor.class))).thenReturn(1);
        
        // 执行测试
        boolean result = factorService.addFactor(newFactor);
        
        // 验证结果
        assertTrue(result);
        assertEquals(1, newFactor.getStatus()); // 验证状态被设置为正常
        
        // 验证方法调用
        verify(factorMapper, times(1)).insert(newFactor);
    }
} 