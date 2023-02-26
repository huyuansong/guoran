package com.guoran.server.sys.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author zhangmengyu
 * @create 2020/9/7 13:52
 * @Modify By
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuditVo {
    private String ids;
    private Integer state;
    private String dismissReason;
}
