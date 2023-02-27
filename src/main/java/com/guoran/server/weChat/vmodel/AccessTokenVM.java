package com.guoran.server.weChat.vmodel;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * <p>
 * 企业微信推荐验证码及token临时存储表DTO
 * </p>
 *
 * @author zhangjx
 * @create 2020-09-16
 * @Modify By
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccessTokenVM {
    private Long id;
    /**
     * token
     */
    private String token;
    /**
     * token凭证的有效时间（秒）
     */
    private Long tokenExpiresIn;

    /*
     * 并发版本号
     * */
    private Integer concurrencyVersion;
    /**
     * 创建时间
     */
    private Date gmtCreate;
    /**
     * 创建人
     */
    private String creator;
    /**
     * 修改时间
     */
    private Date gmtModified;
    /**
     * 修改人
     */
    private String modifier;
}