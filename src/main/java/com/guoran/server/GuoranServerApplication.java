package com.guoran.server;


import org.mybatis.spring.annotation.MapperScan;
import org.mybatis.spring.annotation.MapperScans;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;


//@MapperScan ("com.guoran.server.test.repository")
@MapperScans(value = {
		@MapperScan("com.guoran.server.weicustomer.repository"),
		@MapperScan("com.guoran.server.test.repository"),
		@MapperScan("com.guoran.server.shensales.repository")
})
@EnableTransactionManagement
@SpringBootApplication
public class GuoranServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(GuoranServerApplication.class, args);
	}

}
