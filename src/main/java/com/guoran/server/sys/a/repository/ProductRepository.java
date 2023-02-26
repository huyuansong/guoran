package com.guoran.server.sys.a.repository;

import com.github.pagehelper.Page;
import com.guoran.server.sys.model.ProductEntity;
import com.guoran.server.sys.vmodel.ProductVM;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * @description: 商品信息表数据访问层
 * @author: zhangjx
 * @create: 2020-08-20
 * @Modify By
 */
@Mapper
public interface ProductRepository {
    /**
     * 根据id查找
     *
     * @param id
     * @return
     */
    @Select("select * from sys_product where id =#{id}")
    ProductEntity findById(@Param("id") long id);

    /**
     * 新增
     *
     * @param entity
     */

    @Insert("insert into sys_product (" +
            " `product_code`, `product_name`, `specifications`, `main_measurement`, `inner_measurement`, `product_type`, `inner_order_code`, `produce_channel`, `checkout_price`, `sales_price`, `inventory_up`, `inventory_down`, `audit_status`,`create_time`, `create_by`,`audit_by`,`audit_time`,`product_category`) values(" +
            "#{entity.productCode}, #{entity.productName}, #{entity.specifications}, #{entity.mainMeasurement}, #{entity.innerMeasurement}, #{entity.productType}, #{entity.innerOrderCode}, #{entity.produceChannel}, #{entity.checkoutPrice}, #{entity.salesPrice}, #{entity.inventoryUp}, #{entity.inventoryDown}, #{entity.auditStatus}, #{entity.createTime},#{entity.createBy},#{entity.auditBy},#{entity.auditTime},#{entity.productCategory})")
    @Options(useGeneratedKeys = true, keyProperty = "entity.id", keyColumn = "id")
    Long insert(@Param("entity") ProductEntity entity);

    /**
     * 更新
     *
     * @param entity
     */

    @Update("update sys_product set " +
            "product_code=#{entity.productCode}, product_name=#{entity.productName}, specifications=#{entity.specifications}, main_measurement=#{entity.mainMeasurement}, inner_measurement=#{entity.innerMeasurement}, product_type=#{entity.productType}, inner_order_code=#{entity.innerOrderCode}, produce_channel=#{entity.produceChannel}, checkout_price=#{entity.checkoutPrice}, sales_price=#{entity.salesPrice}, inventory_up=#{entity.inventoryUp}, inventory_down=#{entity.inventoryDown}, dismiss_reason=#{entity.dismissReason}, audit_status=#{entity.auditStatus}, update_time=#{entity.updateTime},update_by=#{entity.updateBy},audit_by =#{entity.auditBy},audit_time=#{entity.auditTime}, product_category = #{entity.productCategory}  where id=#{entity.id}")
    boolean update(@Param("entity") ProductEntity entity);

    /**
     * 更新
     *
     * @param entity
     */

    @Update("update sys_product set " +
            " product_name=#{entity.productName}, specifications=#{entity.specifications}, main_measurement=#{entity.mainMeasurement}, inner_measurement=#{entity.innerMeasurement}, product_type=#{entity.productType}, inner_order_code=#{entity.innerOrderCode}, produce_channel=#{entity.produceChannel}, checkout_price=#{entity.checkoutPrice}, sales_price=#{entity.salesPrice}, inventory_up=#{entity.inventoryUp}, inventory_down=#{entity.inventoryDown}, dismiss_reason=#{entity.dismissReason}, audit_status=#{entity.auditStatus}, update_time=#{entity.updateTime},update_by=#{entity.updateBy},audit_by =#{entity.auditBy},audit_time=#{entity.auditTime} where product_code=#{entity.productCode}")
    boolean updateByPk(@Param("entity") ProductEntity entity);

    /**
     * 根据id删除
     *
     * @param Ids
     */
    @Delete({"<script>" +
            " delete from" +
            " sys_product" +
            " WHERE `id` in " +
            "<foreach collection='ids' item='id' open='(' close=')' separator=','>" +
            "#{id}" +
            "</foreach>" +
            "</script>"})
    boolean deleteById(@Param("ids") String[] Ids);

    /**
     * 根据主键删除
     *
     * @param Ids
     */
    @Delete({"<script>" +
            " delete from" +
            " sys_product" +
            " WHERE `product_code` in " +
            "<foreach collection='ids' item='productCode' open='(' close=')' separator=','>" +
            "#{productCode}" +
            "</foreach>" +
            "</script>"})
    boolean deleteByPk(@Param("ids") String[] Ids);

    /**
     * 分页查询
     *
     * @param where
     * @return
     */
    @Select("select * from sys_product where 1=1 ${where}")
    Page<ProductVM> findEntrysByPage(@Param("where") String where);

    /**
     * 获取所有商品
     *
     * @return
     */
    @Select("select * from sys_product")
    List<ProductEntity> findAllProduct();

    /**
     * 审核商品
     *
     * @param
     */
    @Update({"<script>" +
            "<foreach collection='productEntities' item='entity' open='' close='' separator=';'>" +
            " UPDATE" +
            " sys_product" +
            " SET " +
            " `audit_status` = #{entity.auditStatus}, `dismiss_reason`=#{entity.dismissReason}," +
            " `audit_time` = #{entity.auditTime},`audit_by`=#{entity.auditBy} " +
            " WHERE `id` = " +
            " #{entity.id} " +
            "</foreach>" +
            "</script>"})
    boolean chekcProduct(@Param("productEntities") List<ProductEntity> productEntities);

    /**
     * 获取商品名
     *
     * @param productCode
     */
    @Select("select * from sys_product where product_code =#{productCode}")
    ProductEntity finByCode(@Param("productCode") String productCode);

    /**
     * 获取商品名
     *
     * @param productCode
     */
    @Select("select * from sys_product where product_code =#{productCode} and audit_status = 1")
    ProductEntity finByCodes(@Param("productCode") String productCode);


    /**
     * 待办事项
     *
     * @return
     */
    @Select("select COUNT(id) from sys_product where audit_status=2")
    int getBacklog();

    @Select(" ${where} ")
    List<String> findFind(String where);

    /**
     * 分页查询
     *
     * @param code
     * @return
     */
    @Select("select * from sys_product where product_code = #{code}")
    ProductEntity findEntrysByCode(@Param("code") String code);

    @Select(" SELECT distinct specifications FROM `sys_product` WHERE audit_status = 1  ")
    List<String> findSpecifications();

    @Update("update sys_product set " +
            " status = 2 where product_code=#{code}")
    boolean enablePk(@Param("code") String code);

    @Update("update sys_product set " +
            " status = 1 where product_code=#{code}")
    boolean disenablePk(@Param("code") String code);
}
