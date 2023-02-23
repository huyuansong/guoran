package com.guoran.server.shen.sales.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.guoran.server.common.search.DynamicSearch;
import com.guoran.server.common.search.FilterGroup;
import com.guoran.server.common.search.PageQuery;
import com.guoran.server.shen.sales.repository.OrderManagementSalesOrderRepository;
import com.guoran.server.shen.sales.service.OrderManagementSalesOrderService;
import com.guoran.server.shen.sales.vmodel.OrderManagementSalesOrderVM;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class OrderManagementSalesOrderServiceImpl implements OrderManagementSalesOrderService {


    @Autowired
    OrderManagementSalesOrderRepository orderManagementSalesOrderRepository;

    @Override
    public Page<OrderManagementSalesOrderVM>  findEntrysByPage(PageQuery pageQuery, OrderManagementSalesOrderVM orderManagementSalesOrderVM, String status) {

        FilterGroup filterGroup=pageQuery.getWhere();
        //自动转字符串
        String where= DynamicSearch.getInstance().buildWhere(filterGroup);
        if(status!=null){
            where+=" and ( audit_Status='2' or audit_Status='1'  or audit_Status ='7')";
        }
        if(orderManagementSalesOrderVM.getSalesOrderNumber()!=null){
            where+=" and sales_Order_Number like '%"+orderManagementSalesOrderVM.getSalesOrderNumber()+"%'";
        }
        if(orderManagementSalesOrderVM.getSalesperson()!=null){
            where+=" and salesperson like '%"+orderManagementSalesOrderVM.getSalesperson()+"%'";
        }
        PageHelper.startPage(pageQuery.getPageNum(),pageQuery.getPageSize(),pageQuery.getOrderBy());
        System.out.println(where+"llllllll");
        return orderManagementSalesOrderRepository.findEntrysByPage(where);

     }
}
