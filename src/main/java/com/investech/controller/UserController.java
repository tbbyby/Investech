package com.investech.controller;

import com.investech.common.Result;
import com.investech.entity.User;
import com.investech.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user")
@CrossOrigin(origins = "*")
public class UserController {
    
    @Autowired
    private UserService userService;
    
    @PostMapping("/login")
    public Result<User> login(@RequestBody User user) {
        User loginUser = userService.login(user.getUsername(), user.getPassword());
        if (loginUser != null) {
            return Result.success("登录成功", loginUser);
        } else {
            return Result.error("用户名或密码错误");
        }
    }
    
    @PostMapping("/register")
    public Result<String> register(@RequestBody User user) {
        if (userService.register(user)) {
            return Result.success("注册成功", null);
        } else {
            return Result.error("注册失败，用户名可能已存在");
        }
    }
    
    @GetMapping("/{id}")
    public Result<User> getUserById(@PathVariable Long id) {
        User user = userService.findById(id);
        if (user != null) {
            return Result.success(user);
        } else {
            return Result.error("用户不存在");
        }
    }
    
    @GetMapping("/list")
    public Result<List<User>> getAllUsers() {
        List<User> users = userService.findAll();
        return Result.success(users);
    }
    
    @PutMapping("/update")
    public Result<String> updateUser(@RequestBody User user) {
        if (userService.updateUser(user)) {
            return Result.success("更新成功", null);
        } else {
            return Result.error("更新失败");
        }
    }
    
    @DeleteMapping("/{id}")
    public Result<String> deleteUser(@PathVariable Long id) {
        if (userService.deleteUser(id)) {
            return Result.success("删除成功", null);
        } else {
            return Result.error("删除失败");
        }
    }
} 