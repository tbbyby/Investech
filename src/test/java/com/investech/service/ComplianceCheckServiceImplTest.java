package com.investech.service;

import com.investech.entity.ComplianceCheck;
import com.investech.mapper.ComplianceCheckMapper;
import com.investech.service.impl.ComplianceCheckServiceImpl;
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
class ComplianceCheckServiceImplTest {

    @Mock
    private ComplianceCheckMapper complianceCheckMapper;

    @InjectMocks
    private ComplianceCheckServiceImpl complianceCheckService;

    private ComplianceCheck testCheck;

    @BeforeEach
    void setUp() {
        testCheck = new ComplianceCheck();
        testCheck.setId(1L);
        testCheck.setCheckName("合规检查1");
        testCheck.setCheckType("投资限制");
        testCheck.setCheckRule("单只股票持仓不得超过10%");
        testCheck.setCheckResult("通过");
        testCheck.setViolationType("无");
        testCheck.setViolationDescription("无违规");
        testCheck.setActionRequired("无需操作");
        testCheck.setResponsiblePerson("张三");
        testCheck.setCheckTime(LocalDateTime.now());
        testCheck.setResolveTime(LocalDateTime.now().plusDays(1));
        testCheck.setStatus("已检查");
        testCheck.setStatusCode(1);
        testCheck.setCreateTime(LocalDateTime.now());
        testCheck.setUpdateTime(LocalDateTime.now());
    }

    @Test
    void getAllChecks_shouldCallMapperOnce() {
        List<ComplianceCheck> checks = Arrays.asList(testCheck);
        when(complianceCheckMapper.selectAll()).thenReturn(checks);

        List<ComplianceCheck> result = complianceCheckService.getAllChecks();

        verify(complianceCheckMapper, times(1)).selectAll();
        assertNotNull(result);
        assertEquals(1, result.size());
    }

    @Test
    void getAllChecks_whenMapperReturnsEmptyList() {
        when(complianceCheckMapper.selectAll()).thenReturn(List.of());

        List<ComplianceCheck> result = complianceCheckService.getAllChecks();

        verify(complianceCheckMapper, times(1)).selectAll();
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    void getCheckById_shouldCallMapperOnce() {
        when(complianceCheckMapper.selectById(1L)).thenReturn(testCheck);

        ComplianceCheck result = complianceCheckService.getCheckById(1L);

        verify(complianceCheckMapper, times(1)).selectById(1L);
        assertNotNull(result);
        assertEquals("合规检查1", result.getCheckName());
    }

    @Test
    void getCheckById_whenMapperReturnsNull() {
        when(complianceCheckMapper.selectById(1L)).thenReturn(null);

        ComplianceCheck result = complianceCheckService.getCheckById(1L);

        verify(complianceCheckMapper, times(1)).selectById(1L);
        assertNull(result);
    }

    @Test
    void getChecksByCondition_shouldCallMapperOnce() {
        List<ComplianceCheck> checks = Arrays.asList(testCheck);
        when(complianceCheckMapper.selectByCondition(testCheck)).thenReturn(checks);

        List<ComplianceCheck> result = complianceCheckService.getChecksByCondition(testCheck);

        verify(complianceCheckMapper, times(1)).selectByCondition(testCheck);
        assertNotNull(result);
        assertEquals(1, result.size());
    }

    @Test
    void getChecksByCondition_whenMapperReturnsEmptyList() {
        when(complianceCheckMapper.selectByCondition(testCheck)).thenReturn(List.of());

        List<ComplianceCheck> result = complianceCheckService.getChecksByCondition(testCheck);

        verify(complianceCheckMapper, times(1)).selectByCondition(testCheck);
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    void addCheck_shouldCallInsertOnce() {
        when(complianceCheckMapper.insert(testCheck)).thenReturn(1);

        boolean result = complianceCheckService.addCheck(testCheck);

        verify(complianceCheckMapper, times(1)).insert(testCheck);
        assertTrue(result);
    }

    @Test
    void addCheck_whenInsertReturnsZero() {
        when(complianceCheckMapper.insert(testCheck)).thenReturn(0);

        boolean result = complianceCheckService.addCheck(testCheck);

        verify(complianceCheckMapper, times(1)).insert(testCheck);
        assertFalse(result);
    }

    @Test
    void updateCheck_shouldCallUpdateOnce() {
        when(complianceCheckMapper.update(testCheck)).thenReturn(1);

        boolean result = complianceCheckService.updateCheck(testCheck);

        verify(complianceCheckMapper, times(1)).update(testCheck);
        assertTrue(result);
    }

    @Test
    void updateCheck_whenUpdateReturnsZero() {
        when(complianceCheckMapper.update(testCheck)).thenReturn(0);

        boolean result = complianceCheckService.updateCheck(testCheck);

        verify(complianceCheckMapper, times(1)).update(testCheck);
        assertFalse(result);
    }

    @Test
    void deleteCheck_shouldCallDeleteOnce() {
        when(complianceCheckMapper.deleteById(1L)).thenReturn(1);

        boolean result = complianceCheckService.deleteCheck(1L);

        verify(complianceCheckMapper, times(1)).deleteById(1L);
        assertTrue(result);
    }

    @Test
    void deleteCheck_whenDeleteReturnsZero() {
        when(complianceCheckMapper.deleteById(1L)).thenReturn(0);

        boolean result = complianceCheckService.deleteCheck(1L);

        verify(complianceCheckMapper, times(1)).deleteById(1L);
        assertFalse(result);
    }

    @Test
    void updateCheckStatus_shouldCallUpdateStatusOnce() {
        when(complianceCheckMapper.updateStatus(1L, 0)).thenReturn(1);

        boolean result = complianceCheckService.updateCheckStatus(1L, 0);

        verify(complianceCheckMapper, times(1)).updateStatus(1L, 0);
        assertTrue(result);
    }

    @Test
    void updateCheckStatus_whenUpdateReturnsZero() {
        when(complianceCheckMapper.updateStatus(1L, 0)).thenReturn(0);

        boolean result = complianceCheckService.updateCheckStatus(1L, 0);

        verify(complianceCheckMapper, times(1)).updateStatus(1L, 0);
        assertFalse(result);
    }
}
