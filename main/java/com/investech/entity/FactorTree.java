package com.investech.entity;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class FactorTree {
    private Long id;
    private String treeName; // 树名称
    private String treeDesc; // 树描述
    private String businessScene; // 业务场景：基金研究、量化投研、特色数据等
    private String factorCodes; // 因子代码列表，JSON格式
    private Integer status; // 状态 0-禁用 1-正常
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
} 