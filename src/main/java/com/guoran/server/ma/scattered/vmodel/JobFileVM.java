package com.guoran.server.ma.scattered.vmodel;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * <p>
 * 零工信息管理-附件信息表DTO
 * </p>
 *
 * @author zhangjx
 * @create 2020-08-28
 * @Modify By
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class JobFileVM {
    /**
     * 创建时间
     */
    protected Date createTime;
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
     *年度计划驳回原因
     */
    private String auditRejectReason;
}