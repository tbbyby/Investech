package com.investech.service.impl;

import com.investech.entity.AssetAllocation;
import com.investech.mapper.AssetAllocationMapper;
import com.investech.service.AssetAllocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class AssetAllocationServiceImpl implements AssetAllocationService {
    
    @Autowired
    private AssetAllocationMapper assetAllocationMapper;
    
    @Override
    public List<AssetAllocation> getAllAllocations() {
        return assetAllocationMapper.selectAll();
    }
    
    @Override
    public AssetAllocation getAllocationById(Long id) {
        return assetAllocationMapper.selectById(id);
    }
    
    @Override
    public List<AssetAllocation> getAllocationsByClientId(Long clientId) {
        return assetAllocationMapper.selectByClientId(clientId);
    }
    
    @Override
    public List<AssetAllocation> getAllocationsByCondition(AssetAllocation allocation) {
        return assetAllocationMapper.selectByCondition(allocation);
    }
    
    @Override
    public boolean addAllocation(AssetAllocation allocation) {
        return assetAllocationMapper.insert(allocation) > 0;
    }
    
    @Override
    public boolean updateAllocation(AssetAllocation allocation) {
        return assetAllocationMapper.update(allocation) > 0;
    }
    
    @Override
    public boolean deleteAllocation(Long id) {
        return assetAllocationMapper.deleteById(id) > 0;
    }
    
    @Override
    public boolean updateAllocationStatus(Long id, Integer status) {
        return assetAllocationMapper.updateStatus(id, status) > 0;
    }
} 