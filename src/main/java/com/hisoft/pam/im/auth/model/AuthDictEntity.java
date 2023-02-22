package com.hisoft.pam.im.auth.model;

import io.swagger.models.auth.In;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import java.util.Date;
import java.math.*;
import com.hisoft.pam.im.common.model.BaseOfConcurrencySafeEntity;
import lombok.Data;

/**
 * <p>
    
   </p>
 *
 * @table auth_dict
 * @author wangyongtao
 * @create 2022-3-11
 * @Modify By
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthDictEntity extends BaseOfConcurrencySafeEntity{
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
    private Integer status;
    private String createBy;
    private Date createTime;
    private String updateBy;
    private Date updateTime;

}