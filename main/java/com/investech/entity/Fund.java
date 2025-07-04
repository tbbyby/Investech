package com.investech.entity;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class Fund {
    private Long id;
    private String fundCode; // 基金代码
    private String fundName; // 基金名称
    private String fundType; // 基金类型
    private String fundCompany; // 基金公司
    private String fundManager; // 基金经理
    private BigDecimal netValue; // 最新净值
    private LocalDate netValueDate; // 净值日期
    private BigDecimal totalAssets; // 总资产规模
    private BigDecimal dailyReturn; // 日收益率
    private BigDecimal weeklyReturn; // 周收益率
    private BigDecimal monthlyReturn; // 月收益率
    private BigDecimal yearlyReturn; // 年收益率
    private BigDecimal maxDrawdown; // 最大回撤
    private BigDecimal sharpeRatio; // 夏普比率
    private String riskLevel; // 风险等级
    private String tags; // 基金标签，JSON格式
    private Integer status; // 状态 0-停售 1-正常
    private LocalDate createTime;
    private LocalDate updateTime;
} 