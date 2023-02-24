package com.guoran.server.wei.customer.repository;

import com.github.pagehelper.Page;
import com.guoran.server.common.search.PageQuery;
import com.guoran.server.wei.customer.model.CustomerBalance;
import org.apache.ibatis.annotations.Mapper;

/**
 * 客户管理-客户银行信息
 *
 * @author Wei
 * @TableName customer_bank
 */
@Mapper
public interface CustomerBalanceRepository {
	Page<CustomerBalance> findAll(
			PageQuery page
	);
}
