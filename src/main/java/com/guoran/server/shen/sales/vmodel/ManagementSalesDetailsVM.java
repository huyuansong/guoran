package com.guoran.server.shen.sales.vmodel;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.util.Date;

/**
 * <p>
 * 销售明细表DTO
 * </p>
 *
 * @author zhangjx
 * @create 2020-08-26
 * @Modify By
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ManagementSalesDetailsVM {
	/**
	 * 销售明细表id
	 */
	private Integer id;
	/**
	 * 销售订单编号
	 */
	private String orderNumber;
	/**
	 * 出库流水号
	 */
	private String outboundFlowNumber;
	/**
	 * 出库日期
	 */
	@JsonFormat (timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
	@ApiModelProperty (value = "出库日期")
	@DateTimeFormat (pattern = "yyyy-MM-dd HH:mm:ss")
	private Date deliveryDate;
	/**
	 * 出库仓库编码
	 */
	private String deliveryWarehouseId;
	/**
	 * 出库仓库
	 */
	private String deliveryWarehouse;
	/**
	 * 客户id
	 */
	private Integer relatedECommerceId;
	/**
	 * 客户名称
	 */
	private String relatedECommerce;
	/**
	 * 商品编号
	 */
	private String commodityCode;
	/**
	 * 商品名称
	 */
	private String productName;
	/**
	 * 商品批次号
	 */
	private String commodityBatchNumber;
	/**
	 * 规格型号
	 */
	private String specificationAndModel;
	/**
	 * 计量单位
	 */
	private Integer meteringUnit;
	/**
	 * 销售数量
	 */
	private Integer sellNumber;

	/**
	 * 销售单价
	 */
	private BigDecimal unitPrice;
	/**
	 * 出库数量
	 */
	private Integer salesVolumes;
	/**
	 * 本次销售金额
	 */
	private BigDecimal thisTimeSaleAmountOfMoney;
	/**
	 * 出库单价
	 */
	private BigDecimal deliveryUnitPrice;
	/**
	 * 出库金额（含税）
	 */
	private BigDecimal deliveryAmount;
	/**
	 * 税率
	 */
	private BigDecimal taxRate;
	/**
	 * 不含税金额
	 */
	private BigDecimal amountExcludingTax;
	/**
	 * 客户要求发票类型
	 */
	private Integer customerRequestedInvoiceType;
	/**
	 * 状态
	 */
	private Integer salesDetailsStatus;
	/**
	 * 发货状态
	 */
	private Integer shipmentStatus;
	/**
	 * 上传状态
	 */
	private Integer uploadStatus;
	/**
	 * NC单号
	 */
	private String ncOddNumbers;
	/**
	 * 是否可开发票
	 */
	private Integer billYesNo;
	/**
	 * 开票状态
	 */
	private Integer billingStatus;
	/**
	 * 发票类型
	 */
	private Integer invpoiceType;
	/**
	 * 发票代码
	 */
	private Integer invoiceCode;
	/**
	 * 发票号码
	 */
	private String invoiceNumber;
	/**
	 * 开票时间
	 */
	@JsonFormat (timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
	@ApiModelProperty (value = "开票时间")
	@DateTimeFormat (pattern = "yyyy-MM-dd HH:mm:ss")
	private Date billingDatetime;
	/**
	 * 金额
	 */
	private BigDecimal amountOfMoney;
	/**
	 * 税额
	 */
	private BigDecimal taxAmount;
	/**
	 * 备注
	 */
	private String remarks;
	/**
	 * 创建时间
	 */
	@JsonFormat (timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
	@ApiModelProperty (value = "创建时间")
	@DateTimeFormat (pattern = "yyyy-MM-dd HH:mm:ss")
	private Date createTime;

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
	@JsonFormat (timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
	@ApiModelProperty (value = "修改时间")
	@DateTimeFormat (pattern = "yyyy-MM-dd HH:mm:ss")
	private Date updateTime;
	/**
	 * 修改人
	 */
	private String updateBy;
	/**
	 * 凭证生成  1 是 0否
	 */
	private Integer voucherValue;
	/**
	 * 未发货数量
	 */
	private Integer countYes;

	/**
	 * 已退货数量
	 */
	private Integer countTuiHuoYes;


	/**
	 * 驳回原因
	 */
	private String turnDown;

	/**
	 * 凭证上传返回的凭证主键
	 */
	private String pkSaleout;
	/**
	 * 销售员公司
	 */
	private String salesCompanyCode;
	/**
	 * 销售员部门
	 */
	private String salesDepartmentCode;

	private Integer thisTakeGift;
}
