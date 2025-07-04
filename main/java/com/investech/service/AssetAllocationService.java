package com.investech.service;

import com.investech.entity.AssetAllocation;
import java.util.List;

public interface AssetAllocationService {
    List<AssetAllocation> getAllAllocations();
    AssetAllocation getAllocationById(Long id);
    List<AssetAllocation> getAllocationsByClientId(Long clientId);
    List<AssetAllocation> getAllocationsByCondition(AssetAllocation allocation);
    boolean addAllocation(AssetAllocation allocation);
    boolean updateAllocation(AssetAllocation allocation);
    boolean deleteAllocation(Long id);
    boolean updateAllocationStatus(Long id, Integer status);
} 