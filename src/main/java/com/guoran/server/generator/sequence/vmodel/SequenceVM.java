package com.guoran.server.generator.sequence.vmodel;

import lombok.Data;

import java.util.Date;

/**
 * @description: 序号VM
 * @author: machao
 * @create: 2019-12-12 11:13
 * @Modify By
 **/
@Data
public class SequenceVM {
    /**
     * id
     */
    private Long id;
    /**
     * 编号类型
     */
    private String codeType;
    /**
     * 序号
     */
    private Integer seq;
    /**
     * 备注
     */
    private String remark;
    /*
     * 并发版本号
     * */
    private Integer concurrencyVersion;
    /**
     * 创建时间
     */
    private Date gmtCreate;
    /**
     * 创建人
     */
    private String creator;
    /**
     * 修改时间
     */
    private Date gmtModified;
    /**
     * 修改人
     */
    private String modifier;
}
