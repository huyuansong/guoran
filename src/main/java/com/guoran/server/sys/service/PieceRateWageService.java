package com.guoran.server.sys.service;

import com.github.pagehelper.Page;
import com.guoran.server.common.search.PageQuery;
import com.guoran.server.sys.vmodel.PieceRateWageVM;

/**
 * <p>
 * 计件工资表 服务类
 * </p>
 *
 * @author zhangjx
 * @create 2020-08-21
 * @Modify By
 */
public interface PieceRateWageService {
    /**
     * 根据id获取
     *
     * @param id
     */
    PieceRateWageVM getEntryBy(long id);

    /**
     * 创建
     *
     * @param pieceRateWageVM
     */
    String createEntry(PieceRateWageVM pieceRateWageVM);

    /**
     * 修改
     *
     * @param pieceRateWageVM
     */
    String updateEntry(PieceRateWageVM pieceRateWageVM);

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
    Page<PieceRateWageVM> findEntrysByPage(PageQuery pageQuery);

    /**
     * 导出
     *
     * @param ids
     * @param response
     * @param request
     */
//    boolean explort(String ids, HttpServletResponse response, HttpServletRequest request);

    /**
     * 审核
     *
     * @param ids
     * @param state
     */
    String checkPieceRateWage(String ids, Integer state, String dismissReason);
}
