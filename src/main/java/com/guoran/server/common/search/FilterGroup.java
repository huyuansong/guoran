package com.guoran.server.common.search;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * 查询过滤组
 * @author machao
 * @version 1.0 2017-11-21
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FilterGroup implements Serializable {
    /**
     * 查询过滤规则列表
     */
    private List<FilterRule> rules;

    /**
     * 过滤组操作类型,and,or
     */
    private String op;
    /**
     * 查询过滤组列表
     */
    private List<FilterGroup> groups;
}
