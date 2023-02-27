package com.guoran.server.he.production.service;

import com.github.pagehelper.Page;
import com.guoran.server.common.search.PageQuery;
import com.guoran.server.he.production.vmodel.ManagementElectricityVM;
import com.guoran.server.he.production.vo.ProductionQualityVo;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @time 2023/2/2714:30
 * @outhor zhou
 */
public interface ManagementElectricityService {
    /**
     * 根据id获取
     *
     * @param id
     */
    ManagementElectricityVM getEntryBy(long id);

    /**
     * 创建
     *
     * @param managementElectricityVM
     */
    String createEntry(ManagementElectricityVM managementElectricityVM);

    /**
     * 修改
     *
     * @param managementElectricityVM
     */
    String updateEntry(ManagementElectricityVM managementElectricityVM);

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
    Page<ManagementElectricityVM> findEntrysByPage(PageQuery pageQuery);

    /**
     * 审核
     *
     * @param auditUpdate
     * @return
     */
    boolean auditById(ProductionQualityVo auditUpdate);

    /*
     *
     */
    void explort(Long[] ids, HttpServletResponse response, HttpServletRequest request);

    ManagementElectricityVM getLastReading(String code);
}
