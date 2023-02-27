package com.guoran.server.liu.financial.service;

import com.github.pagehelper.Page;
import com.guoran.server.common.search.PageQuery;
import com.guoran.server.liu.financial.vmodel.VoucherVM;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface VoucherService {
    /**
     * 根据id获取
     *
     * @param id
     */
    VoucherVM getEntryBy(long id);

    /**
     * 创建
     *
     * @param voucherVM
     */
    String createEntry(VoucherVM voucherVM);

    /**
     * 修改
     *
     * @param voucherVM
     */
    String updateEntry(VoucherVM voucherVM);

    /**
     * 删除
     *
     * @param id
     */
    boolean deleteById(long id);

    /**
     * 分页
     *
     * @param pageQuery
     * @return
     */
    Page<VoucherVM> findEntrysByPage(PageQuery pageQuery);

    /**
     * 导出
     *
     * @param ids
     * @param response
     * @param request
     * @return
     */
    void export(String ids, HttpServletResponse response, HttpServletRequest request);

    Object synNCC(Integer id);

    Object deleteNCC(String pk);
}
