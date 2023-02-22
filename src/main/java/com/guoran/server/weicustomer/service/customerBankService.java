package com.guoran.server.weicustomer.service;


import com.guoran.server.weicustomer.model.customer_bank;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;


public interface customerBankService {
	List<customer_bank> findAll();
}
