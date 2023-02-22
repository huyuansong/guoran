package com.hisoft.pam.im.auth.model;

import com.hisoft.pam.im.common.model.BaseOfConcurrencySafeEntity;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import java.util.Date;
import lombok.Data;

/**
 * <p>
    
   </p>
 *
 * @table auth_department
 * @author zhangjx
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
     * NCC部门编码
     */
    private String nccDepartmentCode;
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