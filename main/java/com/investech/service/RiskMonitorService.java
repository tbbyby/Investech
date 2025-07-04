package com.investech.service;

import com.investech.entity.RiskMonitor;
import java.util.List;

public interface RiskMonitorService {
    List<RiskMonitor> getAllMonitors();
    RiskMonitor getMonitorById(Long id);
    List<RiskMonitor> getMonitorsByCondition(RiskMonitor monitor);
    boolean addMonitor(RiskMonitor monitor);
    boolean updateMonitor(RiskMonitor monitor);
    boolean deleteMonitor(Long id);
    boolean updateMonitorStatus(Long id, Integer status);
} 