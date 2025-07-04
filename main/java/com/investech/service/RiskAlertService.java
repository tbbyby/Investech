package com.investech.service;

import com.investech.entity.RiskAlert;
import java.util.List;

public interface RiskAlertService {
    List<RiskAlert> getAllAlerts();
    RiskAlert getAlertById(Long id);
    List<RiskAlert> getAlertsByCondition(RiskAlert alert);
    boolean addAlert(RiskAlert alert);
    boolean updateAlert(RiskAlert alert);
    boolean deleteAlert(Long id);
    boolean updateAlertStatus(Long id, Integer status);
} 