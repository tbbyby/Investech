package com.investech.entity;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class InvestmentAdvice {
    private Long id;
    private Long clientId;
    private String adviceType; // 建议类型：资产配置、基金推荐、调仓建议
    private String title;
    private String content;
    private String riskLevel; // 风险等级：低、中、高
    private Double expectedReturn; // 预期收益率
    private Double maxDrawdown; // 最大回撤
    private String fundRecommendations; // 推荐基金列表，JSON格式
    private String assetAllocation; // 资产配置建议，JSON格式
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
    private Integer status; // 0-草稿 1-已发布 2-已采纳
} 