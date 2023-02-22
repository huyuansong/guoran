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
public class AuthDictItemVM{
    private Long id;
    /**
     * 字典id
     */
    private Long dictId;
    /**
     * 字典项文本
     */
    //itemText
    private String dictName;
    /**
     * 字典项值
     */
    // itemValue
    private String dictCode;
    /**
     * 描述
     */
    private String description;
    /**
     * 排序
     */
    private Integer sortOrder;
    /**
     * 状态（1启用 0不启用）
     */
    private Integer status;
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