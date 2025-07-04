package com.investech.mapper;

import com.investech.entity.Factor;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;

@Mapper
public interface FactorMapper {
    Factor findByFactorCode(String factorCode);
    
    Factor findById(Long id);
    
    List<Factor> findAll();
    
    List<Factor> findByType(String factorType);
    
    List<Factor> findByCategory(String category);
    
    List<Factor> findByParentCode(String parentCode);
    
    List<Factor> findByCondition(@Param("factorCode") String factorCode,
                                @Param("factorName") String factorName,
                                @Param("factorType") String factorType,
                                @Param("category") String category);
    
    int insert(Factor factor);
    
    int update(Factor factor);
    
    int deleteById(Long id);
} 