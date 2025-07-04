package com.investech.service;

import com.investech.entity.Factor;
import java.util.List;

public interface FactorService {
    List<Factor> findAll();
    
    Factor findByFactorCode(String factorCode);
    
    Factor findById(Long id);
    
    List<Factor> findByType(String factorType);
    
    List<Factor> findByCategory(String category);
    
    List<Factor> findByParentCode(String parentCode);
    
    List<Factor> searchFactors(String factorCode, String factorName, String factorType, String category);
    
    boolean addFactor(Factor factor);
    
    boolean updateFactor(Factor factor);
    
    boolean deleteFactor(Long id);
} 