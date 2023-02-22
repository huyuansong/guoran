package com.hisoft.pam.im.auth.repository;

import com.github.pagehelper.Page;
import com.hisoft.pam.im.auth.model.AuthDictItemEntity;
import com.hisoft.pam.im.auth.vmodel.AuthDictItemVM;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * @description: 数据访问层
 * @author wangyongtao
 * @create 2022-3-11
 * @Modify By
 */
@Mapper
public interface AuthDictItemRepository {
	 /**
     * 根据id查找
     * @param id
     * @return
     */
    @Select("select * from auth_dict_item where id =#{id}")
    AuthDictItemEntity findById(@Param("id") long id);

    /**
     * 新增
     * @param entity
     */

    @Insert("insert into auth_dict_item ("+
" `dict_id`, `dict_name`, `dict_code`, `description`, `sort_order`, `status`, `create_by`, `create_time`, `update_by`, `update_time`) values("+
"#{entity.dictId}, #{entity.dictName}, #{entity.dictCode}, #{entity.description}, #{entity.sortOrder}, #{entity.status}, #{entity.createBy}, #{entity.createTime}, #{entity.updateBy}, #{entity.updateTime})")
    @Options(useGeneratedKeys = true, keyProperty = "entity.id", keyColumn = "id")
    Long insert(@Param("entity") AuthDictItemEntity entity);

    /**
     * 更新
     * @param entity
     */

    @Update("update auth_dict_item set "+
"dict_id=#{entity.dictId}, dict_name=#{entity.dictName}, dict_code=#{entity.dictCode}, description=#{entity.description}, sort_order=#{entity.sortOrder}, status=#{entity.status},update_by=#{entity.updateBy}, update_time=#{entity.updateTime} where id=#{entity.id}")
    boolean update(@Param("entity") AuthDictItemEntity entity);

    /**
     * 根据id删除
     * @param id
     */
    @Delete("delete from auth_dict_item where id=#{id}")
    boolean deleteById(@Param("id")long id);


    /**
     * 分页查询
     * @param where
     * @return
     */
    @Select("select * from auth_dict_item where 1=1 ${where}")
    Page<AuthDictItemVM> findEntrysByPage(@Param("where") String where);

    @Insert("<script>" +
            "insert into auth_dict_item ("+
                    " `dict_id`, `dict_name`, `dict_code`, `description`, `sort_order`, `status`, `create_by`, `create_time`, `update_by`, `update_time`) values" +
                    "<foreach collection='list' item='entity'  separator = ',' >"+
                    "(#{entity.dictId}, #{entity.dictName}, #{entity.dictCode}, #{entity.description}, #{entity.sortOrder}, #{entity.status}, " +
                    "#{entity.createBy}, #{entity.createTime}, #{entity.updateBy}, #{entity.updateTime})</foreach></script>")
    void addBanch(@Param("list") List<AuthDictItemEntity> listEntity);

    /**
     * 根据dictId获取字典值
     * @param dictCode
     * @return
     */
    @Select("select a.*,0 mark from auth_dict_item a, auth_dict b where b.dict_code = #{dictCode} and a.dict_id = b.id order by sort_order asc")
    List<AuthDictItemVM> getItemByCode(@Param("dictCode") String dictCode);

    /**
     * 根据dictId删除字典值
     */
    @Delete("delete from auth_dict_item where dict_id=#{dictId}")
    void deleteBanch(@Param("dictId")Long dictId);


    @Select("select *,0 mark from auth_dict_item where dict_id = #{dictId} order by sort_order asc")
    List<AuthDictItemVM> getItemById(@Param("dictId")Long dictId);

    @Select("select dict_name from auth_dict_item where dict_id = #{dictId} and dict_code = #{dictCode} ")
    String getDictValue(@Param("dictId") Long dictId,@Param("dictCode") String dictCode);


    @Select("select dict_code from auth_dict_item where dict_id = #{dictId} and dict_name = #{dictName} ")
    String getDictCode(@Param("dictId")int dictId,@Param("dictName") String dictName);

    @Select("<script> select * from auth_dict_item where dict_id in (" +
            "<foreach collection='collect' item='id' separator = ',' >"+
            " #{id}"+
            " </foreach>)</script>")
    List<AuthDictItemVM> getEntryByIdList(@Param("collect") List<Long> collect);

    @Select("select b.* from auth_dict a ,auth_dict_item b where a.id = b.dict_id and a.dict_code = #{code} and b.dict_code = #{value}")
    AuthDictItemVM getDictItem(@Param("code") String code,@Param("value") String value);

    @Select("select b.* from auth_dict a ,auth_dict_item b where a.id = b.dict_id and a.dict_code = #{code}")
    List<AuthDictItemVM> getDictItems(@Param("code") String code);

    @Select("select id from auth_dict_item  where dict_code = #{entity.dictCode} and dict_id = #{entity.dictId}")
    Long getItemsBy(@Param("entity") AuthDictItemVM entity);

    @Select("select b.* from auth_dict a ,auth_dict_item b where a.id = b.dict_id and a.dict_code = #{code} and b.dict_name = #{name}")
    AuthDictItemVM getDictItemByName(@Param("code") String code,@Param("name") String name);
}
