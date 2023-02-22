package com.hisoft.pam.im.common.search;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 分页查询
 * @author machao
 * @version 1.0 2017-11-21
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PageQuery implements Serializable {

    /**
     * 当前页
     */
    private int pageNum;
    /**
     * 每页条数
     */
    private int pageSize;
    /**
     * 排序
     */
    private String orderBy;
    /**
     * 查询过滤条件
     */
    private FilterGroup where;

    private String commodityCode;
}
