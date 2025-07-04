package com.investech.mapper;

import com.investech.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;

@Mapper
public interface UserMapper {
    User findByUsername(String username);
    
    User findById(Long id);
    
    int insert(User user);
    
    int update(User user);
    
    int deleteById(Long id);
    
    List<User> findAll();
    
    User findByUsernameAndPassword(@Param("username") String username, @Param("password") String password);
} 