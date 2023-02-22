package com.guoran.server.test.repository;

import com.guoran.server.test.model.balance;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface balancerepostiory {
	List<balance> findAll();
}
