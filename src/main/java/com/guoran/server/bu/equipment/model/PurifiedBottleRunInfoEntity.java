package com.guoran.server.bu.equipment.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

/**
 * * <p>
 * 设备管理-设备运行记录-纯净水车间-吹瓶机运行记录
 * </p>
 * *
 * w
 * 2023/1/25  14:56
 * 01-pro
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PurifiedBottleRunInfoEntity {
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

}

