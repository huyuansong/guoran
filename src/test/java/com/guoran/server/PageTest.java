package com.guoran.server;

import com.guoran.server.common.search.PageQuery;
import com.guoran.server.wei.customer.repository.CustomerBalanceRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class PageTest {
	@Autowired
	CustomerBalanceRepository customerBalanceRepository;

	@Test
	public void contextLoads() {
		PageQuery query = new PageQuery();
		query.setPageNum(0);
		query.setPageSize(10);
		System.out.println(customerBalanceRepository.findAll(query));
	}
}
