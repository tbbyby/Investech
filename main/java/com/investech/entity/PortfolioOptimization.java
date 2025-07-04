package com.investech.entity;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class PortfolioOptimization {
    private Long id;
    private Long clientId;
    private String optimizationName;
    private String optimizationMethod; // 优化方法：马科维茨、风险平价、最大夏普比率
    private String fundPool; // 基金池，JSON格式
    private Double targetReturn; // 目标收益率
    private Double maxRisk; // 最大风险
    private String optimizedWeights; // 优化权重，JSON格式
    private Double expectedReturn; // 预期收益率
    private Double expectedRisk; // 预期风险
    private Double sharpeRatio; // 夏普比率
    private String constraints; // 约束条件，JSON格式
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
    private Integer status; // 0-计算中 1-已完成 2-已应用
} 