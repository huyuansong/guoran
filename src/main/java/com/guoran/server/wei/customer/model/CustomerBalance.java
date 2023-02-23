package com.guoran.server.wei.customer.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 客户管理-客户银行信息
 *
 * @author Wei
 * @TableName customer_bank
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerBalance implements Serializable {

	/**
	 *
	 */
	@ApiModelProperty ("")
	private Long id;
	/**
	 * 客户id
	 */
	@ApiModelProperty ("客户id")
	private Long customerId;
	/**
	 * 客户名称
	 */
	@ApiModelProperty ("客户名称")
	private String userName;
	/**
	 * 纳税人识别号
	 */
	@ApiModelProperty ("纳税人识别号")
	private String taxpayerIdentification;
	/**
	 * 期初
	 */
	@ApiModelProperty ("期初")
	private BigDecimal stageFirst;
	/**
	 * 补偿
	 */
	@ApiModelProperty ("补偿")
	private BigDecimal compensate;
	/**
	 * 收款
	 */
	@ApiModelProperty ("收款")
	private BigDecimal collection;
	/**
	 * 销售退款
	 */
	@ApiModelProperty ("销售退款")
	private BigDecimal salesRefund;
	/**
	 * 销售金额
	 */
	@ApiModelProperty ("销售金额")
	private BigDecimal salesAmount;
	/**
	 * 货款余额
	 */
	@ApiModelProperty ("货款余额")
	private BigDecimal paymentBalance;
	/**
	 * NC编码
	 */
	@ApiModelProperty ("NC编码")
	private String ncCode;
	/**
	 * 创建人
	 */
	@ApiModelProperty ("创建人")
	private String createBy;
	/**
	 * 创建时间
	 */
	@ApiModelProperty ("创建时间")
	private Date createTime;
	/**
	 * 更新人
	 */
	@ApiModelProperty ("更新人")
	private String updateBy;
	/**
	 * 更新时间
	 */
	@ApiModelProperty ("更新时间")
	private Date updateTime;

}
