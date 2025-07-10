package com.investech.service;

import com.investech.entity.User;
import com.investech.mapper.UserMapper;
import com.investech.service.impl.UserServiceImpl;
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
class UserServiceImplTest {

    @Mock
    private UserMapper userMapper;

    @InjectMocks
    private UserServiceImpl userService;

    private User testUser;

    @BeforeEach
    void setUp() {
        testUser = new User();
        testUser.setId(1L);
        testUser.setUsername("testuser");
        testUser.setPassword("password123");
        testUser.setEmail("testuser@example.com");
        testUser.setPhone("13800001111");
        testUser.setRealName("Test User");
        testUser.setStatus(1);
        testUser.setCreateTime(LocalDateTime.now());
        testUser.setUpdateTime(LocalDateTime.now());
    }

    @Test
    void login_shouldCallMapperOnce() {
        when(userMapper.findByUsernameAndPassword("testuser", "password123")).thenReturn(testUser);

        User result = userService.login("testuser", "password123");

        verify(userMapper, times(1)).findByUsernameAndPassword("testuser", "password123");
        assertNotNull(result);
        assertEquals("Test User", result.getRealName());
    }
    
    @Test
    void login_shouldReturnNullWhenUserNotFound() {
        when(userMapper.findByUsernameAndPassword("nonexistent", "password")).thenReturn(null);

        User result = userService.login("nonexistent", "password");

        assertNull(result);
    }

    @Test
    void register_shouldCallInsertOnce() {
        LocalDateTime createTime = LocalDateTime.now().minusDays(1);
        testUser.setCreateTime(createTime);

        when(userMapper.insert(testUser)).thenReturn(1);

        boolean result = userService.register(testUser);

        verify(userMapper, times(1)).insert(testUser);
        assertTrue(result);
        assertEquals(1, testUser.getStatus().intValue());
        assertTrue(testUser.getUpdateTime().isAfter(createTime));
    }

    @Test
    void register_shouldReturnFalseWhenUserExists() {
        when(userMapper.findByUsername("testuser")).thenReturn(testUser);

        boolean result = userService.register(testUser);

        assertFalse(result);
    }

    @Test
    void findById_shouldCallMapperOnce() {
        when(userMapper.findById(1L)).thenReturn(testUser);

        User result = userService.findById(1L);

        verify(userMapper, times(1)).findById(1L);
        assertNotNull(result);
        assertEquals("testuser", result.getUsername());
    }
    
    @Test
    void findById_shouldReturnNullWhenNotFound() {
        when(userMapper.findById(999L)).thenReturn(null);

        User result = userService.findById(999L);

        assertNull(result);
    }

    @Test
    void findByUsername_shouldCallMapperOnce() {
        when(userMapper.findByUsername("testuser")).thenReturn(testUser);

        User result = userService.findByUsername("testuser");

        verify(userMapper, times(1)).findByUsername("testuser");
        assertNotNull(result);
        assertEquals("Test User", result.getRealName());
    }
    
    @Test
    void findByUsername_shouldReturnNullWhenNotFound() {
        when(userMapper.findByUsername("nonexistent")).thenReturn(null);

        User result = userService.findByUsername("nonexistent");

        assertNull(result);
    }

    @Test
    void findAll_shouldCallMapperOnce() {
        List<User> users = Arrays.asList(testUser);
        when(userMapper.findAll()).thenReturn(users);

        List<User> result = userService.findAll();

        verify(userMapper, times(1)).findAll();
        assertNotNull(result);
        assertEquals(1, result.size());
    }
    
    @Test
    void findAll_shouldReturnEmptyListWhenNoUsers() {
        when(userMapper.findAll()).thenReturn(List.of());

        List<User> result = userService.findAll();

        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    void updateUser_shouldCallUpdateOnce() {
        LocalDateTime createTime = LocalDateTime.now().minusDays(1);
        testUser.setCreateTime(createTime);
        testUser.setUpdateTime(createTime.plusHours(1));

        when(userMapper.update(testUser)).thenReturn(1);

        boolean result = userService.updateUser(testUser);

        verify(userMapper, times(1)).update(testUser);
        assertTrue(result);
        assertEquals(createTime, testUser.getCreateTime());
        assertTrue(testUser.getUpdateTime().isAfter(createTime));
    }
    
    @Test
    void updateUser_shouldReturnFalseWhenUpdateFails() {
        LocalDateTime createTime = LocalDateTime.now().minusDays(1);
        testUser.setCreateTime(createTime);
        testUser.setUpdateTime(createTime.plusHours(1));

        when(userMapper.update(testUser)).thenReturn(0);

        boolean result = userService.updateUser(testUser);

        assertFalse(result);
    }

    @Test
    void deleteUser_shouldCallDeleteOnce() {
        when(userMapper.deleteById(1L)).thenReturn(1);

        boolean result = userService.deleteUser(1L);

        verify(userMapper, times(1)).deleteById(1L);
        assertTrue(result);
    }
    
    @Test
    void deleteUser_shouldReturnFalseWhenDeleteFails() {
        when(userMapper.deleteById(999L)).thenReturn(0);

        boolean result = userService.deleteUser(999L);

        assertFalse(result);
    }
}
