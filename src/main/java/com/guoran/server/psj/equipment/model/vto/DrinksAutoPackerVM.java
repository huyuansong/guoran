package com.guoran.server.psj.equipment.model.vto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

/**
 * <p>
 * 设备管理-设备运行记录-饮料车间-自动包装机运行记录DTO
 * </p>
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DrinksAutoPackerVM {
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
     * 程序号
     */
    private String programNum;
    /**
     * 主机工作速度
     */
    private BigDecimal hostRunSpeed;
    /**
     * 胶机温度
     */
    private BigDecimal glueMachineTemperature;
    /**
     * 胶管温度
     */
    private BigDecimal rubberHoseTemperature;
    /**
     * 喷嘴温度
     */
    private BigDecimal injectorTemperature;
    /**
     * 压力
     */
    private BigDecimal pressure;
    /**
     * 于小宁状况
     */
    private String yuState;
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