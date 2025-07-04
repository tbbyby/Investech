package com.investech.service;

import com.investech.entity.InvestmentAdvice;
import java.util.List;

public interface InvestmentAdviceService {
    List<InvestmentAdvice> getAllAdvices();
    InvestmentAdvice getAdviceById(Long id);
    List<InvestmentAdvice> getAdvicesByClientId(Long clientId);
    List<InvestmentAdvice> getAdvicesByCondition(InvestmentAdvice advice);
    boolean addAdvice(InvestmentAdvice advice);
    boolean updateAdvice(InvestmentAdvice advice);
    boolean deleteAdvice(Long id);
    boolean updateAdviceStatus(Long id, Integer status);
} 