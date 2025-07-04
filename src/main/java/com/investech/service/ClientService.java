package com.investech.service;

import com.investech.entity.Client;
import java.util.List;

public interface ClientService {
    List<Client> getAllClients();
    Client getClientById(Long id);
    List<Client> getClientsByCondition(Client client);
    boolean addClient(Client client);
    boolean updateClient(Client client);
    boolean deleteClient(Long id);
    boolean updateClientStatus(Long id, Integer status);
} 