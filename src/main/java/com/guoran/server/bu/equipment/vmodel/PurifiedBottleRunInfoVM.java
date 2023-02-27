package com.guoran.server.bu.equipment.vmodel;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

/**
 * <p>
 * 设备管理-设备运行记录-纯净水车间-吹瓶机运行记录DTO
 * </p>
 *
 * @author zhangjx
 * @create 2020-08-25
 * @Modify By
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PurifiedBottleRunInfoVM {
    private Long id;
    /**
     * 部门ID
     */
    private String departId;
    /**
     * 部门名称
     */
    private String departName;
    /**
     * 商品编码：SPBM+6位序号
     */
    private String prodectCode;
    /**
     * 商品编码名称
     */
    private String prodectName;
    /**
     * 规格
     */
    private String specifications;
    /**
     * 记录员
     */
    private String noteTaker;
    /**
     * 记录时间
     */
    private Date noteTakerTime;
    /**
     * 压力（Mpa）
     */
    private BigDecimal programNum;
    /**
     * 冷干机温度/℃
     */
    private BigDecimal hostRunSpeed;
    /**
     * 冷却水温度/℃
     */
    private BigDecimal glueMachineTemperature;
    /**
     * 温控温度/℃
     */
    private BigDecimal rubberHoseTemperature;
    /**
     * 1加温温度/℃
     */
    private BigDecimal oneTemperature;
    /**
     * 2加温温度/℃
     */
    private BigDecimal twoTemperature;
    /**
     * 3加温温度/℃
     */
    private BigDecimal threeTemperature;
    /**
     * 4加温温度/℃/℃
     */
    private BigDecimal fourTemperature;
    /**
     * 5加温温度/℃
     */
    private BigDecimal fiveTemperature;
    /**
     * 使用瓶胚生产日期
     */
    private Date productionDate;
    /**
     * 备注
     */
    private String remark;

    /*
     * 并发版本号
     * */
    private Integer concurrencyVersion;
    /**
     * 创建人
     */
    private String createBy;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 更新人
     */
    private String updateBy;

    /**
     * 更新时间
     */
    private Date updateTime;
}