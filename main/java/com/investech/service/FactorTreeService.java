package com.investech.service;

import com.investech.entity.FactorTree;
import java.util.List;

public interface FactorTreeService {
    List<FactorTree> findAll();
    
    FactorTree findById(Long id);
    
    List<FactorTree> findByBusinessScene(String businessScene);
    
    List<FactorTree> searchTrees(String treeName, String businessScene);
    
    boolean createTree(FactorTree factorTree);
    
    boolean updateTree(FactorTree factorTree);
    
    boolean deleteTree(Long id);
} 