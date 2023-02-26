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
 * @table access_token
 * @create 2020-09-16
 * @Modify By
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccessTokenEntity extends BaseOfConcurrencySafeEntity {
    private Long id;
    /**
     * token
     */
    private String token;
    /**
     * token凭证的有效时间（秒）
     */
    private Long tokenExpiresIn;

}