package com.guoran.server.weChat.a.repository;

import com.github.pagehelper.Page;
import com.guoran.server.weChat.model.AccessTokenEntity;
import com.guoran.server.weChat.vmodel.AccessTokenVM;
import org.apache.ibatis.annotations.*;

/**
 * @description: 企业微信推荐验证码及token临时存储表数据访问层
 * @author: zhangjx
 * @create: 2020-09-16
 * @Modify By
 */
@Mapper
public interface AccessTokenRepository {

    /**
     * 查找所有
     *
     * @return
     */
    @Select("select * from access_token")
    AccessTokenEntity find();

    /**
     * 根据id查找
     *
     * @param id
     * @return
     */
    @Select("select * from access_token where id =#{id}")
    AccessTokenEntity findById(@Param("id") long id);

    /**
     * 根据id查找
     *
     * @param userId
     * @return
     */
    @Select("select * from access_token where user_id =#{userId}")
    AccessTokenEntity findByUserId(@Param("userId") String userId);

    /**
     * 新增
     *
     * @param entity
     */

    @Insert("insert into access_token (" +
            " `token`,  `token_expires_in`, `create_time`, `create_by`) values(" +
            "#{entity.token}, #{entity.tokenExpiresIn}, #{entity.createTime},#{entity.createBy})")
    @Options(useGeneratedKeys = true, keyProperty = "entity.id", keyColumn = "id")
    Long insert(@Param("entity") AccessTokenEntity entity);

    /**
     * 更新
     *
     * @param entity
     */

    @Update("update access_token set " +
            "token=#{entity.token}, token_expires_in=#{entity.tokenExpiresIn}, update_time=#{entity.updateTime},update_by=#{entity.updateBy} where id=#{entity.id}")
    boolean update(@Param("entity") AccessTokenEntity entity);

    /**
     * 根据id删除
     *
     * @param id
     */
    @Delete("delete from access_token where id=#{id}")
    boolean deleteById(@Param("id") long id);

    /**
     * 分页查询
     *
     * @param where
     * @return
     */
    @Select("select * from access_token where 1=1 ${where}")
    Page<AccessTokenVM> findEntrysByPage(@Param("where") String where);

}
