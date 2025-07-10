package com.investech.service;

import com.investech.entity.Client;
import com.investech.mapper.ClientMapper;
import com.investech.service.impl.ClientServiceImpl;
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
public class ClientServiceImplTest {

    @Mock
    private ClientMapper clientMapper;

    @InjectMocks
    private ClientServiceImpl clientService;

    private Client testClient;

    @BeforeEach
    void setUp() {
        testClient = new Client();
        testClient.setId(1L);
        testClient.setName("Test Client");
        testClient.setStatus(1);
    }

    @Test
    void getAllClients_shouldCallMapperOnce() {
        List<Client> clients = Arrays.asList(testClient);
        when(clientMapper.selectAll()).thenReturn(clients);

        List<Client> result = clientService.getAllClients();

        verify(clientMapper, times(1)).selectAll();
        assertNotNull(result);
        assertEquals(1, result.size());
    }

    @Test
    void getClientById_shouldCallMapperOnce() {
        when(clientMapper.selectById(1L)).thenReturn(testClient);

        Client result = clientService.getClientById(1L);

        verify(clientMapper, times(1)).selectById(1L);
        assertNotNull(result);
        assertEquals("Test Client", result.getName());
    }

    @Test
    void getClientsByCondition_shouldCallMapperOnce() {
        List<Client> clients = Arrays.asList(testClient);
        when(clientMapper.selectByCondition(testClient)).thenReturn(clients);

        List<Client> result = clientService.getClientsByCondition(testClient);

        verify(clientMapper, times(1)).selectByCondition(testClient);
        assertNotNull(result);
        assertEquals(1, result.size());
    }

    @Test
    void addClient_shouldCallInsertOnce() {
        when(clientMapper.insert(testClient)).thenReturn(1);

        boolean result = clientService.addClient(testClient);

        verify(clientMapper, times(1)).insert(testClient);
        assertTrue(result);
    }

    @Test
    void updateClient_shouldCallUpdateOnce() {
        when(clientMapper.update(testClient)).thenReturn(1);

        boolean result = clientService.updateClient(testClient);

        verify(clientMapper, times(1)).update(testClient);
        assertTrue(result);
    }

    @Test
    void deleteClient_shouldCallDeleteOnce() {
        when(clientMapper.deleteById(1L)).thenReturn(1);

        boolean result = clientService.deleteClient(1L);

        verify(clientMapper, times(1)).deleteById(1L);
        assertTrue(result);
    }

    @Test
    void updateClientStatus_shouldCallUpdateStatusOnce() {
        when(clientMapper.updateStatus(1L, 0)).thenReturn(1);

        boolean result = clientService.updateClientStatus(1L, 0);

        verify(clientMapper, times(1)).updateStatus(1L, 0);
        assertTrue(result);
    }

    // 新增的失败情况测试用例
    @Test
    void getClientById_whenMapperReturnsNull() {
        when(clientMapper.selectById(1L)).thenReturn(null);

        Client result = clientService.getClientById(1L);

        verify(clientMapper, times(1)).selectById(1L);
        assertNull(result);
    }

    @Test
    void getClientsByCondition_whenMapperReturnsEmptyList() {
        when(clientMapper.selectByCondition(testClient)).thenReturn(List.of());

        List<Client> result = clientService.getClientsByCondition(testClient);

        verify(clientMapper, times(1)).selectByCondition(testClient);
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    void addClient_whenMapperReturnsZero() {
        when(clientMapper.insert(testClient)).thenReturn(0);

        boolean result = clientService.addClient(testClient);

        verify(clientMapper, times(1)).insert(testClient);
        assertFalse(result);
    }

    @Test
    void updateClient_whenMapperReturnsZero() {
        when(clientMapper.update(testClient)).thenReturn(0);

        boolean result = clientService.updateClient(testClient);

        verify(clientMapper, times(1)).update(testClient);
        assertFalse(result);
    }

    @Test
    void deleteClient_whenMapperReturnsZero() {
        when(clientMapper.deleteById(1L)).thenReturn(0);

        boolean result = clientService.deleteClient(1L);

        verify(clientMapper, times(1)).deleteById(1L);
        assertFalse(result);
    }

    @Test
    void updateClientStatus_whenMapperReturnsZero() {
        when(clientMapper.updateStatus(1L, 0)).thenReturn(0);

        boolean result = clientService.updateClientStatus(1L, 0);

        verify(clientMapper, times(1)).updateStatus(1L, 0);
        assertFalse(result);
    }
}
