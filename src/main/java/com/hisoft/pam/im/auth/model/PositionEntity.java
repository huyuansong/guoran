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
 * @table auth_position
 * @author zhangjx
 * @create 2020-09-10
 * @Modify By
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PositionEntity extends BaseOfConcurrencySafeEntity {
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
    /**
     * 是否可修改 标识新增  1为可  非1为不可
     */
    private int isAdd;
    /**
     * 岗位明细
     */
    private String positionDetail;

}