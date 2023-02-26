package com.guoran.server.sys.model;

import com.guoran.server.common.BaseOfConcurrencySafeEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * <p>
 * 员工信息表
 * </p>
 *
 * @author zhangjx
 * @table sys_employee
 * @create 2020-08-19
 * @Modify By
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeEntity extends BaseOfConcurrencySafeEntity {
    private Long id;
    /**
     * 员工编号：YG+6位序号
     */
    private String userCode;
    /**
     * 档案所在公司编码
     */
    private String archivesInCompanyId;
    /**
     * 档案所在公司名称
     */
    private String archivesInCompanyName;
    /**
     * 部门ID
     */
    private String departmentId;
    /**
     * 部门名称
     */
    private String departmentName;
    /**
     * 岗位ID
     */
    private String positionId;
    /**
     * 岗位名称
     */
    private String positionName;
    /**
     * 姓名
     */
    private String userName;
    /**
     * 身份证号
     */
    private String idCard;
    /**
     * 工号
     */
    private String jobNumber;
    /**
     * 联系电话
     */
    private Long mobile;
    /**
     * 审核状态
     */
    private Integer auditStatus;
    /**
     * 是否删除：默认0， 删除：1
     */
    private Integer isDelete;
    /**
     * 驳回原因
     */
    private String dismissReason;
    /**
     * 是否已经绑定登录账号：默认0未绑定， ：1已绑定
     */
    private Integer binding;
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

    /*
     * 并发版本号
     * */
    private Integer concurrencyVersion;
    /**
     * 是否可修改 标识新增  1为可  非1为不可
     */
    private int isAdd;
    /**
     * 岗位明细
     */
    private String positionDetail;
}