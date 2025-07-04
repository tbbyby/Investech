package com.investech.service.impl;

import com.investech.entity.FactorTree;
import com.investech.mapper.FactorTreeMapper;
import com.investech.service.FactorTreeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class FactorTreeServiceImpl implements FactorTreeService {
    
    @Autowired
    private FactorTreeMapper factorTreeMapper;
    
    @Override
    public List<FactorTree> findAll() {
        return factorTreeMapper.findAll();
    }
    
    @Override
    public FactorTree findById(Long id) {
        return factorTreeMapper.findById(id);
    }
    
    @Override
    public List<FactorTree> findByBusinessScene(String businessScene) {
        return factorTreeMapper.findByBusinessScene(businessScene);
    }
    
    @Override
    public List<FactorTree> searchTrees(String treeName, String businessScene) {
        return factorTreeMapper.findByCondition(treeName, businessScene);
    }
    
    @Override
    public boolean createTree(FactorTree factorTree) {
        factorTree.setStatus(1);
        return factorTreeMapper.insert(factorTree) > 0;
    }
    
    @Override
    public boolean updateTree(FactorTree factorTree) {
        return factorTreeMapper.update(factorTree) > 0;
    }
    
    @Override
    public boolean deleteTree(Long id) {
        return factorTreeMapper.deleteById(id) > 0;
    }
} 