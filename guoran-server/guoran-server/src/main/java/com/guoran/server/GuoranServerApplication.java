package com.guoran.server;

import org.mybatis.spring.annotation.MapperScan;
import org.mybatis.spring.annotation.MapperScans;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;



@MapperScans (value = {
		@MapperScan("com.guoran.server.test.repository"),
		@MapperScan("com.guoran.server.wei_customer.repository")
})
@SpringBootApplication
public class GuoranServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(GuoranServerApplication.class, args);
	}

}
