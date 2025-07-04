package com.investech.mapper;

import com.investech.entity.RiskAlert;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;

@Mapper
public interface RiskAlertMapper {
    List<RiskAlert> selectAll();
    RiskAlert selectById(Long id);
    List<RiskAlert> selectByCondition(RiskAlert alert);
    int insert(RiskAlert alert);
    int update(RiskAlert alert);
    int updateStatus(Long id, Integer status);
    int deleteById(Long id);
} 