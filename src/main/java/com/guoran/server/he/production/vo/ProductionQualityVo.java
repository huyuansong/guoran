package com.guoran.server.he.production.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @time 2023/2/2414:52
 * @outhor zhou
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductionQualityVo implements Serializable {
    private Long[] ids;
    private Integer status;

    private String auditRejectReason;
}
