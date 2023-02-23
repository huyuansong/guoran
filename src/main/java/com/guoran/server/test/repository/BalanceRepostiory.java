package com.guoran.server.test.repository;


import com.guoran.server.test.model.Balance;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface BalanceRepostiory {
	List<Balance> findAll();
}
