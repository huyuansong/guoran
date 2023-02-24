package com.guoran.server.wei.customer.repository;


import com.guoran.server.common.search.PageQuery;
import com.guoran.server.wei.customer.model.CustomerBank;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author Wei
 */
@Mapper
public interface CustomerBankRepository {

	/**
	 * 查询所有
	 *
	 * @return List<CustomerBank>
	 */

	List<CustomerBank> findAll(
			PageQuery page
	);
}
