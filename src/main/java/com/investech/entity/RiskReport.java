package com.investech.entity;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class RiskReport {
    private Long id;
    private String reportName; // 报告名称
    private String reportType; // 报告类型：日报、周报、月报、季报、年报
    private String reportPeriod; // 报告期间
    private String riskSummary; // 风险摘要
    private String marketRisk; // 市场风险分析
    private String creditRisk; // 信用风险分析
    private String liquidityRisk; // 流动性风险分析
    private String operationalRisk; // 操作风险分析
    private String riskMetrics; // 风险指标，JSON格式
    private String recommendations; // 风险建议
    private String author; // 报告作者
    private String reviewer; // 报告审核人
    private LocalDateTime reportDate; // 报告日期
    private String status; // 状态：草稿、已提交、已审核、已发布
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
    private Integer statusCode; // 0-禁用 1-启用
} 