package com.guoran.server.wei.customer.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
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
public class CustomerBank implements Serializable {

	/**
	 *
	 */
	@ApiModelProperty ("")
	private Long id;
	/**
	 * 客户ID
	 */
	@ApiModelProperty ("客户ID")
	private Long customerId;
	/**
	 * 户名
	 */
	@ApiModelProperty ("户名")
	private String accountName;
	/**
	 * 银行账户
	 */
	@ApiModelProperty ("银行账户")
	private Long bankAccount;
	/**
	 * 开启行
	 */
	@ApiModelProperty ("开启行")
	private String openAccountBank;
	/**
	 * 联行号12位组成：3位银行代码+4位城市代码+4位银行编号+1位校验位。
	 */
	@ApiModelProperty ("联行号12位组成：3位银行代码+4位城市代码+4位银行编号+1位校验位。")
	private Long interBankNumber;
	/**
	 * 是否默认账户
	 */
	@ApiModelProperty ("是否默认账户")
	private Integer defaultAccount;
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
	/**
	 * 供应商主键
	 */
	@ApiModelProperty ("供应商主键")
	private String pkCust;
	/**
	 * 银行账户主键
	 */
	@ApiModelProperty ("银行账户主键")
	private String pkId;

}
