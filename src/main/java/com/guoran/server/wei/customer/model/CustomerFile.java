package com.guoran.server.wei.customer.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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

public class CustomerFile implements Serializable {

	/**
	 * id
	 */
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
	@ApiModelProperty ("文件名称")
	private String fileName;
	/**
	 * 附件地址
	 */
	@ApiModelProperty ("附件地址")
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
	@ApiModelProperty ("创建时间")
	private String createBy;
	/**
	 * 修改人
	 */
	@ApiModelProperty ("修改人")
	private Date updateTime;
	/**
	 * 修改时间
	 */
	@ApiModelProperty ("修改时间")
	private String updateBy;
	/**
	 * 文件所属 0 客户信息  1网点信息
	 */
	@ApiModelProperty ("文件所属 0 客户信息  1网点信息")
	private Integer fileBelongs;


}
