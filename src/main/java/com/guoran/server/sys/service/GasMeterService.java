package com.guoran.server.sys.service;

import com.github.pagehelper.Page;
import com.guoran.server.common.search.PageQuery;
import com.guoran.server.sys.vmodel.GasMeterVM;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author zcq
 * @create 2020-11-27
 * @Modify By
 */
public interface GasMeterService {
    /**
     * 根据id获取
     *
     * @param id
     */
    GasMeterVM getEntryBy(long id);

    /**
     * 创建
     *
     * @param gasMeterVM
     */
    Long createEntry(GasMeterVM gasMeterVM);

    /**
     * 修改
     *
     * @param gasMeterVM
     */
    boolean updateEntry(GasMeterVM gasMeterVM);

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
    Page<GasMeterVM> findEntrysByPage(PageQuery pageQuery);

    String checkGasMeter(String ids, Integer state, String dismissReason);

    /**
     * 导出
     *
     * @param
     * @return
     */
/*
    void explort(HttpServletResponse response, HttpServletRequest request);
*/
}
