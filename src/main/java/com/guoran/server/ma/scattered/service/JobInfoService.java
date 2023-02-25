package com.guoran.server.ma.scattered.service;


import com.github.pagehelper.Page;
import com.guoran.server.common.search.PageQuery;
import com.guoran.server.ma.scattered.vmodel.JobInfoVM;
import com.guoran.server.ma.scattered.vo.JobInfoQualityVo;

/**
 * <p>
 * 零工信息 服务类
 * </p>
 *
 * @author zhangjx
 * @create 2020-08-28
 * @Modify By
 */
public interface JobInfoService {
    /**
     * 根据id获取
     *
     * @param id
     */
    JobInfoVM getEntryBy(long id);

    /**
     * 创建
     *
     * @param jobInfoVM
     */
    String createEntry(JobInfoVM jobInfoVM);

    /**
     * 修改
     *
     * @param jobInfoVM
     */
    String updateEntry(JobInfoVM jobInfoVM);

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
    Page<JobInfoVM> findEntrysByPage(PageQuery pageQuery);

    /*
     *审核
     */
    boolean auditById(JobInfoQualityVo qualityVo);
}
