package com.guoran.server.common.search;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * grid分页结果
 * @author machao
 * @version 1.0 2017-11-21
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PageResult implements Serializable {
    /**
     * 当前第几页
     */
    private int pageNum;
    /**
     * 总条数
     */
    private long total;
    /**
     * 总页数
     */
    private int pages;
    /**
     * 数据数组
     */
    private Object rows;
}
