package com.investech.service;

import com.investech.entity.Fund;
import com.investech.mapper.FundMapper;
import com.investech.service.impl.FundServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

/**
 * FundService单元测试类
 * 测试基金查询、搜索、增删改等核心功能
 */
@SpringBootTest
class FundServiceTest {

    @Mock
    private FundMapper fundMapper;

    @InjectMocks
    private FundServiceImpl fundService;

    private Fund testFund;
    private Fund testFund2;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        
        // 初始化测试数据
        testFund = new Fund();
        testFund.setId(1L);
        testFund.setFundCode("000001");
        testFund.setFundName("华夏成长混合");
        testFund.setFundType("混合型");
        testFund.setFundCompany("华夏基金");
        testFund.setFundManager("张三");
        testFund.setNetValue(new BigDecimal("1.2345"));
        testFund.setNetValueDate(LocalDate.now());
        testFund.setTotalAssets(new BigDecimal("1000000000"));
        testFund.setDailyReturn(new BigDecimal("0.015"));
        testFund.setWeeklyReturn(new BigDecimal("0.025"));
        testFund.setMonthlyReturn(new BigDecimal("0.085"));
        testFund.setYearlyReturn(new BigDecimal("0.156"));
        testFund.setMaxDrawdown(new BigDecimal("-0.12"));
        testFund.setSharpeRatio(new BigDecimal("1.25"));
        testFund.setRiskLevel("中风险");
        testFund.setTags("成长,科技,消费");
        testFund.setStatus(1);
        testFund.setCreateTime(LocalDate.now());
        testFund.setUpdateTime(LocalDate.now());

