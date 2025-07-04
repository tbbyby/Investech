package com.investech.entity;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class Client {
    private Long id;
    private String name;
    private String phone;
    private String email;
    private String idCard;
    private Integer age;
    private String gender;
    private String occupation;
    private Double annualIncome;
    private Double totalAssets;
    private String riskTolerance; // 风险承受能力：保守、稳健、积极
    private String investmentGoal; // 投资目标：保值、增值、养老、教育
    private String investmentHorizon; // 投资期限：短期、中期、长期
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
    private Integer status; // 0-禁用 1-启用
} 