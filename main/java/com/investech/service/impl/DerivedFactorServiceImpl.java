package com.investech.service.impl;

import com.investech.entity.DerivedFactor;
import com.investech.mapper.DerivedFactorMapper;
import com.investech.service.DerivedFactorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class DerivedFactorServiceImpl implements DerivedFactorService {
    
    @Autowired
    private DerivedFactorMapper derivedFactorMapper;
    
    @Override
    public List<DerivedFactor> findAll() {
        return derivedFactorMapper.findAll();
    }
    
    @Override
    public DerivedFactor findByFactorCode(String factorCode) {
        return derivedFactorMapper.findByFactorCode(factorCode);
    }
    
    @Override
    public DerivedFactor findById(Long id) {
        return derivedFactorMapper.findById(id);
    }
    
    @Override
    public List<DerivedFactor> findByCreatorId(Long creatorId) {
        return derivedFactorMapper.findByCreatorId(creatorId);
    }
    
    @Override
    public List<DerivedFactor> findByCategory(String category) {
        return derivedFactorMapper.findByCategory(category);
    }
    
    @Override
    public List<DerivedFactor> searchFactors(String factorCode, String factorName, String category, Long creatorId) {
        return derivedFactorMapper.findByCondition(factorCode, factorName, category, creatorId);
    }
    
    @Override
    public boolean createFactor(DerivedFactor derivedFactor) {
        derivedFactor.setStatus(1);
        return derivedFactorMapper.insert(derivedFactor) > 0;
    }
    
    @Override
    public boolean updateFactor(DerivedFactor derivedFactor) {
        return derivedFactorMapper.update(derivedFactor) > 0;
    }
    
    @Override
    public boolean deleteFactor(Long id) {
        return derivedFactorMapper.deleteById(id) > 0;
    }
} 