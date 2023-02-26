package com.guoran.server.sys.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.guoran.server.auth.b.repository.DepartmentRepository;
import com.guoran.server.auth.vmodel.DepartmentVM;
import com.guoran.server.common.enumeration.MeterTypeEnum;
import com.guoran.server.common.exception.ServiceException;
import com.guoran.server.common.search.DynamicSearch;
import com.guoran.server.common.search.FilterGroup;
import com.guoran.server.common.search.PageQuery;
import com.guoran.server.security.JwtUserUtil;
import com.guoran.server.sys.a.repository.MeterDepartRepository;
import com.guoran.server.sys.model.MeterDepartEntity;
import com.guoran.server.sys.service.MeterDepartService;
import com.guoran.server.sys.vmodel.MeterDepartVM;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * <p>
 * 水表，电表关联部门使用比例服务实现类
 * </p>
 *
 * @author zhangjx
 * @create 2020-08-20
 * @Modify By
 */
@Service
public class MeterDepartServiceImpl implements MeterDepartService {
    @Autowired
    JwtUserUtil jwtUserUtil;
    @Autowired
    MeterDepartRepository meterDepartRepository;
    @Autowired
    DepartmentRepository departmentRepository;

    MeterTypeEnum meterTypeEnum;

    /**
     * 根据id获取
     *
     * @param id
     * @return
     */
    @Override
    public MeterDepartVM getEntryBy(long id) {
        MeterDepartEntity meterDepartEntity = meterDepartRepository.findById(id);
        MeterDepartVM meterDepartVM = new MeterDepartVM();
        BeanUtils.copyProperties(meterDepartEntity, meterDepartVM);
        return meterDepartVM;
    }

    /**
     * 创建
     *
     * @param meterDepartVM
     * @return entity的id
     */
    @Override
    public Long createEntry(MeterDepartVM meterDepartVM) {
//        String text = meterTypeEnum.getText();
        MeterDepartEntity meterDepartEntity = new MeterDepartEntity();
        String departId = meterDepartVM.getDepartId();
        String where = " and department_code = '" + departId + "'";
        Page<DepartmentVM> departmentVMS = departmentRepository.findEntrysByPage(where);
        BeanUtils.copyProperties(meterDepartVM, meterDepartEntity);
        meterDepartEntity.setCreateBy(jwtUserUtil.getUserName());
        meterDepartEntity.setDepartName(departmentVMS.get(0).getDepartmentName());
        meterDepartEntity.setCreateTime(new Date());
//        meterDepartEntity.setMeterType(text);
        meterDepartRepository.insert(meterDepartEntity);
        return meterDepartEntity.getId();
    }

    /**
     * 修改
     *
     * @param meterDepartVM
     * @return 是否成功
     */
    @Override
    public boolean updateEntry(MeterDepartVM meterDepartVM) throws ServiceException {

        MeterDepartEntity meterDepartEntity = meterDepartRepository.findById(meterDepartVM.getId());
        meterDepartEntity.failWhenConcurrencyViolation(meterDepartVM.getConcurrencyVersion());
        String departId = meterDepartVM.getDepartId();
        String where = " and department_code = '" + departId + "'";
        Page<DepartmentVM> departmentVMS = departmentRepository.findEntrysByPage(where);
        BeanUtils.copyProperties(meterDepartVM, meterDepartEntity);
        meterDepartEntity.setDepartName(departmentVMS.get(0).getDepartmentName());
        meterDepartEntity.setUpdateBy(jwtUserUtil.getUserName());
        meterDepartEntity.setUpdateTime(new Date());
        return meterDepartRepository.update(meterDepartEntity);
    }

    /**
     * 删除
     *
     * @param id
     * @return 是否成功
     */
    @Override
    public void deleteById(long id) {
        meterDepartRepository.deleteById(id);
    }

    /**
     * 分页
     *
     * @param pageQuery
     * @return
     */
    @Override
    public Page<MeterDepartVM> findEntrysByPage(PageQuery pageQuery) {
        FilterGroup filterGroup = pageQuery.getWhere();
        //自动转字符串
        String where = DynamicSearch.getInstance().buildWhere(filterGroup);
        PageHelper.startPage(pageQuery.getPageNum(), pageQuery.getPageSize(), pageQuery.getOrderBy());
        return meterDepartRepository.findEntrysByPage(where);
    }

    @Override
    public String updateEntrys(List<MeterDepartVM> meterDepartVMS) {
        meterDepartRepository.updateBanch(meterDepartVMS);
        return null;
    }


}
