package com.guoran.server.example.balance.repository;


import com.guoran.server.example.balance.model.balance;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author Wei
 */
@Mapper
public interface balancerepostiory {
	List<balance> findAll();
}
