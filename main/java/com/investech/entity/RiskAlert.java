package com.investech.entity;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class RiskAlert {
    private Long id;
    private String alertName; // 预警名称
    private String alertType; // 预警类型：市场风险、信用风险、流动性风险、操作风险
    private String alertLevel; // 预警等级：低、中、高、极高
    private String alertSource; // 预警来源
    private String alertContent; // 预警内容
    private String affectedPortfolio; // 受影响组合
    private String impactAssessment; // 影响评估
    private String actionRequired; // 需要采取的行动
    private String responsiblePerson; // 责任人
    private LocalDateTime alertTime; // 预警时间
    private LocalDateTime acknowledgeTime; // 确认时间
    private LocalDateTime resolveTime; // 解决时间
    private String status; // 状态：未确认、已确认、处理中、已解决
    private String resolution; // 解决方案
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
    private Integer statusCode; // 0-禁用 1-启用
} 