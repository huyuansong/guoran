package com.guoran.server.wei.customer.model.vto;

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
 * 客户管理-分销商信息
 *
 * @TableName customer_distributor
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerDistributorVto implements Serializable {

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
	 * 分销商名称
	 */
	@Size (max = 100, message = "编码长度不能超过100")
	@ApiModelProperty ("分销商名称")
	@Length (max = 100, message = "编码长度不能超过100")
	private String distributorName;
	/**
	 * 联系人
	 */
	@Size (max = 100, message = "编码长度不能超过100")
	@ApiModelProperty ("联系人")
	@Length (max = 100, message = "编码长度不能超过100")
	private String contacts;
	/**
	 * 联系电话
	 */
	@ApiModelProperty ("联系电话")
	private Long contactNumber;
	/**
	 * 联系地址
	 */
	@Size (max = 100, message = "编码长度不能超过100")
	@ApiModelProperty ("联系地址")
	@Length (max = 100, message = "编码长度不能超过100")
	private String contactAddress;
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
