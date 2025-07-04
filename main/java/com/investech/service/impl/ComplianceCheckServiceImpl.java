package com.investech.service.impl;

import com.investech.entity.ComplianceCheck;
import com.investech.mapper.ComplianceCheckMapper;
import com.investech.service.ComplianceCheckService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ComplianceCheckServiceImpl implements ComplianceCheckService {
    
    @Autowired
    private ComplianceCheckMapper complianceCheckMapper;
    
    @Override
    public List<ComplianceCheck> getAllChecks() {
        return complianceCheckMapper.selectAll();
    }
    
    @Override
    public ComplianceCheck getCheckById(Long id) {
        return complianceCheckMapper.selectById(id);
    }
    
    @Override
    public List<ComplianceCheck> getChecksByCondition(ComplianceCheck check) {
        return complianceCheckMapper.selectByCondition(check);
    }
    
    @Override
    public boolean addCheck(ComplianceCheck check) {
        return complianceCheckMapper.insert(check) > 0;
    }
    
    @Override
    public boolean updateCheck(ComplianceCheck check) {
        return complianceCheckMapper.update(check) > 0;
    }
    
    @Override
    public boolean deleteCheck(Long id) {
        return complianceCheckMapper.deleteById(id) > 0;
    }
    
    @Override
    public boolean updateCheckStatus(Long id, Integer status) {
        return complianceCheckMapper.updateStatus(id, status) > 0;
    }
} 