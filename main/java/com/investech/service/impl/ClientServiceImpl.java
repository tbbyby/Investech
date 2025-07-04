package com.investech.service.impl;

import com.investech.entity.Client;
import com.investech.mapper.ClientMapper;
import com.investech.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ClientServiceImpl implements ClientService {
    
    @Autowired
    private ClientMapper clientMapper;
    
    @Override
    public List<Client> getAllClients() {
        return clientMapper.selectAll();
    }
    
    @Override
    public Client getClientById(Long id) {
        return clientMapper.selectById(id);
    }
    
    @Override
    public List<Client> getClientsByCondition(Client client) {
        return clientMapper.selectByCondition(client);
    }
    
    @Override
    public boolean addClient(Client client) {
        return clientMapper.insert(client) > 0;
    }
    
    @Override
    public boolean updateClient(Client client) {
        return clientMapper.update(client) > 0;
    }
    
    @Override
    public boolean deleteClient(Long id) {
        return clientMapper.deleteById(id) > 0;
    }
    
    @Override
    public boolean updateClientStatus(Long id, Integer status) {
        return clientMapper.updateStatus(id, status) > 0;
    }
} 