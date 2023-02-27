package com.guoran.server.sys.service;

import com.github.pagehelper.Page;
import com.guoran.server.common.search.PageQuery;
import com.guoran.server.sys.vmodel.ElectricityMeterVM;

/**
 * <p>
 * 电表信息 服务类
 * </p>
 *
 * @author zhangjx
 * @create 2020-08-20
 * @Modify By
 */
public interface ElectricityMeterService {
    /**
     * 根据id获取
     *
     * @param id
     */
    ElectricityMeterVM getEntryBy(long id);

    /**
     * 创建
     *
     * @param electricityMeterVM
     */
    Long createEntry(ElectricityMeterVM electricityMeterVM);

    /**
     * 修改
     *
     * @param electricityMeterVM
     */
    boolean updateEntry(ElectricityMeterVM electricityMeterVM);

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
    Page<ElectricityMeterVM> findEntrysByPage(PageQuery pageQuery);


    /**
     * 多删除
     *
     * @param id
     */
    void deleteByIds(String id);

    /**
     * 导出
     *
     * @param ids
     * @return
     */
/*
    void explort(String ids, HttpServletResponse response, HttpServletRequest request);
*/


    /**
     * 审核
     *
     * @param ids
     * @return
     */
/*
    String checkelecticity(String ids, Integer state, String dismissReason);
*/
}
