package com.guoran.server.wei_customer.repository;



import com.guoran.server.wei_customer.model.customer_bank;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface balancerepostiory {
	List<customer_bank> findAll();
}
