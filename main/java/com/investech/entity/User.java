package com.investech.entity;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class User {
    private Long id;
    private String username;
    private String password;
    private String email;
    private String phone;
    private String realName;
    private Integer status; // 0-禁用 1-正常
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
} 