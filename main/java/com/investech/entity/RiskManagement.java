package com.investech.entity;

import lombok.Data;
import java.time.LocalDateTime;
import java.math.BigDecimal;

@Data
public class RiskManagement {
    private Long id;
    private String riskCode; // 风险代码
    private String riskName; // 风险名称
    private String riskType; // 风险类型：市场风险、信用风险、流动性风险等
    private String riskLevel; // 风险等级：低、中、高
    private BigDecimal riskValue; // 风险值
    private String riskDesc; // 风险描述
    private String measurementMethod; // 测量方法
    private String riskLimit; // 风险限额
    private String alertThreshold; // 预警阈值
    private String mitigationMeasures; // 缓解措施
    private String relatedStrategy; // 相关策略
    private String relatedPortfolio; // 相关组合
    private Long creatorId; // 创建者ID
    private Integer status; // 状态 0-禁用 1-正常
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
} 