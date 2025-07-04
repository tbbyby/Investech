package com.investech.entity;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class Factor {
    private Long id;
    private String factorCode; // 因子代码
    private String factorName; // 因子名称
    private String factorDesc; // 因子描述
    private String factorType; // 因子类型：基础因子、衍生因子、风格因子
    private String dataSource; // 数据来源
    private String calculationMethod; // 计算方法
    private String unit; // 单位
    private String category; // 因子分类
    private String parentCode; // 父因子代码
    private Integer level; // 因子层级
    private Integer sortOrder; // 排序
    private Integer status; // 状态 0-禁用 1-正常
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
} 