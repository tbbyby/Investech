package com.investech.controller;

import com.investech.common.Result;
import com.investech.entity.User;
import com.investech.service.UserService;
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
public class UserControllerTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    private User testUser;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        // 初始化测试数据
        testUser = new User();
        testUser.setId(1L);
        testUser.setUsername("testuser");
        testUser.setPassword("password123");
        testUser.setEmail("testuser@example.com");
        testUser.setPhone("13800001111");
        testUser.setRealName("张三");
        testUser.setStatus(1);
        testUser.setCreateTime(LocalDateTime.now());
        testUser.setUpdateTime(LocalDateTime.now());
    }

    @Test
    public void login_success() {
        // 模拟服务层返回
        when(userService.login("testuser", "password123")).thenReturn(testUser);

        // 调用控制器方法
        Result<User> result = userController.login(testUser);

        // 验证结果
        assertNotNull(result);
        assertEquals(200, result.getCode().intValue());
        assertEquals("登录成功", result.getMessage());
        assertEquals(testUser, result.getData());
        verify(userService, times(1)).login("testuser", "password123");
    }

    @Test
    public void login_authenticationFailed() {
        // 模拟服务层返回null（登录失败）
        when(userService.login("testuser", "password123")).thenReturn(null);

        // 调用控制器方法
        Result<User> result = userController.login(testUser);

        // 验证结果
        assertNotNull(result);
        assertEquals(500, result.getCode().intValue());
        assertEquals("用户名或密码错误", result.getMessage());
        assertNull(result.getData());
        verify(userService, times(1)).login("testuser", "password123");
    }

    @Test
    public void register_success() {
        // 模拟服务层返回true
        when(userService.register(testUser)).thenReturn(true);

        // 调用控制器方法
        Result<String> result = userController.register(testUser);

        // 验证结果
        assertNotNull(result);
        assertEquals(200, result.getCode().intValue());
        assertEquals("注册成功", result.getMessage());
        assertNull(result.getData());
        verify(userService, times(1)).register(testUser);
    }

    @Test
    public void register_failure() {
        // 模拟服务层返回false
        when(userService.register(testUser)).thenReturn(false);

        // 调用控制器方法
        Result<String> result = userController.register(testUser);

        // 验证结果
        assertNotNull(result);
        assertEquals(500, result.getCode().intValue());
        assertEquals("注册失败，用户名可能已存在", result.getMessage());
        assertNull(result.getData());
        verify(userService, times(1)).register(testUser);
    }

    @Test
    public void getUserById_success() {
        // 模拟服务层返回
        when(userService.findById(1L)).thenReturn(testUser);

        // 调用控制器方法
        Result<User> result = userController.getUserById(1L);

        // 验证结果
        assertNotNull(result);
        assertEquals(200, result.getCode().intValue());
        assertEquals("成功", result.getMessage());
        assertEquals(testUser, result.getData());
        verify(userService, times(1)).findById(1L);
    }

    @Test
    public void getUserById_notFound() {
        // 模拟服务层返回null
        when(userService.findById(1L)).thenReturn(null);

        // 调用控制器方法
        Result<User> result = userController.getUserById(1L);

        // 验证结果
        assertNotNull(result);
        assertEquals(500, result.getCode().intValue());
        assertEquals("用户不存在", result.getMessage());
        assertNull(result.getData());
        verify(userService, times(1)).findById(1L);
    }

    @Test
    public void getAllUsers_success() {
        // 准备测试数据
        List<User> users = Arrays.asList(testUser);

        // 模拟服务层返回
        when(userService.findAll()).thenReturn(users);

        // 调用控制器方法
        Result<List<User>> result = userController.getAllUsers();

        // 验证结果
        assertNotNull(result);
        assertEquals(200, result.getCode().intValue());
        assertEquals("成功", result.getMessage());
        assertEquals(users, result.getData());
        verify(userService, times(1)).findAll();
    }

    @Test
    public void getAllUsers_emptyList() {
        // 模拟服务层返回空列表
        when(userService.findAll()).thenReturn(Arrays.asList());

        // 调用控制器方法
        Result<List<User>> result = userController.getAllUsers();

        // 验证结果
        assertNotNull(result);
        assertEquals(200, result.getCode().intValue());
        assertEquals("成功", result.getMessage());
        assertTrue(result.getData().isEmpty());
        verify(userService, times(1)).findAll();
    }

    @Test
    public void updateUser_success() {
        // 模拟服务层返回true
        when(userService.updateUser(testUser)).thenReturn(true);

        // 调用控制器方法
        Result<String> result = userController.updateUser(testUser);

        // 验证结果
        assertNotNull(result);
        assertEquals(200, result.getCode().intValue());
        assertEquals("更新成功", result.getMessage());
        assertNull(result.getData());
        verify(userService, times(1)).updateUser(testUser);
    }

    @Test
    public void updateUser_failure() {
        // 模拟服务层返回false
        when(userService.updateUser(testUser)).thenReturn(false);

        // 调用控制器方法
        Result<String> result = userController.updateUser(testUser);

        // 验证结果
        assertNotNull(result);
        assertEquals(500, result.getCode().intValue());
        assertEquals("更新失败", result.getMessage());
        assertNull(result.getData());
        verify(userService, times(1)).updateUser(testUser);
    }

    @Test
    public void deleteUser_success() {
        // 模拟服务层返回true
        when(userService.deleteUser(1L)).thenReturn(true);

        // 调用控制器方法
        Result<String> result = userController.deleteUser(1L);

        // 验证结果
        assertNotNull(result);
        assertEquals(200, result.getCode().intValue());
        assertEquals("删除成功", result.getMessage());
        assertNull(result.getData());
        verify(userService, times(1)).deleteUser(1L);
    }

    @Test
    public void deleteUser_failure() {
        // 模拟服务层返回false
        when(userService.deleteUser(1L)).thenReturn(false);

        // 调用控制器方法
        Result<String> result = userController.deleteUser(1L);

        // 验证结果
        assertNotNull(result);
        assertEquals(500, result.getCode().intValue());
        assertEquals("删除失败", result.getMessage());
        assertNull(result.getData());
        verify(userService, times(1)).deleteUser(1L);
    }
}
