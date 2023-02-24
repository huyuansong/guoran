package com.guoran.server.psj.equipment.model;

import com.baomidou.mybatisplus.annotation.TableName;
import com.guoran.server.common.BaseOfConcurrencySafeEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

/**
 * <p>
 * 设备管理-设备运行记录-饮料车间-易拉罐灌装记录
 * </p>
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "drinks_cans_filling")
public class DrinksCansFillingEntity extends BaseOfConcurrencySafeEntity {
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
     * 灌装温度（℃）
     */
    private BigDecimal fillingTemperature;
    /**
     * 易拉罐机-自动进料
     */
    private String cansAutoFeed;
    /**
     * 易拉罐机-自动放盖
     */
    private String cansAutoCover;
    /**
     * 液氮加注机-液位补偿量
     */
    private BigDecimal levelCompensation;
    /**
     * 液氮加注机-速度补偿量
     */
    private BigDecimal speedCompensation;
    /**
     * 液氮加注机-实际加注量
     */
    private BigDecimal actualAmount;
    /**
     * 液氮加注机-设定加注时长
     */
    private BigDecimal setTime;
    /**
     * 液氮加注机-速度/min
     */
    private BigDecimal speed;
    /**
     * 液氮加注机-液位/CM
     */
    private BigDecimal level;
    /**
     * 液氮加注机-系统运行时间/ms
     */
    private BigDecimal sysRunTime;
    /**
     * 液氮加注机-罐子间距/mm
     */
    private BigDecimal canSpacing;
    /**
     * 液氮加注机-加注检测器到加注头中心距离/mm
     */
    private BigDecimal distance;
    /**
     * 备注
     */
    private String remark;

}