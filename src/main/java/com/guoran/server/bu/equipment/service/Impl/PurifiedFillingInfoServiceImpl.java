package com.guoran.server.bu.equipment.service.Impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.guoran.server.auth.b.repository.DepartmentRepository;
import com.guoran.server.auth.model.DepartmentEntity;
import com.guoran.server.bu.equipment.model.PurifiedFillingInfoEntity;
import com.guoran.server.bu.equipment.repository.PurifiedFillingInfoRepository;
import com.guoran.server.bu.equipment.service.PurifiedFillingInfoService;
import com.guoran.server.bu.equipment.vmodel.PurifiedFillingInfoVM;
import com.guoran.server.common.exception.ServiceException;
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
 * 设备管理-设备运行记录-纯净水车间-灌装记录表 服务实现类
 * </p>
 * <p>
 * w
 * 2023/1/26  19:12
 * 01-pro
 **/
@Service
public class PurifiedFillingInfoServiceImpl implements PurifiedFillingInfoService {
    @Autowired
    JwtUserUtil jwtUserUtil;
    @Autowired
    PurifiedFillingInfoRepository purifiedFillingInfoRepository;
    @Autowired
    ProductRepository productRepository;
    @Autowired
    DepartmentRepository departmentRepository;

    /**
     * 根据id获取
     *
     * @param id
     * @return
     */
    @Override
    public PurifiedFillingInfoVM getEntryBy(long id) {
        PurifiedFillingInfoEntity purifiedFillingInfoEntity = purifiedFillingInfoRepository.findById(id);
        PurifiedFillingInfoVM purifiedFillingInfoVM = new PurifiedFillingInfoVM();
        BeanUtils.copyProperties(purifiedFillingInfoEntity, purifiedFillingInfoVM);
        return purifiedFillingInfoVM;
    }

    /**
     * 创建
     *
     * @param purifiedFillingInfoVM
     * @return entity的id
     */
    @Override
    public String createEntry(PurifiedFillingInfoVM purifiedFillingInfoVM) {
        //获取部门名
        DepartmentEntity departmentEntity = departmentRepository.findBycode(purifiedFillingInfoVM.getDepartId());
        //获取商品名
        ProductEntity productEntity = productRepository.finByCode(purifiedFillingInfoVM.getProdectCode());
        PurifiedFillingInfoEntity purifiedFillingInfoEntity = new PurifiedFillingInfoEntity();
        BeanUtils.copyProperties(purifiedFillingInfoVM, purifiedFillingInfoEntity);
        purifiedFillingInfoEntity.setCreateBy(jwtUserUtil.getUserName());
        purifiedFillingInfoEntity.setDepartName(departmentEntity.getDepartmentName());
        purifiedFillingInfoEntity.setProdectName(productEntity.getProductName());
        purifiedFillingInfoEntity.setCreateTime(new Date());
//        purifiedFillingInfoEntity.setUpdateTime(new Date());
//        purifiedFillingInfoEntity.setNoteTakerTime(new Date());
        purifiedFillingInfoRepository.insert(purifiedFillingInfoEntity);
        return null;
    }

    /**
     * 修改
     *
     * @param purifiedFillingInfoVM
     * @return 是否成功
     */
    @Override
    public String updateEntry(PurifiedFillingInfoVM purifiedFillingInfoVM) throws ServiceException {
        //获取部门名
        DepartmentEntity departmentEntity = departmentRepository.findBycode(purifiedFillingInfoVM.getDepartId());
        //获取商品名
        ProductEntity productEntity = productRepository.finByCode(purifiedFillingInfoVM.getProdectCode());
        PurifiedFillingInfoEntity purifiedFillingInfoEntity = purifiedFillingInfoRepository.findById(purifiedFillingInfoVM.getId());
        purifiedFillingInfoEntity.failWhenConcurrencyViolation(purifiedFillingInfoVM.getConcurrencyVersion());
        BeanUtils.copyProperties(purifiedFillingInfoVM, purifiedFillingInfoEntity);
        purifiedFillingInfoEntity.setDepartName(departmentEntity.getDepartmentName());
        purifiedFillingInfoEntity.setProdectName(productEntity.getProductName());
        purifiedFillingInfoEntity.setUpdateBy(jwtUserUtil.getUserName());
        purifiedFillingInfoEntity.setUpdateTime(new Date());
        purifiedFillingInfoRepository.update(purifiedFillingInfoEntity);
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
        return purifiedFillingInfoRepository.deleteById(Ids);
    }

    /**
     * 分页
     *
     * @param pageQuery
     * @return
     */
    @Override
    public Page<PurifiedFillingInfoVM> findEntrysByPage(PageQuery pageQuery) {
        FilterGroup filterGroup = pageQuery.getWhere();
        //自动转字符串
        String where = DynamicSearch.getInstance().buildWhere(filterGroup);
        PageHelper.startPage(pageQuery.getPageNum(), pageQuery.getPageSize(), pageQuery.getOrderBy());
        return purifiedFillingInfoRepository.findEntrysByPage(where);
    }
}

