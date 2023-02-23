package com.guoran.server.liu.financial.vmodel;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

/**
 * <p>
 * 财务管理-工资管理DTO
 * </p>
 *
 * @author zhangjx
 * @create 2020-08-28
 * @Modify By
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class WagesVM {
    private Long id;
    /**
     * 年月2020—09
     */
    private String yearmonth;
    /**
     * 员工编号：YG+6位序号
     */
    private String userCode;
    /**
     * 档案所在公司ID
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
     * 工号
     */
    private String jobNumber;
    /**
     * 姓名
     */
    private String userName;
    /**
     * 基本工资
     */
    private BigDecimal basePay;
    /**
     * 绩效工资
     */
    private BigDecimal meritPay;
    /**
     * 计件工资
     */
    private BigDecimal piecerateWage;
    /**
     * 缺勤天数
     */
    private BigDecimal absenceDays;
    /**
     * 缺勤工资
     */
    private BigDecimal absencePay;
    /**
     * 应发工资
     */
    private BigDecimal wagesPayable;
    /**
     * 审核状态
     */
    private Integer auditStatus;
    /**
     * 审核人
     */
    private String auditBy;
    /**
     * 审核时间
     */
    private Date auditTime;
    /**
     * 驳回原因
     */
    private String dismissReason;

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