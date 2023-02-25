package com.guoran.server.ma.scattered.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.guoran.server.common.JsonResult;
import com.guoran.server.common.exception.ServiceException;
import com.guoran.server.common.search.DynamicSearch;
import com.guoran.server.common.search.FilterGroup;
import com.guoran.server.common.search.PageQuery;
import com.guoran.server.ma.scattered.model.JobInfoEntity;
import com.guoran.server.ma.scattered.repository.JobFileRepository;
import com.guoran.server.ma.scattered.repository.JobInfoRepository;
import com.guoran.server.ma.scattered.service.JobFileService;
import com.guoran.server.ma.scattered.service.JobInfoService;
import com.guoran.server.ma.scattered.vmodel.JobFileVM;
import com.guoran.server.ma.scattered.vmodel.JobInfoVM;
import com.guoran.server.ma.scattered.vo.JobInfoQualityVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 零工信息 服务实现类
 * </p>
 *
 * @author zhangjx
 * @create 2020-08-28
 * @Modify By
 */
@Service
public class JobInfoServiceImpl implements JobInfoService {

    @Autowired
    JobInfoRepository jobInfoRepository;
    @Autowired
    JobFileService jobFileService;
    @Autowired
    JobFileRepository jobFileRepository;

    /**
     * 根据id获取
     *
     * @param id
     * @return
     */
    @Override
    public JobInfoVM getEntryBy(long id) {
        JobInfoEntity jobInfoEntity = jobInfoRepository.findById(id);
        JobInfoVM jobInfoVM = new JobInfoVM();
        BeanUtils.copyProperties(jobInfoEntity, jobInfoVM);
        return jobInfoVM;
    }

    /**
     * 创建
     *
     * @param jobInfoVM
     * @return entity的id
     */
    @Override
    public String createEntry(JobInfoVM jobInfoVM) {
        String where = " and id_card ='" + jobInfoVM.getIdCard() + "'";
        Page<JobInfoVM> entrysByPage = this.jobInfoRepository.findEntrysByPage(where);
        if (entrysByPage.size() > 0) {
            return JsonResult.failed("零工信息已存在，请勿重复添加");
        }
        String where1 = " and contact_number ='" + jobInfoVM.getContactNumber() + "'";
        Page<JobInfoVM> entrysByPage1 = this.jobInfoRepository.findEntrysByPage(where1);
        if (entrysByPage1.size() > 0) {
            return JsonResult.failed("联系电话重复，请修改");
        }
        JobInfoEntity jobInfoEntity = new JobInfoEntity();
        BeanUtils.copyProperties(jobInfoVM, jobInfoEntity);

        //默认未审核
        //jobInfoEntity.setAuditStatus(0);

        jobInfoEntity.setCreateTime(new Date());
        List<JobFileVM> list = jobInfoEntity.getJobFileVMList();

        for (JobFileVM jobFileVm : list) {


            jobFileVm.setUpdateTime(new Date());
            jobFileVm.setConcurrencyVersion(0);
            jobFileService.updateEntry(jobFileVm);
        }
        jobInfoRepository.insert(jobInfoEntity);
        return null;
    }

    /**
     * 修改
     *
     * @param jobInfoVM
     * @return 是否成功
     */
    @Override
    public String updateEntry(JobInfoVM jobInfoVM) throws ServiceException {
        String where = " and id_card ='" + jobInfoVM.getIdCard() + "' and id <> " + jobInfoVM.getId();
        Page<JobInfoVM> entrysByPage = this.jobInfoRepository.findEntrysByPage(where);
        if (entrysByPage.size() > 0) {
            return JsonResult.failed("零工身份信息重复,请修改");
        }
        String where1 = " and contact_number ='" + jobInfoVM.getContactNumber() + "' and id <> " + jobInfoVM.getId();
        Page<JobInfoVM> entrysByPage1 = this.jobInfoRepository.findEntrysByPage(where1);
        if (entrysByPage1.size() > 0) {
            return JsonResult.failed("联系电话重复，请修改");
        }
        JobInfoEntity jobInfoEntity = jobInfoRepository.findById(jobInfoVM.getId());
        jobInfoEntity.failWhenConcurrencyViolation(jobInfoVM.getConcurrencyVersion());
        BeanUtils.copyProperties(jobInfoVM, jobInfoEntity);
        jobInfoEntity.setUpdateTime(new Date());
        this.checkDate(jobInfoVM);
        jobInfoRepository.update(jobInfoEntity);
        return null;
    }

