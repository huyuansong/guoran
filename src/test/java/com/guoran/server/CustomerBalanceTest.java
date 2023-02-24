package com.guoran.server;

import com.guoran.server.wei.customer.repository.CustomerBalanceRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class CustomerBalanceTest {
	@Autowired
	private CustomerBalanceRepository customerBalanceRepository;

	@Test
	public void contextLoads() {
		System.out.println(customerBalanceRepository.findAll(10, 1));
	}
}
