package com.guoran.server;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import com.guoran.server.test.repository.balancerepostiory;


@SpringBootTest
public class GuoranServerApplicationTests {


	@Autowired
	private balancerepostiory balancerepostiory;
	@Test
	void contextLoads() {
		System.out.println(balancerepostiory.findAll());
	}

}
