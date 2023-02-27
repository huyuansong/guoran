package com.guoran.server.ma.scattered.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ScatteredQualityVo implements Serializable {
    private Integer[] ids;
    private Integer status;
    //驳回原因
    private String auditRejectReason;
}
