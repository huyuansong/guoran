package com.guoran.server.sys.service;

import com.github.pagehelper.Page;
import com.guoran.server.common.search.PageQuery;
import com.guoran.server.sys.vmodel.WarehouseVM;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 仓库信息表 服务类
 * </p>
 *
 * @author zhangjx
 * @create 2020-08-20
 * @Modify By
 */
public interface WarehouseService {
    /**
     * 根据id获取
     *
     * @param id
     */
    WarehouseVM getEntryBy(long id);

    /**
     * 创建
     *
     * @param warehouseVM
     */
    String createEntry(WarehouseVM warehouseVM);

    /**
     * 修改
     *
     * @param warehouseVM
     * @return
     */
    String updateEntry(WarehouseVM warehouseVM);

    /**
     * 删除
     *
     * @param ids
     */
    boolean deleteById(String ids);

    /**
     * 分页
     *
     * @param pageQuery
     * @return
     */
    Page<WarehouseVM> findEntrysByPage(PageQuery pageQuery);

    /**
     * 查询所有
     *
     * @param
     */
    List<WarehouseVM> findAllWarehouse();

    /**
     * 审核
     *
     * @param ids
     */
    String checkProduct(String ids, Integer state, String dismissReason);

    /**
     * 导出
     *
     * @param request response
     */
/*
    void explortWarehouse(HttpServletResponse response, HttpServletRequest request);
*/

    String synToGRW(Map[] data, String op);
}
