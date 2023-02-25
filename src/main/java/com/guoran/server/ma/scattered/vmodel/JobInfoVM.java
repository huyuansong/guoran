package com.guoran.server.ma.scattered.vmodel;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 零工信息DTO
 * </p>
 *
 * @author zhangjx
 * @create 2020-08-28
 * @Modify By
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class JobInfoVM {
    /**
     * 创建时间
     */
    protected Date createTime;
    private Long id;
    /**
     * 零工编号:LG+六位序号
     */
    private String oddJobCode;
    /**
     * 零工姓名
     */
    private String oddJobName;
    /**
     * 身份证号
     */
    private String idCard;
    /**
     * 联系电话
     */
    private String contactNumber;
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
    private LocalDateTime auditTime;
    /*
     * 并发版本号
     * */
    private Integer concurrencyVersion;
    /**
     * 创建人
     */
    private String createBy;
    /**
     * 修改时间
     */
    private Date updateTime;
    /**
     * 修改人
     */
    private String updateBy;

    /*
     *文件信息
     */
    private List<JobFileVM> jobFileVMList;


    /*
     *年度计划驳回原因
     */
    private String auditRejectReason;
}