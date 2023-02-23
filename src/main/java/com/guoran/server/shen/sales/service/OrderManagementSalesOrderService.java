package com.guoran.server.shen.sales.service;

import com.github.pagehelper.Page;
import com.guoran.server.common.search.PageQuery;
import com.guoran.server.shen.sales.vmodel.OrderManagementSalesOrderVM;


public interface OrderManagementSalesOrderService {
    public Page<OrderManagementSalesOrderVM> findEntrysByPage(PageQuery pageQuery, OrderManagementSalesOrderVM orderManagementSalesOrderVM, String status);
}
