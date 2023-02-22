package com.hisoft.pam.im.common.resource;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hisoft.pam.im.common.search.PageResult;
import com.hisoft.pam.im.common.JsonResult;
import com.hisoft.pam.im.common.utils.DateUtils;
import com.hisoft.pam.im.common.utils.StringUtils;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;

import java.beans.PropertyEditorSupport;
import java.util.Date;
import java.util.List;

/**
 * web层通用数据处理
 * 
 * @author ruoyi
 */
public class BaseController
{

    /**
     * 将前台传递过来的日期格式的字符串，自动转化为Date类型
     */
    @InitBinder
    public void initBinder(WebDataBinder binder)
    {
        // Date 类型转换
        binder.registerCustomEditor(Date.class, new PropertyEditorSupport()
        {
            @Override
            public void setAsText(String text)
            {
                setValue(DateUtils.parseDate(text));
            }
        });
    }

    /**
     * 设置请求分页数据
     */
    public void startPage(Integer pageSize,Integer pageNum)
    {
        if (StringUtils.isNotNull(pageNum) && StringUtils.isNotNull(pageSize))
        {
            PageHelper.startPage(pageNum, pageSize);
        }
    }

    /**
     * 响应请求分页数据
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    protected PageResult getDataTable(List<?> list)
    {
        PageInfo<?> pageInfo = new PageInfo<>(list);
        PageResult result = new PageResult();
        result.setPageNum(pageInfo.getPageNum());
        result.setPages(pageInfo.getPages());
        result.setTotal(pageInfo.getTotal());
        result.setRows(pageInfo.getList());
        return result;
    }

    /**
     * 响应返回结果
     * 
     * @param rows 影响行数
     * @return 操作结果
     */
    protected String toAjax(int rows)
    {
        return rows > 0 ? JsonResult.success() : JsonResult.failed();
    }
}
