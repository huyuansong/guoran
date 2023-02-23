package com.guoran.server.bu.equipment.service;


import com.github.pagehelper.Page;
import com.guoran.server.bu.equipment.vmodel.PurifiedBottleRunInfoVM;
import com.guoran.server.common.search.PageQuery;

/**
 * w
 * 2023/1/25  15:08
 * 01-pro
 **/
public interface PurifiedBottleRunInfoService {
    /**
     * 根据id获取
     *
     * @param id
     */
    PurifiedBottleRunInfoVM getEntryBy(long id);

    /**
     * 创建
     *
     * @param purifiedBottleRunInfoVM
     */
    String createEntry(PurifiedBottleRunInfoVM purifiedBottleRunInfoVM);

    /**
     * 修改
     *
     * @param purifiedBottleRunInfoVM
     */
    String updateEntry(PurifiedBottleRunInfoVM purifiedBottleRunInfoVM);

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
    Page<PurifiedBottleRunInfoVM> findEntrysByPage(PageQuery pageQuery);
}
