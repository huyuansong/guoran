package com.guoran.server;

import com.guoran.server.example.balance.repository.balancerepostiory;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest
public class GuoranServerApplicationTests {
    @Autowired
    private balancerepostiory balancerePostiory;
    @Autowired
    private com.guoran.server.wei.customer.repository.CustomerBankRepository CustomerBankRepository;

    @Test
    void contextLoads2() {
        System.out.println(balancerePostiory.findAll());
    }

    @Test
    void contextLoads() {
        System.out.println(CustomerBankRepository.findAll());
    }

}
