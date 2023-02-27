package com.guoran.server.sys.vmodel;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * <p>
 * 物流公司基本信息DTO
 * </p>
 *
 * @author zhangjx
 * @create 2020-09-02
 * @Modify By
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LogisticsCompanyVM {
    private Long id;
    /**
     * 物流公司名称
     */
    private String logisticsName;
    /**
     * 物流公司地址
     */
    private String logisticsAddress;
    /**
     * 物流公司联系电话
     */
    private Long mobile;

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