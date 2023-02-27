package com.guoran.server.sys.model;

import com.guoran.server.common.BaseOfConcurrencySafeEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * <p>
 * 物流公司基本信息
 * </p>
 *
 * @author zhangjx
 * @table sys_logistics_company
 * @create 2020-09-02
 * @Modify By
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LogisticsCompanyEntity extends BaseOfConcurrencySafeEntity {
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

}