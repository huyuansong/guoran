package com.hisoft.pam.im.auth.repository;

import com.github.pagehelper.Page;
import com.hisoft.pam.im.auth.model.AuthDictEntity;
import com.hisoft.pam.im.auth.vmodel.AuthDictItemVM;
import com.hisoft.pam.im.auth.vmodel.AuthDictVM;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * @description: 数据访问层
 * @author wangyongtao
 * @create 2022-3-11
 * @Modify By
 */
@Mapper
public interface AuthDictRepository {
	 /**
     * 根据id查找
     * @param id
     * @return
     */
    @Select("select * from auth_dict where id =#{id}")
    AuthDictEntity findById(@Param("id") long id);

    /**
     * 新增
     * @param entity
     */

    @Insert("insert into auth_dict ("+
" `dict_name`, `dict_code`, `description`, `del_flag`, `type`,`create_time`,`create_by`) values("+
"#{entity.dictName}, #{entity.dictCode}, #{entity.description}, #{entity.delFlag}, #{entity.type},#{entity.createTime},#{entity.createBy})")
    @Options(useGeneratedKeys = true, keyProperty = "entity.id", keyColumn = "id")
    Long insert(@Param("entity") AuthDictEntity entity);

    /**
     * 更新
     * @param entity
     */

    @Update("update auth_dict set "+
"dict_name=#{entity.dictName}, dict_code=#{entity.dictCode}, description=#{entity.description}, del_flag=#{entity.delFlag}, type=#{entity.type},update_time=#{entity.updateTime},update_by = #{entity.updateBy} where id=#{entity.id}")
    boolean update(@Param("entity") AuthDictEntity entity);

    /**
     * 根据id删除
     * @param id
     */
    @Delete("delete from auth_dict where id=#{id}")
    boolean deleteById(@Param("id")long id);

    /**
    * 根据id删除 做更新操作 更新is_delete
    * @param entity
    * @return
    */
    @Update("update auth_dict set is_delete=#{entity.isDelete} where id=#{entity.id}")
    boolean updateIsDelete(@Param("entity") AuthDictEntity entity);


    /**
     * 分页查询
     * @param where
     * @return
     */
    @Select(" SELECT *,1 mark" +
            " FROM `auth_dict` " +
            " WHERE 1 = 1 ${where}")
    Page<AuthDictVM> findEntrysByPage(@Param("where") String where);

    @Select("select * from auth_dict where dict_code = #{dictCode}")
    List<AuthDictVM> getDictsByCode(@Param("dictCode")String dictCode);
}
