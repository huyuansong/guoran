package com.guoran.server.common.search;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 查询过滤规则
 * @author machao
 * @version 1.0 2017-11-21
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FilterRule implements Serializable {

    /**
     * 过滤字段数据类型
     */
    private String type;
    /**
     * 过滤字段名
     */
    private String field;
    /**
     * 过滤值
     */
    private Object value;
    /**
     * 操作类型
     */
    private FileterOperatorEnum op;

}
