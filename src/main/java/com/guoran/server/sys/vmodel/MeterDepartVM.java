package com.guoran.server.sys.vmodel;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * <p>
 * 水表，电表关联部门使用比例 DTO
 * </p>
 *
 * @author zhangjx
 * @create 2020-08-20
 * @Modify By
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MeterDepartVM {
    private Long id;
    /**
     * 类型：1水表，2电表,3气表
     */
    private String meterType;
    /**
     * 部门code
     */
    private String departId;
    /**
     * 部门名称
     */
    private String departName;
    /**
     * 分摊比例:%
     */
    private String apportionmentRatio;
    /**
     * 根据Code判断是水表还是电表
     */
    private String typeCode;
    /*
     * 并发版本号
     * */
    private Integer concurrencyVersion;

    /**
     * 创建人
     */
    private String createBy;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 更新人
     */
    private String updateBy;
    /**
     * 更新时间
     */
    private Date updateTime;

}