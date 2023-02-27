package com.guoran.server.sys.service;

import com.github.pagehelper.Page;
import com.guoran.server.common.search.PageQuery;
import com.guoran.server.sys.vmodel.MeterDepartVM;

import java.util.List;

/**
 * <p>
 * 水表，电表关联部门使用比例服务类
 * </p>
 *
 * @author zhangjx
 * @create 2020-08-20
 * @Modify By
 */
public interface MeterDepartService {
    /**
     * 根据id获取
     *
     * @param id
     */
    MeterDepartVM getEntryBy(long id);

    /**
     * 创建
     *
     * @param meterDepartVM
     */
    Long createEntry(MeterDepartVM meterDepartVM);

    /**
     * 修改
     *
     * @param meterDepartVM
     */
    boolean updateEntry(MeterDepartVM meterDepartVM);

    /**
     * 删除
     *
     * @param id
     */
    void deleteById(long id);

    /**
     * 分页
     *
     * @param pageQuery
     * @return
     */
    Page<MeterDepartVM> findEntrysByPage(PageQuery pageQuery);


    /**
     * 批量修改
     *
     * @param meterDepartVMS
     */
    String updateEntrys(List<MeterDepartVM> meterDepartVMS);
}
