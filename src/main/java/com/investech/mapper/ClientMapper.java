package com.investech.mapper;

import com.investech.entity.Client;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;

@Mapper
public interface ClientMapper {
    List<Client> selectAll();
    Client selectById(Long id);
    List<Client> selectByCondition(Client client);
    int insert(Client client);
    int update(Client client);
    int deleteById(Long id);
    int updateStatus(Long id, Integer status);
} 