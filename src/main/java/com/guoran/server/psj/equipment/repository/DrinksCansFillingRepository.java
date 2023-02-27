package com.guoran.server.psj.equipment.repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.github.pagehelper.Page;
import com.guoran.server.psj.equipment.model.DrinksCansFillingEntity;
import com.guoran.server.psj.equipment.model.vto.DrinksCansFillingVM;

public interface DrinksCansFillingRepository extends BaseMapper<DrinksCansFillingEntity> {

    /**
     * 分页查询
     *
     * @param where
     * @return
     */
    Page<DrinksCansFillingVM> findEntrysByPage(String where);

}
