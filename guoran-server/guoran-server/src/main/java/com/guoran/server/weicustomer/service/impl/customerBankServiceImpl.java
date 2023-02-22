package com.guoran.server.weicustomer.service.impl;


import com.guoran.server.weicustomer.model.customer_bank;
import com.guoran.server.weicustomer.repository.customerBankRepository;
import com.guoran.server.weicustomer.service.customerBankService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class customerBankServiceImpl implements customerBankService {

	@Autowired
	private customerBankRepository customerBankRepository;
	@Override
	public List<customer_bank> findAll() {
		return customerBankRepository.findAll();
	}

}
