package com.investech.entity;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class RiskMonitor {
    private Long id;
    private String monitorName; // 监控名称
    private String monitorType; // 监控类型：市场风险、信用风险、流动性风险、操作风险
    private String riskIndicator; // 风险指标
    private Double threshold; // 阈值
    private Double currentValue; // 当前值
    private String riskLevel; // 风险等级：低、中、高、极高
    private String status; // 状态：正常、预警、超限
    private String description; // 描述
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
    private Integer statusCode; // 0-禁用 1-启用
} 