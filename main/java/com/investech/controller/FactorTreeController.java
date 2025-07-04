package com.investech.controller;

import com.investech.common.Result;
import com.investech.entity.FactorTree;
import com.investech.service.FactorTreeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/factor-tree")
@CrossOrigin(origins = "*")
public class FactorTreeController {
    
    @Autowired
    private FactorTreeService factorTreeService;
    
    @GetMapping("/list")
    public Result<List<FactorTree>> getAllTrees() {
        List<FactorTree> trees = factorTreeService.findAll();
        return Result.success(trees);
    }
    
    @GetMapping("/{id}")
    public Result<FactorTree> getTreeById(@PathVariable Long id) {
        FactorTree tree = factorTreeService.findById(id);
        if (tree != null) {
            return Result.success(tree);
        } else {
            return Result.error("因子树不存在");
        }
    }
    
    @GetMapping("/scene/{businessScene}")
    public Result<List<FactorTree>> getTreesByScene(@PathVariable String businessScene) {
        List<FactorTree> trees = factorTreeService.findByBusinessScene(businessScene);
        return Result.success(trees);
    }
    
    @GetMapping("/search")
    public Result<List<FactorTree>> searchTrees(
            @RequestParam(required = false) String treeName,
            @RequestParam(required = false) String businessScene) {
        
        List<FactorTree> trees = factorTreeService.searchTrees(treeName, businessScene);
        return Result.success(trees);
    }
    
    @PostMapping("/create")
    public Result<String> createTree(@RequestBody FactorTree factorTree) {
        if (factorTreeService.createTree(factorTree)) {
            return Result.success("创建成功", null);
        } else {
            return Result.error("创建失败");
        }
    }
    
    @PutMapping("/update")
    public Result<String> updateTree(@RequestBody FactorTree factorTree) {
        if (factorTreeService.updateTree(factorTree)) {
            return Result.success("更新成功", null);
        } else {
            return Result.error("更新失败");
        }
    }
    
    @DeleteMapping("/{id}")
    public Result<String> deleteTree(@PathVariable Long id) {
        if (factorTreeService.deleteTree(id)) {
            return Result.success("删除成功", null);
        } else {
            return Result.error("删除失败");
        }
    }
} 