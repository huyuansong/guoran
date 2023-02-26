package com.guoran.server.auth.service;

import com.github.pagehelper.Page;
import com.guoran.server.auth.vmodel.PositionVM;
import com.guoran.server.auth.vo.PositionVO;
import com.guoran.server.common.search.PageQuery;

import java.io.IOException;
import java.util.List;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author zhangjx
 * @create 2020-09-10
 * @Modify By
 */
public interface PositionService {
    /**
     * 根据id获取
     *
     * @param id
     */
    PositionVM getEntryBy(long id);

    /**
     * 创建
     *
     * @param positionVM
     */
    String createEntry(PositionVM positionVM);

    /**
     * 修改
     *
     * @param positionVM
     */
    String updateEntry(PositionVM positionVM);

    /**
     * 删除
     *
     * @param id
     */
    String deleteById(long id);

    /**
     * 分页
     *
     * @param pageQuery
     * @return
     */
    Page<PositionVM> findEntrysByPage(PageQuery pageQuery);

    /**
     * 同步岗位信息
     */
    String synInfos() throws IOException;

    /**
     * 获取所有岗位
     *
     * @return
     */
    List<PositionVO> getAllPostion(String code);
}
