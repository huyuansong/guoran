package com.guoran.server.wei.customer.repository;


import com.guoran.server.wei.customer.model.CustomerBank;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

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
			@Param ("size") String size,
			@Param ("page") String page
	);
}
