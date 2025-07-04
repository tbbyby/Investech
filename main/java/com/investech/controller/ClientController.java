package com.investech.controller;

import com.investech.common.Result;
import com.investech.entity.Client;
import com.investech.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/client")
@CrossOrigin
public class ClientController {
    
    @Autowired
    private ClientService clientService;
    
    @GetMapping("/list")
    public Result<List<Client>> getAllClients() {
        try {
            List<Client> clients = clientService.getAllClients();
            return Result.success(clients);
        } catch (Exception e) {
            return Result.error("获取客户列表失败：" + e.getMessage());
        }
    }
    
    @GetMapping("/{id}")
    public Result<Client> getClientById(@PathVariable Long id) {
        try {
            Client client = clientService.getClientById(id);
            if (client != null) {
                return Result.success(client);
            } else {
                return Result.error("客户不存在");
            }
        } catch (Exception e) {
            return Result.error("获取客户信息失败：" + e.getMessage());
        }
    }
    
    @PostMapping("/search")
    public Result<List<Client>> searchClients(@RequestBody Client client) {
        try {
            List<Client> clients = clientService.getClientsByCondition(client);
            return Result.success(clients);
        } catch (Exception e) {
            return Result.error("搜索客户失败：" + e.getMessage());
        }
    }
    
    @PostMapping("/add")
    public Result<String> addClient(@RequestBody Client client) {
        try {
            if (clientService.addClient(client)) {
                return Result.success("添加客户成功");
            } else {
                return Result.error("添加客户失败");
            }
        } catch (Exception e) {
            return Result.error("添加客户失败：" + e.getMessage());
        }
    }
    
    @PutMapping("/update")
    public Result<String> updateClient(@RequestBody Client client) {
        try {
            if (clientService.updateClient(client)) {
                return Result.success("更新客户成功");
            } else {
                return Result.error("更新客户失败");
            }
        } catch (Exception e) {
            return Result.error("更新客户失败：" + e.getMessage());
        }
    }
    
    @DeleteMapping("/{id}")
    public Result<String> deleteClient(@PathVariable Long id) {
        try {
            if (clientService.deleteClient(id)) {
                return Result.success("删除客户成功");
            } else {
                return Result.error("删除客户失败");
            }
        } catch (Exception e) {
            return Result.error("删除客户失败：" + e.getMessage());
        }
    }
    
    @PutMapping("/{id}/status")
    public Result<String> updateClientStatus(@PathVariable Long id, @RequestParam Integer status) {
        try {
            if (clientService.updateClientStatus(id, status)) {
                return Result.success("更新客户状态成功");
            } else {
                return Result.error("更新客户状态失败");
            }
        } catch (Exception e) {
            return Result.error("更新客户状态失败：" + e.getMessage());
        }
    }
} 