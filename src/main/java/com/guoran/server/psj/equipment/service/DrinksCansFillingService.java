package com.guoran.server.psj.equipment.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.guoran.server.common.search.PageQuery;
import com.guoran.server.common.search.PageResult;
import com.guoran.server.psj.equipment.model.DrinksCansFillingEntity;
import com.guoran.server.psj.equipment.model.vto.DrinksCansFillingVM;

/**
 * @description 针对表【drinks_cans_filling(设备管理-设备运行记录-饮料车间-易拉罐灌装记录)】的数据库操作Service
 */
public interface DrinksCansFillingService extends IService<DrinksCansFillingEntity> {

    DrinksCansFillingVM getEntryBy(long id);

    PageResult findEntrysByPage(PageQuery pageQuery);
}