        testFund2 = new Fund();
        testFund2.setId(2L);
        testFund2.setFundCode("000002");
        testFund2.setFundName("易方达消费行业股票");
        testFund2.setFundType("股票型");
        testFund2.setFundCompany("易方达基金");
        testFund2.setFundManager("李四");
        testFund2.setNetValue(new BigDecimal("2.5678"));
        testFund2.setNetValueDate(LocalDate.now());
        testFund2.setTotalAssets(new BigDecimal("2000000000"));
        testFund2.setDailyReturn(new BigDecimal("0.008"));
        testFund2.setWeeklyReturn(new BigDecimal("0.018"));
        testFund2.setMonthlyReturn(new BigDecimal("0.065"));
        testFund2.setYearlyReturn(new BigDecimal("0.128"));
        testFund2.setMaxDrawdown(new BigDecimal("-0.15"));
        testFund2.setSharpeRatio(new BigDecimal("1.18"));
        testFund2.setRiskLevel("高风险");
        testFund2.setTags("消费,白酒,医药");
        testFund2.setStatus(1);
        testFund2.setCreateTime(LocalDate.now());
        testFund2.setUpdateTime(LocalDate.now());
    }

    @Test
    @DisplayName("测试查找所有基金")
    void testFindAll() {
        // 准备测试数据
        List<Fund> fundList = Arrays.asList(testFund, testFund2);
        
        // 模拟Mapper返回基金列表
        when(fundMapper.findAll()).thenReturn(fundList);
        
        // 执行测试
        List<Fund> result = fundService.findAll();
        
        // 验证结果
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("000001", result.get(0).getFundCode());
        assertEquals("000002", result.get(1).getFundCode());
        
        // 验证方法调用
        verify(fundMapper, times(1)).findAll();
    }

    @Test
    @DisplayName("测试根据基金代码查找基金")
    void testFindByFundCode() {
        // 准备测试数据
        String fundCode = "000001";
        
        // 模拟Mapper返回基金
        when(fundMapper.findByFundCode(fundCode)).thenReturn(testFund);
        
        // 执行测试
        Fund result = fundService.findByFundCode(fundCode);
        
        // 验证结果
        assertNotNull(result);
        assertEquals(fundCode, result.getFundCode());
        assertEquals("华夏成长混合", result.getFundName());
        assertEquals("华夏基金", result.getFundCompany());
        
        // 验证方法调用
        verify(fundMapper, times(1)).findByFundCode(fundCode);
    }

    @Test
    @DisplayName("测试根据基金代码查找基金-不存在")
    void testFindByFundCodeNotFound() {
        // 准备测试数据
        String fundCode = "999999";
        
        // 模拟Mapper返回null
        when(fundMapper.findByFundCode(fundCode)).thenReturn(null);
        
        // 执行测试
        Fund result = fundService.findByFundCode(fundCode);
        
        // 验证结果
        assertNull(result);
        
        // 验证方法调用
        verify(fundMapper, times(1)).findByFundCode(fundCode);
    }

    @Test
    @DisplayName("测试根据ID查找基金")
    void testFindById() {
        // 准备测试数据
        Long fundId = 1L;
        
        // 模拟Mapper返回基金
        when(fundMapper.findById(fundId)).thenReturn(testFund);
        
        // 执行测试
        Fund result = fundService.findById(fundId);
        
        // 验证结果
        assertNotNull(result);
        assertEquals(fundId, result.getId());
        assertEquals("000001", result.getFundCode());
        
        // 验证方法调用
        verify(fundMapper, times(1)).findById(fundId);
    }

    @Test
    @DisplayName("测试基金搜索功能")
    void testSearchFunds() {
        // 准备测试数据
        String fundCode = "000";
        String fundName = "华夏";
        String fundCompany = "华夏基金";
        String fundManager = "张三";
        String fundType = "混合型";
        String riskLevel = "中风险";
        String tags = "成长";
        
        List<Fund> searchResult = Arrays.asList(testFund);
        
        // 模拟Mapper返回搜索结果
        when(fundMapper.findByCondition(fundCode, fundName, fundCompany, fundManager, fundType, riskLevel, tags))
                .thenReturn(searchResult);
        
        // 执行测试
        List<Fund> result = fundService.searchFunds(fundCode, fundName, fundCompany, fundManager, fundType, riskLevel, tags);
        
        // 验证结果
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("000001", result.get(0).getFundCode());
        assertEquals("华夏成长混合", result.get(0).getFundName());
        
        // 验证方法调用
        verify(fundMapper, times(1)).findByCondition(fundCode, fundName, fundCompany, fundManager, fundType, riskLevel, tags);
    }

    @Test
    @DisplayName("测试根据基金公司查找基金")
    void testFindByCompany() {
        // 准备测试数据
        String fundCompany = "华夏基金";
        List<Fund> companyFunds = Arrays.asList(testFund);
        
        // 模拟Mapper返回基金列表
        when(fundMapper.findByCompany(fundCompany)).thenReturn(companyFunds);
        
        // 执行测试
        List<Fund> result = fundService.findByCompany(fundCompany);
        
        // 验证结果
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(fundCompany, result.get(0).getFundCompany());
        
        // 验证方法调用
        verify(fundMapper, times(1)).findByCompany(fundCompany);
    }

    @Test
    @DisplayName("测试根据基金经理查找基金")
    void testFindByManager() {
        // 准备测试数据
        String fundManager = "张三";
        List<Fund> managerFunds = Arrays.asList(testFund);
        
        // 模拟Mapper返回基金列表
        when(fundMapper.findByManager(fundManager)).thenReturn(managerFunds);
        
        // 执行测试
        List<Fund> result = fundService.findByManager(fundManager);
        
        // 验证结果
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(fundManager, result.get(0).getFundManager());
        
        // 验证方法调用
        verify(fundMapper, times(1)).findByManager(fundManager);
    }

    @Test
    @DisplayName("测试添加基金成功")
    void testAddFundSuccess() {
        // 准备测试数据
        Fund newFund = new Fund();
        newFund.setFundCode("000003");
        newFund.setFundName("新基金");
        newFund.setFundType("债券型");
        newFund.setFundCompany("新基金公司");
        newFund.setFundManager("王五");
        newFund.setNetValue(new BigDecimal("1.0000"));
        newFund.setRiskLevel("低风险");
        
        // 模拟插入成功
        when(fundMapper.insert(any(Fund.class))).thenReturn(1);
        
        // 执行测试
        boolean result = fundService.addFund(newFund);
        
        // 验证结果
        assertTrue(result);
        assertEquals(1, newFund.getStatus()); // 验证状态被设置为正常
        
        // 验证方法调用
        verify(fundMapper, times(1)).insert(newFund);
    }

    @Test
    @DisplayName("测试添加基金失败")
    void testAddFundFailure() {
        // 准备测试数据
        Fund newFund = new Fund();
        newFund.setFundCode("000003");
        newFund.setFundName("新基金");
        
        // 模拟插入失败
        when(fundMapper.insert(any(Fund.class))).thenReturn(0);
        
        // 执行测试
        boolean result = fundService.addFund(newFund);
        
        // 验证结果
        assertFalse(result);
        assertEquals(1, newFund.getStatus()); // 验证状态仍被设置
        
        // 验证方法调用
        verify(fundMapper, times(1)).insert(newFund);
    }

    @Test
    @DisplayName("测试更新基金成功")
    void testUpdateFundSuccess() {
        // 准备测试数据
        Fund updateFund = new Fund();
        updateFund.setId(1L);
        updateFund.setFundCode("000001");
        updateFund.setFundName("华夏成长混合(更新)");
        updateFund.setNetValue(new BigDecimal("1.3000"));
        
        // 模拟更新成功
        when(fundMapper.update(any(Fund.class))).thenReturn(1);
        
        // 执行测试
        boolean result = fundService.updateFund(updateFund);
        
        // 验证结果
        assertTrue(result);
        
        // 验证方法调用
        verify(fundMapper, times(1)).update(updateFund);
    }

    @Test
    @DisplayName("测试更新基金失败")
    void testUpdateFundFailure() {
        // 准备测试数据
        Fund updateFund = new Fund();
        updateFund.setId(999L);
        updateFund.setFundCode("999999");
        updateFund.setFundName("不存在的基金");
        
        // 模拟更新失败
        when(fundMapper.update(any(Fund.class))).thenReturn(0);
        
        // 执行测试
        boolean result = fundService.updateFund(updateFund);
        
        // 验证结果
        assertFalse(result);
        
        // 验证方法调用
        verify(fundMapper, times(1)).update(updateFund);
    }

    @Test
    @DisplayName("测试删除基金成功")
    void testDeleteFundSuccess() {
        // 准备测试数据
        Long fundId = 1L;
        
        // 模拟删除成功
        when(fundMapper.deleteById(fundId)).thenReturn(1);
        
        // 执行测试
        boolean result = fundService.deleteFund(fundId);
        
        // 验证结果
        assertTrue(result);
        
        // 验证方法调用
        verify(fundMapper, times(1)).deleteById(fundId);
    }

    @Test
    @DisplayName("测试删除基金失败")
    void testDeleteFundFailure() {
        // 准备测试数据
        Long fundId = 999L;
        
        // 模拟删除失败
        when(fundMapper.deleteById(fundId)).thenReturn(0);
        
        // 执行测试
        boolean result = fundService.deleteFund(fundId);
        
        // 验证结果
        assertFalse(result);
        
        // 验证方法调用
        verify(fundMapper, times(1)).deleteById(fundId);
    }

    @Test
    @DisplayName("测试边界条件-空基金代码搜索")
    void testSearchFundsWithEmptyFundCode() {
        // 准备测试数据
        List<Fund> searchResult = Arrays.asList(testFund, testFund2);
        
        // 模拟Mapper返回搜索结果
        when(fundMapper.findByCondition("", null, null, null, null, null, null))
                .thenReturn(searchResult);
        
        // 执行测试
        List<Fund> result = fundService.searchFunds("", null, null, null, null, null, null);
        
        // 验证结果
        assertNotNull(result);
        assertEquals(2, result.size());
        
        // 验证方法调用
        verify(fundMapper, times(1)).findByCondition("", null, null, null, null, null, null);
    }

    @Test
    @DisplayName("测试边界条件-空基金公司查找")
    void testFindByCompanyWithEmptyCompany() {
        // 准备测试数据
        List<Fund> companyFunds = Arrays.asList();
        
        // 模拟Mapper返回空列表
        when(fundMapper.findByCompany("")).thenReturn(companyFunds);
        
        // 执行测试
        List<Fund> result = fundService.findByCompany("");
        
        // 验证结果
        assertNotNull(result);
        assertEquals(0, result.size());
        
        // 验证方法调用
        verify(fundMapper, times(1)).findByCompany("");
    }

    @Test
    @DisplayName("测试基金净值计算相关功能")
    void testFundNetValueCalculation() {
        // 准备测试数据
        BigDecimal netValue = new BigDecimal("1.2345");
        BigDecimal dailyReturn = new BigDecimal("0.015");
        
        testFund.setNetValue(netValue);
        testFund.setDailyReturn(dailyReturn);
        
        // 模拟Mapper返回基金
        when(fundMapper.findById(1L)).thenReturn(testFund);
        
        // 执行测试
        Fund result = fundService.findById(1L);
        
        // 验证结果
        assertNotNull(result);
        assertEquals(netValue, result.getNetValue());
        assertEquals(dailyReturn, result.getDailyReturn());
        
        // 验证净值大于0
        assertTrue(result.getNetValue().compareTo(BigDecimal.ZERO) > 0);
        
        // 验证方法调用
        verify(fundMapper, times(1)).findById(1L);
    }
} 