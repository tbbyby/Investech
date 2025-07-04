package com.investech.mapper;

import com.investech.entity.PortfolioOptimization;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;

@Mapper
public interface PortfolioOptimizationMapper {
    List<PortfolioOptimization> selectAll();
    PortfolioOptimization selectById(Long id);
    List<PortfolioOptimization> selectByClientId(Long clientId);
    List<PortfolioOptimization> selectByCondition(PortfolioOptimization optimization);
    int insert(PortfolioOptimization optimization);
    int update(PortfolioOptimization optimization);
    int deleteById(Long id);
    int updateStatus(Long id, Integer status);
} 