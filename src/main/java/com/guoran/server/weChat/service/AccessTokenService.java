package com.guoran.server.weChat.service;

import com.github.pagehelper.Page;
import com.guoran.server.common.search.PageQuery;
import com.guoran.server.weChat.model.AccessTokenEntity;
import com.guoran.server.weChat.vmodel.AccessTokenVM;

import java.io.IOException;

/**
 * <p>
 * 企业微信推荐验证码及token临时存储表 服务类
 * </p>
 *
 * @author zhangjx
 * @create 2020-09-16
 * @Modify By
 */
public interface AccessTokenService {

    AccessTokenEntity find();

    String getToken() throws IOException;

    /**
     * 发送验证码到企业微信指定账号
     *
     * @param userId
     * @return
     */
    String send(String userId) throws IOException;

    /**
     * 根据id获取
     *
     * @param id
     */
    AccessTokenVM getEntryBy(long id);

    /**
     * 创建
     *
     * @param accessTokenVM
     */
    String createEntry(AccessTokenVM accessTokenVM);

    /**
     * 修改
     *
     * @param accessTokenVM
     */
    String updateEntry(AccessTokenVM accessTokenVM);

    /**
     * 删除
     *
     * @param id
     */
    boolean deleteById(long id);

    /**
     * 分页
     *
     * @param pageQuery
     * @return
     */
    Page<AccessTokenVM> findEntrysByPage(PageQuery pageQuery);
}
