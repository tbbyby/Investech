package com.investech.service;

import com.investech.entity.Fund;
import java.util.List;

public interface FundService {
    List<Fund> findAll();
    
    Fund findByFundCode(String fundCode);
    
    Fund findById(Long id);
    
    List<Fund> searchFunds(String fundCode, String fundName, String fundCompany, 
                          String fundManager, String fundType, String riskLevel, String tags);
    
    List<Fund> findByCompany(String fundCompany);
    
    List<Fund> findByManager(String fundManager);
    
    boolean addFund(Fund fund);
    
    boolean updateFund(Fund fund);
    
    boolean deleteFund(Long id);
} 