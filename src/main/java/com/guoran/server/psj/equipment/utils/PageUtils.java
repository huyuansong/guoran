package com.guoran.server.psj.equipment.utils;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.guoran.server.common.search.PageQuery;
import com.guoran.server.common.search.PageResult;

public class PageUtils {

    public static PageResult PageCopy(PageQuery pageQuery, Page pageInfo) {
        PageHelper.startPage(pageQuery.getPageNum(), pageQuery.getPageSize(), pageQuery.getOrderBy());
        PageResult pageResult = new PageResult();
        pageResult.setRows(pageInfo);
        pageResult.setTotal(pageInfo.getTotal());
        pageResult.setPages(pageInfo.getPages());
        pageResult.setPageNum(pageQuery.getPageNum());
        return pageResult;
    }
}
