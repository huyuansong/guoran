package com.guoran.server.wei.customer.service;

import com.guoran.server.wei.customer.model.CustomerBank;

import java.util.List;

/**
 * @author Wei
 */
public interface CustomerBankService {
	/**
	 * 查询所有CustomerBank
	 *
	 * @return List<CustomerBank>
	 */
	List<CustomerBank> findAll(
			String size,
			String page
	);
}
