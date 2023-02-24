package com.guoran.server.wei.customer.service;

import com.github.pagehelper.Page;
import com.guoran.server.wei.customer.model.CustomerBalance;

/**
 * @author Wei
 */
public interface CustomerBanlanceService {
	/**
	 * 查询所有CustomerBank
	 *
	 * @return List<CustomerBank>
	 */
	Page<CustomerBalance> findAll(
			String size,
			String page
	);

	Page<CustomerBalance> findByName(String name);
}
