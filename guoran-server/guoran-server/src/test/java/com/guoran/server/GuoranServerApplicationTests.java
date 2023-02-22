package com.guoran.server;

import com.guoran.server.test.repository.balancerepostiory;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import com.guoran.server.weicustomer.repository.customerBankRepository;

@SpringBootTest
public class GuoranServerApplicationTests {

//	@Autowired
//	private balancerepostiory balancerePostiory;
//
//	@Test
//	void contextLoads2() {
//		System.out.println(balancerePostiory.findAll());
//	}


	@Autowired
	private customerBankRepository CustomerBankRepository;
	@Test
	void contextLoads() {
		System.out.println(CustomerBankRepository.findAll());
	}

}
