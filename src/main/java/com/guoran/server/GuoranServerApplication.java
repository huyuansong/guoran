package com.guoran.server;


import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


/**
 * @author Wei
 */

@MapperScan ("com.guoran.server.*.*.repository")
@SpringBootApplication
public class GuoranServerApplication {
	public static void main(String[] args) {
		SpringApplication.run(GuoranServerApplication.class, args);

	}
}
