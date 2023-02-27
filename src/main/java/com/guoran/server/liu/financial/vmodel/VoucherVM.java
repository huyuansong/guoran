package com.guoran.server.liu.financial.vmodel;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class VoucherVM {
    private Long id;
    /**
     * 上传状态：已上传、待上传、上传失败
     * 已上传0  待上传1  上传失败 2
     */
    private Integer uploadState;
    /**
     * 上传错误原因
     */
    private String uploadErrorContent;
    /**
     * 业务类型：销售明细、销售退回
     * 销售明细 0    销售退回  1
     */
    private Integer businessType;
    /**
     * 编码：销售明细销售订单编号、销售退回明细表退回单号
     */
    private String orderCode;
    /**
     * 核算主体：内蒙古奈曼牧原农牧有限公司/31
     */
    private String accountingEntity;
    /**
     * 供应商：供应商编码/供应商
     */
    private String supplier;
    /**
     * 凭证类型：转账凭证
     */
    private String documentType;
    /**
     * 摘要
     */
    private String remark;
    /**
     * 借方金额：借方科目合计值
     */
    private BigDecimal debitAmount;
    /**
     * 贷方金额：贷方科目合计值
     */
    private BigDecimal creditAmount;
    /**
     * 制单日期：生成凭证日期
     */
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "制单日期")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date preparationDate;
    /**
     * 会计期间
     */
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "会计期间")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date accountingPeriod;
    /**
     * 凭证编号
     */
    private String voucherCode;
    /**
     * 子表集合
     */
    private List<VoucherDetailsVM> voucherDetailsVMS;
    /**
     * 制单人
     */
    private String creator;
    /**
     * 制单人
     */
    private String creatorCode;

    /*
     * 并发版本号
     * */
    private Integer concurrencyVersion;
    /**
     * 创建时间
     */
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "创建时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;
    /**
     * 创建人
     */
    private String createBy;
    /**
     * 修改时间
     */
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "修改时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;
    /**
     * 修改人
     */
    private String updateBy;
    /**
     * 凭证上传返回的凭证主键
     */
    private String pkVoucher;
    /**
     * 所属公司编码
     */
    private String CompanyCode;


}