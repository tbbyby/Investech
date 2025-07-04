package com.investech.mapper;

import com.investech.entity.FundPortfolio;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;

@Mapper
public interface FundPortfolioMapper {
    FundPortfolio findById(Long id);
    
    List<FundPortfolio> findByUserId(Long userId);
    
    List<FundPortfolio> findAll();
    
    int insert(FundPortfolio portfolio);
    
    int update(FundPortfolio portfolio);
    
    int deleteById(Long id);
} 