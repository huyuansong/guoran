package com.guoran.server.ma.scattered.service;


import com.github.pagehelper.Page;
import com.guoran.server.common.search.PageQuery;
import com.guoran.server.ma.scattered.model.JobFileEntity;
import com.guoran.server.ma.scattered.vmodel.JobFileVM;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * <p>
 * 零工信息管理-附件信息表 服务类
 * </p>
 *
 * @author zhangjx
 * @create 2020-08-28
 * @Modify By
 */
public interface JobFileService {
    /**
     * 根据id获取
     *
     * @param id
     */
    JobFileVM getEntryBy(long id);

    /**
     * 创建
     *
     * @param jobFileVM
     */
    JobFileVM createEntry(JobFileVM jobFileVM);

    /**
     * 修改
     *
     * @param jobFileVM
     */
    String updateEntry(JobFileVM jobFileVM);


    /**
     * 删除
     *
     * @param id
     */
    boolean deleteById(long id);

    /**
     * 删除
     *
     * @param oddJobCode
     */
    boolean deleteByFile(String oddJobCode);

    /**
     * 分页
     *
     * @param pageQuery
     * @return
     */
    Page<JobFileVM> findEntrysByPage(PageQuery pageQuery);

    String downLoad(Long id, HttpServletRequest request, HttpServletResponse response);

    List<JobFileEntity> findEntrysByPageJobFile(String oddJobCode);

    String createEntryBanch(List<JobFileVM> list4);

    String updateEntryBanch(List<JobFileVM> list3);

    String deleteEntryBanch(List<Long> list5);
}
