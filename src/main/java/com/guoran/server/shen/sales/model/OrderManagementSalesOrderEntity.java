package com.guoran.server.shen.sales.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.guoran.server.common.BaseOfConcurrencySafeEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.util.Date;

/**
 * <p>
    销售订单表主键
   </p>
 *
 * @table order_management_sales_order
 * @author zhangjx
 * @create 2020-08-20
 * @Modify By
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderManagementSalesOrderEntity extends BaseOfConcurrencySafeEntity {
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
     * */
    private Integer customerId;
    /**
     * 客户
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
     *1:已审核
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
    /**
     *  创建人
     * */
    private String createBy;
    /**
     *  创建时间
     * */
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "更新时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    /**
     *  修改人
     * */
    private String updateBy;
    /**
     *  修改时间
     * */
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "更新时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;
    /**
     * 合同id
     * */
    private String  contractId;

    /**
     * 驳回原因
     * */
    private String turnDown;
    /**
     * 销售员公司
     */
    private String salesCompanyCode;
    /**
     * 销售员部门
     */
    private String salesDepartmentCode;

}