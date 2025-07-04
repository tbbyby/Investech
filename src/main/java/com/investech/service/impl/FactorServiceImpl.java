package com.investech.service.impl;

import com.investech.entity.Factor;
import com.investech.mapper.FactorMapper;
import com.investech.service.FactorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class FactorServiceImpl implements FactorService {
    
    @Autowired
    private FactorMapper factorMapper;
    
    @Override
    public List<Factor> findAll() {
        return factorMapper.findAll();
    }
    
    @Override
    public Factor findByFactorCode(String factorCode) {
        return factorMapper.findByFactorCode(factorCode);
    }
    
    @Override
    public Factor findById(Long id) {
        return factorMapper.findById(id);
    }
    
    @Override
    public List<Factor> findByType(String factorType) {
        return factorMapper.findByType(factorType);
    }
    
    @Override
    public List<Factor> findByCategory(String category) {
        return factorMapper.findByCategory(category);
    }
    
    @Override
    public List<Factor> findByParentCode(String parentCode) {
        return factorMapper.findByParentCode(parentCode);
    }
    
    @Override
    public List<Factor> searchFactors(String factorCode, String factorName, String factorType, String category) {
        return factorMapper.findByCondition(factorCode, factorName, factorType, category);
    }
    
    @Override
    public boolean addFactor(Factor factor) {
        factor.setStatus(1);
        LocalDateTime now = LocalDateTime.now();
        factor.setCreateTime(now);
        factor.setUpdateTime(now);
        return factorMapper.insert(factor) > 0;
    }
    
    @Override
    public boolean updateFactor(Factor factor) {
        factor.setUpdateTime(LocalDateTime.now());
        return factorMapper.update(factor) > 0;
    }
    
    @Override
    public boolean deleteFactor(Long id) {
        return factorMapper.deleteById(id) > 0;
    }
} 