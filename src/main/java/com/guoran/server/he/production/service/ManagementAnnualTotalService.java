package com.guoran.server.he.production.service;

import com.github.pagehelper.Page;
import com.guoran.server.common.search.PageQuery;
import com.guoran.server.he.production.vmodel.ManagementAnnualTotalVM;
import com.guoran.server.he.production.vmodel.ManagementAnnualVM;

import java.util.List;

/**
 * @time 2023/2/2314:50
 * @outhor zhou
 */
public interface ManagementAnnualTotalService {
    /**
     * 根据id获取
     *
     * @param id
     */
    ManagementAnnualTotalVM getEntryBy(long id);

    /**
     * 分页
     *
     * @param pageQuery
     * @return
     */
    Page<ManagementAnnualTotalVM> findEntrysByPage(PageQuery pageQuery);

    /**
     * 根据年度计划id 查询各个商品的年度计划列表
     *
     * @param id
     * @return
     */
    List<ManagementAnnualVM> findEntrysPagetotal(long id);

}
