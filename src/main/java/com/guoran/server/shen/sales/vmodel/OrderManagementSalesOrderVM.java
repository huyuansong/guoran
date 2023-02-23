package com.guoran.server.shen.sales.vmodel;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.guoran.server.common.file.model.OSSFile;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 销售订单表主键DTO
 * </p>
 *
 * @author zhangjx
 * @create 2020-08-20
 * @Modify By
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderManagementSalesOrderVM {
    /**
     * 销售订单表主键ID
     */
    private Integer id;
    /**
     * 销售订单编号
     */
    private String salesOrderNumber;
    /**
     * 订单日期
     */
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "更新时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date orderDate;

    /**
     * 客户id
     */
    private Integer customerId;
    /**
     * 客户名称
     */
    private String customer;
    /**
     * 纳税人识别号
     */
    private String taxpayerIdentifccationNumber;
    /**
     * 销售员id
     */
    private String salespersonId;
    /**
     * 销售员
     */
    private String salesperson;
    /**
     * 订单金额
     */
    private BigDecimal orderAmount;
    /**
     * 合同信息
     */
    private String contractInformation;
    /**
     * 审核状态
     * 1:已审核
     * 2:审核中
     * 3:未提交
     * 4:撤回
     */
    private Integer auditStatus;
    /**
     * 审核人
     */
    private String reviewer;
    /**
     * 订单执行情况
     * 1:未执行
     * 2:执行中
     * 3:执行完毕
     */
    private Integer orderExecution;
    /**
     * 已执行金额
     */
    private BigDecimal amountExecuted;

    /*
     * 并发版本号
     * */
    private Integer concurrencyVersion;
    /**
     * 销售商品明细
     */
    private List<OrderMangProductDetailedVm> orderMangProductDetailedVmList;

    /**
     * 销售明细表
     */
    private List<ManagementSalesDetailsVM> managementSalesDetailsVMList;
    /**
     * 创建人
     */
    private String createBy;
    /**
     * 创建时间
     */
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "更新时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    /**
     * 修改人
     */
    private String updateBy;
    /**
     * 修改时间
     */
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "更新时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;
    /**
     * 合同id
     */
    private String contractId;
    /**
     * 驳回原因
     */
    private String turnDown;
    /**
     * 合同list
     */
    private List<OSSFile> ossFiles;
    /**
     * 销售员公司
     */
    private String salesCompanyCode;
    /**
     * 销售员部门
     */
    private String salesDepartmentCode;
}