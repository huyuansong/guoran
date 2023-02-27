package com.guoran.server.liu.financial.service.Impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.guoran.server.common.exception.ServiceException;
import com.guoran.server.common.search.DynamicSearch;
import com.guoran.server.common.search.FilterGroup;
import com.guoran.server.common.search.PageQuery;
import com.guoran.server.liu.financial.model.VoucherDetailsEntity;
import com.guoran.server.liu.financial.repository.VoucherDetailsRepository;
import com.guoran.server.liu.financial.service.VoucherDetailsService;
import com.guoran.server.liu.financial.vmodel.VoucherDetailsVM;
import com.guoran.server.security.JwtUserUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Service
public class VoucherDetailsServiceImpl implements VoucherDetailsService {
    @Autowired
    JwtUserUtil jwtUserUtil;
    @Resource
    VoucherDetailsRepository voucherDetailsRepository;

    /**
     * 根据id获取
     *
     * @param id
     * @return
     */
    @Override
    public VoucherDetailsVM getEntryBy(long id) {
        VoucherDetailsEntity voucherDetailsEntity = voucherDetailsRepository.findById(id);
        VoucherDetailsVM voucherDetailsVM = new VoucherDetailsVM();
        BeanUtils.copyProperties(voucherDetailsEntity, voucherDetailsVM);
        return voucherDetailsVM;
    }

    /**
     * 创建
     *
     * @param voucherDetailsVM
     * @return entity的id
     */
    @Override
    public String createEntry(VoucherDetailsVM voucherDetailsVM) {
        VoucherDetailsEntity voucherDetailsEntity = new VoucherDetailsEntity();
        BeanUtils.copyProperties(voucherDetailsVM, voucherDetailsEntity);
        voucherDetailsEntity.setCreateBy(jwtUserUtil.getUserName());
        voucherDetailsEntity.setCreateTime(new Date());
        voucherDetailsRepository.insert(voucherDetailsEntity);
        return null;
    }

    /**
     * 修改x
     *
     * @param voucherDetailsVM
     * @return 是否成功
     */
    @Override
    public String updateEntry(VoucherDetailsVM voucherDetailsVM) throws ServiceException {
        VoucherDetailsEntity voucherDetailsEntity = voucherDetailsRepository.findById(voucherDetailsVM.getId());
        voucherDetailsEntity.failWhenConcurrencyViolation(voucherDetailsVM.getConcurrencyVersion());
        BeanUtils.copyProperties(voucherDetailsVM, voucherDetailsEntity);
        voucherDetailsEntity.setUpdateBy(jwtUserUtil.getUserName());
        voucherDetailsEntity.setUpdateTime(new Date());
        voucherDetailsRepository.update(voucherDetailsEntity);
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
        return voucherDetailsRepository.deleteById(id);
    }

    /**
     * 分页
     *
     * @param pageQuery
     * @return
     */
    @Override
    public Page<VoucherDetailsVM> findEntrysByPage(PageQuery pageQuery) {
        FilterGroup filterGroup = pageQuery.getWhere();
        //自动转字符串
        String where = DynamicSearch.getInstance().buildWhere(filterGroup);
        PageHelper.startPage(pageQuery.getPageNum(), pageQuery.getPageSize(), pageQuery.getOrderBy());
        return voucherDetailsRepository.findEntrysByPage(where);
    }

    /**
     * 批量新增
     *
     * @param voucherDetailsVMS
     * @return
     */
    @Override
    public String createEntryBanch(List<VoucherDetailsVM> voucherDetailsVMS) {
        List<VoucherDetailsEntity> voucherDetailsEntities = new ArrayList<>();
        for (VoucherDetailsVM detailsVM : voucherDetailsVMS) {
            VoucherDetailsEntity voucherDetailsEntity = new VoucherDetailsEntity();
            BeanUtils.copyProperties(detailsVM, voucherDetailsEntity);
            voucherDetailsEntity.setCreateBy(jwtUserUtil.getUserName());
            voucherDetailsEntity.setCreateTime(new Date());
            voucherDetailsEntities.add(voucherDetailsEntity);
        }
        voucherDetailsRepository.insertBanch(voucherDetailsEntities);
        return null;
    }

    /**
     * 批量操作
     *
     * @param voucherDetailsVMS
     * @return
     */
    @Override
    public String checkData(long id, List<VoucherDetailsVM> voucherDetailsVMS) {

        List<Long> list1 = new ArrayList<>();//后台查询到的id
        List<Long> list2 = new ArrayList<>();//前端传过来的id
        List<VoucherDetailsVM> list3 = new ArrayList<>();//前端传过来的修改后的数据
        List<VoucherDetailsVM> list4 = new ArrayList<>();//需要新增的数据
        List<Long> list5 = new ArrayList<>();//需要删除的数据id集合

        String where = " and voucher_id = " + id;
        Page<VoucherDetailsVM> entrysByPage = voucherDetailsRepository.findEntrysByPage(where);
        //后台存在的数据 id存放到list1中
        for (VoucherDetailsVM voucherDetailsVM : entrysByPage) {
            list1.add(voucherDetailsVM.getId());
        }
        for (VoucherDetailsVM voucherDetailsVM : voucherDetailsVMS) {
            if (voucherDetailsVM.getId() != null) {
                list2.add(voucherDetailsVM.getId());
                list3.add(voucherDetailsVM);
            } else {
                voucherDetailsVM.setVoucherId(id);
                list4.add(voucherDetailsVM);
            }
        }
        for (long l : list1) {
            if (!list2.contains(l)) {
                list5.add(l);
            }
        }
        if (list4.size() > 0) {
            this.createEntryBanch(list4);
        }
        if (list3.size() > 0) {
            this.updateBanch(list3);
        }
        if (list5.size() > 0) {
            this.deleteEntryBanch(list5);
        }

        return null;
    }

    /**
     * 批量修改
     *
     * @param voucherDetailsVMS
     * @return
     */
    @Override
    public String updateBanch(List<VoucherDetailsVM> voucherDetailsVMS) {
        List<VoucherDetailsEntity> voucherDetailsEntityList = new ArrayList<>();
        for (VoucherDetailsVM voucherDetailsVM : voucherDetailsVMS) {
            VoucherDetailsEntity voucherDetailsEntity = new VoucherDetailsEntity();
            BeanUtils.copyProperties(voucherDetailsVM, voucherDetailsEntity);
            voucherDetailsEntity.setCreateBy(jwtUserUtil.getUserName());
            voucherDetailsEntity.setCreateTime(new Date());
            voucherDetailsEntityList.add(voucherDetailsEntity);
        }
        voucherDetailsRepository.updateBanch(voucherDetailsEntityList);
        return null;
    }

    /**
     * 批量删除
     *
     * @param longs
     * @return
     */
    @Override
    public String deleteEntryBanch(List<Long> longs) {
        voucherDetailsRepository.deleteBanch(longs);
        return null;
    }
}
