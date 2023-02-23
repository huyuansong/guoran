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
 * 客户管理-客户文件信息表
 *
 * @TableName customer_file
 */
@Data
@AllArgsConstructor
@NoArgsConstructor

public class CustomerFileVto implements Serializable {

	/**
	 * id
	 */
	@NotNull (message = "[id]不能为空")
	@ApiModelProperty ("id")
	private Long id;
	/**
	 * 上传文件通用表数据ID
	 */
	@ApiModelProperty ("上传文件通用表数据ID")
	private Long ossId;
	/**
	 * 客户id
	 */
	@ApiModelProperty ("客户id")
	private Long customerId;
	/**
	 * 文件类型：图片或文件，图片只能5个，文件1个，文件可以上传图片
	 */
	@ApiModelProperty ("文件类型：图片或文件，图片只能5个，文件1个，文件可以上传图片")
	private Integer fileType;
	/**
	 * 文件标识
	 */
	@ApiModelProperty ("文件标识")
	private Integer fileMark;
	/**
	 * 文件名称
	 */
	@Size (max = 100, message = "编码长度不能超过100")
	@ApiModelProperty ("文件名称")
	@Length (max = 100, message = "编码长度不能超过100")
	private String fileName;
	/**
	 * 附件地址
	 */
	@Size (max = 255, message = "编码长度不能超过255")
	@ApiModelProperty ("附件地址")
	@Length (max = 255, message = "编码长度不能超过255")
	private String fileUrl;
	/**
	 * 文件大小
	 */
	@ApiModelProperty ("文件大小")
	private Double fileSize;
	/**
	 * 并发版本号
	 */
	@ApiModelProperty ("并发版本号")
	private Long concurrencyVersion;
	/**
	 * 创建人
	 */
	@ApiModelProperty ("创建人")
	private Date createTime;
	/**
	 * 创建时间
	 */
	@Size (max = 100, message = "编码长度不能超过100")
	@ApiModelProperty ("创建时间")
	@Length (max = 100, message = "编码长度不能超过100")
	private String createBy;
	/**
	 * 修改人
	 */
	@ApiModelProperty ("修改人")
	private Date updateTime;
	/**
	 * 修改时间
	 */
	@Size (max = 100, message = "编码长度不能超过100")
	@ApiModelProperty ("修改时间")
	@Length (max = 100, message = "编码长度不能超过100")
	private String updateBy;
	/**
	 * 文件所属 0 客户信息  1网点信息
	 */
	@ApiModelProperty ("文件所属 0 客户信息  1网点信息")
	private Integer fileBelongs;


}
