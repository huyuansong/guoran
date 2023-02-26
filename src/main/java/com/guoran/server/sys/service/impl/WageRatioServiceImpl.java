package com.guoran.server.sys.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.guoran.server.common.JsonResult;
import com.guoran.server.common.exception.ServiceException;
import com.guoran.server.common.search.DynamicSearch;
import com.guoran.server.common.search.FilterGroup;
import com.guoran.server.common.search.PageQuery;
import com.guoran.server.security.JwtUserUtil;
import com.guoran.server.sys.a.repository.WageRatioRepository;
import com.guoran.server.sys.model.WageRatioEntity;
import com.guoran.server.sys.service.WageRatioService;
import com.guoran.server.sys.vmodel.WageRatioVM;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 计件工资表子表岗位工资比例 服务实现类
 * </p>
 *
 * @author zhangjx
 * @create 2020-08-21
 * @Modify By
 */
@Service
public class WageRatioServiceImpl implements WageRatioService {
    @Autowired
    JwtUserUtil jwtUserUtil;
    @Autowired
    WageRatioRepository wageRatioRepository;

    /**
     * 根据id获取
     *
     * @param id
     * @return
     */
    @Override
    public WageRatioVM getEntryBy(long id) {
        WageRatioEntity wageRatioEntity = wageRatioRepository.findById(id);
        WageRatioVM wageRatioVM = new WageRatioVM();
        BeanUtils.copyProperties(wageRatioEntity, wageRatioVM);
        return wageRatioVM;
    }

    /**
     * 创建
     *
     * @param wageRatioVM
     * @return entity的id
     */
    @Override
    public Long createEntry(WageRatioVM wageRatioVM) {
        WageRatioEntity wageRatioEntity = new WageRatioEntity();
        BeanUtils.copyProperties(wageRatioVM, wageRatioEntity);
        wageRatioEntity.setCreateBy(jwtUserUtil.getUserName());
        wageRatioEntity.setCreateTime(new Date());
        wageRatioRepository.insert(wageRatioEntity);
        return wageRatioEntity.getId();
    }

    @Override
    public String createEntryBanch(List<WageRatioVM> wageRatioVMS) {
        List<WageRatioEntity> wageRatioEntities = new ArrayList<>();
        for (WageRatioVM wageRatioVM : wageRatioVMS) {
            WageRatioEntity wageRatioEntity = new WageRatioEntity();
            BeanUtils.copyProperties(wageRatioVM, wageRatioEntity);
            wageRatioEntity.setCreateBy(jwtUserUtil.getUserName());
            wageRatioEntity.setCreateTime(new Date());
            wageRatioEntities.add(wageRatioEntity);
        }

        wageRatioRepository.insertBanch(wageRatioEntities);
        return null;
    }

    /**
     * 修改
     *
     * @param wageRatioVM
     * @return 是否成功
     */
    @Override
    public boolean updateEntry(WageRatioVM wageRatioVM) throws ServiceException {
        WageRatioEntity wageRatioEntity = wageRatioRepository.findById(wageRatioVM.getId());
        wageRatioEntity.failWhenConcurrencyViolation(wageRatioVM.getConcurrencyVersion());
        BeanUtils.copyProperties(wageRatioVM, wageRatioEntity);
        wageRatioEntity.setUpdateBy(jwtUserUtil.getUserName());
        wageRatioEntity.setUpdateTime(new Date());
        return wageRatioRepository.update(wageRatioEntity);
    }

    /**
     * 删除
     *
     * @param id
     * @return 是否成功
     */
    @Override
    public boolean deleteById(long id) {
        return wageRatioRepository.deleteById(id);
    }

    /**
     * 分页
     *
     * @param pageQuery
     * @return
     */
    @Override
    public Page<WageRatioVM> findEntrysByPage(PageQuery pageQuery) {

        FilterGroup filterGroup = pageQuery.getWhere();
        //自动转字符串
        String where = DynamicSearch.getInstance().buildWhere(filterGroup);
        PageHelper.startPage(pageQuery.getPageNum(), pageQuery.getPageSize(), pageQuery.getOrderBy());
        return wageRatioRepository.findEntrysByPage(where);
    }

    /**
     * 批量删除
     *
     * @param wageRatioVMS
     */
    @Override
    public void deleteBanch(List<WageRatioVM> wageRatioVMS) {
        if (wageRatioVMS.size() > 0) {
            wageRatioRepository.deleteBanch(wageRatioVMS);
        }
    }

    /**
     * 批量修改
     */
    @Override
    public String updateBanch(List<WageRatioVM> wageRatioVMS) {
        for (int i = 0; i < wageRatioVMS.size(); i++) {
            String where = " and position_id = '" + wageRatioVMS.get(i).getPositionId() + "' and product_code = '" + wageRatioVMS.get(i).getProductCode() + "' and id <>" + wageRatioVMS.get(i).getId();
            Page<WageRatioVM> entrysByPage = wageRatioRepository.findEntrysByPage(where);
            if (entrysByPage.size() > 0) {
                return JsonResult.failed("岗位工资信息重复，修改失败");
            }
        }

        wageRatioRepository.updateBanch(wageRatioVMS);
        return null;
    }
}
