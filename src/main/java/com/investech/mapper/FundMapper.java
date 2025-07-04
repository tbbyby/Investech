package com.investech.mapper;

import com.investech.entity.Fund;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;

@Mapper
public interface FundMapper {
    Fund findByFundCode(String fundCode);
    
    Fund findById(Long id);
    
    List<Fund> findAll();
    
    List<Fund> findByCondition(@Param("fundCode") String fundCode, 
                              @Param("fundName") String fundName,
                              @Param("fundCompany") String fundCompany,
                              @Param("fundManager") String fundManager,
                              @Param("fundType") String fundType,
                              @Param("riskLevel") String riskLevel,
                              @Param("tags") String tags);
    
    List<Fund> findByCompany(String fundCompany);
    
    List<Fund> findByManager(String fundManager);
    
    int insert(Fund fund);
    
    int update(Fund fund);
    
    int deleteById(Long id);
} 