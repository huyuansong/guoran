package com.guoran.server.wei.customer.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @TableName customer_info
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerInfo implements Serializable {

	/**
	 *
	 */
	@ApiModelProperty ("")
	private Long id;
	/**
	 * 客户名称
	 */
	@ApiModelProperty ("客户名称")
	private String userName;
	/**
	 * 纳税人识别号
	 */
	@ApiModelProperty ("纳税人识别号")
	private String taxpayerIdentification;
	/**
	 * 客户类型，枚举处理
	 */
	@ApiModelProperty ("客户类型，枚举处理")
	private Integer userType;
	/**
	 * 是否个人,枚举处理
	 */
	@ApiModelProperty ("是否个人,枚举处理")
	private Integer isPersonal;
	/**
	 * NC编码
	 */
	@ApiModelProperty ("NC编码")
	private String ncCode;
	/**
	 * 销售员:员工编码
	 */
	@ApiModelProperty ("销售员:员工编码")
	private String salespersonId;
	/**
	 * 销售员名称
	 */
	@ApiModelProperty ("销售员名称")
	private String salespersonName;
	/**
	 * 注册地址-省
	 */
	@ApiModelProperty ("注册地址-省")
	private String registeredProvince;
	/**
	 * 注册地址-市
	 */
	@ApiModelProperty ("注册地址-市")
	private String registeredCity;
	/**
	 * 注册地址-区/县
	 */
	@ApiModelProperty ("注册地址-区/县")
	private String registeredArea;
	/**
	 * 注册地址-详细地址
	 */
	@ApiModelProperty ("注册地址-详细地址")
	private String registeredAddress;
	/**
	 * 收货地址-省
	 */
	@ApiModelProperty ("收货地址-省")
	private String deliveryProvince;
	/**
	 * 收货地址-市
	 */
	@ApiModelProperty ("收货地址-市")
	private String deliveryCity;
	/**
	 * 收货地址-区/县
	 */
	@ApiModelProperty ("收货地址-区/县")
	private String deliveryArea;
	/**
	 * 收货地址-详细地址
	 */
	@ApiModelProperty ("收货地址-详细地址")
	private String deliveryAddress;
	/**
	 * 联系人
	 */
	@ApiModelProperty ("联系人")
	private String contacts;
	/**
	 * 联系电话
	 */
	@ApiModelProperty ("联系电话")
	private Long contactsNumber;
	/**
	 * 邮箱
	 */
	@ApiModelProperty ("邮箱")
	private String email;
	/**
	 * 办公地址-省
	 */
	@ApiModelProperty ("办公地址-省")
	private String officeProvince;
	/**
	 * 办公地址-市
	 */
	@ApiModelProperty ("办公地址-市")
	private String officeCity;
	/**
	 * 办公地址-区/县
	 */
	@ApiModelProperty ("办公地址-区/县")
	private String officeArea;
	/**
	 * 办公地址-详细地址
	 */
	@ApiModelProperty ("办公地址-详细地址")
	private String officeAddress;
	/**
	 * 是否可赊销
	 */
	@ApiModelProperty ("是否可赊销")
	private Integer isCredit;
	/**
	 * 赊销额度（元）
	 */
	@ApiModelProperty ("赊销额度（元）")
	private BigDecimal creditQuota;
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
	 * 驳回原因
	 */
	@ApiModelProperty ("驳回原因")
	private String dismissReason;
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
	/**
	 * 客商编码
	 */
	@ApiModelProperty ("客商编码")
	private String code;
	/**
	 * 供应商分类主键
	 */
	@ApiModelProperty ("供应商分类主键")
	private String pkSupplierclass;
	/**
	 * 供应商分类编码
	 */
	@ApiModelProperty ("供应商分类编码")
	private String supclasscode;
	/**
	 * 供应商分类名称
	 */
	@ApiModelProperty ("供应商分类名称")
	private String supclassname;
	/**
	 * 来源系统主键
	 */
	@ApiModelProperty ("来源系统主键")
	private String srcsystempk;
	/**
	 * 来源系统编码 HB=环保系统，
	 * OA=蓝凌OA，
	 * HR=人资系统，
	 * DC=电采系统
	 */
	@ApiModelProperty ("来源系统编码 HB=环保系统， OA=蓝凌OA， HR=人资系统， DC=电采系统")
	private String srcsystemcode;
	/**
	 * 来源系统名称
	 */
	@ApiModelProperty ("来源系统名称")
	private String srcsystemname;
	/**
	 * 客商主键
	 */
	@ApiModelProperty ("客商主键")
	private String pkId;
	/**
	 * 上传状态 0未上传 1已上传
	 */
	@ApiModelProperty ("上传状态 0未上传 1已上传 ")
	private Integer uploadStatus;

}
