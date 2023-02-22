package com.guoran.server.shensales.repository;

import com.github.pagehelper.Page;
import com.guoran.server.shensales.vmodel.OrderManagementSalesOrderVM;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderManagementSalesOrderRepository {
    /**
     * 分页查询
     * @param where
     * @return
     */
    @Select("select * from order_management_sales_order where 1=1 ${where}")
    Page<OrderManagementSalesOrderVM> findEntrysByPage(@Param("where") String where);
}
