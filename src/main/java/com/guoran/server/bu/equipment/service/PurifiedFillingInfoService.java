package com.guoran.server.bu.equipment.service;


import com.github.pagehelper.Page;
import com.guoran.server.bu.equipment.vmodel.PurifiedFillingInfoVM;
import com.guoran.server.common.search.PageQuery;

/**
 * w
 * 2023/1/26  19:11
 * 01-pro
 **/
public interface PurifiedFillingInfoService {
    /**
     * 根据id获取
     *
     * @param id
     */
    PurifiedFillingInfoVM getEntryBy(long id);

    /**
     * 创建
     *
     * @param purifiedFillingInfoVM
     */
    String createEntry(PurifiedFillingInfoVM purifiedFillingInfoVM);

    /**
     * 修改
     *
     * @param purifiedFillingInfoVM
     */
    String updateEntry(PurifiedFillingInfoVM purifiedFillingInfoVM);

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
    Page<PurifiedFillingInfoVM> findEntrysByPage(PageQuery pageQuery);
}

