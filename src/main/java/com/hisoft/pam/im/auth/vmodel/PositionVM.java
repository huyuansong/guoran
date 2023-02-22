package com.hisoft.pam.im.auth.vmodel;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import java.util.Date;
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
public class PositionVM{
    /**
     * 岗位表id
     */
    private Long id;
    /**
     * 岗位编码
     */
    private String positionCode;
    /**
     * 岗位名称
     */
    private String positionName;
    /**
     * 部门编码
     */
    private String departmentCode;
    /**
     * 部门名称
     */
    private String departmentName;

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
    /**
     * 是否可修改 标识新增  1为可  非1为不可
     */
    private int isAdd;
    /**
     * 岗位明细
     */
    private String positionDetail;
}