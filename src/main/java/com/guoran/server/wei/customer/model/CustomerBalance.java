package com.guoran.server.wei.customer.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
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
	@NotNull (message = "[]不能为空")
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
	@Size (max = 255, message = "编码长度不能超过255")
	@ApiModelProperty ("客户名称")
	@Length (max = 255, message = "编码长度不能超过255")
	private String userName;
	/**
	 * 纳税人识别号
	 */
	@Size (max = 255, message = "编码长度不能超过255")
	@ApiModelProperty ("纳税人识别号")
	@Length (max = 255, message = "编码长度不能超过255")
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
	@Size (max = 13, message = "编码长度不能超过13")
	@ApiModelProperty ("NC编码")
	@Length (max = 13, message = "编码长度不能超过13")
	private String ncCode;
	/**
	 * 创建人
	 */
	@Size (max = 32, message = "编码长度不能超过32")
	@ApiModelProperty ("创建人")
	@Length (max = 32, message = "编码长度不能超过32")
	private String createBy;
	/**
	 * 创建时间
	 */
	@ApiModelProperty ("创建时间")
	private Date createTime;
	/**
	 * 更新人
	 */
	@Size (max = 32, message = "编码长度不能超过32")
	@ApiModelProperty ("更新人")
	@Length (max = 32, message = "编码长度不能超过32")
	private String updateBy;
	/**
	 * 更新时间
	 */
	@ApiModelProperty ("更新时间")
	private Date updateTime;

}
