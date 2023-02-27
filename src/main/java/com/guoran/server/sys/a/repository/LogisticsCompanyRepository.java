package com.guoran.server.sys.a.repository;

import com.github.pagehelper.Page;
import com.guoran.server.sys.model.LogisticsCompanyEntity;
import com.guoran.server.sys.vmodel.LogisticsCompanyVM;
import org.apache.ibatis.annotations.*;

/**
 * @description: 物流公司基本信息数据访问层
 * @author: zhangjx
 * @create: 2020-09-02
 * @Modify By
 */
@Mapper
public interface LogisticsCompanyRepository {
    /**
     * 根据id查找
     *
     * @param id
     * @return
     */
    @Select("select * from sys_logistics_company where id =#{id}")
    LogisticsCompanyEntity findById(@Param("id") long id);

    /**
     * 根据名称查找
     *
     * @param logisticsName
     * @return
     */
    @Select("select * from sys_logistics_company where logistics_name =#{logisticsName}")
    LogisticsCompanyEntity findByName(@Param("logisticsName") String logisticsName);

    /**
     * 新增
     *
     * @param entity
     */

    @Insert("insert into sys_logistics_company (" +
            " `logistics_name`, `logistics_address`, `mobile`,`create_time`, `create_by`) values(" +
            "#{entity.logisticsName}, #{entity.logisticsAddress}, #{entity.mobile}, #{entity.createTime},#{entity.createBy})")
    @Options(useGeneratedKeys = true, keyProperty = "entity.id", keyColumn = "id")
    Long insert(@Param("entity") LogisticsCompanyEntity entity);

    /**
     * 更新
     *
     * @param entity
     */

    @Update("update sys_logistics_company set " +
            "logistics_name=#{entity.logisticsName}, logistics_address=#{entity.logisticsAddress}, mobile=#{entity.mobile}, update_time=#{entity.updateTime},update_by=#{entity.updateBy} where id=#{entity.id}")
    boolean update(@Param("entity") LogisticsCompanyEntity entity);

    /**
     * 根据id删除
     *
     * @param id
     */
    @Delete("delete from sys_logistics_company where id=#{id}")
    boolean deleteById(@Param("id") long id);

    /**
     * 分页查询
     *
     * @param where
     * @return
     */
    @Select("select * from sys_logistics_company where 1=1 ${where}")
    Page<LogisticsCompanyVM> findEntrysByPage(@Param("where") String where);

}
