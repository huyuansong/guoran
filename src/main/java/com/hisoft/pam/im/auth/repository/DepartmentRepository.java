package com.hisoft.pam.im.auth.repository;

import com.github.pagehelper.Page;
import com.hisoft.pam.im.auth.model.DepartmentEntity;
import com.hisoft.pam.im.auth.vmodel.DepartmentVM;
import com.hisoft.pam.im.auth.vo.DepartmentVO;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * @description: 数据访问层
 * @author: zhangjx
 * @create: 2020-09-10
 * @Modify By
 */
@Mapper
public interface DepartmentRepository {
	 /**
     * 根据id查找
     * @param id
     * @return
     */
    @Select("select * from auth_department where id =#{id}")
    DepartmentEntity findById(@Param("id") long id);
	 /**
     * 根据id查找
     * @param code
     * @return
     */
    @Select("select * from auth_department where department_code =#{code}")
    DepartmentEntity findBycode(@Param("code") String code);

    /**
     * 新增
     * @param entity
     */

    @Insert("insert into auth_department ("+
" `department_code`, `department_name`, `high_department_code`, `company_code`, `company_name`,`create_time`, `create_by`) values("+
"#{entity.departmentCode}, #{entity.departmentName}, #{entity.highDepartmentCode}, #{entity.companyCode}, #{entity.companyName}, #{entity.createTime},#{entity.createBy})")
    @Options(useGeneratedKeys = true, keyProperty = "entity.id", keyColumn = "id")
    Long insert(@Param("entity") DepartmentEntity entity);

    /**
     * 更新
     * @param entity
     */

    @Update("update auth_department set "+
"department_code=#{entity.departmentCode}, department_name=#{entity.departmentName}, high_department_code=#{entity.highDepartmentCode}, company_code=#{entity.companyCode}, company_name=#{entity.companyName}, update_time=#{entity.updateTime},update_by=#{entity.updateBy} where id=#{entity.id}")
    boolean update(@Param("entity") DepartmentEntity entity);

    /**
     * 根据id删除
     * @param id
     */
    @Delete("delete from auth_department where id=#{id}")
    boolean deleteById(@Param("id")long id);

    /**
     * 分页查询
     * @param where
     * @return
     */
    @Select("select * from auth_department where 1=1 ${where}")
    Page<DepartmentVM> findEntrysByPage(@Param("where") String where);

    /**
    *同步
     */
    @Insert({"<script>" +
            "<foreach collection='entitys' item='entity' open='' close='' separator=';'>" +
            " insert into " +
            " auth_department (" +
            " `ncc_department_code`, `department_code`, `department_name`, `high_department_code`, `company_code`, `company_name`,`create_time`, `create_by`) values("+
            "#{entity.nccDepartmentCode},#{entity.departmentCode}, #{entity.departmentName}, #{entity.highDepartmentCode}, #{entity.companyCode}, #{entity.companyName}, #{entity.createTime},#{entity.createBy})"+
            "</foreach>" +
            "</script>"})
    void insertBanch(@Param(value = "entitys") List<DepartmentEntity> departmentEntities);

    /**
     * 批量更新
     * @param departmentEntities2
     */
    @Update({"<script>" +
            "<foreach collection='entitys' item='entity' open='' close='' separator=';'>" +
            " update  auth_department" +
            " SET "+
            "ncc_department_code=#{entity.nccDepartmentCode}, department_code=#{entity.departmentCode}, department_name=#{entity.departmentName}, high_department_code=#{entity.highDepartmentCode}, company_code=#{entity.companyCode}, company_name=#{entity.companyName}, update_time=#{entity.updateTime},update_by=#{entity.updateBy} where id=#{entity.id}"+
            "</foreach>" +
            "</script>"})
    void updateBanch(@Param(value = "entitys") List<DepartmentEntity> departmentEntities2);


    @Select("select company_code from auth_department where department_code = #{code} ")
    String findbyDepartmentCode(@Param("code") String departmentCode);

    @Select("select * from auth_department where department_code = #{code} ")
    DepartmentVO findEntrysByCode(@Param("code") String productionWorkshopId);

    @Select("select department_name from auth_department where department_code = #{code} ")
    String getCompanyNameByCode(String departmentCode);

    @Select("select ncc_department_code from auth_department where department_code = #{code} ")
    String getNcCodeByCompanyCode(String departmentCode);
}
