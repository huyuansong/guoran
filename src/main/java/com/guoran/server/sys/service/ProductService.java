package com.guoran.server.sys.service;

import com.github.pagehelper.Page;
import com.guoran.server.common.search.PageQuery;
import com.guoran.server.sys.vmodel.ProductVM;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 商品信息表 服务类
 * </p>
 *
 * @author zhangjx
 * @create 2020-08-20
 * @Modify By
 */
public interface ProductService {
    /**
     * 根据id获取
     *
     * @param id
     */
    ProductVM getEntryBy(long id);

    /**
     * 创建
     *
     * @param productVM
     */
    String createEntry(ProductVM productVM);

    /**
     * 修改
     *
     * @param productVM
     */
    String updateEntry(ProductVM productVM);

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
    Page<ProductVM> findEntrysByPage(PageQuery pageQuery);

    /**
     * 导出
     *
     * @param ids
     * @param response
     * @param request
     */
/*
    void explort(String ids, HttpServletResponse response, HttpServletRequest request);
*/

    /**
     * 审核
     *
     * @param ids
     */
    String checkProduct(String ids, Integer state, String dismissReason);

    List<ProductVM> getAllProduct();

    /**
     * 获取规格
     *
     * @return
     */
    List<String> getSpecifications();

    String synToGRMC(Map[] data, String op);


    String synToGRM(Map[] data, String op);
}
