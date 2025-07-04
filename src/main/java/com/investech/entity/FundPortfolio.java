package com.investech.entity;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class FundPortfolio {
    private Long id;
    private String portfolioName; // 组合名称
    private String portfolioDesc; // 组合描述
    private Long userId; // 创建用户ID
    private String fundCodes; // 基金代码列表，逗号分隔
    private String weights; // 权重列表，JSON格式
    private Integer status; // 状态 0-禁用 1-正常
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
} 