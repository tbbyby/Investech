package com.investech.service;

import com.investech.entity.ComplianceCheck;
import java.util.List;

public interface ComplianceCheckService {
    List<ComplianceCheck> getAllChecks();
    ComplianceCheck getCheckById(Long id);
    List<ComplianceCheck> getChecksByCondition(ComplianceCheck check);
    boolean addCheck(ComplianceCheck check);
    boolean updateCheck(ComplianceCheck check);
    boolean deleteCheck(Long id);
    boolean updateCheckStatus(Long id, Integer status);
} 