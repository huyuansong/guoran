package com.guoran.server.auth.model;

import com.guoran.server.common.BaseOfConcurrencySafeEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * <p>
 *
 * </p>
 *
 * @author zhangjx
 * @table auth_company
 * @create 2020-09-10
 * @Modify By
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CompanyEntity extends BaseOfConcurrencySafeEntity {
    /**
     * 公司表id
     */
    private Long id;
    /**
     * 公司编码
     */
    private String companyCode;
    /**
     * 公司名称
     */
    private String companyName;

}