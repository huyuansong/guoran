package com.guoran.server.liu.financial.service;

import com.github.pagehelper.Page;
import com.guoran.server.common.search.PageQuery;
import com.guoran.server.liu.financial.vmodel.VoucherDetailsVM;

import java.util.List;

public interface VoucherDetailsService {

    /**
     * 根据id获取
     *
     * @param id
     */
    VoucherDetailsVM getEntryBy(long id);

    /**
     * 创建
     *
     * @param voucherDetailsVM
     */
    String createEntry(VoucherDetailsVM voucherDetailsVM);

    /**
     * 修改
     *
     * @param voucherDetailsVM
     */
    String updateEntry(VoucherDetailsVM voucherDetailsVM);

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
    Page<VoucherDetailsVM> findEntrysByPage(PageQuery pageQuery);

    /**
     * 批量新增
     *
     * @param voucherDetailsVMS
     * @return
     */
    String createEntryBanch(List<VoucherDetailsVM> voucherDetailsVMS);

    /**
     * 根据凭证id进行操作
     *
     * @param voucherDetailsVMS
     * @return
     */
    String checkData(long id, List<VoucherDetailsVM> voucherDetailsVMS);

    /**
     * 批量修改
     */
    String updateBanch(List<VoucherDetailsVM> voucherDetailsVMS);

    /**
     * 批量删除
     */
    String deleteEntryBanch(List<Long> longs);
}
