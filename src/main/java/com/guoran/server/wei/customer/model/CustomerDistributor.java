package com.guoran.server.wei.customer.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * 客户管理-分销商信息
 *
 * @TableName customer_distributor
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerDistributor implements Serializable {

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
	 * 分销商名称
	 */
	@ApiModelProperty ("分销商名称")
	private String distributorName;
	/**
	 * 联系人
	 */
	@ApiModelProperty ("联系人")
	private String contacts;
	/**
	 * 联系电话
	 */
	@ApiModelProperty ("联系电话")
	private Long contactNumber;
	/**
	 * 联系地址
	 */
	@ApiModelProperty ("联系地址")
	private String contactAddress;
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