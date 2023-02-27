package com.guoran.server.ma.scattered.model;

import com.guoran.server.common.BaseOfConcurrencySafeEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * <p>
 * 零工信息管理-附件信息表
 * </p>
 *
 * @author zhangjx
 * @table scattered_job_file
 * @create 2020-08-28
 * @Modify By
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class JobFileEntity extends BaseOfConcurrencySafeEntity {
    /**
     * id
     */
    private Long id;
    /**
     * 零工编号:LG+六位序号
     */
    private String oddJobCode;
    /**
     * 文件类型：身份证
     */
    private Integer fileType;
    /**
     * 文件名称
     */
    private String fileName;
    /**
     * 附件地址
     */
    private String fileUrl;
    /**
     * 文件大小
     */
    private Double fileSize;

    /*
     *年度计划驳回原因
     */
    private String auditRejectReason;
}