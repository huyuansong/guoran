package com.guoran.server.wei.customer.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * 客户管理-网点信息
 *
 * @TableName customer_outlets
 */
@Data
@AllArgsConstructor
@NoArgsConstructor

public class CustomerOutlets implements Serializable {

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
	 * 上级供货点
	 */
	@ApiModelProperty ("上级供货点")
	private String parentSupplyPoint;
	/**
	 * 零售商名称
	 */
	@ApiModelProperty ("零售商名称")
	private String retailerName;
	/**
	 * 零售商地址-省
	 */
	@ApiModelProperty ("零售商地址-省")
	private String retailerProvince;
	/**
	 * 零售商地址-市
	 */
	@ApiModelProperty ("零售商地址-市")
	private String retailerCity;
	/**
	 * 零售商地址-区/县
	 */
	@ApiModelProperty ("零售商地址-区/县")
	private String retailerArea;
	/**
	 * 零售商地址-详细地址
	 */
	@ApiModelProperty ("零售商地址-详细地址")
	private String retailerAddress;
	/**
	 * 零售商地址
	 */
	@ApiModelProperty ("零售商地址")
	private String address;
	/**
	 * 零售商联系人
	 */
	@ApiModelProperty ("零售商联系人")
	private String retailerContact;
	/**
	 * 零售商联系电话
	 */
	@ApiModelProperty ("零售商联系电话")
	private Long retailerMobile;
	/**
	 * 业务员
	 */
	@ApiModelProperty ("业务员")
	private String salesman;
	/**
	 * 审核状态
	 */
	@ApiModelProperty ("审核状态 ")
	private Integer auditStatus;
	/**
	 * 审核人
	 */
	@ApiModelProperty ("审核人")
	private String auditBy;
	/**
	 * 审核时间
	 */
	@ApiModelProperty ("审核时间")
	private Date auditTime;
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
	 * 驳回原因
	 */
	@ApiModelProperty ("驳回原因")
	private String dismissReason;


}
