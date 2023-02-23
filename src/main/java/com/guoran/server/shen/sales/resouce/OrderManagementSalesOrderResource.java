package com.guoran.server.shen.sales.resouce;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.pagehelper.Page;
import com.guoran.server.common.Result;
import com.guoran.server.common.exception.ImErrorCode;
import com.guoran.server.common.exception.ServiceException;
import com.guoran.server.common.i18n.MessageUtils;
import com.guoran.server.common.search.PageQuery;
import com.guoran.server.common.search.PageResult;
import com.guoran.server.shen.sales.service.OrderManagementSalesOrderService;
import com.guoran.server.shen.sales.vmodel.OrderManagementSalesOrderVM;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@Api (tags = {"销售订单表主键"})
@RestController
@RequestMapping ("/sales/OrderManagementSalesOrderEntity")
public class OrderManagementSalesOrderResource {
    /*    @Autowired
		private ManagementSalesDetailsService managementSalesDetailsService;*/
    @Autowired
    ObjectMapper objectMapper;
    @Autowired
    private OrderManagementSalesOrderService orderManagementSalesOrderService;
/*    @Autowired
    MenuService menuService;*/

    @ApiOperation (value = "查询分页数据")
    @RequestMapping (value = "/page", method = RequestMethod.POST)
    public String getEntryByPage(@RequestBody PageQuery pageQuery, OrderManagementSalesOrderVM orderManagementSalesOrderVM, String status) {
        String result = null;

        System.out.println("aaaaddddddddf");
        try {
            Page<OrderManagementSalesOrderVM> pages = orderManagementSalesOrderService.findEntrysByPage(pageQuery, orderManagementSalesOrderVM, status);

            PageResult pageResult = new PageResult();
            pageResult.setPageNum(pageQuery.getPageNum());
            pageResult.setRows(pages);
            pageResult.setTotal(pageResult.getTotal());
            pageResult.setPages(pageResult.getPages());
            result = Result.success(ImErrorCode.MSG_SUCCESS, MessageUtils.get(ImErrorCode.MSG_SUCCESS), pageResult);
            System.out.println(result);

        } catch (ServiceException serviceException) {
            throw serviceException;
        } catch (Exception e) {
            ServiceException se = new ServiceException(ImErrorCode.MSG_FAIL, MessageUtils.get(ImErrorCode.MSG_FAIL));
            throw se;
        }

        return result;
    }
}
