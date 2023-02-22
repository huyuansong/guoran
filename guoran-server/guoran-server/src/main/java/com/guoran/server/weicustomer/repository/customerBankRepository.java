package com.guoran.server.weicustomer.repository;


import com.guoran.server.weicustomer.model.customer_bank;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface customerBankRepository {
	List<customer_bank> findAll();
}
