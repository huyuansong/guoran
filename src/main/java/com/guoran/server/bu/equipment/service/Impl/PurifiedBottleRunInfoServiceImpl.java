package com.guoran.server.bu.equipment.service.Impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.guoran.server.auth.b.repository.DepartmentRepository;
import com.guoran.server.auth.model.DepartmentEntity;
import com.guoran.server.bu.equipment.model.PurifiedBottleRunInfoEntity;
import com.guoran.server.bu.equipment.repository.PurifiedBottleRunInfoRepository;
import com.guoran.server.bu.equipment.service.PurifiedBottleRunInfoService;
import com.guoran.server.bu.equipment.vmodel.PurifiedBottleRunInfoVM;
import com.guoran.server.common.exception.ServiceException;
import com.guoran.server.common.model.BaseEntity;
import com.guoran.server.common.search.DynamicSearch;
import com.guoran.server.common.search.FilterGroup;
import com.guoran.server.common.search.PageQuery;
import com.guoran.server.security.JwtUserUtil;
import com.guoran.server.sys.a.repository.ProductRepository;
import com.guoran.server.sys.model.ProductEntity;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * <p>
 * 设备管理-设备运行记录-纯净水车间-吹瓶机运行记录 服务实现类
 * </p>
 *
 * @author zhangjx
 * @create 2020-08-25
 * @Modify By
 */
@Service
public class PurifiedBottleRunInfoServiceImpl implements PurifiedBottleRunInfoService {
    @Autowired
    JwtUserUtil jwtUserUtil;
    @Autowired
    PurifiedBottleRunInfoRepository purifiedBottleRunInfoRepository;
    @Autowired
    ProductRepository productRepository;
    @Autowired
    DepartmentRepository departmentRepository;
    @Autowired
    BaseEntity baseEntity;

    /**
     * 根据id获取
     *
     * @param id
     * @return
     */
    @Override
    public PurifiedBottleRunInfoVM getEntryBy(long id) {
        PurifiedBottleRunInfoEntity purifiedBottleRunInfoEntity = purifiedBottleRunInfoRepository.findById(id);
        PurifiedBottleRunInfoVM purifiedBottleRunInfoVM = new PurifiedBottleRunInfoVM();
        BeanUtils.copyProperties(purifiedBottleRunInfoEntity, purifiedBottleRunInfoVM);
        return purifiedBottleRunInfoVM;
    }

    /**
     * 创建
     *
     * @param purifiedBottleRunInfoVM
     * @return entity的id
     */
    @Override
    public String createEntry(PurifiedBottleRunInfoVM purifiedBottleRunInfoVM) {
        //获取部门名
        DepartmentEntity departmentEntity = departmentRepository.findBycode(purifiedBottleRunInfoVM.getDepartId());
        //获取商品名
        ProductEntity productEntity = productRepository.finByCode(purifiedBottleRunInfoVM.getProdectCode());
        PurifiedBottleRunInfoEntity purifiedBottleRunInfoEntity = new PurifiedBottleRunInfoEntity();
        BeanUtils.copyProperties(purifiedBottleRunInfoVM, purifiedBottleRunInfoEntity);
        purifiedBottleRunInfoEntity.setDepartName(departmentEntity.getDepartmentName());
        purifiedBottleRunInfoEntity.setProdectName(productEntity.getProductName());
        purifiedBottleRunInfoEntity.setCreateBy(jwtUserUtil.getUserName());
        purifiedBottleRunInfoEntity.setCreateTime(new Date());
//        purifiedBottleRunInfoEntity.setUpdateTime(new Date());
//        purifiedBottleRunInfoEntity.setNoteTakerTime();
        purifiedBottleRunInfoRepository.insert(purifiedBottleRunInfoEntity);
        return null;
    }

    /**
     * 修改
     *
     * @param purifiedBottleRunInfoVM
     * @return 是否成功
     */
    @Override
    public String updateEntry(PurifiedBottleRunInfoVM purifiedBottleRunInfoVM) throws ServiceException {
        //获取部门名
        DepartmentEntity departmentEntity = departmentRepository.findBycode(purifiedBottleRunInfoVM.getDepartId());
        //获取商品名
        ProductEntity productEntity = productRepository.finByCode(purifiedBottleRunInfoVM.getProdectCode());
        PurifiedBottleRunInfoEntity purifiedBottleRunInfoEntity = purifiedBottleRunInfoRepository.findById(purifiedBottleRunInfoVM.getId());
        purifiedBottleRunInfoEntity.failWhenConcurrencyViolation(purifiedBottleRunInfoVM.getConcurrencyVersion());
        BeanUtils.copyProperties(purifiedBottleRunInfoVM, purifiedBottleRunInfoEntity);
        purifiedBottleRunInfoEntity.setDepartName(departmentEntity.getDepartmentName());
        purifiedBottleRunInfoEntity.setProdectName(productEntity.getProductName());
        purifiedBottleRunInfoEntity.setUpdateBy(jwtUserUtil.getUserName());
        purifiedBottleRunInfoEntity.setUpdateTime(new Date());
        purifiedBottleRunInfoRepository.update(purifiedBottleRunInfoEntity);
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
        String[] Ids = ids.split(",");
        return purifiedBottleRunInfoRepository.deleteById(Ids);
    }

    /**
     * 分页
     *
     * @param pageQuery
     * @return
     */
    @Override
    public Page<PurifiedBottleRunInfoVM> findEntrysByPage(PageQuery pageQuery) {
        FilterGroup filterGroup = pageQuery.getWhere();
        //自动转字符串
        String where = DynamicSearch.getInstance().buildWhere(filterGroup);
        PageHelper.startPage(pageQuery.getPageNum(), pageQuery.getPageSize(), pageQuery.getOrderBy());
        return purifiedBottleRunInfoRepository.findEntrysByPage(where);
    }
}
