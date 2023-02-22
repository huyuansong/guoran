package com.guoran.server.shensales.resouce;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.pagehelper.Page;
import com.guoran.server.common.search.PageQuery;
import com.guoran.server.common.search.PageResult;
import com.guoran.server.shensales.service.OrderManagementSalesOrderService;
import com.guoran.server.shensales.vmodel.OrderManagementSalesOrderVM;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = {"销售订单表主键"})
@RestController
@RequestMapping("/sales/OrderManagementSalesOrderEntity")
public class OrderManagementSalesOrderResource {
    @Autowired
    private OrderManagementSalesOrderService orderManagementSalesOrderService;
/*    @Autowired
    private ManagementSalesDetailsService managementSalesDetailsService;*/
    @Autowired
    ObjectMapper objectMapper;
/*    @Autowired
    MenuService menuService;*/

    @ApiOperation(value = "查询分页数据")
    @RequestMapping(value = "/page",method = RequestMethod.POST)
    public String getEntryByPage(@RequestBody PageQuery pageQuery, OrderManagementSalesOrderVM orderManagementSalesOrderVM, String status){
        Page<OrderManagementSalesOrderVM> pages = orderManagementSalesOrderService.findEntrysByPage(pageQuery, orderManagementSalesOrderVM, status);
        PageResult pageResult=new PageResult();

        return "";
    }
}
