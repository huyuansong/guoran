package com.guoran.server.generator.sequence.service.impl;

import com.github.pagehelper.Page;
import com.guoran.server.generator.sequence.model.SequenceEntity;
import com.guoran.server.generator.sequence.repository.SequenceRepository;
import com.guoran.server.generator.sequence.service.SequenceService;
import com.guoran.server.generator.sequence.vmodel.SequenceVM;
import com.guoran.server.security.JwtUserUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @description: 序号接口实现
 * @author: machao
 * @create: 2019-12-12 11:19
 * @Modify By
 **/
@Service
public class SequenceServiceImpl implements SequenceService {
    @Autowired
    JwtUserUtil jwtUserUtil;
    @Autowired
    SequenceRepository sequenceRepository;

    /**
     * 根据id获取
     *
     * @param id
     */
    @Override
    public SequenceVM findEntryBy(long id) {
        SequenceVM sequenceVM = new SequenceVM();
        SequenceEntity sequenceEntity = sequenceRepository.findById(id);
        BeanUtils.copyProperties(sequenceEntity, sequenceVM);
        return sequenceVM;
    }

    /**
     * 根据编号类型查找
     *
     * @param typeCode
     * @return
     */
    @Override
    public SequenceVM findEntryByTypeCode(String typeCode) {
        SequenceVM sequenceVM = new SequenceVM();
        SequenceEntity sequenceEntity = sequenceRepository.findSequenceByCodeType(typeCode);
        BeanUtils.copyProperties(sequenceEntity, sequenceVM);
        return sequenceVM;
    }

    /**
     * 分页
     *
     * @param where
     * @return
     */
    @Override
    public Page<SequenceVM> findEntrysByPage(String where) {
        return sequenceRepository.findSequencesByPage(where);
    }

    /**
     * 创建
     *
     * @param sequenceVM
     */
    @Override
    public void createEntry(SequenceVM sequenceVM) {
        SequenceEntity sequenceEntity = new SequenceEntity();
        BeanUtils.copyProperties(sequenceVM, sequenceEntity);
        sequenceEntity.setCreateBy(jwtUserUtil.getUserName());
        sequenceEntity.setCreateTime(new Date());
        sequenceRepository.insert(sequenceEntity);
    }

    /**
     * 修改
     *
     * @param sequenceVM
     */
    @Override
    public void updateEntry(SequenceVM sequenceVM) {
        SequenceEntity sequenceEntity = sequenceRepository.findById(sequenceVM.getId());
        sequenceEntity.failWhenConcurrencyViolation(sequenceVM.getConcurrencyVersion());
        BeanUtils.copyProperties(sequenceVM, sequenceEntity);
        sequenceEntity.setUpdateBy(jwtUserUtil.getUserName());
        sequenceEntity.setUpdateTime(new Date());
        sequenceRepository.update(sequenceEntity);
    }

    /**
     * 删除
     *
     * @param id
     */
    @Override
    public void deleteById(long id) {
        sequenceRepository.deleteById(id);
    }
}
