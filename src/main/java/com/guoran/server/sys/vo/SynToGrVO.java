package com.guoran.server.sys.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

/**
 * @author zhangmengyu
 * @create 2020/11/14 17:09
 * @Modify By
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SynToGrVO {
    private String dataType;

    private Map[] data;

    private String op;
}
