package com.investech.entity;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class Strategy {
    private Long id;
    private String strategyCode; // 策略代码
    private String strategyName; // 策略名称
    private String strategyDesc; // 策略描述
    private String strategyType; // 策略类型：股票策略、债券策略、混合策略等
    private String investmentStyle; // 投资风格：价值型、成长型、平衡型等
    private String riskLevel; // 风险等级：低风险、中风险、高风险
    private String targetReturn; // 目标收益率
    private String maxDrawdown; // 最大回撤限制
    private String benchmark; // 基准指数
    private String factorCodes; // 使用的因子代码，JSON格式
    private String parameters; // 策略参数，JSON格式
    private Long creatorId; // 创建者ID
    private Integer status; // 状态 0-禁用 1-正常
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
} 