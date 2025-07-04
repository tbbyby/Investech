package com.investech.mapper;

import com.investech.entity.RiskMonitor;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;

@Mapper
public interface RiskMonitorMapper {
    List<RiskMonitor> selectAll();
    RiskMonitor selectById(Long id);
    List<RiskMonitor> selectByCondition(RiskMonitor monitor);
    int insert(RiskMonitor monitor);
    int update(RiskMonitor monitor);
    int updateStatus(Long id, Integer status);
    int deleteById(Long id);
} 