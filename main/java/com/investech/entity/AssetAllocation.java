package com.investech.entity;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class AssetAllocation {
    private Long id;
    private Long clientId;
    private String allocationName;
    private String assetClass; // 资产类别：股票、债券、货币、商品、另类
    private Double allocationRatio; // 配置比例
    private Double currentValue; // 当前市值
    private Double targetValue; // 目标市值
    private String fundCodes; // 相关基金代码，逗号分隔
    private String description;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
    private Integer status; // 0-禁用 1-启用
} 