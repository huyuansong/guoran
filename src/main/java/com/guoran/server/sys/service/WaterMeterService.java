package com.guoran.server.sys.service;

import com.github.pagehelper.Page;
import com.guoran.server.common.search.PageQuery;
import com.guoran.server.sys.vmodel.WaterMeterVM;

/**
 * <p>
 * 水表信息 服务类
 * </p>
 *
 * @author zhangjx
 * @create 2020-08-20
 * @Modify By
 */
public interface WaterMeterService {
    /**
     * 根据id获取
     *
     * @param id
     */
    WaterMeterVM getEntryBy(long id);

    /**
     * 创建
     *
     * @param waterMeterVM
     */
    Long createEntry(WaterMeterVM waterMeterVM);

    /**
     * 修改
     *
     * @param waterMeterVM
     */
    boolean updateEntry(WaterMeterVM waterMeterVM);

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
    Page<WaterMeterVM> findEntrysByPage(PageQuery pageQuery);
    /*

     */
/**
 * 导出
 *
 * @param ids
 * @return
 *//*

    void explort(String ids, HttpServletResponse response, HttpServletRequest request);
*/

    /**
     * 多项删除
     *
     * @param
     * @return
     */
    void deleteById(String id);

    /**
     * 审核
     *
     * @param
     * @return
     */
    String checkWaterMeter(String ids, Integer state, String dismissReason);

}
