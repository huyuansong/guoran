package com.guoran.server.he.production.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.guoran.server.common.JsonResult;
import com.guoran.server.common.exception.ServiceException;
import com.guoran.server.common.search.DynamicSearch;
import com.guoran.server.common.search.FilterGroup;
import com.guoran.server.common.search.PageQuery;
import com.guoran.server.he.production.model.ManagementAnnualEntity;
import com.guoran.server.he.production.model.ManagementAnnualTotalEntity;
import com.guoran.server.he.production.repository.ManagementAnnualRepository;
import com.guoran.server.he.production.repository.ManagementAnnualTotalRepository;
import com.guoran.server.he.production.service.ManagementAnnualTotalService;
import com.guoran.server.he.production.vmodel.ManagementAnnualTotalVM;
import com.guoran.server.he.production.vmodel.ManagementAnnualVM;
import com.guoran.server.he.production.vo.ProductionQualityVo;
import com.guoran.server.security.JwtUserUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * @time 2023/2/2314:51
 * @outhor zhou
 */
@Service
public class ManagementAnnualTotalServiceImpl implements ManagementAnnualTotalService {

    @Autowired
    JwtUserUtil jwtUserUtil;

    @Autowired
    ManagementAnnualTotalRepository managementAnnualTotalRepository;

    @Autowired
    ManagementAnnualRepository managementAnnualRepository;

    @Autowired
    ManagementAnnualServiceImpl managementAnnualServiceImpl;

    /**
     * 根据id获取
     *
     * @param id
     * @return
     */
    @Override
    public ManagementAnnualTotalVM getEntryBy(long id) {
        ManagementAnnualTotalEntity managementAnnualTotalEntity = managementAnnualTotalRepository.findById(id);
        ManagementAnnualTotalVM managementAnnualTotalVM = new ManagementAnnualTotalVM();
        BeanUtils.copyProperties(managementAnnualTotalEntity, managementAnnualTotalVM);
        return managementAnnualTotalVM;

    }

    /**
     * 分页
     *
     * @param pageQuery
     * @return
     */
    @Override
    public Page<ManagementAnnualTotalVM> findEntrysByPage(PageQuery pageQuery) {
        FilterGroup filterGroup = pageQuery.getWhere();

        String where = DynamicSearch.getInstance().buildWhere(filterGroup);
        PageHelper.startPage(pageQuery.getPageNum(), pageQuery.getPageSize(), pageQuery.getOrderBy());
        return managementAnnualTotalRepository.findEntrysByPage(where);
    }

    /**
     * 根据年度计划id 查询各个商品的年度计划列表
     *
     * @param id
     * @return
     */
    @Override
    public List<ManagementAnnualVM> findEntrysPagetotal(long id) {
        return managementAnnualTotalRepository.findEntrysPagetotal(id);
    }


    /**
     * 创建
     *
     * @param managementAnnualTotalVM
     * @return entity的id
     */
    @Override
    public String createEntry(ManagementAnnualTotalVM managementAnnualTotalVM) {

        String where = " and budget_year = '" + managementAnnualTotalVM.getBudgetYear() + "'";
        Page<ManagementAnnualTotalVM> entrysByPage = managementAnnualTotalRepository.findEntrysByPage(where);
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int budgeYear = Integer.parseInt(managementAnnualTotalVM.getBudgetYear());
        if (entrysByPage.size() == 0) {
            ManagementAnnualTotalEntity managementAnnualTotalEntity = new ManagementAnnualTotalEntity();
            BeanUtils.copyProperties(managementAnnualTotalVM, managementAnnualTotalEntity);
            if (budgeYear >= year) {
                managementAnnualTotalEntity.setIsBiger(1);
            } else {
                managementAnnualTotalEntity.setIsBiger(0);
            }
            managementAnnualTotalEntity.setCreateBy(jwtUserUtil.getUserName());
            managementAnnualTotalEntity.setCreateTime(new Date());
            managementAnnualTotalEntity.setAuditStatus(3);
            managementAnnualTotalEntity.setIsAuditPass(0);
            managementAnnualTotalRepository.insert(managementAnnualTotalEntity);
            long annualTotalId = managementAnnualTotalEntity.getId();
            List<ManagementAnnualEntity> managementAnnualEntityList = managementAnnualTotalVM.getManagementAnnualEntity();

            for (ManagementAnnualEntity managementAnnualEntitys : managementAnnualEntityList) {

                managementAnnualEntitys.setBudgetYear(managementAnnualTotalVM.getBudgetYear());
                managementAnnualEntitys.setTopicInformation(managementAnnualTotalVM.getTopicInformation());
                managementAnnualEntitys.setAnnualTotalId(annualTotalId);
                managementAnnualEntitys.setAnnualProductionAuditStatus(3);
                managementAnnualEntitys.setCreateBy(jwtUserUtil.getUserName());
                managementAnnualEntitys.setCreateTime(new Date());
                managementAnnualRepository.insert(managementAnnualEntitys);
            }
            return null;
        } else {
            return JsonResult.failed("每年度只能有一条数据，请勿重复添加");
        }
    }

