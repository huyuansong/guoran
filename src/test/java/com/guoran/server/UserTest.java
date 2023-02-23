package com.guoran.server;

import com.guoran.server.test.repository.BalanceRepostiory;
import com.guoran.server.wei.customer.service.CustomerBankService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class UserTest {
	@Autowired
	private CustomerBankService customerBankService;

	@Autowired
	private BalanceRepostiory balanceRepostiory;


	@Test
	void test() {
		System.out.println(balanceRepostiory.findAll());
	}

	@Test
	void contex() {
		System.out.println(customerBankService.findAll());
	}
}
