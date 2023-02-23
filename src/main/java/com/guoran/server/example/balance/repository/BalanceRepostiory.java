package com.guoran.server.example.balance.repository;


import com.guoran.server.example.balance.model.Balance;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface BalanceRepostiory {
	List<Balance> findAll();
}
