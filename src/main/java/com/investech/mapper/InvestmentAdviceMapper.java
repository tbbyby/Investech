package com.investech.mapper;

import com.investech.entity.InvestmentAdvice;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;

@Mapper
public interface InvestmentAdviceMapper {
    List<InvestmentAdvice> selectAll();
    InvestmentAdvice selectById(Long id);
    List<InvestmentAdvice> selectByClientId(Long clientId);
    List<InvestmentAdvice> selectByCondition(InvestmentAdvice advice);
    int insert(InvestmentAdvice advice);
    int update(InvestmentAdvice advice);
    int deleteById(Long id);
    int updateStatus(Long id, Integer status);
} 