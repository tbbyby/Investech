package com.investech.mapper;

import com.investech.entity.RiskManagement;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;

@Mapper
public interface RiskManagementMapper {
    RiskManagement findByRiskCode(String riskCode);
    
    RiskManagement findById(Long id);
    
    List<RiskManagement> findAll();
    
    List<RiskManagement> findByRiskType(String riskType);
    
    List<RiskManagement> findByRiskLevel(String riskLevel);
    
    List<RiskManagement> findByCreatorId(Long creatorId);
    
    List<RiskManagement> findByCondition(@Param("riskCode") String riskCode,
                                         @Param("riskName") String riskName,
                                         @Param("riskType") String riskType,
                                         @Param("riskLevel") String riskLevel,
                                         @Param("creatorId") Long creatorId);
    
    int insert(RiskManagement riskManagement);
    
    int update(RiskManagement riskManagement);
    
    int deleteById(Long id);
} 