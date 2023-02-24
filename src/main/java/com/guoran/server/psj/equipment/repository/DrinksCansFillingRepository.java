package com.guoran.server.psj.equipment.repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.github.pagehelper.Page;
import com.guoran.server.psj.equipment.model.DrinksCansFillingEntity;
import com.guoran.server.psj.equipment.model.vto.DrinksCansFillingVM;
import org.apache.ibatis.annotations.Select;

public interface DrinksCansFillingRepository extends BaseMapper<DrinksCansFillingEntity> {

    /**
     * 分页查询
     *
     * @param where
     * @return
     */
    @Select("select * from drinks_cans_filling where 1=1 ${where}")
    Page<DrinksCansFillingVM> findEntrysByPage(String where);

}
