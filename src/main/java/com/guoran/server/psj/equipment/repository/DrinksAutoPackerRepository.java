package com.guoran.server.psj.equipment.repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.github.pagehelper.Page;
import com.guoran.server.psj.equipment.model.DrinksAutoPackerEntity;
import com.guoran.server.psj.equipment.model.vto.DrinksAutoPackerVM;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

/**
 * 设备管理-设备运行记录-饮料车间-自动包装机运行记录数据访问层
 */
@Mapper
public interface DrinksAutoPackerRepository extends BaseMapper<DrinksAutoPackerEntity> {

    /**
     * 分页查询
     *
     * @param where
     * @return
     */
    @Select("select * from drinks_auto_packer where 1=1 ${where}")
    Page<DrinksAutoPackerVM> findEntrysByPage(String where);
}




