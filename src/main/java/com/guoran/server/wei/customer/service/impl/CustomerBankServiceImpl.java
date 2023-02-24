package com.guoran.server.wei.customer.service.impl;


import com.github.pagehelper.Page;
import com.guoran.server.common.search.PageQuery;
import com.guoran.server.wei.customer.model.CustomerBank;
import com.guoran.server.wei.customer.repository.CustomerBankRepository;
import com.guoran.server.wei.customer.service.CustomerBankService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Wei
 */
@Service
public class CustomerBankServiceImpl implements CustomerBankService {

	@Autowired
	private CustomerBankRepository customerBankRepository;

	@Override
	public Page<CustomerBank> findAll(PageQuery page) {
		return customerBankRepository.findAll(page);
	}

}
