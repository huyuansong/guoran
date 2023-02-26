package com.guoran.server.weChat.model;

import com.guoran.server.common.BaseOfConcurrencySafeEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * <p>
 * 企业微信推荐验证码及token临时存储表
 * </p>
 *
 * @author zhangjx
 * @table captcha
 * @create 2020-09-21
 * @Modify By
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CaptchaEntity extends BaseOfConcurrencySafeEntity {
    private Long id;
    /**
     * 用户账号
     */
    private String userId;
    /**
     * 验证码
     */
    private String captcha;
    /**
     * 验证码-有效时间（秒）
     */
    private Long captchaExpiresIn;

}