package com.investech.mapper;

import com.investech.entity.FactorTree;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;

@Mapper
public interface FactorTreeMapper {
    FactorTree findById(Long id);
    
    List<FactorTree> findAll();
    
    List<FactorTree> findByBusinessScene(String businessScene);
    
    List<FactorTree> findByCondition(@Param("treeName") String treeName,
                                    @Param("businessScene") String businessScene);
    
    int insert(FactorTree factorTree);
    
    int update(FactorTree factorTree);
    
    int deleteById(Long id);
} 