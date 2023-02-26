package com.guoran.server.auth.vo;

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
public class CompanyVO {
    /**
     * 公司表id
     */
    private Long id;
    /**
     * 公司编码
     */
    private String code;
    /**
     * 公司名称
     */
    private String name;
    /**
     * 部门集合
     */
    private List<DepartmentVO> departmentVMList;
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