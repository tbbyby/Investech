package com.investech.service;

import com.investech.entity.DerivedFactor;
import java.util.List;

public interface DerivedFactorService {
    List<DerivedFactor> findAll();
    
    DerivedFactor findByFactorCode(String factorCode);
    
    DerivedFactor findById(Long id);
    
    List<DerivedFactor> findByCreatorId(Long creatorId);
    
    List<DerivedFactor> findByCategory(String category);
    
    List<DerivedFactor> searchFactors(String factorCode, String factorName, String category, Long creatorId);
    
    boolean createFactor(DerivedFactor derivedFactor);
    
    boolean updateFactor(DerivedFactor derivedFactor);
    
    boolean deleteFactor(Long id);
} 