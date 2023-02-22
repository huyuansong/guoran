package com.guoran.server.shensales.service;

import com.github.pagehelper.Page;
import com.guoran.server.common.search.PageQuery;
import com.guoran.server.shensales.vmodel.OrderManagementSalesOrderVM;
import org.springframework.stereotype.Service;


public interface OrderManagementSalesOrderService {
    public Page<OrderManagementSalesOrderVM> findEntrysByPage(PageQuery pageQuery, OrderManagementSalesOrderVM orderManagementSalesOrderVM, String status);
}
