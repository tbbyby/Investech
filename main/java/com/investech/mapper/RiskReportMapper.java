package com.investech.mapper;

import com.investech.entity.RiskReport;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;

@Mapper
public interface RiskReportMapper {
    List<RiskReport> selectAll();
    RiskReport selectById(Long id);
    List<RiskReport> selectByCondition(RiskReport report);
    int insert(RiskReport report);
    int update(RiskReport report);
    int updateStatus(Long id, Integer status);
    int deleteById(Long id);
} 