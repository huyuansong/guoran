package com.guoran.server.shen.sales.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.guoran.server.common.JsonResult;
import com.guoran.server.common.search.DynamicSearch;
import com.guoran.server.common.search.FilterGroup;
import com.guoran.server.common.search.PageQuery;
import com.guoran.server.generator.CodeGenerator;
import com.guoran.server.security.JwtUserUtil;
import com.guoran.server.shen.sales.model.OrderManagementSalesOrderEntity;
import com.guoran.server.shen.sales.repository.OrderManagementSalesOrderRepository;
import com.guoran.server.shen.sales.service.OrderManagementSalesOrderService;
import com.guoran.server.shen.sales.vmodel.OrderManagementSalesOrderVM;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class OrderManagementSalesOrderServiceImpl implements OrderManagementSalesOrderService {
    @Autowired
    OrderManagementSalesOrderRepository orderManagementSalesOrderRepository;
    @Autowired
    ObjectMapper objectMapper;
    @Qualifier(value = "SALES")
    CodeGenerator salesOrderGenerator;
    @Autowired
    JwtUserUtil jwtUserUtil;


    @Override
    public Page<OrderManagementSalesOrderVM> findEntrysByPage(PageQuery pageQuery, OrderManagementSalesOrderVM orderManagementSalesOrderVM, String status) {

        FilterGroup filterGroup = pageQuery.getWhere();
        //自动转字符串
        String where = DynamicSearch.getInstance().buildWhere(filterGroup);
        if (status != null) {
            where += " and ( audit_Status='2' or audit_Status='1'  or audit_Status ='7')";
        }
        if (orderManagementSalesOrderVM.getSalesOrderNumber() != null) {
            where += " and sales_Order_Number like '%" + orderManagementSalesOrderVM.getSalesOrderNumber() + "%'";
        }
        if (orderManagementSalesOrderVM.getSalesperson() != null) {
            where += " and salesperson like '%" + orderManagementSalesOrderVM.getSalesperson() + "%'";
        }
        PageHelper.startPage(pageQuery.getPageNum(), pageQuery.getPageSize(), pageQuery.getOrderBy());

        return orderManagementSalesOrderRepository.findEntrysByPage(where);

    }

    @Override
    public String saveEntry(OrderManagementSalesOrderVM orderManagementSalesOrderVM) {
        String resut = null;
        JsonResult jsonResult = new JsonResult();
        if (orderManagementSalesOrderVM.getOrderMangProductDetailedVmList().size() < 1) {
            jsonResult.setMessage("此订单没有商品，请添加商品");
            jsonResult.setIsSuccess(false);
            try {
                resut = objectMapper.writeValueAsString(jsonResult);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
            return resut;
        }

        OrderManagementSalesOrderEntity orderManagementSalesOrderEntity = new OrderManagementSalesOrderEntity();
        BeanUtils.copyProperties(orderManagementSalesOrderVM, orderManagementSalesOrderEntity);
        String salelOrderCode = salesOrderGenerator.generateCode();
        orderManagementSalesOrderEntity.setSalesOrderNumber(salelOrderCode);
        orderManagementSalesOrderEntity.setCreateBy(jwtUserUtil.getUserName());
        return "";
    }

}
