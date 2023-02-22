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
 * @table auth_company
 * @author zhangjx
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
     * NCC公司编码
     */
    private String nccCompanyCode;
    /**
     * 公司名称
     */
    private String companyName;

}