    String checkDate(JobInfoVM jobInfoVM) {
        String where1 = " and odd_job_code = '" + jobInfoVM.getOddJobCode() + "'";
        Page<JobFileVM> entrysByPage1 = jobFileRepository.findEntrysByPage(where1);
        //后台查到的文件
        List<Long> list1 = new ArrayList<>();//后台查询到的id
        List<Long> list2 = new ArrayList<>();//前端传过来的id
        List<JobFileVM> list3 = new ArrayList<>();//前端传过来的修改后的数据
        List<JobFileVM> list4 = new ArrayList<>();//需要新增的数据
        List<Long> list5 = new ArrayList<>();//需要删除的数据id集合
        for (JobFileVM jobFileVm : entrysByPage1) {
            list1.add(jobFileVm.getId());
        }

        for (JobFileVM jobFileVm : jobInfoVM.getJobFileVMList()) {
            if (jobFileVm.getId() != null) {
                list2.add(jobFileVm.getId());
                jobFileVm.setOddJobCode(jobInfoVM.getOddJobCode());
                list3.add(jobFileVm);
            } else {
                jobFileVm.setOddJobCode(jobInfoVM.getOddJobCode());
                list4.add(jobFileVm);
            }
        }
        for (long l : list1) {
            if (!list2.contains(l)) {
                list5.add(l);
            }
        }
        //批量创建
        if (list4.size() > 0) {
            jobFileService.createEntryBanch(list4);
        }
        //批量更新
        if (list3.size() > 0) {
            jobFileService.updateEntryBanch(list3);
        }
        //批量删除
        if (list5.size() > 0) {
            jobFileService.deleteEntryBanch(list5);
        }


//        jobFileVm.setOddJobCode(jobInfoVM.getOddJobCode());
//        jobFileVm.setUpdateBy(jwtUserUtil.getUserName());
//        jobFileVm.setUpdateTime(new Date());
//        jobFileVm.setConcurrencyVersion(0);
//        jobFileService.updateEntry(jobFileVm);
        return null;
    }

    /**
     * 删除
     *
     * @param ids
     * @return 是否成功
     */
    @Override
    public boolean deleteById(String ids) {
        String[] idString = ids.split(",");
        boolean returenCode = false;
        for (String id : idString) {
            Long idl = Long.parseLong(id);
            returenCode = jobInfoRepository.deleteById(idl);
        }
        return returenCode;
    }

    /**
     * 分页
     *
     * @param pageQuery
     * @return
     */
    @Override
    public Page<JobInfoVM> findEntrysByPage(PageQuery pageQuery) {
        FilterGroup filterGroup = pageQuery.getWhere();
        //自动转字符串
        String where = DynamicSearch.getInstance().buildWhere(filterGroup);

        PageHelper.startPage(pageQuery.getPageNum(), pageQuery.getPageSize(), pageQuery.getOrderBy());
        return jobInfoRepository.findEntrysByPage(where);
    }


    @Override
    public boolean auditById(JobInfoQualityVo qualityVo) {
        boolean returnCode = false;
        Integer[] ids = qualityVo.getIds();
        Integer status = qualityVo.getStatus();
        String auditRejectReason = qualityVo.getAuditRejectReason();
        for (Integer id : ids) {
            JobInfoEntity jobInfoEntity = jobInfoRepository.findById(id);
            if (jobInfoEntity != null) {
                jobInfoEntity.setAuditStatus(status);

                jobInfoEntity.setUpdateTime(new Date());
                if (qualityVo.getStatus() != 2 && qualityVo.getStatus() != 3) {

                    jobInfoEntity.setAuditTime(new Date());
                    if (qualityVo.getStatus() == 7) {
                        jobInfoEntity.setAuditRejectReason(qualityVo.getAuditRejectReason());
                    }
                }
//                jobInfoEntity.setAuditRejectReason(auditRejectReason);
                returnCode = jobInfoRepository.auditBy(jobInfoEntity);
            }

        }


        return returnCode;
    }
}
