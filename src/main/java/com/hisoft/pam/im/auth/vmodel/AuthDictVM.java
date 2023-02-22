package com.hisoft.pam.im.auth.vmodel;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import java.util.Date;
import java.math.*;
import lombok.Data;

/**
 * <p>
 DTO
 </p>
 *
 * @author wangyongtao
 * @create 2022-3-11
 * @Modify By
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthDictVM{
    private Long id;
    /**
     * 字典名称
     */
    private String dictName;
    /**
     * 字典编码
     */
    private String dictCode;
    /**
     * 描述
     */
    private String description;
    /**
     * 删除状态
     */
    private Integer delFlag;
    /**
     * 字典类型0为string,1为number
     */
    private Integer type;
    private String createBy;
    private Date createTime;
    private String updateBy;
    private Date updateTime;
    /*
     * 并发版本号
     * */
    private Integer concurrencyVersion;

    /**
     *
     */
    private String hasChildren;
    /**
     * 标识不可以维护下级字典值
     */
    private Integer mark;
}