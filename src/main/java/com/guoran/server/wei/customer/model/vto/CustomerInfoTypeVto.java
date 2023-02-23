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
 * @TableName customer_info_type
 */
@Data
@AllArgsConstructor
@NoArgsConstructor

public class CustomerInfoTypeVto implements Serializable {

	/**
	 * 供应商分类主键
	 */
	@NotNull (message = "[供应商分类主键]不能为空")
	@ApiModelProperty ("供应商分类主键")
	private String id;
	/**
	 * 供应商分类编码
	 */
	@Size (max = 40, message = "编码长度不能超过40")
	@ApiModelProperty ("供应商分类编码")
	@Length (max = 40, message = "编码长度不能超过40")
	private String code;
	/**
	 * 供应商分类名称
	 */
	@Size (max = 200, message = "编码长度不能超过200")
	@ApiModelProperty ("供应商分类名称")
	@Length (max = 200, message = "编码长度不能超过200")
	private String name;
	/**
	 * 上级供应商分类主键
	 */
	@Size (max = 20, message = "编码长度不能超过20")
	@ApiModelProperty ("上级供应商分类主键")
	@Length (max = 20, message = "编码长度不能超过20")
	private String parentId;
	/**
	 * 上级供应商分类编码
	 */
	@Size (max = 40, message = "编码长度不能超过40")
	@ApiModelProperty ("上级供应商分类编码")
	@Length (max = 40, message = "编码长度不能超过40")
	private String parentcode;
	/**
	 * 上级供应商分类名称
	 */
	@Size (max = 200, message = "编码长度不能超过200")
	@ApiModelProperty ("上级供应商分类名称")
	@Length (max = 200, message = "编码长度不能超过200")
	private String parentname;
	/**
	 * 所属组织主键
	 */
	@Size (max = 20, message = "编码长度不能超过20")
	@ApiModelProperty ("所属组织主键")
	@Length (max = 20, message = "编码长度不能超过20")
	private String pkOrg;
	/**
	 * 所属组织编码
	 */
	@Size (max = 40, message = "编码长度不能超过40")
	@ApiModelProperty ("所属组织编码")
	@Length (max = 40, message = "编码长度不能超过40")
	private String orgcode;
	/**
	 * 所属组织名称
	 */
	@Size (max = 200, message = "编码长度不能超过200")
	@ApiModelProperty ("所属组织名称")
	@Length (max = 200, message = "编码长度不能超过200")
	private String orgname;
	/**
	 * 启用状态 1=未启用，2=已启用，3=已停用，
	 */
	@ApiModelProperty ("启用状态 1=未启用，2=已启用，3=已停用，")
	private Integer enablestate;
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
	 * 启停状态
	 */
	@ApiModelProperty ("启停状态")
	private Integer isUseful;


}
