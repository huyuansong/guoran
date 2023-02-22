package com.guoran.server.shensales.service.impl;

import com.github.pagehelper.Page;
import com.guoran.server.common.search.PageQuery;
import com.guoran.server.shensales.repository.OrderManagementSalesOrderRepository;
import com.guoran.server.shensales.service.OrderManagementSalesOrderService;
import com.guoran.server.shensales.vmodel.OrderManagementSalesOrderVM;
import org.springframework.beans.factory.annotation.Autowired;

public class OrderManagementSalesOrderServiceImpl implements OrderManagementSalesOrderService {


    @Autowired
    OrderManagementSalesOrderRepository orderManagementSalesOrderRepository;

    @Override
    public Page<OrderManagementSalesOrderVM>  findEntrysByPage(PageQuery pageQuery, OrderManagementSalesOrderVM orderManagementSalesOrderVM, String status) {

        // orderManagementSalesOrderRepository.findEntrysByPage(pageQuery,orderManagementSalesOrderVM,status);

        return null;
    }
}
