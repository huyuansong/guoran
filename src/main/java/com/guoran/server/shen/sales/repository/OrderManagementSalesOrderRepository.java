package com.guoran.server.shen.sales.repository;

import com.github.pagehelper.Page;
import com.guoran.server.shen.sales.vmodel.OrderManagementSalesOrderVM;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;


@Mapper
public interface OrderManagementSalesOrderRepository {
    /**
     * 分页查询
     *
     * @param where
     * @return
     */

    Page<OrderManagementSalesOrderVM> findEntrysByPage(@Param("where") String where);
}
