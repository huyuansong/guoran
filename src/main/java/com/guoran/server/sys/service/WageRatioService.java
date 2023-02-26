package com.guoran.server.sys.service;

import com.github.pagehelper.Page;
import com.guoran.server.common.search.PageQuery;
import com.guoran.server.sys.vmodel.WageRatioVM;

import java.util.List;

/**
 * <p>
 * 计件工资表子表岗位工资比例 服务类
 * </p>
 *
 * @author zhangjx
 * @create 2020-08-21
 * @Modify By
 */
public interface WageRatioService {
    /**
     * 根据id获取
     *
     * @param id
     */
    WageRatioVM getEntryBy(long id);

    /**
     * 创建
     *
     * @param wageRatioVM
     */
    Long createEntry(WageRatioVM wageRatioVM);

    /**
     * 批量创建
     */
    String createEntryBanch(List<WageRatioVM> wageRatioVMS);

    /**
     * 修改
     *
     * @param wageRatioVM
     */
    boolean updateEntry(WageRatioVM wageRatioVM);

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
    Page<WageRatioVM> findEntrysByPage(PageQuery pageQuery);

    /**
     * 批量删除
     *
     * @param wageRatioVMS
     */
    void deleteBanch(List<WageRatioVM> wageRatioVMS);

    /**
     * 批量修改
     */
    String updateBanch(List<WageRatioVM> wageRatioVMS);
}
