package com.investech.entity;

import lombok.Data;
import java.time.LocalDateTime;
import java.math.BigDecimal;

@Data
public class PerformanceEvaluation {
    private Long id;
    private String evaluationCode; // 评估代码
    private String evaluationName; // 评估名称
    private String strategyCode; // 策略代码
    private String portfolioCode; // 组合代码
    private LocalDateTime evaluationDate; // 评估日期
    private BigDecimal totalReturn; // 总收益率
    private BigDecimal annualReturn; // 年化收益率
    private BigDecimal benchmarkReturn; // 基准收益率
    private BigDecimal excessReturn; // 超额收益
    private BigDecimal sharpeRatio; // 夏普比率
    private BigDecimal sortinoRatio; // 索提诺比率
    private BigDecimal calmarRatio; // 卡玛比率
    private BigDecimal informationRatio; // 信息比率
    private BigDecimal maxDrawdown; // 最大回撤
    private BigDecimal volatility; // 波动率
    private BigDecimal beta; // 贝塔系数
    private BigDecimal alpha; // 阿尔法系数
    private BigDecimal treynorRatio; // 特雷诺比率
    private BigDecimal jensenAlpha; // 詹森阿尔法
    private String riskAdjustedReturn; // 风险调整后收益
    private String performanceRanking; // 业绩排名
    private String evaluationResult; // 评估结果：优秀、良好、一般、较差
    private String evaluationReport; // 评估报告，JSON格式
    private Long evaluatorId; // 评估者ID
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
} 