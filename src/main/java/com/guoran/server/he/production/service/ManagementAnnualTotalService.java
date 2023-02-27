package com.guoran.server.he.production.service;

import com.github.pagehelper.Page;
import com.guoran.server.common.search.PageQuery;
import com.guoran.server.he.production.vmodel.ManagementAnnualTotalVM;
import com.guoran.server.he.production.vmodel.ManagementAnnualVM;
import com.guoran.server.he.production.vo.ProductionQualityVo;

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


    /**
     * 新增
     *
     * @param managementAnnualTotalVM
     * @return
     */
    String createEntry(ManagementAnnualTotalVM managementAnnualTotalVM);

    /**
     * 修改
     *
     * @param managementAnnualTotalVM
     */
    String updateEntry(ManagementAnnualTotalVM managementAnnualTotalVM);

    /**
     * 审核
     *
     * @param auditUpdates
     */
    boolean auditById(ProductionQualityVo auditUpdates);

    /**
     * 删除
     *
     * @param ids
     */
    boolean deleteById(String ids);

    /**
     * 年度计划是否重复
     *
     * @param budgetYear 年份
     */
    boolean queryBudgetYearCont(String budgetYear);
}
