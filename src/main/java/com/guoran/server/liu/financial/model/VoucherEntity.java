package com.guoran.server.liu.financial.model;

import com.guoran.server.common.BaseOfConcurrencySafeEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VoucherEntity extends BaseOfConcurrencySafeEntity {
    private Long id;
    /**
     * 上传状态：已上传、待上传、上传失败
     */
    private Integer uploadState;
    /**
     * 上传错误原因
     */
    private String uploadErrorContent;
    /**
     * 业务类型：销售明细、销售退回
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
    private Date preparationDate;
    /**
     * 会计期间
     */
    private Date accountingPeriod;
    /**
     * 凭证编号
     */
    private String voucherCode;
    /**
     * 制单人
     */
    private String creator;
    /**
     * 凭证上传返回的凭证主键
     */
    private String pkVoucher;
    /**
     * 所属公司编码
     */
    private String CompanyCode;
    /**
     * 制单人
     */
    private String creatorCode;
}
