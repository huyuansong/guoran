package com.guoran.server.psj.equipment.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.guoran.server.psj.equipment.model.DrinksAutoPackerEntity;
import com.guoran.server.psj.equipment.model.vto.DrinksAutoPackerVM;
import com.guoran.server.psj.equipment.repository.DrinksAutoPackerRepository;
import com.guoran.server.psj.equipment.service.DrinksAutoPackerService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Collections;

/**
 * <p>
 * 设备管理-设备运行记录-饮料车间-自动包装机运行记录 服务实现类
 * </p>
 */
@Service
public class DrinksAutoPackerServiceImpl extends ServiceImpl<DrinksAutoPackerRepository, DrinksAutoPackerEntity>
        implements DrinksAutoPackerService {

    @Resource
    private DrinksAutoPackerRepository drinksAutoPackerRepository;

    @Override
    public int deleteByIds(String ids) {
        String[] Ids = ids.split(",");

        return drinksAutoPackerRepository.deleteBatchIds(Collections.singletonList(Ids));
    }

    @Override
    public DrinksAutoPackerVM getEntryBy(long id) {
        DrinksAutoPackerEntity drinksAutoPackerEntity = drinksAutoPackerRepository.selectById(id);
        DrinksAutoPackerVM drinksAutoPackerVM = new DrinksAutoPackerVM();
        BeanUtils.copyProperties(drinksAutoPackerEntity, drinksAutoPackerVM);
        return drinksAutoPackerVM;
    }
}




