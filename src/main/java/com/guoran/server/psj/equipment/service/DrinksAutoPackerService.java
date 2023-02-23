package com.guoran.server.psj.equipment.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.guoran.server.psj.equipment.model.DrinksAutoPackerEntity;
import com.guoran.server.psj.equipment.model.vto.DrinksAutoPackerVM;


/**
 * 设备管理-设备运行记录-饮料车间-自动包装机运行记录数据访问层
 */
public interface DrinksAutoPackerService extends IService<DrinksAutoPackerEntity> {

    int deleteByIds(String ids);

    DrinksAutoPackerVM getEntryBy(long id);

    void createEntry(DrinksAutoPackerVM drinksAutoPackerVM);


    void updateEntry(DrinksAutoPackerVM drinksAutoPackerVM);
}
