package com.guoran.server.he.production.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.guoran.server.common.search.DynamicSearch;
import com.guoran.server.common.search.FilterGroup;
import com.guoran.server.common.search.PageQuery;
import com.guoran.server.he.production.model.ManagementAnnualTotalEntity;
import com.guoran.server.he.production.reposittory.ManagementAnnualTotalRepository;
import com.guoran.server.he.production.service.ManagementAnnualTotalService;
import com.guoran.server.he.production.vmodel.ManagementAnnualTotalVM;
import com.guoran.server.he.production.vmodel.ManagementAnnualVM;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @time 2023/2/2314:51
 * @outhor zhou
 */
@Service
public class ManagementAnnualTotalServiceImpl implements ManagementAnnualTotalService {

    @Resource
    ManagementAnnualTotalRepository managementAnnualTotalRepository;

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

    @Override
    public Page<ManagementAnnualTotalVM> findEntrysByPage(PageQuery pageQuery) {
        FilterGroup filterGroup = pageQuery.getWhere();

        String where = DynamicSearch.getInstance().buildWhere(filterGroup);
        PageHelper.startPage(pageQuery.getPageNum(), pageQuery.getPageSize(), pageQuery.getOrderBy());
        return managementAnnualTotalRepository.findEntresByPage(where);
    }

    @Override
    public List<ManagementAnnualVM> findEntrysPagetotal(long id) {
        return managementAnnualTotalRepository.findEntrysPagetotal(id);
    }
}
