package com.investech.service;

import com.investech.entity.Fund;
import com.investech.mapper.FundMapper;
import com.investech.service.impl.FundServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class FundServiceImplTest {

    @Mock
    private FundMapper fundMapper;

    @InjectMocks
    private FundServiceImpl fundService;

    private Fund testFund;

    @BeforeEach
    void setUp() {
        testFund = new Fund();
        testFund.setId(1L);
        testFund.setFundCode("FUND001");
        testFund.setFundName("成长型基金");
        testFund.setFundType("股票型");
        testFund.setFundCompany("华夏基金");
        testFund.setFundManager("张伟");
        testFund.setNetValue(new BigDecimal("1.2345"));
        testFund.setNetValueDate(LocalDate.now());
        testFund.setTotalAssets(new BigDecimal("1000000000.00"));
        testFund.setDailyReturn(new BigDecimal("0.55"));
        testFund.setWeeklyReturn(new BigDecimal("3.25"));
        testFund.setMonthlyReturn(new BigDecimal("5.75"));
        testFund.setYearlyReturn(new BigDecimal("25.50"));
        testFund.setMaxDrawdown(new BigDecimal("15.25"));
        testFund.setSharpeRatio(new BigDecimal("1.85"));
        testFund.setRiskLevel("中风险");
        testFund.setTags("[\"科技\", \"成长\", \"股票\"]");
        testFund.setStatus(1);
        testFund.setCreateTime(LocalDate.now());
        testFund.setUpdateTime(LocalDate.now());
    }

    @Test
    void findAll_shouldCallMapperOnce() {
        List<Fund> funds = Arrays.asList(testFund);
        when(fundMapper.findAll()).thenReturn(funds);

        List<Fund> result = fundService.findAll();

        verify(fundMapper, times(1)).findAll();
        assertNotNull(result);
        assertEquals(1, result.size());
    }

    @Test
    void findAll_whenNoData_returnsEmptyList() {
        when(fundMapper.findAll()).thenReturn(List.of());

        List<Fund> result = fundService.findAll();

        verify(fundMapper, times(1)).findAll();
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    void findByFundCode_shouldCallMapperOnce() {
        when(fundMapper.findByFundCode("FUND001")).thenReturn(testFund);

        Fund result = fundService.findByFundCode("FUND001");

        verify(fundMapper, times(1)).findByFundCode("FUND001");
        assertNotNull(result);
        assertEquals("成长型基金", result.getFundName());
    }

    @Test
    void findByFundCode_whenNotFound_returnsNull() {
        when(fundMapper.findByFundCode("FUND999")).thenReturn(null);

        Fund result = fundService.findByFundCode("FUND999");

        verify(fundMapper, times(1)).findByFundCode("FUND999");
        assertNull(result);
    }

    @Test
    void findById_shouldCallMapperOnce() {
        when(fundMapper.findById(1L)).thenReturn(testFund);

        Fund result = fundService.findById(1L);

        verify(fundMapper, times(1)).findById(1L);
        assertNotNull(result);
        assertEquals("FUND001", result.getFundCode());
    }

    @Test
    void findById_whenNotFound_returnsNull() {
        when(fundMapper.findById(999L)).thenReturn(null);

        Fund result = fundService.findById(999L);

        verify(fundMapper, times(1)).findById(999L);
        assertNull(result);
    }

    @Test
    void searchFunds_shouldCallMapperOnce() {
        List<Fund> funds = Arrays.asList(testFund);
        when(fundMapper.findByCondition("FUND001", "成长型基金", "华夏基金",
                "张伟", "股票型", "中风险", "[\"科技\", \"成长\", \"股票\"]")).thenReturn(funds);

        List<Fund> result = fundService.searchFunds("FUND001", "成长型基金", "华夏基金",
                "张伟", "股票型", "中风险", "[\"科技\", \"成长\", \"股票\"]");

        verify(fundMapper, times(1)).findByCondition("FUND001", "成长型基金", "华夏基金",
                "张伟", "股票型", "中风险", "[\"科技\", \"成长\", \"股票\"]");
        assertNotNull(result);
        assertEquals(1, result.size());
    }

    @Test
    void searchFunds_whenNoMatch_returnsEmptyList() {
        when(fundMapper.findByCondition("FUND999", null, null, null, null, null, null)).thenReturn(List.of());

        List<Fund> result = fundService.searchFunds("FUND999", null, null, null, null, null, null);

        verify(fundMapper, times(1)).findByCondition("FUND999", null, null, null, null, null, null);
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    void findByCompany_shouldCallMapperOnce() {
        List<Fund> funds = Arrays.asList(testFund);
        when(fundMapper.findByCompany("华夏基金")).thenReturn(funds);

        List<Fund> result = fundService.findByCompany("华夏基金");

        verify(fundMapper, times(1)).findByCompany("华夏基金");
        assertNotNull(result);
        assertEquals(1, result.size());
    }

    @Test
    void findByCompany_whenNoFunds_returnsEmptyList() {
        when(fundMapper.findByCompany("不存在的公司")).thenReturn(List.of());

        List<Fund> result = fundService.findByCompany("不存在的公司");

        verify(fundMapper, times(1)).findByCompany("不存在的公司");
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    void findByManager_shouldCallMapperOnce() {
        List<Fund> funds = Arrays.asList(testFund);
        when(fundMapper.findByManager("张伟")).thenReturn(funds);

        List<Fund> result = fundService.findByManager("张伟");

        verify(fundMapper, times(1)).findByManager("张伟");
        assertNotNull(result);
        assertEquals(1, result.size());
    }

    @Test
    void findByManager_whenNoFunds_returnsEmptyList() {
        when(fundMapper.findByManager("不存在的经理")).thenReturn(List.of());

        List<Fund> result = fundService.findByManager("不存在的经理");

        verify(fundMapper, times(1)).findByManager("不存在的经理");
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    void addFund_shouldCallInsertOnce() {
        when(fundMapper.insert(testFund)).thenReturn(1);

        boolean result = fundService.addFund(testFund);

        verify(fundMapper, times(1)).insert(testFund);
        assertTrue(result);
        assertEquals(1, testFund.getStatus().intValue());
        assertNotNull(testFund.getCreateTime());
        assertNotNull(testFund.getUpdateTime());
    }

    @Test
    void addFund_whenInsertFails_returnsFalse() {
        when(fundMapper.insert(testFund)).thenReturn(0);

        boolean result = fundService.addFund(testFund);

        verify(fundMapper, times(1)).insert(testFund);
        assertFalse(result);
    }

    @Test
    void updateFund_shouldCallUpdateOnce() {
        LocalDate createTime = LocalDate.now().minusDays(1);
        testFund.setCreateTime(createTime);

        when(fundMapper.update(testFund)).thenReturn(1);

        boolean result = fundService.updateFund(testFund);

        verify(fundMapper, times(1)).update(testFund);
        assertTrue(result);
        assertEquals(createTime, testFund.getCreateTime());
        assertTrue(testFund.getUpdateTime().isAfter(createTime));
    }

    @Test
    void updateFund_whenUpdateFails_returnsFalse() {
        when(fundMapper.update(testFund)).thenReturn(0);

        boolean result = fundService.updateFund(testFund);

        verify(fundMapper, times(1)).update(testFund);
        assertFalse(result);
    }

    @Test
    void deleteFund_shouldCallDeleteOnce() {
        when(fundMapper.deleteById(1L)).thenReturn(1);

        boolean result = fundService.deleteFund(1L);

        verify(fundMapper, times(1)).deleteById(1L);
        assertTrue(result);
    }

    @Test
    void deleteFund_whenDeleteFails_returnsFalse() {
        when(fundMapper.deleteById(999L)).thenReturn(0);

        boolean result = fundService.deleteFund(999L);

        verify(fundMapper, times(1)).deleteById(999L);
        assertFalse(result);
    }
    
}
