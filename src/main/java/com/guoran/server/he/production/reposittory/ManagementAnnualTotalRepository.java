package com.guoran.server.he.production.reposittory;

import com.github.pagehelper.Page;
import com.guoran.server.he.production.model.ManagementAnnualTotalEntity;
import com.guoran.server.he.production.vmodel.ManagementAnnualTotalVM;
import com.guoran.server.he.production.vmodel.ManagementAnnualVM;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @time 2023/2/2313:58
 * @outhor zhou
 */
public interface ManagementAnnualTotalRepository {
    /**
     * 根据id查找
     *
     * @param id
     * @return
     */
    @Select("select * from production_management_annual_total where id =#{id}")
    ManagementAnnualTotalEntity findById(@Param("id") long id);

    /**
     * 分页查询
     *
     * @param where
     * @return
     */
    @Select("select * from production_management_annual_total where 1=1 ${where}")
    Page<ManagementAnnualTotalVM> findEntresByPage(@Param("where") String where);


    /**
     * 根据年度计划id 查询各个商品的年度计划列表
     *
     * @param id
     * @return
     */
    @Select("select * from production_management_annual where  annual_total_id=#{id}")
    List<ManagementAnnualVM> findEntrysPagetotal(@Param("id") long id);

}
