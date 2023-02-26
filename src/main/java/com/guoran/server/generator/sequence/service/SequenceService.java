package com.guoran.server.generator.sequence.service;

import com.github.pagehelper.Page;
import com.guoran.server.generator.sequence.vmodel.SequenceVM;

/**
 * @description: 序号接口
 * @author: machao
 * @create: 2019-12-12 11:10
 * @Modify By
 **/
public interface SequenceService {
    /**
     * 根据id获取
     *
     * @param id
     */
    SequenceVM findEntryBy(long id);

    /**
     * 根据编号类型查找
     *
     * @param typeCode
     * @return
     */
    SequenceVM findEntryByTypeCode(String typeCode);

    /**
     * 分页
     *
     * @param where
     * @return
     */
    Page<SequenceVM> findEntrysByPage(String where);

    /**
     * 创建
     *
     * @param sequenceVM
     */
    void createEntry(SequenceVM sequenceVM);

    /**
     * 修改
     *
     * @param sequenceVM
     */
    void updateEntry(SequenceVM sequenceVM);

    /**
     * 删除
     *
     * @param id
     */
    void deleteById(long id);
}
