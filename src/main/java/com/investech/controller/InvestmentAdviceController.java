package com.investech.controller;

import com.investech.common.Result;
import com.investech.entity.InvestmentAdvice;
import com.investech.service.InvestmentAdviceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/investment-advice")
@CrossOrigin
public class InvestmentAdviceController {
    
    @Autowired
    private InvestmentAdviceService investmentAdviceService;
    
    @GetMapping("/list")
    public Result<List<InvestmentAdvice>> getAllAdvices() {
        try {
            List<InvestmentAdvice> advices = investmentAdviceService.getAllAdvices();
            return Result.success(advices);
        } catch (Exception e) {
            return Result.error("获取投资建议列表失败：" + e.getMessage());
        }
    }
    
    @GetMapping("/{id}")
    public Result<InvestmentAdvice> getAdviceById(@PathVariable Long id) {
        try {
            InvestmentAdvice advice = investmentAdviceService.getAdviceById(id);
            if (advice != null) {
                return Result.success(advice);
            } else {
                return Result.error("投资建议不存在");
            }
        } catch (Exception e) {
            return Result.error("获取投资建议失败：" + e.getMessage());
        }
    }
    
    @GetMapping("/client/{clientId}")
    public Result<List<InvestmentAdvice>> getAdvicesByClientId(@PathVariable Long clientId) {
        try {
            List<InvestmentAdvice> advices = investmentAdviceService.getAdvicesByClientId(clientId);
            return Result.success(advices);
        } catch (Exception e) {
            return Result.error("获取客户投资建议失败：" + e.getMessage());
        }
    }
    
    @PostMapping("/search")
    public Result<List<InvestmentAdvice>> searchAdvices(@RequestBody InvestmentAdvice advice) {
        try {
            List<InvestmentAdvice> advices = investmentAdviceService.getAdvicesByCondition(advice);
            return Result.success(advices);
        } catch (Exception e) {
            return Result.error("搜索投资建议失败：" + e.getMessage());
        }
    }
    
    @PostMapping("/add")
    public Result<String> addAdvice(@RequestBody InvestmentAdvice advice) {
        try {
            if (investmentAdviceService.addAdvice(advice)) {
                return Result.success("添加投资建议成功");
            } else {
                return Result.error("添加投资建议失败");
            }
        } catch (Exception e) {
            return Result.error("添加投资建议失败：" + e.getMessage());
        }
    }
    
    @PutMapping("/update")
    public Result<String> updateAdvice(@RequestBody InvestmentAdvice advice) {
        try {
            if (investmentAdviceService.updateAdvice(advice)) {
                return Result.success("更新投资建议成功");
            } else {
                return Result.error("更新投资建议失败");
            }
        } catch (Exception e) {
            return Result.error("更新投资建议失败：" + e.getMessage());
        }
    }
    
    @DeleteMapping("/{id}")
    public Result<String> deleteAdvice(@PathVariable Long id) {
        try {
            if (investmentAdviceService.deleteAdvice(id)) {
                return Result.success("删除投资建议成功");
            } else {
                return Result.error("删除投资建议失败");
            }
        } catch (Exception e) {
            return Result.error("删除投资建议失败：" + e.getMessage());
        }
    }
    
    @PutMapping("/{id}/status")
    public Result<String> updateAdviceStatus(@PathVariable Long id, @RequestParam Integer status) {
        try {
            if (investmentAdviceService.updateAdviceStatus(id, status)) {
                return Result.success("更新投资建议状态成功");
            } else {
                return Result.error("更新投资建议状态失败");
            }
        } catch (Exception e) {
            return Result.error("更新投资建议状态失败：" + e.getMessage());
        }
    }
} 