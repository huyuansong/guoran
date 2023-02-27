package com.guoran.server.sys.model;

import com.guoran.server.common.BaseOfConcurrencySafeEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;


/**
 * <p>
 *
 * </p>
 *
 * @author zcq
 * @table sys_product_type
 * @create 2020-11-16
 * @Modify By
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductTypeEntity extends BaseOfConcurrencySafeEntity {
    /**
     * 自增ID
     */
    private Long id;
    /**
     * 父级ID
     */
    private Long parentId;
    /**
     * 类别编号
     */
    private Integer catagoryNo;
    /**
     * 类别名称
     */
    private String catagoryName;


    private String companyId;
    /**
     * 平均单价
     */
    private BigDecimal avgPrice;
    /**
     * 平均成本
     */
    private BigDecimal avgCost;
    /**
     * 平均采购提前期
     */
    private Integer avgBuyTime;
    /**
     * 平均成产提前期
     */
    private Integer avgProductionTime;
    /**
     * 辅助属性结构
     */
    private String moreAttribute;
    /**
     * 是否启用 1:是  0:否
     */
    private Integer status;


    private String isPublic;
    /**
     * 排序数字
     */
    private Integer sortId;
    /**
     * 质量标准
     */
    private String qualityStandard;
    /**
     * 质保类型
     * FK0401:房屋及构筑物
     * FK0402:办公设备
     * FK0403:机器设备
     * FK0404:电子电器设备
     * FK0405:仪器仪表设备
     * FK0406:交通运输设备
     * FK0407:其他固定资产
     * FK0408:文体文物陈列品
     * FK1501:其他物资质保金
     */
    private String ftypeZhibao;
    /**
     * 预付类型
     * YF0201:房屋及构筑物
     * YF0202:办公设备
     * YF0203:机器设备
     * YF0204:电子电器设备
     * YF0205:仪器仪表设备
     * YF0206:交通运输设备
     * YF0207:文体文物陈列品
     * YF0208:其他固定资产
     * YF0701:兽药
     * YF0801:疫苗
     * YF0901:其他物资
     */
    private String ftypePrepay;
    /**
     * 应付类型
     * FK0101:房屋及构筑物
     * FK0102:办公设备
     * FK0103:机器设备
     * FK0104:电子电器设备
     * FK0105:仪器仪表设备
     * FK0106:交通运输设备
     * FK0107:文体文物陈列品
     * FK0108:其他固定资产
     * FK0801:兽药
     * FK0901:疫苗
     * FK1001:其他物资
     */
    private String ftypePayable;
    /**
     * 资产类别
     */
    private Integer fixedType;
    /**
     * 使用月限
     */
    private Integer useMonth;
    /**
     * 更新人
     */
    private String updateBy;
    /**
     * 更新时间
     */
    private Date updateTime;
    /**
     * 审核人
     */
    private String auditBy;
    /**
     * 审核时间
     */
    private Date auditTime;

}