package com.investech.service.impl;

import com.investech.entity.User;
import com.investech.mapper.UserMapper;
import com.investech.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    
    @Autowired
    private UserMapper userMapper;
    
    @Override
    public User login(String username, String password) {
        return userMapper.findByUsernameAndPassword(username, password);
    }
    
    @Override
    public boolean register(User user) {
        // 检查用户名是否已存在
        User existingUser = userMapper.findByUsername(user.getUsername());
        if (existingUser != null) {
            return false;
        }
        
        // 设置用户状态和时间戳
        user.setStatus(1); // 设置用户状态为正常
        LocalDateTime now = LocalDateTime.now();
        user.setCreateTime(now);
        user.setUpdateTime(now);
        
        return userMapper.insert(user) > 0;
    }
    
    @Override
    public User findById(Long id) {
        return userMapper.findById(id);
    }
    
    @Override
    public User findByUsername(String username) {
        return userMapper.findByUsername(username);
    }
    
    @Override
    public List<User> findAll() {
        return userMapper.findAll();
    }
    
    @Override
    public boolean updateUser(User user) {
        // 设置更新时间
        user.setUpdateTime(LocalDateTime.now());
        return userMapper.update(user) > 0;
    }
    
    @Override
    public boolean deleteUser(Long id) {
        return userMapper.deleteById(id) > 0;
    }
} 