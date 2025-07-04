package com.investech.mapper;

import com.investech.entity.ComplianceCheck;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;

@Mapper
public interface ComplianceCheckMapper {
    List<ComplianceCheck> selectAll();
    ComplianceCheck selectById(Long id);
    List<ComplianceCheck> selectByCondition(ComplianceCheck check);
    int insert(ComplianceCheck check);
    int update(ComplianceCheck check);
    int updateStatus(Long id, Integer status);
    int deleteById(Long id);
} 