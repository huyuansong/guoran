package com.guoran.server.sys.a.repository;

import com.github.pagehelper.Page;
import com.guoran.server.sys.model.ProductTypeEntity;
import com.guoran.server.sys.vmodel.ProductTypeVM;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

/**
 * @description: 数据访问层
 * @author: zcq
 * @create: 2020-11-16
 * @Modify By
 */
@Mapper
@Repository
public interface ProductTypeRepository {
    /**
     * 根据id查找
     *
     * @param id
     * @return
     */
    @Select("select * from sys_product_type where id =#{id}")
    ProductTypeEntity findById(@Param("id") long id);

    /**
     * 新增
     *
     * @param entity
     */

    @Insert("insert into sys_product_type (" +
            " `id`, `parent_id`, `catagory_no`, `catagory_name`, `company_id`, `avg_price`, `avg_cost`, `avg_buy_time`, `avg_production_time`, `more_attribute`, `status`, `is_public`, `sort_id`, `quality_standard`, `ftype_zhibao`, `ftype_prepay`, `ftype_payable`, `fixed_type`, `use_month`, `update_by`, `update_time`, `audit_by`, `audit_time`) values(" +
            "#{entity.id},#{entity.parentId}, #{entity.catagoryNo}, #{entity.catagoryName}, #{entity.companyId}, #{entity.avgPrice}, #{entity.avgCost}, #{entity.avgBuyTime}, #{entity.avgProductionTime}, #{entity.moreAttribute}, #{entity.status}, #{entity.isPublic}, #{entity.sortId}, #{entity.qualityStandard}, #{entity.ftypeZhibao}, #{entity.ftypePrepay}, #{entity.ftypePayable}, #{entity.fixedType}, #{entity.useMonth}, #{entity.updateBy}, #{entity.updateTime}, #{entity.auditBy}, #{entity.auditTime})")
    @Options(useGeneratedKeys = true, keyProperty = "entity.id", keyColumn = "id")
    boolean insert(@Param("entity") ProductTypeEntity entity);

    /**
     * 更新
     *
     * @param entity
     */

    @Update("update sys_product_type set " +
            "parent_id=#{entity.parentId}, catagory_no=#{entity.catagoryNo}, catagory_name=#{entity.catagoryName}, company_id=#{entity.companyId}, avg_price=#{entity.avgPrice}, avg_cost=#{entity.avgCost}, avg_buy_time=#{entity.avgBuyTime}, avg_production_time=#{entity.avgProductionTime}, more_attribute=#{entity.moreAttribute}, status=#{entity.status}, is_public=#{entity.isPublic}, sort_id=#{entity.sortId}, quality_standard=#{entity.qualityStandard}, ftype_zhibao=#{entity.ftypeZhibao}, ftype_prepay=#{entity.ftypePrepay}, ftype_payable=#{entity.ftypePayable}, fixed_type=#{entity.fixedType}, use_month=#{entity.useMonth}, update_by=#{entity.updateBy}, update_time=#{entity.updateTime}, audit_by=#{entity.auditBy}, audit_time=#{entity.auditTime} where id=#{entity.id}")
    boolean update(@Param("entity") ProductTypeEntity entity);

    /**
     * 根据id删除
     *
     * @param id
     */
    @Delete("delete from sys_product_type where id=#{id}")
    boolean deleteById(@Param("id") long id);

    /**
     * 分页查询
     *
     * @param where
     * @return
     */
    @Select("select * from sys_product_type where 1=1 ${where}")
    Page<ProductTypeVM> findEntrysByPage(@Param("where") String where);


    @Update("update sys_product_type set status = 1 where  id = #{id} ")
    boolean enablePk(@Param("id") Integer id);

    @Update("update sys_product_type set status = 0 where  id = #{id} ")
    boolean disablePk(@Param("id") Integer id);
}
