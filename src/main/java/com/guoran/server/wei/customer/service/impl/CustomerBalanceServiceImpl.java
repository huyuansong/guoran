package com.guoran.server.wei.customer.service.impl;


import com.github.pagehelper.Page;
import com.guoran.server.wei.customer.model.CustomerBalance;
import com.guoran.server.wei.customer.repository.CustomerBalanceRepository;
import com.guoran.server.wei.customer.service.CustomerBanlanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Wei
 */
@Service
public class CustomerBalanceServiceImpl implements CustomerBanlanceService {
	@Autowired
	private CustomerBalanceRepository customerBalanceRepository;


	@Override
	public Page<CustomerBalance> findAll(String size, String page) {

		return customerBalanceRepository.findAll(Integer.parseInt(size), Integer.parseInt(page));
	}

	@Override
	public Page<CustomerBalance> findByName(String name) {
		return null;
	}
}
