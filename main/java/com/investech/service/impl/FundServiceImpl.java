package com.investech.service.impl;

import com.investech.entity.Fund;
import com.investech.mapper.FundMapper;
import com.investech.service.FundService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.List;

@Service
public class FundServiceImpl implements FundService {
    
    @Autowired
    private FundMapper fundMapper;
    
    @Override
    public List<Fund> findAll() {
        return fundMapper.findAll();
    }
    
    @Override
    public Fund findByFundCode(String fundCode) {
        return fundMapper.findByFundCode(fundCode);
    }
    
    @Override
    public Fund findById(Long id) {
        return fundMapper.findById(id);
    }
    
    @Override
    public List<Fund> searchFunds(String fundCode, String fundName, String fundCompany, 
                                 String fundManager, String fundType, String riskLevel, String tags) {
        return fundMapper.findByCondition(fundCode, fundName, fundCompany, fundManager, fundType, riskLevel, tags);
    }
    
    @Override
    public List<Fund> findByCompany(String fundCompany) {
        return fundMapper.findByCompany(fundCompany);
    }
    
    @Override
    public List<Fund> findByManager(String fundManager) {
        return fundMapper.findByManager(fundManager);
    }
    
    @Override
    public boolean addFund(Fund fund) {
        fund.setStatus(1);
        LocalDate now = LocalDate.now();
        fund.setCreateTime(now);
        fund.setUpdateTime(now);
        return fundMapper.insert(fund) > 0;
    }
    
    @Override
    public boolean updateFund(Fund fund) {
        fund.setUpdateTime(LocalDate.now());
        return fundMapper.update(fund) > 0;
    }
    
    @Override
    public boolean deleteFund(Long id) {
        return fundMapper.deleteById(id) > 0;
    }
} 