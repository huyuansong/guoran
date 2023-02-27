package com.guoran.server.shen.sales.service;

import com.github.pagehelper.Page;
import com.guoran.server.common.search.PageQuery;
import com.guoran.server.shen.sales.vmodel.OrderManagementSalesOrderVM;


public interface OrderManagementSalesOrderService {
    Page<OrderManagementSalesOrderVM> findEntrysByPage(PageQuery pageQuery, OrderManagementSalesOrderVM orderManagementSalesOrderVM, String status);

    String saveEntry(OrderManagementSalesOrderVM orderManagementSalesOrderVM);

}
