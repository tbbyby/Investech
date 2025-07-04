package com.investech.service;

import com.investech.entity.FundPortfolio;
import com.investech.mapper.FundPortfolioMapper;
import com.investech.service.impl.FundPortfolioServiceImpl;
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
 * FundPortfolioService单元测试类
 * 测试基金组合管理相关的核心功能
 */
@SpringBootTest
class FundPortfolioServiceTest {

    @Mock
    private FundPortfolioMapper fundPortfolioMapper;

    @InjectMocks
    private FundPortfolioServiceImpl fundPortfolioService;

    private FundPortfolio testPortfolio;
    private FundPortfolio testPortfolio2;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        
        // 初始化测试数据
        testPortfolio = new FundPortfolio();
        testPortfolio.setId(1L);
        testPortfolio.setPortfolioName("成长型组合");
        testPortfolio.setPortfolioDesc("专注于成长型基金的组合");
        testPortfolio.setUserId(1L);
        testPortfolio.setFundCodes("000001,000002,000003");
        testPortfolio.setWeights("[0.4,0.35,0.25]");
        testPortfolio.setStatus(1);
        testPortfolio.setCreateTime(LocalDateTime.now());
        testPortfolio.setUpdateTime(LocalDateTime.now());

        testPortfolio2 = new FundPortfolio();
        testPortfolio2.setId(2L);
        testPortfolio2.setPortfolioName("稳健型组合");
        testPortfolio2.setPortfolioDesc("风险较低的稳健型组合");
        testPortfolio2.setUserId(1L);
        testPortfolio2.setFundCodes("000004,000005");
        testPortfolio2.setWeights("[0.6,0.4]");
        testPortfolio2.setStatus(1);
        testPortfolio2.setCreateTime(LocalDateTime.now());
        testPortfolio2.setUpdateTime(LocalDateTime.now());
    }

    @Test
    @DisplayName("测试查找所有基金组合")
    void testFindAll() {
        // 准备测试数据
        List<FundPortfolio> portfolioList = Arrays.asList(testPortfolio, testPortfolio2);
        
        // 模拟Mapper返回组合列表
        when(fundPortfolioMapper.findAll()).thenReturn(portfolioList);
        
        // 执行测试
        List<FundPortfolio> result = fundPortfolioService.findAll();
        
        // 验证结果
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("成长型组合", result.get(0).getPortfolioName());
        assertEquals("稳健型组合", result.get(1).getPortfolioName());
        
        // 验证方法调用
        verify(fundPortfolioMapper, times(1)).findAll();
    }

    @Test
    @DisplayName("测试根据ID查找基金组合")
    void testFindById() {
        // 准备测试数据
        Long portfolioId = 1L;
        
        // 模拟Mapper返回组合
        when(fundPortfolioMapper.findById(portfolioId)).thenReturn(testPortfolio);
        
        // 执行测试
        FundPortfolio result = fundPortfolioService.findById(portfolioId);
        
        // 验证结果
        assertNotNull(result);
        assertEquals(portfolioId, result.getId());
        assertEquals("成长型组合", result.getPortfolioName());
        assertEquals("专注于成长型基金的组合", result.getPortfolioDesc());
        assertEquals(1L, result.getUserId());
        
        // 验证方法调用
        verify(fundPortfolioMapper, times(1)).findById(portfolioId);
    }

    @Test
    @DisplayName("测试根据ID查找基金组合-不存在")
    void testFindByIdNotFound() {
        // 准备测试数据
        Long portfolioId = 999L;
        
        // 模拟Mapper返回null
        when(fundPortfolioMapper.findById(portfolioId)).thenReturn(null);
        
        // 执行测试
        FundPortfolio result = fundPortfolioService.findById(portfolioId);
        
        // 验证结果
        assertNull(result);
        
        // 验证方法调用
        verify(fundPortfolioMapper, times(1)).findById(portfolioId);
    }

    @Test
    @DisplayName("测试根据用户ID查找基金组合")
    void testFindByUserId() {
        // 准备测试数据
        Long userId = 1L;
        List<FundPortfolio> userPortfolios = Arrays.asList(testPortfolio, testPortfolio2);
        
        // 模拟Mapper返回用户组合列表
        when(fundPortfolioMapper.findByUserId(userId)).thenReturn(userPortfolios);
        
        // 执行测试
        List<FundPortfolio> result = fundPortfolioService.findByUserId(userId);
        
        // 验证结果
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(userId, result.get(0).getUserId());
        assertEquals(userId, result.get(1).getUserId());
        assertEquals("成长型组合", result.get(0).getPortfolioName());
        assertEquals("稳健型组合", result.get(1).getPortfolioName());
        
        // 验证方法调用
        verify(fundPortfolioMapper, times(1)).findByUserId(userId);
    }

    @Test
    @DisplayName("测试根据用户ID查找基金组合-用户无组合")
    void testFindByUserIdNoPortfolios() {
        // 准备测试数据
        Long userId = 999L;
        List<FundPortfolio> userPortfolios = Arrays.asList();
        
        // 模拟Mapper返回空列表
        when(fundPortfolioMapper.findByUserId(userId)).thenReturn(userPortfolios);
        
        // 执行测试
        List<FundPortfolio> result = fundPortfolioService.findByUserId(userId);
        
        // 验证结果
        assertNotNull(result);
        assertEquals(0, result.size());
        
        // 验证方法调用
        verify(fundPortfolioMapper, times(1)).findByUserId(userId);
    }

    @Test
    @DisplayName("测试创建基金组合成功")
    void testCreatePortfolioSuccess() {
        // 准备测试数据
        FundPortfolio newPortfolio = new FundPortfolio();
        newPortfolio.setPortfolioName("新组合");
        newPortfolio.setPortfolioDesc("新创建的基金组合");
        newPortfolio.setUserId(2L);
        newPortfolio.setFundCodes("000006,000007");
        newPortfolio.setWeights("[0.5,0.5]");
        
        // 模拟插入成功
        when(fundPortfolioMapper.insert(any(FundPortfolio.class))).thenReturn(1);
        
        // 执行测试
        boolean result = fundPortfolioService.createPortfolio(newPortfolio);
        
        // 验证结果
        assertTrue(result);
        assertEquals(1, newPortfolio.getStatus()); // 验证状态被设置为正常
        
        // 验证方法调用
        verify(fundPortfolioMapper, times(1)).insert(newPortfolio);
    }

    @Test
    @DisplayName("测试创建基金组合失败")
    void testCreatePortfolioFailure() {
        // 准备测试数据
        FundPortfolio newPortfolio = new FundPortfolio();
        newPortfolio.setPortfolioName("新组合");
        newPortfolio.setUserId(2L);
        
        // 模拟插入失败
        when(fundPortfolioMapper.insert(any(FundPortfolio.class))).thenReturn(0);
        
        // 执行测试
        boolean result = fundPortfolioService.createPortfolio(newPortfolio);
        
        // 验证结果
        assertFalse(result);
        assertEquals(1, newPortfolio.getStatus()); // 验证状态仍被设置
        
        // 验证方法调用
        verify(fundPortfolioMapper, times(1)).insert(newPortfolio);
    }

    @Test
    @DisplayName("测试更新基金组合成功")
    void testUpdatePortfolioSuccess() {
        // 准备测试数据
        FundPortfolio updatePortfolio = new FundPortfolio();
        updatePortfolio.setId(1L);
        updatePortfolio.setPortfolioName("成长型组合(更新)");
        updatePortfolio.setPortfolioDesc("更新后的组合描述");
        updatePortfolio.setFundCodes("000001,000002,000003,000004");
        updatePortfolio.setWeights("[0.3,0.25,0.25,0.2]");
        
        // 模拟更新成功
        when(fundPortfolioMapper.update(any(FundPortfolio.class))).thenReturn(1);
        
        // 执行测试
        boolean result = fundPortfolioService.updatePortfolio(updatePortfolio);
        
        // 验证结果
        assertTrue(result);
        
        // 验证方法调用
        verify(fundPortfolioMapper, times(1)).update(updatePortfolio);
    }

    @Test
    @DisplayName("测试更新基金组合失败")
    void testUpdatePortfolioFailure() {
        // 准备测试数据
        FundPortfolio updatePortfolio = new FundPortfolio();
        updatePortfolio.setId(999L);
        updatePortfolio.setPortfolioName("不存在的组合");
        
        // 模拟更新失败
        when(fundPortfolioMapper.update(any(FundPortfolio.class))).thenReturn(0);
        
        // 执行测试
        boolean result = fundPortfolioService.updatePortfolio(updatePortfolio);
        
        // 验证结果
        assertFalse(result);
        
        // 验证方法调用
        verify(fundPortfolioMapper, times(1)).update(updatePortfolio);
    }

    @Test
    @DisplayName("测试删除基金组合成功")
    void testDeletePortfolioSuccess() {
        // 准备测试数据
        Long portfolioId = 1L;
        
        // 模拟删除成功
        when(fundPortfolioMapper.deleteById(portfolioId)).thenReturn(1);
        
        // 执行测试
        boolean result = fundPortfolioService.deletePortfolio(portfolioId);
        
        // 验证结果
        assertTrue(result);
        
        // 验证方法调用
        verify(fundPortfolioMapper, times(1)).deleteById(portfolioId);
    }

    @Test
    @DisplayName("测试删除基金组合失败")
    void testDeletePortfolioFailure() {
        // 准备测试数据
        Long portfolioId = 999L;
        
        // 模拟删除失败
        when(fundPortfolioMapper.deleteById(portfolioId)).thenReturn(0);
        
        // 执行测试
        boolean result = fundPortfolioService.deletePortfolio(portfolioId);
        
        // 验证结果
        assertFalse(result);
        
        // 验证方法调用
        verify(fundPortfolioMapper, times(1)).deleteById(portfolioId);
    }

    @Test
    @DisplayName("测试边界条件-空组合名称")
    void testCreatePortfolioWithEmptyName() {
        // 准备测试数据
        FundPortfolio newPortfolio = new FundPortfolio();
        newPortfolio.setPortfolioName("");
        newPortfolio.setUserId(2L);
        newPortfolio.setFundCodes("000006");
        newPortfolio.setWeights("[1.0]");
        
        // 模拟插入成功
        when(fundPortfolioMapper.insert(any(FundPortfolio.class))).thenReturn(1);
        
        // 执行测试
        boolean result = fundPortfolioService.createPortfolio(newPortfolio);
        
        // 验证结果
        assertTrue(result);
        assertEquals(1, newPortfolio.getStatus());
        
        // 验证方法调用
        verify(fundPortfolioMapper, times(1)).insert(newPortfolio);
    }

    @Test
    @DisplayName("测试边界条件-空基金代码")
    void testCreatePortfolioWithEmptyFundCodes() {
        // 准备测试数据
        FundPortfolio newPortfolio = new FundPortfolio();
        newPortfolio.setPortfolioName("空组合");
        newPortfolio.setUserId(2L);
        newPortfolio.setFundCodes("");
        newPortfolio.setWeights("[]");
        
        // 模拟插入成功
        when(fundPortfolioMapper.insert(any(FundPortfolio.class))).thenReturn(1);
        
        // 执行测试
        boolean result = fundPortfolioService.createPortfolio(newPortfolio);
        
        // 验证结果
        assertTrue(result);
        assertEquals(1, newPortfolio.getStatus());
        
        // 验证方法调用
        verify(fundPortfolioMapper, times(1)).insert(newPortfolio);
    }

    @Test
    @DisplayName("测试组合权重验证")
    void testPortfolioWeightsValidation() {
        // 准备测试数据
        FundPortfolio portfolio = new FundPortfolio();
        portfolio.setId(1L);
        portfolio.setPortfolioName("测试组合");
        portfolio.setFundCodes("000001,000002");
        portfolio.setWeights("[0.6,0.4]");
        
        // 模拟Mapper返回组合
        when(fundPortfolioMapper.findById(1L)).thenReturn(portfolio);
        
        // 执行测试
        FundPortfolio result = fundPortfolioService.findById(1L);
        
        // 验证结果
        assertNotNull(result);
        assertEquals("[0.6,0.4]", result.getWeights());
        
        // 验证权重格式正确
        assertTrue(result.getWeights().startsWith("[") && result.getWeights().endsWith("]"));
        
        // 验证方法调用
        verify(fundPortfolioMapper, times(1)).findById(1L);
    }

    @Test
    @DisplayName("测试组合状态管理")
    void testPortfolioStatusManagement() {
        // 准备测试数据
        FundPortfolio newPortfolio = new FundPortfolio();
        newPortfolio.setPortfolioName("状态测试组合");
        newPortfolio.setUserId(3L);
        newPortfolio.setStatus(0); // 初始状态为禁用
        
        // 模拟插入成功
        when(fundPortfolioMapper.insert(any(FundPortfolio.class))).thenReturn(1);
        
        // 执行测试
        boolean result = fundPortfolioService.createPortfolio(newPortfolio);
        
        // 验证结果
        assertTrue(result);
        assertEquals(1, newPortfolio.getStatus()); // 验证状态被设置为正常
        
        // 验证方法调用
        verify(fundPortfolioMapper, times(1)).insert(newPortfolio);
    }

    @Test
    @DisplayName("测试用户组合数量限制")
    void testUserPortfolioCount() {
        // 准备测试数据
        Long userId = 1L;
        List<FundPortfolio> userPortfolios = Arrays.asList(testPortfolio, testPortfolio2);
        
        // 模拟Mapper返回用户组合列表
        when(fundPortfolioMapper.findByUserId(userId)).thenReturn(userPortfolios);
        
        // 执行测试
        List<FundPortfolio> result = fundPortfolioService.findByUserId(userId);
        
        // 验证结果
        assertNotNull(result);
        assertEquals(2, result.size());
        
        // 验证所有组合都属于同一用户
        for (FundPortfolio portfolio : result) {
            assertEquals(userId, portfolio.getUserId());
        }
        
        // 验证方法调用
        verify(fundPortfolioMapper, times(1)).findByUserId(userId);
    }
} 