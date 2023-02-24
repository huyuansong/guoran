package com.guoran.server.he.production.model;

import com.guoran.server.common.BaseOfConcurrencySafeEntity;
import com.guoran.server.he.production.vo.PostaslaryVo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 生产入库表
 * </p>
 *
 * @author
 * @table production_management_warehousing
 * @create
 * @Modify By
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ManagementWarehousingEntity<postaslaryVo> extends BaseOfConcurrencySafeEntity {
    /**
     * 生产入库表id
     */
    private long id;
    /**
     * 入库单号
     */
    private String warehousingNumber;
    /**
     * 生产计划单号
     */
    private String productionPlanNo;
    /**
     * 商品编号
     */
    private String commodityCode;
    /**
     * 商品名称
     */
    private String tradeName;
    /**
     * 规格
     */
    private String specificationAndModel;
    /**
     * 计量单位
     */
    private String meteringUnit;
    /**
     * 计划生产数量
     */
    private Integer plannedProductionQuantity;
    /**
     * 本次生产数量
     */
    private Integer yieldThisTime;
    /**
     * 商品批次号
     */
    private String commodityBatchNumber;
    /**
     * 生产日期
     */
    private Date dateOfManufacture;
    /**
     * 有效时间
     */
    private Date effectiveTime;
    /**
     * 审核状态
     */
    private Integer auditStatus;
    /**
     * 审核人
     */
    private String reviewer;
    /**
     * 审核时间
     */
    private Date auditTime;
    /**
     * 质检状态
     */
    private Integer qualityInspectionStatus;
    /**
     * 上传状态  1已上传   不为1未上传
     */
    private Integer uploadStatus;
    /**
     * NC流水号
     */
    private String ncFlowingWateNumber;
    /**
     * 上传时间
     */
    private Date uploadTime;
    /**
     * 上传人
     */
    private String uploadeBy;
    /**
     * 备注
     */
    private String remarks;
    /**
     * 生产车间
     */
    private String productionWorkshopId;
    /**
     * 车间名称
     */
    private String productionWorkshopName;

    /*
     *岗位工资中间表信息
     */
    private List<PostaslaryVo> postaslaryVo;

    /*
     *年度计划驳回原因
     */
    private String auditRejectReason;

    /*
     * 仓库编码
     */
    private String warehouseCode;
    /*
     *仓库名称
     */
    private String warehouseName;
    /**
     * 验收单号
     */
    private String acceptanceNumber;
    /**
     * 销售出库后剩余数量
     */
    private BigDecimal remainingNo;
    /**
     * 返回主键
     */
    private String pkProduct;
}