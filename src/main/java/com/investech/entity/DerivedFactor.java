package com.investech.entity;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class DerivedFactor {
    private Long id;
    private String factorCode; // 衍生因子代码
    private String factorName; // 衍生因子名称
    private String factorDesc; // 衍生因子描述
    private String baseFactors; // 基础因子列表，JSON格式
    private String weights; // 权重配置，JSON格式
    private String formula; // 计算公式
    private String category; // 因子分类
    private Long creatorId; // 创建者ID
    private Integer status; // 状态 0-禁用 1-正常
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
} 