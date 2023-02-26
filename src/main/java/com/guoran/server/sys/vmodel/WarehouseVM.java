package com.guoran.server.sys.vmodel;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * <p>
 * 仓库信息表DTO
 * </p>
 *
 * @author zhangjx
 * @create 2020-08-20
 * @Modify By
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class WarehouseVM {
    private Long id;
    /**
     * 仓库编码：CK+4位序号
     */
    private String warehouseCode;
    /**
     * 仓库名称
     */
    private String warehouseName;
    /**
     * 仓库管理人，员工信息表CODE
     */
    private String userCode;
    /**
     * 仓库管理人，员工信息表NAME
     */
    private String userName;
    /**
     * 联系电话
     */
    private Long mobile;
    /**
     * 审核状态  3未提交   2审核中  4撤回审核中  1已审核
     */
    private Integer auditStatus;
    /**
     * 所属组织主键
     */
    private String pkOrg;
    /**
     * 所属组织编码
     */
    private String orgCode;
    /**
     * 所属组织名称
     */
    private String orgName;
    /**
     * 启用状态
     */
    private Long enableState;
    /**
     * 驳回原因
     */
    private String dismissReason;

    /*
     * 并发版本号
     * */
    private Integer concurrencyVersion;

    /**
     * 创建人
     */
    private String createBy;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 更新人
     */
    private String updateBy;
    /**
     * 更新时间
     */
    private Date updateTime;
    /**
     * 审核人
     */
    private String auditBy;

    /**
     * 仓库主键
     */
    private String warehousePk;
}