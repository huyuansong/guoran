package com.hisoft.pam.im.auth.repository;

import com.github.pagehelper.Page;
import com.hisoft.pam.im.auth.model.CompanyEntity;
import com.hisoft.pam.im.auth.vmodel.CompanyVM;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * @description: 数据访问层
 * @author: zhangjx
 * @create: 2020-09-10
 * @Modify By
 */
@Mapper
public interface CompanyRepository {
	 /**
     * 根据id查找
     * @param id
     * @return
     */
    @Select("select * from auth_company where id =#{id}")
    CompanyEntity findById(@Param("id") long id);

    /**
     * 新增
     * @param entity
     */

    @Insert("insert into auth_company ("+
" `company_code`, `company_name`,`create_time`, `create_by`) values("+
"#{entity.companyCode}, #{entity.companyName}, #{entity.createTime},#{entity.createBy})")
    @Options(useGeneratedKeys = true, keyProperty = "entity.id", keyColumn = "id")
    Long insert(@Param("entity") CompanyEntity entity);

    /**
     * 更新
     * @param entity
     */

    @Update("update auth_company set "+
"company_code=#{entity.companyCode}, company_name=#{entity.companyName}, update_time=#{entity.updateTime},update_by=#{entity.updateBy} where id=#{entity.id}")
    boolean update(@Param("entity") CompanyEntity entity);

    /**
     * 根据id删除
     * @param id
     */
    @Delete("delete from auth_company where id=#{id}")
    boolean deleteById(@Param("id")long id);

    /**
     * 分页查询
     * @param where
     * @return
     */
    @Select("select * from auth_company where 1=1 ${where}")
    Page<CompanyVM> findEntrysByPage(@Param("where") String where);

    @Insert({"<script>" +
            "<foreach collection='entitys' item='entity' open='' close='' separator=';'>" +
            " insert into " +
            " auth_company (" +
            " `ncc_company_code`, `company_code`, `company_name`,`create_time`, `create_by`) values("+
            "#{entity.nccCompanyCode}, #{entity.companyCode}, #{entity.companyName}, #{entity.createTime},#{entity.createBy})"+
            "</foreach>" +
            "</script>"})
    void insertBanch(@Param(value = "entitys") List<CompanyEntity> companyEntities);

    @Update({"<script>" +
            "<foreach collection='entitys' item='entity' open='' close='' separator=';'>" +
            " update  auth_company" +
            " SET "+
            " ncc_company_code = #{entity.nccCompanyCode}, company_code=#{entity.companyCode}, company_name=#{entity.companyName}, update_time=#{entity.updateTime},update_by=#{entity.updateBy} where id=#{entity.id}"+
            "</foreach>" +
            "</script>"})
    void updateBanch(@Param(value = "entitys") List<CompanyEntity> companyEntities2);
}
