package com.investech.service.impl;

import com.investech.entity.InvestmentAdvice;
import com.investech.mapper.InvestmentAdviceMapper;
import com.investech.service.InvestmentAdviceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class InvestmentAdviceServiceImpl implements InvestmentAdviceService {
    
    @Autowired
    private InvestmentAdviceMapper investmentAdviceMapper;
    
    @Override
    public List<InvestmentAdvice> getAllAdvices() {
        return investmentAdviceMapper.selectAll();
    }
    
    @Override
    public InvestmentAdvice getAdviceById(Long id) {
        return investmentAdviceMapper.selectById(id);
    }
    
    @Override
    public List<InvestmentAdvice> getAdvicesByClientId(Long clientId) {
        return investmentAdviceMapper.selectByClientId(clientId);
    }
    
    @Override
    public List<InvestmentAdvice> getAdvicesByCondition(InvestmentAdvice advice) {
        return investmentAdviceMapper.selectByCondition(advice);
    }
    
    @Override
    public boolean addAdvice(InvestmentAdvice advice) {
        return investmentAdviceMapper.insert(advice) > 0;
    }
    
    @Override
    public boolean updateAdvice(InvestmentAdvice advice) {
        return investmentAdviceMapper.update(advice) > 0;
    }
    
    @Override
    public boolean deleteAdvice(Long id) {
        return investmentAdviceMapper.deleteById(id) > 0;
    }
    
    @Override
    public boolean updateAdviceStatus(Long id, Integer status) {
        return investmentAdviceMapper.updateStatus(id, status) > 0;
    }
} 