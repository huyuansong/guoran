package com.guoran.server.sys.service;

import com.github.pagehelper.Page;
import com.guoran.server.common.search.PageQuery;
import com.guoran.server.sys.vmodel.LogisticsCompanyVM;


/**
 * <p>
 * 物流公司基本信息 服务类
 * </p>
 *
 * @author zhangjx
 * @create 2020-09-02
 * @Modify By
 */
public interface LogisticsCompanyService {
    /**
     * 根据id获取
     *
     * @param id
     */
    LogisticsCompanyVM getEntryBy(long id);

    /**
     * 创建
     *
     * @param logisticsCompanyVM
     */
    String createEntry(LogisticsCompanyVM logisticsCompanyVM);

    /**
     * 修改
     *
     * @param logisticsCompanyVM
     */
    String updateEntry(LogisticsCompanyVM logisticsCompanyVM);

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
    Page<LogisticsCompanyVM> findEntrysByPage(PageQuery pageQuery);
}
