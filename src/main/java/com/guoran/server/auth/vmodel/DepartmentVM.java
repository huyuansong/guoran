package com.guoran.server.auth.vmodel;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

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
public class DepartmentVM{
    /**
     * 部门表id
     */
    private Long id;

    private String code;

    private String name;
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
    /**
     * 子级部门集合
     */
    private List children;
//    /**
//     * 岗位集合
//     */
//    private List<PositionVM> positionVMList;
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