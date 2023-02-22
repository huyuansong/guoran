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
 * @table auth_dict_item
 * @author wangyongtao
 * @create 2022-3-11
 * @Modify By
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthDictItemEntity extends BaseOfConcurrencySafeEntity {
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

}