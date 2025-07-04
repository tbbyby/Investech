package com.investech.entity;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class ComplianceCheck {
    private Long id;
    private String checkName; // 检查名称
    private String checkType; // 检查类型：投资限制、持仓限制、交易限制、信息披露
    private String checkRule; // 检查规则
    private String checkResult; // 检查结果：通过、不通过、待确认
    private String violationType; // 违规类型
    private String violationDescription; // 违规描述
    private String actionRequired; // 需要采取的行动
    private String responsiblePerson; // 责任人
    private LocalDateTime checkTime; // 检查时间
    private LocalDateTime resolveTime; // 解决时间
    private String status; // 状态：待检查、已检查、已解决
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
    private Integer statusCode; // 0-禁用 1-启用
} 