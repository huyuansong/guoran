package com.guoran.server.ma.scattered.model;

import com.guoran.server.common.BaseOfConcurrencySafeEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

/**
 * <p>
 * 零工出勤信息
 * </p>
 *
 * @author zhangjx
 * @table scattered_job_attendance
 * @create 2020-08-28
 * @Modify By
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class JobAttendanceEntity extends BaseOfConcurrencySafeEntity {
    private Integer id;
    /**
     * 零工姓名
     */
    private String oddJobName;
    /**
     * 身份证号
     */
    private String idCard;
    /**
     * 雇佣部门ID
     */
    private String hireDepartmentId;
    /**
     * 雇佣部门名称
     */
    private String hireDepartmentName;
    /**
     * 雇佣目的
     */
    private String hireContent;
    /**
     * 出勤开始日
     */
    private Date attendanceBegintime;
    /**
     * 出勤结束日
     */
    private Date attendanceEndttime;
    /**
     * 出勤天数
     */
    private Integer attendanceDays;
    /**
     * 按天工资（元）
     */
    private BigDecimal dayWages;
    /**
     * 出勤总工资（元）
     */
    private BigDecimal attendanceTotleWages;
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
     *年度计划驳回原因
     */
    private String auditRejectReason;
}