    /**
     * 修改
     *
     * @param managementAnnualTotalVM
     * @return 是否成功
     */
    @Override
    public String updateEntry(ManagementAnnualTotalVM managementAnnualTotalVM) throws ServiceException {
        ManagementAnnualTotalEntity managementAnnualTotalEntity = managementAnnualTotalRepository.findById(managementAnnualTotalVM.getId());
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int budgeYear = Integer.parseInt(managementAnnualTotalVM.getBudgetYear());
        managementAnnualTotalEntity.failWhenConcurrencyViolation(managementAnnualTotalVM.getConcurrencyVersion());
        BeanUtils.copyProperties(managementAnnualTotalVM, managementAnnualTotalEntity);
        if (budgeYear >= year) {
            managementAnnualTotalEntity.setIsBiger(1);
        } else {
            managementAnnualTotalEntity.setIsBiger(0);
        }
        managementAnnualTotalEntity.setUpdateBy(jwtUserUtil.getUserName());
        managementAnnualTotalEntity.setUpdateTime(new Date());
        managementAnnualTotalRepository.update(managementAnnualTotalEntity);
        long annualTotalId = managementAnnualTotalEntity.getId();
        List<ManagementAnnualVM> entrysPagetotal = this.findEntrysPagetotal(annualTotalId);//数据库存在的子表数据
        List<Long> list1 = new ArrayList();//数据库存在的子表数据ID
        List<Long> list2 = new ArrayList();//前段传递的子表数据ID
        List<Long> list3 = new ArrayList();//需要删除的数据

        for (ManagementAnnualVM management : entrysPagetotal) {
            list1.add(management.getId());
        }
        List<ManagementAnnualEntity> managementAnnualEntityList = managementAnnualTotalVM.getManagementAnnualEntity();
        for (ManagementAnnualEntity managementAnnualEntitys : managementAnnualEntityList) {
            if (managementAnnualEntitys.getId() == 0) {
                managementAnnualEntitys.setBudgetYear(managementAnnualTotalVM.getBudgetYear());
                managementAnnualEntitys.setTopicInformation(managementAnnualTotalVM.getTopicInformation());
                managementAnnualEntitys.setAnnualTotalId(annualTotalId);
                managementAnnualEntitys.setAnnualProductionAuditStatus(3);
                managementAnnualEntitys.setCreateBy(jwtUserUtil.getUserName());
                managementAnnualEntitys.setCreateTime(new Date());
                managementAnnualRepository.insert(managementAnnualEntitys);
            } else {
                list2.add(managementAnnualEntitys.getId());
                managementAnnualEntitys.setBudgetYear(managementAnnualTotalVM.getBudgetYear());
                managementAnnualEntitys.setTopicInformation(managementAnnualTotalVM.getTopicInformation());
                managementAnnualEntitys.setAnnualTotalId(annualTotalId);
                managementAnnualEntitys.setUpdateBy(jwtUserUtil.getUserName());
                managementAnnualEntitys.setUpdateTime(new Date());
                managementAnnualRepository.update(managementAnnualEntitys);
            }
        }
        for (Long id : list1) {
            if (!list2.contains(id)) {
                list3.add(id);
            }
        }
        if (list3.size() > 0) {
            managementAnnualServiceImpl.deleteBanch(list3);
        }
        return null;
    }

    /*
     * 审核
     *
     * @param managementAnnualVM
     * @return
     */
    @Override
    public boolean auditById(ProductionQualityVo auditUpdate) {

        boolean returnCode = false;
        Long[] ids = auditUpdate.getIds();
        int startus = auditUpdate.getStatus();
        String auditRejectReason = auditUpdate.getAuditRejectReason();
        for (Long id : ids) {
            ManagementAnnualTotalEntity managementAnnualTotalEntity = managementAnnualTotalRepository.findById(id);
            managementAnnualTotalEntity.setAuditStatus(startus);
            if (startus == 1 || startus == 7) {
                managementAnnualTotalEntity.setAuditBy(jwtUserUtil.getRealName());
                managementAnnualTotalEntity.setAuditTime(new Date());
            }
            if (startus == 1) {
                managementAnnualTotalEntity.setIsAuditPass(1);
            }

            managementAnnualTotalEntity.setUpdateBy(jwtUserUtil.getUserName());
            managementAnnualTotalEntity.setUpdateTime(new Date());
            managementAnnualTotalEntity.setAuditRejectReason(auditRejectReason);
            returnCode = managementAnnualTotalRepository.auditBy(managementAnnualTotalEntity);

            ManagementAnnualEntity managementAnnualEntity = new ManagementAnnualEntity();

            managementAnnualEntity.setAnnualProductionAuditStatus(startus);
            if (startus == 1 || startus == 7) {
                managementAnnualEntity.setAnnualProductionReviewer(jwtUserUtil.getRealName());
                managementAnnualEntity.setAnnualProductionAuditDate(new Date());
            }
            managementAnnualEntity.setUpdateBy(jwtUserUtil.getUserName());
            managementAnnualEntity.setUpdateTime(new Date());
            managementAnnualEntity.setId(id);
            managementAnnualEntity.setAuditRejectReason(auditRejectReason);
            returnCode = managementAnnualRepository.auditBy(managementAnnualEntity);
        }
        return returnCode;
    }

    /**
     * 删除
     *
     * @param ids
     * @return 是否成功
     */
    @Override
    public boolean deleteById(String ids) {
        boolean returnCode = false;
        String[] idString = ids.split(",");
        for (String id : idString) {
            Long idl = Long.parseLong(id);
            returnCode = managementAnnualRepository.deleteByoTalId(idl);
            returnCode = managementAnnualTotalRepository.deleteById(idl);
        }
        return returnCode;
    }

    @Override
    public boolean queryBudgetYearCont(String budgetYear) {
        boolean returnCode = false;
        int count = managementAnnualTotalRepository.queryBudgetYearCont(budgetYear);
        if (count == 0) {
            returnCode = true;
        }
        return returnCode;
    }
}
