package com.investech.controller;

import com.investech.common.Result;
import com.investech.entity.Client;
import com.investech.service.ClientService;
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
public class ClientControllerTest {

    @Mock
    private ClientService clientService;

    @InjectMocks
    private ClientController clientController;

    private Client testClient;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        // 初始化测试数据
        testClient = new Client();
        testClient.setId(1L);
        testClient.setName("张三");
        testClient.setPhone("13800001111");
        testClient.setEmail("zhangsan@example.com");
        testClient.setIdCard("110101199003072516");
        testClient.setAge(35);
        testClient.setGender("男");
        testClient.setOccupation("软件工程师");
        testClient.setAnnualIncome(200000.0);
        testClient.setTotalAssets(500000.0);
        testClient.setRiskTolerance("稳健");
        testClient.setInvestmentGoal("增值");
        testClient.setInvestmentHorizon("长期");
        testClient.setCreateTime(LocalDateTime.now());
        testClient.setUpdateTime(LocalDateTime.now());
        testClient.setStatus(1);
    }

    @Test
    public void getAllClients_success() {
        // 准备测试数据
        List<Client> clients = Arrays.asList(testClient);

        // 模拟服务层返回
        when(clientService.getAllClients()).thenReturn(clients);

        // 调用控制器方法
        Result<List<Client>> result = clientController.getAllClients();

        // 验证结果
        assertNotNull(result);
        assertEquals(200, result.getCode().intValue());
        assertEquals("成功", result.getMessage());
        assertEquals(clients, result.getData());
        verify(clientService, times(1)).getAllClients();
    }

    @Test
    public void getAllClients_failure() {
        // 模拟异常
        when(clientService.getAllClients()).thenThrow(new RuntimeException("Database error"));

        // 调用控制器方法
        Result<List<Client>> result = clientController.getAllClients();

        // 验证结果
        assertNotNull(result);
        assertEquals(500, result.getCode().intValue());
        assertTrue(result.getMessage().contains("获取客户列表失败"));
        assertNull(result.getData());
        verify(clientService, times(1)).getAllClients();
    }

    @Test
    public void getClientById_success() {
        // 模拟服务层返回
        when(clientService.getClientById(1L)).thenReturn(testClient);

        // 调用控制器方法
        Result<Client> result = clientController.getClientById(1L);

        // 验证结果
        assertNotNull(result);
        assertEquals(200, result.getCode().intValue());
        assertEquals("成功", result.getMessage());
        assertEquals(testClient, result.getData());
        verify(clientService, times(1)).getClientById(1L);
    }

    @Test
    public void getClientById_notFound() {
        // 模拟服务层返回null
        when(clientService.getClientById(1L)).thenReturn(null);

        // 调用控制器方法
        Result<Client> result = clientController.getClientById(1L);

        // 验证结果
        assertNotNull(result);
        assertEquals(500, result.getCode().intValue());
        assertEquals("客户不存在", result.getMessage());
        assertNull(result.getData());
        verify(clientService, times(1)).getClientById(1L);
    }

    @Test
    public void getClientById_failure() {
        // 模拟异常
        when(clientService.getClientById(1L)).thenThrow(new RuntimeException("Database error"));

        // 调用控制器方法
        Result<Client> result = clientController.getClientById(1L);

        // 验证结果
        assertNotNull(result);
        assertEquals(500, result.getCode().intValue());
        assertTrue(result.getMessage().contains("获取客户信息失败"));
        assertNull(result.getData());
        verify(clientService, times(1)).getClientById(1L);
    }

    @Test
    public void searchClients_success() {
        // 准备测试数据
        List<Client> clients = Arrays.asList(testClient);

        // 模拟服务层返回
        when(clientService.getClientsByCondition(testClient)).thenReturn(clients);

        // 调用控制器方法
        Result<List<Client>> result = clientController.searchClients(testClient);

        // 验证结果
        assertNotNull(result);
        assertEquals(200, result.getCode().intValue());
        assertEquals("成功", result.getMessage());
        assertEquals(clients, result.getData());
        verify(clientService, times(1)).getClientsByCondition(testClient);
    }

    @Test
    public void searchClients_failure() {
        // 模拟异常
        when(clientService.getClientsByCondition(testClient)).thenThrow(new RuntimeException("Database error"));

        // 调用控制器方法
        Result<List<Client>> result = clientController.searchClients(testClient);

        // 验证结果
        assertNotNull(result);
        assertEquals(500, result.getCode().intValue());
        assertTrue(result.getMessage().contains("搜索客户失败"));
        assertNull(result.getData());
        verify(clientService, times(1)).getClientsByCondition(testClient);
    }

    @Test
    public void addClient_success() {
        // 模拟服务层返回true
        when(clientService.addClient(testClient)).thenReturn(true);

        // 调用控制器方法
        Result<String> result = clientController.addClient(testClient);

        // 验证结果
        assertNotNull(result);
        assertEquals(200, result.getCode().intValue());
        assertEquals("添加客户成功", result.getData());
        assertEquals("成功", result.getMessage());
        verify(clientService, times(1)).addClient(testClient);
    }

    @Test
    public void addClient_failure() {
        // 模拟服务层返回false
        when(clientService.addClient(testClient)).thenReturn(false);

        // 调用控制器方法
        Result<String> result = clientController.addClient(testClient);

        // 验证结果
        assertNotNull(result);
        assertEquals(500, result.getCode().intValue());
        assertEquals("添加客户失败", result.getMessage());
        assertNull(result.getData());
        verify(clientService, times(1)).addClient(testClient);
    }

    @Test
    public void addClient_serviceException() {
        // 模拟异常
        when(clientService.addClient(testClient)).thenThrow(new RuntimeException("Database error"));

        // 调用控制器方法
        Result<String> result = clientController.addClient(testClient);

        // 验证结果
        assertNotNull(result);
        assertEquals(500, result.getCode().intValue());
        assertTrue(result.getMessage().contains("添加客户失败"));
        assertNull(result.getData());
        verify(clientService, times(1)).addClient(testClient);
    }

    @Test
    public void updateClient_success() {
        // 模拟服务层返回true
        when(clientService.updateClient(testClient)).thenReturn(true);

        // 调用控制器方法
        Result<String> result = clientController.updateClient(testClient);

        // 验证结果
        assertNotNull(result);
        assertEquals(200, result.getCode().intValue());
        assertEquals("更新客户成功", result.getData());
        assertEquals("成功",result.getMessage());
        verify(clientService, times(1)).updateClient(testClient);
    }

    @Test
    public void updateClient_failure() {
        // 模拟服务层返回false
        when(clientService.updateClient(testClient)).thenReturn(false);

        // 调用控制器方法
        Result<String> result = clientController.updateClient(testClient);

        // 验证结果
        assertNotNull(result);
        assertEquals(500, result.getCode().intValue());
        assertEquals("更新客户失败", result.getMessage());
        assertNull(result.getData());
        verify(clientService, times(1)).updateClient(testClient);
    }

    @Test
    public void updateClient_serviceException() {
        // 模拟异常
        when(clientService.updateClient(testClient)).thenThrow(new RuntimeException("Database error"));

        // 调用控制器方法
        Result<String> result = clientController.updateClient(testClient);

        // 验证结果
        assertNotNull(result);
        assertEquals(500, result.getCode().intValue());
        assertTrue(result.getMessage().contains("更新客户失败"));
        assertNull(result.getData());
        verify(clientService, times(1)).updateClient(testClient);
    }

    @Test
    public void deleteClient_success() {
        // 模拟服务层返回true
        when(clientService.deleteClient(1L)).thenReturn(true);

        // 调用控制器方法
        Result<String> result = clientController.deleteClient(1L);

        // 验证结果
        assertNotNull(result);
        assertEquals(200, result.getCode().intValue());
        assertEquals("删除客户成功", result.getData());
        assertEquals("成功",result.getMessage());
        verify(clientService, times(1)).deleteClient(1L);
    }

    @Test
    public void deleteClient_failure() {
        // 模拟服务层返回false
        when(clientService.deleteClient(1L)).thenReturn(false);

        // 调用控制器方法
        Result<String> result = clientController.deleteClient(1L);

        // 验证结果
        assertNotNull(result);
        assertEquals(500, result.getCode().intValue());
        assertEquals("删除客户失败", result.getMessage());
        assertNull(result.getData());
        verify(clientService, times(1)).deleteClient(1L);
    }

    @Test
    public void deleteClient_serviceException() {
        // 模拟异常
        when(clientService.deleteClient(1L)).thenThrow(new RuntimeException("Database error"));

        // 调用控制器方法
        Result<String> result = clientController.deleteClient(1L);

        // 验证结果
        assertNotNull(result);
        assertEquals(500, result.getCode().intValue());
        assertTrue(result.getMessage().contains("删除客户失败"));
        assertNull(result.getData());
        verify(clientService, times(1)).deleteClient(1L);
    }

    @Test
    public void updateClientStatus_success() {
        // 模拟服务层返回true
        when(clientService.updateClientStatus(1L, 0)).thenReturn(true);

        // 调用控制器方法
        Result<String> result = clientController.updateClientStatus(1L, 0);

        // 验证结果
        assertNotNull(result);
        assertEquals(200, result.getCode().intValue());
        assertEquals("更新客户状态成功", result.getData());
        assertEquals("成功",result.getMessage());
        verify(clientService, times(1)).updateClientStatus(1L, 0);
    }

    @Test
    public void updateClientStatus_failure() {
        // 模拟服务层返回false
        when(clientService.updateClientStatus(1L, 0)).thenReturn(false);

        // 调用控制器方法
        Result<String> result = clientController.updateClientStatus(1L, 0);

        // 验证结果
        assertNotNull(result);
        assertEquals(500, result.getCode().intValue());
        assertEquals("更新客户状态失败", result.getMessage());
        assertNull(result.getData());
        verify(clientService, times(1)).updateClientStatus(1L, 0);
    }

    @Test
    public void updateClientStatus_serviceException() {
        // 模拟异常
        when(clientService.updateClientStatus(1L, 0)).thenThrow(new RuntimeException("Database error"));

        // 调用控制器方法
        Result<String> result = clientController.updateClientStatus(1L, 0);

        // 验证结果
        assertNotNull(result);
        assertEquals(500, result.getCode().intValue());
        assertTrue(result.getMessage().contains("更新客户状态失败"));
        assertNull(result.getData());
        verify(clientService, times(1)).updateClientStatus(1L, 0);
    }
}
