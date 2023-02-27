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
 * @table auth_department
 * @create 2020-09-10
 * @Modify By
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DepartmentEntity extends BaseOfConcurrencySafeEntity {
    /**
     * 部门表id
     */
    private Long id;
    /**
     * 部门编码
     */
    private String departmentCode;
    /**
     * 部门名称
     */
    private String departmentName;
    /**
     * 上级部门编码
     */
    private String highDepartmentCode;
    /**
     * 公司编码
     */
    private String companyCode;
    /**
     * 公司名称
     */
    private String companyName;

}