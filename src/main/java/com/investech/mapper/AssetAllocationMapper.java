package com.investech.mapper;

import com.investech.entity.AssetAllocation;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;

@Mapper
public interface AssetAllocationMapper {
    List<AssetAllocation> selectAll();
    AssetAllocation selectById(Long id);
    List<AssetAllocation> selectByClientId(Long clientId);
    List<AssetAllocation> selectByCondition(AssetAllocation allocation);
    int insert(AssetAllocation allocation);
    int update(AssetAllocation allocation);
    int deleteById(Long id);
    int updateStatus(Long id, Integer status);
} 