package com.investech.entity;

import lombok.Data;
import java.time.LocalDateTime;
import java.math.BigDecimal;

@Data
public class Backtest {
    private Long id;
    private String backtestCode; // 回测代码
    private String backtestName; // 回测名称
    private String strategyCode; // 策略代码
    private LocalDateTime startDate; // 回测开始日期
    private LocalDateTime endDate; // 回测结束日期
    private BigDecimal initialCapital; // 初始资金
    private BigDecimal finalCapital; // 最终资金
    private BigDecimal totalReturn; // 总收益率
    private BigDecimal annualReturn; // 年化收益率
    private BigDecimal maxDrawdown; // 最大回撤
    private BigDecimal sharpeRatio; // 夏普比率
    private BigDecimal volatility; // 波动率
    private BigDecimal winRate; // 胜率
    private BigDecimal profitLossRatio; // 盈亏比
    private String benchmarkReturn; // 基准收益率
    private String benchmarkMaxDrawdown; // 基准最大回撤
    private String benchmarkSharpeRatio; // 基准夏普比率
    private String status; // 回测状态：运行中、已完成、失败
    private String resultData; // 回测结果数据，JSON格式
    private Long creatorId; // 创建者ID
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
} 