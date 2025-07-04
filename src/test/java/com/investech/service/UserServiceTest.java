package com.investech.service;

import com.investech.entity.User;
import com.investech.mapper.UserMapper;
import com.investech.service.impl.UserServiceImpl;
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
 * UserService单元测试类
 * 测试用户管理相关的核心功能
 */
@SpringBootTest
class UserServiceTest {

    @Mock
    private UserMapper userMapper;

    @InjectMocks
    private UserServiceImpl userService;

    private User testUser;
    private User existingUser;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        
        // 初始化测试数据
        testUser = new User();
        testUser.setId(1L);
        testUser.setUsername("testuser");
        testUser.setPassword("password123");
        testUser.setEmail("test@example.com");
        testUser.setPhone("13800138000");
        testUser.setRealName("测试用户");
        testUser.setStatus(1);
        testUser.setCreateTime(LocalDateTime.now());
        testUser.setUpdateTime(LocalDateTime.now());

        existingUser = new User();
        existingUser.setId(2L);
        existingUser.setUsername("existinguser");
        existingUser.setPassword("password456");
        existingUser.setEmail("existing@example.com");
        existingUser.setStatus(1);
    }

    @Test
    @DisplayName("测试用户登录成功")
    void testLoginSuccess() {
        // 准备测试数据
        String username = "testuser";
        String password = "password123";
        
        // 模拟Mapper返回用户
        when(userMapper.findByUsernameAndPassword(username, password)).thenReturn(testUser);
        
        // 执行测试
        User result = userService.login(username, password);
        
        // 验证结果
        assertNotNull(result);
        assertEquals(username, result.getUsername());
        assertEquals(password, result.getPassword());
        assertEquals(1, result.getStatus());
        
        // 验证方法调用
        verify(userMapper, times(1)).findByUsernameAndPassword(username, password);
    }

    @Test
    @DisplayName("测试用户登录失败")
    void testLoginFailure() {
        // 准备测试数据
        String username = "wronguser";
        String password = "wrongpassword";
        
        // 模拟Mapper返回null
        when(userMapper.findByUsernameAndPassword(username, password)).thenReturn(null);
        
        // 执行测试
        User result = userService.login(username, password);
        
        // 验证结果
        assertNull(result);
        
        // 验证方法调用
        verify(userMapper, times(1)).findByUsernameAndPassword(username, password);
    }

    @Test
    @DisplayName("测试用户注册成功")
    void testRegisterSuccess() {
        // 准备测试数据
        User newUser = new User();
        newUser.setUsername("newuser");
        newUser.setPassword("newpassword");
        newUser.setEmail("new@example.com");
        
        // 模拟用户名不存在
        when(userMapper.findByUsername("newuser")).thenReturn(null);
        // 模拟插入成功
        when(userMapper.insert(any(User.class))).thenReturn(1);
        
        // 执行测试
        boolean result = userService.register(newUser);
        
        // 验证结果
        assertTrue(result);
        assertEquals(1, newUser.getStatus()); // 验证状态被设置为正常
        
        // 验证方法调用
        verify(userMapper, times(1)).findByUsername("newuser");
        verify(userMapper, times(1)).insert(any(User.class));
    }

    @Test
    @DisplayName("测试用户注册失败-用户名已存在")
    void testRegisterFailureUserExists() {
        // 准备测试数据
        User newUser = new User();
        newUser.setUsername("existinguser");
        newUser.setPassword("newpassword");
        
        // 模拟用户名已存在
        when(userMapper.findByUsername("existinguser")).thenReturn(existingUser);
        
        // 执行测试
        boolean result = userService.register(newUser);
        
        // 验证结果
        assertFalse(result);
        
        // 验证方法调用
        verify(userMapper, times(1)).findByUsername("existinguser");
        verify(userMapper, never()).insert(any(User.class));
    }

    @Test
    @DisplayName("测试根据ID查找用户")
    void testFindById() {
        // 准备测试数据
        Long userId = 1L;
        
        // 模拟Mapper返回用户
        when(userMapper.findById(userId)).thenReturn(testUser);
        
        // 执行测试
        User result = userService.findById(userId);
        
        // 验证结果
        assertNotNull(result);
        assertEquals(userId, result.getId());
        assertEquals("testuser", result.getUsername());
        
        // 验证方法调用
        verify(userMapper, times(1)).findById(userId);
    }

    @Test
    @DisplayName("测试查找所有用户")
    void testFindAll() {
        // 准备测试数据
        List<User> userList = Arrays.asList(testUser, existingUser);
        
        // 模拟Mapper返回用户列表
        when(userMapper.findAll()).thenReturn(userList);
        
        // 执行测试
        List<User> result = userService.findAll();
        
        // 验证结果
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("testuser", result.get(0).getUsername());
        assertEquals("existinguser", result.get(1).getUsername());
        
        // 验证方法调用
        verify(userMapper, times(1)).findAll();
    }

    @Test
    @DisplayName("测试更新用户成功")
    void testUpdateUserSuccess() {
        // 准备测试数据
        User updateUser = new User();
        updateUser.setId(1L);
        updateUser.setUsername("updateduser");
        updateUser.setEmail("updated@example.com");
        
        // 模拟更新成功
        when(userMapper.update(any(User.class))).thenReturn(1);
        
        // 执行测试
        boolean result = userService.updateUser(updateUser);
        
        // 验证结果
        assertTrue(result);
        
        // 验证方法调用
        verify(userMapper, times(1)).update(updateUser);
    }

    @Test
    @DisplayName("测试更新用户失败")
    void testUpdateUserFailure() {
        // 准备测试数据
        User updateUser = new User();
        updateUser.setId(999L);
        updateUser.setUsername("nonexistentuser");
        
        // 模拟更新失败
        when(userMapper.update(any(User.class))).thenReturn(0);
        
        // 执行测试
        boolean result = userService.updateUser(updateUser);
        
        // 验证结果
        assertFalse(result);
        
        // 验证方法调用
        verify(userMapper, times(1)).update(updateUser);
    }

    @Test
    @DisplayName("测试删除用户成功")
    void testDeleteUserSuccess() {
        // 准备测试数据
        Long userId = 1L;
        
        // 模拟删除成功
        when(userMapper.deleteById(userId)).thenReturn(1);
        
        // 执行测试
        boolean result = userService.deleteUser(userId);
        
        // 验证结果
        assertTrue(result);
        
        // 验证方法调用
        verify(userMapper, times(1)).deleteById(userId);
    }

    @Test
    @DisplayName("测试删除用户失败")
    void testDeleteUserFailure() {
        // 准备测试数据
        Long userId = 999L;
        
        // 模拟删除失败
        when(userMapper.deleteById(userId)).thenReturn(0);
        
        // 执行测试
        boolean result = userService.deleteUser(userId);
        
        // 验证结果
        assertFalse(result);
        
        // 验证方法调用
        verify(userMapper, times(1)).deleteById(userId);
    }

    @Test
    @DisplayName("测试边界条件-空用户名登录")
    void testLoginWithEmptyUsername() {
        // 执行测试
        User result = userService.login("", "password");
        
        // 验证结果
        assertNull(result);
        
        // 验证方法调用
        verify(userMapper, times(1)).findByUsernameAndPassword("", "password");
    }

    @Test
    @DisplayName("测试边界条件-空密码登录")
    void testLoginWithEmptyPassword() {
        // 执行测试
        User result = userService.login("username", "");
        
        // 验证结果
        assertNull(result);
        
        // 验证方法调用
        verify(userMapper, times(1)).findByUsernameAndPassword("username", "");
    }

    @Test
    @DisplayName("测试边界条件-注册时用户名为空")
    void testRegisterWithEmptyUsername() {
        // 准备测试数据
        User newUser = new User();
        newUser.setUsername("");
        newUser.setPassword("password");
        
        // 模拟用户名不存在
        when(userMapper.findByUsername("")).thenReturn(null);
        when(userMapper.insert(any(User.class))).thenReturn(1);
        
        // 执行测试
        boolean result = userService.register(newUser);
        
        // 验证结果
        assertTrue(result);
        assertEquals(1, newUser.getStatus());
    }
} 