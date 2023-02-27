package com.guoran.server.weChat.service;

import com.github.pagehelper.Page;
import com.guoran.server.common.search.PageQuery;
import com.guoran.server.weChat.vmodel.CaptchaVM;


/**
 * <p>
 * 企业微信推荐验证码及token临时存储表 服务类
 * </p>
 *
 * @author zhangjx
 * @create 2020-09-21
 * @Modify By
 */
public interface CaptchaService {
    /**
     * 根据id获取
     *
     * @param id
     */
    CaptchaVM getEntryBy(long id);

    /**
     * 创建
     *
     * @param captchaVM
     */
    String createEntry(CaptchaVM captchaVM);

    /**
     * 修改
     *
     * @param captchaVM
     */
    String updateEntry(CaptchaVM captchaVM);

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
    Page<CaptchaVM> findEntrysByPage(PageQuery pageQuery);
}
