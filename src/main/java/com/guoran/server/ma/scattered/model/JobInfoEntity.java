package com.guoran.server.ma.scattered.model;


import com.guoran.server.common.BaseOfConcurrencySafeEntity;
import com.guoran.server.ma.scattered.vmodel.JobFileVM;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

/**
 * <p>
 * 零工信息
 * </p>
 *
 * @author zhangjx
 * @table scattered_job_info
 * @create 2020-08-28
 * @Modify By
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class JobInfoEntity extends BaseOfConcurrencySafeEntity {
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
    private Date auditTime;
    /*
     *文件信息
     */
    private List<JobFileVM> jobFileVMList;


    /*
     *年度计划驳回原因
     */
    private String auditRejectReason;

}