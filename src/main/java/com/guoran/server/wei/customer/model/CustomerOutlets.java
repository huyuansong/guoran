package com.guoran.server.wei.customer.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
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
	@NotNull (message = "[]不能为空")
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
	@Size (max = 100, message = "编码长度不能超过100")
	@ApiModelProperty ("上级供货点")
	@Length (max = 100, message = "编码长度不能超过100")
	private String parentSupplyPoint;
	/**
	 * 零售商名称
	 */
	@Size (max = 100, message = "编码长度不能超过100")
	@ApiModelProperty ("零售商名称")
	@Length (max = 100, message = "编码长度不能超过100")
	private String retailerName;
	/**
	 * 零售商地址-省
	 */
	@Size (max = 50, message = "编码长度不能超过50")
	@ApiModelProperty ("零售商地址-省")
	@Length (max = 50, message = "编码长度不能超过50")
	private String retailerProvince;
	/**
	 * 零售商地址-市
	 */
	@Size (max = 50, message = "编码长度不能超过50")
	@ApiModelProperty ("零售商地址-市")
	@Length (max = 50, message = "编码长度不能超过50")
	private String retailerCity;
	/**
	 * 零售商地址-区/县
	 */
	@Size (max = 50, message = "编码长度不能超过50")
	@ApiModelProperty ("零售商地址-区/县")
	@Length (max = 50, message = "编码长度不能超过50")
	private String retailerArea;
	/**
	 * 零售商地址-详细地址
	 */
	@Size (max = 100, message = "编码长度不能超过100")
	@ApiModelProperty ("零售商地址-详细地址")
	@Length (max = 100, message = "编码长度不能超过100")
	private String retailerAddress;
	/**
	 * 零售商地址
	 */
	@Size (max = 255, message = "编码长度不能超过255")
	@ApiModelProperty ("零售商地址")
	@Length (max = 255, message = "编码长度不能超过255")
	private String address;
	/**
	 * 零售商联系人
	 */
	@Size (max = 100, message = "编码长度不能超过100")
	@ApiModelProperty ("零售商联系人")
	@Length (max = 100, message = "编码长度不能超过100")
	private String retailerContact;
	/**
	 * 零售商联系电话
	 */
	@ApiModelProperty ("零售商联系电话")
	private Long retailerMobile;
	/**
	 * 业务员
	 */
	@Size (max = 100, message = "编码长度不能超过100")
	@ApiModelProperty ("业务员")
	@Length (max = 100, message = "编码长度不能超过100")
	private String salesman;
	/**
	 * 审核状态
	 */
	@ApiModelProperty ("审核状态 ")
	private Integer auditStatus;
	/**
	 * 审核人
	 */
	@Size (max = 32, message = "编码长度不能超过32")
	@ApiModelProperty ("审核人")
	@Length (max = 32, message = "编码长度不能超过32")
	private String auditBy;
	/**
	 * 审核时间
	 */
	@ApiModelProperty ("审核时间")
	private Date auditTime;
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
	/**
	 * 驳回原因
	 */
	@Size (max = 255, message = "编码长度不能超过255")
	@ApiModelProperty ("驳回原因")
	@Length (max = 255, message = "编码长度不能超过255")
	private String dismissReason;


}
