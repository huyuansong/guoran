package com.guoran.server.wei.customer.service;

import com.github.pagehelper.Page;
import com.guoran.server.common.search.PageQuery;
import com.guoran.server.wei.customer.model.CustomerBank;

/**
 * @author Wei
 */
public interface CustomerBankService {
	/**
	 * 查询所有CustomerBank
	 *
	 * @return List<CustomerBank>
	 */
	Page<CustomerBank> findAll(
			PageQuery page
	);
}
