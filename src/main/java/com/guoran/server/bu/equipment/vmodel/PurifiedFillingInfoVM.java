package com.guoran.server.bu.equipment.vmodel;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

/**
 * <p>
 * 设备管理-设备运行记录-纯净水车间-灌装记录表DTO
 * </p>
 *
 * @author zhangjx
 * @create 2020-08-25
 * @Modify By
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PurifiedFillingInfoVM {
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