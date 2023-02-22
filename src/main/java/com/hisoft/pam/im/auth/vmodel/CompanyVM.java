package com.hisoft.pam.im.auth.vmodel;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import java.util.Date;
import java.util.List;

import lombok.Data;

/**
 * <p>
    DTO
   </p>
 *
 * @author zhangjx
 * @create 2020-09-10
 * @Modify By
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CompanyVM{
    /**
     * 公司表id
     */
    private Long id;
    private String code;
    private String name;
    /**
     * 公司编码
     */
    private String companyCode;
    /**
     * 公司编码
     */
    private String nccCompanyCode;
    /**
     * 公司名称
     */
    private String companyName;
    /**
     * 部门集合
     */
    private List<DepartmentVM> children;
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