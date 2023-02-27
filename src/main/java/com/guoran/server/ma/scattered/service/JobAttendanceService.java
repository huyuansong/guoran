package com.guoran.server.ma.scattered.service;


import com.github.pagehelper.Page;
import com.guoran.server.common.search.PageQuery;
import com.guoran.server.ma.scattered.vmodel.JobAttendanceVM;
import com.guoran.server.ma.scattered.vo.ScatteredQualityVo;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * <p>
 * 零工出勤信息 服务类
 * </p>
 *
 * @author zhangjx
 * @create 2020-08-28
 * @Modify By
 */
public interface JobAttendanceService {
    /**
     * 根据id获取
     *
     * @param id
     */
    JobAttendanceVM getEntryBy(long id);

    /**
     * 创建
     *
     * @param jobAttendanceVM
     */
    String createEntry(JobAttendanceVM jobAttendanceVM);

    /**
     * 修改
     *
     * @param jobAttendanceVM
     */
    String updateEntry(JobAttendanceVM jobAttendanceVM);

    /**
     * 删除
     *
     * @param ids
     */
    boolean deleteById(String ids);

    /**
     * 分页
     *
     * @param pageQuery
     * @return
     */
    Page<JobAttendanceVM> findEntrysByPage(PageQuery pageQuery);

    /*
     *审核
     */
    boolean auditById(ScatteredQualityVo qualityVo);


    /*
     *导出
     */
    void explort(Long[] ids, HttpServletResponse response, HttpServletRequest request);

}
