package com.investech.service;

import com.investech.entity.User;
import java.util.List;

public interface UserService {
    User login(String username, String password);
    
    boolean register(User user);
    
    User findById(Long id);
    
    User findByUsername(String username);
    
    List<User> findAll();
    
    boolean updateUser(User user);
    
    boolean deleteUser(Long id);
} 