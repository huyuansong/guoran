package com.guoran.server.bu.equipment.model;


import com.guoran.server.common.model.BaseOfConcurrencySafeEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

/**
 * <p>
 * 设备管理-设备运行记录-纯净水车间-灌装记录表
 * </p>
 * <p>
 * <p>
 * w
 * 2023/1/26  19:09
 * 01-pro
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PurifiedFillingInfoEntity extends BaseOfConcurrencySafeEntity {
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
     * 液位
     */
    private BigDecimal level;
    /**
     * 进瓶风机
     */
    private String bottleFeedingFan;
    /**
     * 主电机
     */
    private String host;
    /**
     * 进液泵
     */
    private String feedPump;
    /**
     * 出瓶输送
     */
    private String bottleDelivery;
    /**
     * 吹气阀
     */
    private String blowValve;
    /**
     * 理盖刷机
     */
    private String ligai;
    /**
     * 上盖风机
     */
    private String topCoverFan;
    /**
     * 上盖输送
     */
    private String upperCoverConveying;
    /**
     * 备注
     */
    private String remark;

}

