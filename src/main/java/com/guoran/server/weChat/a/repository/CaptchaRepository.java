package com.guoran.server.weChat.a.repository;

import com.github.pagehelper.Page;
import com.guoran.server.weChat.model.CaptchaEntity;
import com.guoran.server.weChat.vmodel.CaptchaVM;
import org.apache.ibatis.annotations.*;

/**
 * @description: 企业微信推荐验证码及token临时存储表数据访问层
 * @author: zhangjx
 * @create: 2020-09-21
 * @Modify By
 */
@Mapper
public interface CaptchaRepository {
    /**
     * 根据id查找
     *
     * @param id
     * @return
     */
    @Select("select * from captcha where id =#{id}")
    CaptchaEntity findById(@Param("id") long id);

    /**
     * 根据id查找
     *
     * @param userId
     * @return
     */
    @Select("select * from captcha where user_id =#{userId}")
    CaptchaEntity findByUserId(@Param("userId") String userId);


    /**
     * 新增
     *
     * @param entity
     */

    @Insert("insert into captcha (" +
            " `user_id`, `captcha`, `captcha_expires_in`,`create_time`, `create_by`) values(" +
            "#{entity.userId}, #{entity.captcha}, #{entity.captchaExpiresIn}, #{entity.createTime},#{entity.createBy})")
    @Options(useGeneratedKeys = true, keyProperty = "entity.id", keyColumn = "id")
    Long insert(@Param("entity") CaptchaEntity entity);

    /**
     * 更新
     *
     * @param entity
     */

    @Update("update captcha set " +
            "user_id=#{entity.userId}, captcha=#{entity.captcha}, captcha_expires_in=#{entity.captchaExpiresIn}, update_time=#{entity.updateTime},update_by=#{entity.updateBy} where id=#{entity.id}")
    boolean update(@Param("entity") CaptchaEntity entity);

    /**
     * 根据id删除
     *
     * @param id
     */
    @Delete("delete from captcha where id=#{id}")
    boolean deleteById(@Param("id") long id);

    /**
     * 分页查询
     *
     * @param where
     * @return
     */
    @Select("select * from captcha where 1=1 ${where}")
    Page<CaptchaVM> findEntrysByPage(@Param("where") String where);

}
