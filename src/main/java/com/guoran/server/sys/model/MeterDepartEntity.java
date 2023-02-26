package com.guoran.server.sys.model;

import com.guoran.server.common.BaseOfConcurrencySafeEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * <p>
 * 水表，电表关联部门使用比例
 * </p>
 *
 * @author zhangjx
 * @table sys_meter_depart
 * @create 2020-08-20
 * @Modify By
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MeterDepartEntity extends BaseOfConcurrencySafeEntity {
    private Long id;
    /**
     * 类型：水表，电表
     */
    private String meterType;

    /**
     * 水表编码，电表编码
     */
    private String typeCode;
    /**
     * 部门ID
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


}