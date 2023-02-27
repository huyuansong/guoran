package com.guoran.server.ma.scattered.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.guoran.server.common.JsonResult;
import com.guoran.server.common.exception.ServiceException;
import com.guoran.server.common.search.DynamicSearch;
import com.guoran.server.common.search.FilterGroup;
import com.guoran.server.common.search.PageQuery;
import com.guoran.server.ma.scattered.model.JobFileEntity;
import com.guoran.server.ma.scattered.repository.JobFileRepository;
import com.guoran.server.ma.scattered.service.JobFileService;
import com.guoran.server.ma.scattered.vmodel.JobFileVM;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 零工信息管理-附件信息表 服务实现类
 * </p>
 *
 * @author zhangjx
 * @create 2020-08-28
 * @Modify By
 */
@Service
public class JobFileServiceImpl implements JobFileService {

    @Autowired
    JobFileRepository jobFileRepository;

    /**
     * 根据id获取
     *
     * @param id
     * @return
     */
    @Override
    public JobFileVM getEntryBy(long id) {
        JobFileEntity jobFileEntity = jobFileRepository.findById(id);
        JobFileVM jobFileVM = new JobFileVM();
        BeanUtils.copyProperties(jobFileEntity, jobFileVM);
        return jobFileVM;
    }

    /**
     * 创建
     *
     * @param jobFileVM
     * @return entity的id
     */
    @Override
    public JobFileVM createEntry(JobFileVM jobFileVM) {
        JobFileEntity jobFileEntity = new JobFileEntity();
        BeanUtils.copyProperties(jobFileVM, jobFileEntity);
        jobFileEntity.setCreateTime(new Date());
        jobFileRepository.insert(jobFileEntity);
        jobFileVM.setId(jobFileEntity.getId());
        return jobFileVM;
    }

    /**
     * 修改
     *
     * @param jobFileVM
     * @return 是否成功
     */
    @Override
    public String updateEntry(JobFileVM jobFileVM) throws ServiceException {
        JobFileEntity jobFileEntity = jobFileRepository.findById(jobFileVM.getId());
        jobFileEntity.failWhenConcurrencyViolation(jobFileVM.getConcurrencyVersion());
        BeanUtils.copyProperties(jobFileVM, jobFileEntity);
        jobFileEntity.setUpdateTime(new Date());
        jobFileRepository.update(jobFileEntity);
        return null;
    }

    /**
     * 删除
     *
     * @param id
     * @return 是否成功
     */
    @Override
    public boolean deleteById(long id) {
        JobFileEntity jobFileEntity = jobFileRepository.findById(id);
        new File((jobFileEntity.getFileUrl()).replace("/", "\\")).delete();

        return jobFileRepository.deleteById(id);
    }

    /**
     * 删除
     *
     * @param oddJobCode
     * @return 是否成功
     */
    @Override
    public boolean deleteByFile(String oddJobCode) {
        return jobFileRepository.deleteByFile(oddJobCode);
    }


    /**
     * 分页
     *
     * @param pageQuery
     * @return
     */
    @Override
    public Page<JobFileVM> findEntrysByPage(PageQuery pageQuery) {
        FilterGroup filterGroup = pageQuery.getWhere();
        //自动转字符串
        String where = DynamicSearch.getInstance().buildWhere(filterGroup);
        PageHelper.startPage(pageQuery.getPageNum(), pageQuery.getPageSize(), pageQuery.getOrderBy());
        return jobFileRepository.findEntrysByPage(where);
    }

    @Override
    public String downLoad(Long id, HttpServletRequest request, HttpServletResponse response) {
        JobFileEntity jobFileVM = jobFileRepository.findById(id);
        File file = new File(jobFileVM.getFileUrl());
        if (!file.exists()) {
            return JsonResult.failed("文件不存在！");
        }
        return null;
    }

    @Override
    public List<JobFileEntity> findEntrysByPageJobFile(String oddJobCode) {

        return jobFileRepository.findEntrysByPageJobFile(oddJobCode);
    }

    @Override
    public String createEntryBanch(List<JobFileVM> jobFileVMList) {
        List<JobFileEntity> jobFileVMS = new ArrayList<>();
        if (jobFileVMList.size() > 0 && jobFileVMList != null) {
            for (JobFileVM jobFileVM : jobFileVMList) {
                JobFileEntity jobFileEntity = new JobFileEntity();

                BeanUtils.copyProperties(jobFileVM, jobFileEntity);
                jobFileEntity.setCreateTime(new Date());
                jobFileVMS.add(jobFileEntity);
            }
            jobFileRepository.insertBanch(jobFileVMS);
        }
        return null;
    }

    @Override
    public String updateEntryBanch(List<JobFileVM> list3) {

        List<JobFileEntity> jobFileVMS = new ArrayList<>();
        for (JobFileVM jobFileVM : list3) {
            JobFileEntity jobFileEntity = new JobFileEntity();

            BeanUtils.copyProperties(jobFileVM, jobFileEntity);
            jobFileEntity.setCreateTime(new Date());
            jobFileVMS.add(jobFileEntity);
        }
        jobFileRepository.updateBanch(jobFileVMS);
        return null;
    }

    @Override
    public String deleteEntryBanch(List<Long> list5) {
        jobFileRepository.deleteBanch(list5);
        return null;
    }
}
