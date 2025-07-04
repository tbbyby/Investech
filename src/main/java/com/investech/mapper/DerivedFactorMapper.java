package com.investech.mapper;

import com.investech.entity.DerivedFactor;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;

@Mapper
public interface DerivedFactorMapper {
    DerivedFactor findByFactorCode(String factorCode);
    
    DerivedFactor findById(Long id);
    
    List<DerivedFactor> findAll();
    
    List<DerivedFactor> findByCreatorId(Long creatorId);
    
    List<DerivedFactor> findByCategory(String category);
    
    List<DerivedFactor> findByCondition(@Param("factorCode") String factorCode,
                                       @Param("factorName") String factorName,
                                       @Param("category") String category,
                                       @Param("creatorId") Long creatorId);
    
    int insert(DerivedFactor derivedFactor);
    
    int update(DerivedFactor derivedFactor);
    
    int deleteById(Long id);
} 