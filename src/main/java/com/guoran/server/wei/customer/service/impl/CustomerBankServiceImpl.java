package com.guoran.server.wei.customer.service.impl;


import com.guoran.server.wei.customer.model.CustomerBank;
import com.guoran.server.wei.customer.repository.CustomerBankRepository;
import com.guoran.server.wei.customer.service.CustomerBankService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Wei
 */
@Service
public class CustomerBankServiceImpl implements CustomerBankService {

	@Autowired
	private CustomerBankRepository customerBankRepository;

	@Override
	public List<CustomerBank> findAll(String size, String page) {
		return customerBankRepository.findAll(Integer.parseInt(size), Integer.parseInt(page));
	}

}
