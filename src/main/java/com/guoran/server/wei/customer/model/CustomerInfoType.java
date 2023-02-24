package com.guoran.server.wei.customer.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * @TableName customer_info_type
 */
@Data
@AllArgsConstructor
@NoArgsConstructor

public class CustomerInfoType implements Serializable {

	/**
	 * 供应商分类主键
	 */
	@ApiModelProperty ("供应商分类主键")
	private String id;
	/**
	 * 供应商分类编码
	 */
	@ApiModelProperty ("供应商分类编码")
	private String code;
	/**
	 * 供应商分类名称
	 */
	@ApiModelProperty ("供应商分类名称")
	private String name;
	/**
	 * 上级供应商分类主键
	 */
	@ApiModelProperty ("上级供应商分类主键")
	private String parentId;
	/**
	 * 上级供应商分类编码
	 */
	@ApiModelProperty ("上级供应商分类编码")
	private String parentcode;
	/**
	 * 上级供应商分类名称
	 */
	@ApiModelProperty ("上级供应商分类名称")
	private String parentname;
	/**
	 * 所属组织主键
	 */
	@ApiModelProperty ("所属组织主键")
	private String pkOrg;
	/**
	 * 所属组织编码
	 */
	@ApiModelProperty ("所属组织编码")
	private String orgcode;
	/**
	 * 所属组织名称
	 */
	@ApiModelProperty ("所属组织名称")
	private String orgname;
	/**
	 * 启用状态 1=未启用，2=已启用，3=已停用，
	 */
	@ApiModelProperty ("启用状态 1=未启用，2=已启用，3=已停用，")
	private Integer enablestate;
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
	 * 启停状态
	 */
	@ApiModelProperty ("启停状态")
	private Integer isUseful;


}
