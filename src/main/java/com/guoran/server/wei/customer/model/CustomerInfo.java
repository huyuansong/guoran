package com.guoran.server.wei.customer.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
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
	@NotNull (message = "[]不能为空")
	@ApiModelProperty ("")
	private Long id;
	/**
	 * 客户名称
	 */
	@Size (max = 255, message = "编码长度不能超过255")
	@ApiModelProperty ("客户名称")
	@Length (max = 255, message = "编码长度不能超过255")
	private String userName;
	/**
	 * 纳税人识别号
	 */
	@Size (max = 255, message = "编码长度不能超过255")
	@ApiModelProperty ("纳税人识别号")
	@Length (max = 255, message = "编码长度不能超过255")
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
	@Size (max = 13, message = "编码长度不能超过13")
	@ApiModelProperty ("NC编码")
	@Length (max = 13, message = "编码长度不能超过13")
	private String ncCode;
	/**
	 * 销售员:员工编码
	 */
	@Size (max = 20, message = "编码长度不能超过20")
	@ApiModelProperty ("销售员:员工编码")
	@Length (max = 20, message = "编码长度不能超过20")
	private String salespersonId;
	/**
	 * 销售员名称
	 */
	@Size (max = 255, message = "编码长度不能超过255")
	@ApiModelProperty ("销售员名称")
	@Length (max = 255, message = "编码长度不能超过255")
	private String salespersonName;
	/**
	 * 注册地址-省
	 */
	@Size (max = 50, message = "编码长度不能超过50")
	@ApiModelProperty ("注册地址-省")
	@Length (max = 50, message = "编码长度不能超过50")
	private String registeredProvince;
	/**
	 * 注册地址-市
	 */
	@Size (max = 50, message = "编码长度不能超过50")
	@ApiModelProperty ("注册地址-市")
	@Length (max = 50, message = "编码长度不能超过50")
	private String registeredCity;
	/**
	 * 注册地址-区/县
	 */
	@Size (max = 50, message = "编码长度不能超过50")
	@ApiModelProperty ("注册地址-区/县")
	@Length (max = 50, message = "编码长度不能超过50")
	private String registeredArea;
	/**
	 * 注册地址-详细地址
	 */
	@Size (max = 255, message = "编码长度不能超过255")
	@ApiModelProperty ("注册地址-详细地址")
	@Length (max = 255, message = "编码长度不能超过255")
	private String registeredAddress;
	/**
	 * 收货地址-省
	 */
	@Size (max = 50, message = "编码长度不能超过50")
	@ApiModelProperty ("收货地址-省")
	@Length (max = 50, message = "编码长度不能超过50")
	private String deliveryProvince;
	/**
	 * 收货地址-市
	 */
	@Size (max = 50, message = "编码长度不能超过50")
	@ApiModelProperty ("收货地址-市")
	@Length (max = 50, message = "编码长度不能超过50")
	private String deliveryCity;
	/**
	 * 收货地址-区/县
	 */
	@Size (max = 50, message = "编码长度不能超过50")
	@ApiModelProperty ("收货地址-区/县")
	@Length (max = 50, message = "编码长度不能超过50")
	private String deliveryArea;
	/**
	 * 收货地址-详细地址
	 */
	@Size (max = 255, message = "编码长度不能超过255")
	@ApiModelProperty ("收货地址-详细地址")
	@Length (max = 255, message = "编码长度不能超过255")
	private String deliveryAddress;
	/**
	 * 联系人
	 */
	@Size (max = 50, message = "编码长度不能超过50")
	@ApiModelProperty ("联系人")
	@Length (max = 50, message = "编码长度不能超过50")
	private String contacts;
	/**
	 * 联系电话
	 */
	@ApiModelProperty ("联系电话")
	private Long contactsNumber;
	/**
	 * 邮箱
	 */
	@Size (max = 100, message = "编码长度不能超过100")
	@ApiModelProperty ("邮箱")
	@Length (max = 100, message = "编码长度不能超过100")
	private String email;
	/**
	 * 办公地址-省
	 */
	@Size (max = 50, message = "编码长度不能超过50")
	@ApiModelProperty ("办公地址-省")
	@Length (max = 50, message = "编码长度不能超过50")
	private String officeProvince;
	/**
	 * 办公地址-市
	 */
	@Size (max = 50, message = "编码长度不能超过50")
	@ApiModelProperty ("办公地址-市")
	@Length (max = 50, message = "编码长度不能超过50")
	private String officeCity;
	/**
	 * 办公地址-区/县
	 */
	@Size (max = 50, message = "编码长度不能超过50")
	@ApiModelProperty ("办公地址-区/县")
	@Length (max = 50, message = "编码长度不能超过50")
	private String officeArea;
	/**
	 * 办公地址-详细地址
	 */
	@Size (max = 255, message = "编码长度不能超过255")
	@ApiModelProperty ("办公地址-详细地址")
	@Length (max = 255, message = "编码长度不能超过255")
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
	 * 驳回原因
	 */
	@Size (max = 255, message = "编码长度不能超过255")
	@ApiModelProperty ("驳回原因")
	@Length (max = 255, message = "编码长度不能超过255")
	private String dismissReason;
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
	/**
	 * 客商编码
	 */
	@Size (max = 255, message = "编码长度不能超过255")
	@ApiModelProperty ("客商编码")
	@Length (max = 255, message = "编码长度不能超过255")
	private String code;
	/**
	 * 供应商分类主键
	 */
	@Size (max = 20, message = "编码长度不能超过20")
	@ApiModelProperty ("供应商分类主键")
	@Length (max = 20, message = "编码长度不能超过20")
	private String pkSupplierclass;
	/**
	 * 供应商分类编码
	 */
	@Size (max = 40, message = "编码长度不能超过40")
	@ApiModelProperty ("供应商分类编码")
	@Length (max = 40, message = "编码长度不能超过40")
	private String supclasscode;
	/**
	 * 供应商分类名称
	 */
	@Size (max = 200, message = "编码长度不能超过200")
	@ApiModelProperty ("供应商分类名称")
	@Length (max = 200, message = "编码长度不能超过200")
	private String supclassname;
	/**
	 * 来源系统主键
	 */
	@Size (max = 20, message = "编码长度不能超过20")
	@ApiModelProperty ("来源系统主键")
	@Length (max = 20, message = "编码长度不能超过20")
	private String srcsystempk;
	/**
	 * 来源系统编码 HB=环保系统，
	 * OA=蓝凌OA，
	 * HR=人资系统，
	 * DC=电采系统
	 */
	@Size (max = 40, message = "编码长度不能超过40")
	@ApiModelProperty ("来源系统编码 HB=环保系统， OA=蓝凌OA， HR=人资系统， DC=电采系统")
	@Length (max = 40, message = "编码长度不能超过40")
	private String srcsystemcode;
	/**
	 * 来源系统名称
	 */
	@Size (max = 40, message = "编码长度不能超过40")
	@ApiModelProperty ("来源系统名称")
	@Length (max = 40, message = "编码长度不能超过40")
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
