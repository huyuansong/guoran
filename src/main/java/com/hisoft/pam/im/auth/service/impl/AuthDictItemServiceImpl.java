package com.hisoft.pam.im.auth.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.hisoft.pam.im.auth.model.AuthDictItemEntity;
import com.hisoft.pam.im.auth.repository.AuthDictItemRepository;
import com.hisoft.pam.im.auth.service.AuthDictItemService;
import com.hisoft.pam.im.auth.vmodel.AuthDictItemVM;
import com.hisoft.pam.im.common.exception.ServiceException;
import com.hisoft.pam.im.common.search.DynamicSearch;
import com.hisoft.pam.im.common.search.FilterGroup;
import com.hisoft.pam.im.common.search.PageQuery;
import com.hisoft.pam.im.security.JwtUserUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author wangyongtao
 * @create 2022-3-11
 * @Modify By
 */
@Service
public class AuthDictItemServiceImpl  implements AuthDictItemService {
    @Autowired
    JwtUserUtil jwtUserUtil;
    @Autowired
    AuthDictItemRepository authDictItemRepository;
    /**
     * 根据id获取
     *
     * @param id
     * @return
     */
    @Override
    public AuthDictItemVM getEntryBy(long id) {
        AuthDictItemEntity authDictItemEntity = authDictItemRepository.findById(id);
        AuthDictItemVM authDictItemVM = new AuthDictItemVM();
        BeanUtils.copyProperties(authDictItemEntity,authDictItemVM);
        return authDictItemVM ;
    }

    /**
     * 创建
     *
     * @param authDictItemVM
     * @return entity的id
     */
    @Override
    public Long createEntry(AuthDictItemVM authDictItemVM) {
        AuthDictItemEntity authDictItemEntity=new AuthDictItemEntity();
        BeanUtils.copyProperties(authDictItemVM,authDictItemEntity);
        authDictItemEntity.setCreateBy(jwtUserUtil.getUserName());
        authDictItemEntity.setCreateTime(new Date());
        authDictItemRepository.insert(authDictItemEntity);
        return authDictItemEntity.getId();
    }

    /**
     * 修改
     *
     * @param authDictItemVM
     * @return 是否成功
     */
    @Override
    public boolean updateEntry(AuthDictItemVM authDictItemVM) throws ServiceException {
        AuthDictItemEntity authDictItemEntity=authDictItemRepository.findById(authDictItemVM.getId());
        authDictItemEntity.failWhenConcurrencyViolation(authDictItemVM.getConcurrencyVersion());
        BeanUtils.copyProperties(authDictItemVM,authDictItemEntity);
        authDictItemEntity.setUpdateBy(jwtUserUtil.getUserName());
        authDictItemEntity.setUpdateTime(new Date());
        return authDictItemRepository.update(authDictItemEntity);
    }

    /**
     * 删除
     *
     * @param id
     * @return 是否成功
     */
    @Override
    public boolean deleteById(long id) {
        return authDictItemRepository.deleteById(id);
    }

    /**
     * 分页
     *
     * @param pageQuery
     * @return
     */
    @Override
    public Page<AuthDictItemVM> findEntrysByPage(PageQuery pageQuery) {
        FilterGroup filterGroup=pageQuery.getWhere();
        //自动转字符串
        String where= DynamicSearch.getInstance().buildWhere(filterGroup);
        PageHelper.startPage(pageQuery.getPageNum(),pageQuery.getPageSize(),pageQuery.getOrderBy());
        return authDictItemRepository.findEntrysByPage(where);
    }

    /**
    *批量删除
    * @param ids
    */
    @Override
    public void deleteIds(String ids) {
        String[] idf = ids.split(",");
        for(String id:idf){
            authDictItemRepository.deleteById(Long.valueOf(id));
        }
    }

    @Override
    public void addBanch(List<AuthDictItemVM> list) {
        List<AuthDictItemEntity> listEntity = new ArrayList<>();
        for(AuthDictItemVM authDictItemVM : list){
            AuthDictItemEntity authDictItemEntity=new AuthDictItemEntity();
            BeanUtils.copyProperties(authDictItemVM,authDictItemEntity);
            authDictItemEntity.setCreateBy(jwtUserUtil.getUserName());
            authDictItemEntity.setCreateTime(new Date());
            authDictItemEntity.setStatus(1);
            listEntity.add(authDictItemEntity);
        }
        if (listEntity!=null && listEntity.size()>0){
            authDictItemRepository.addBanch(listEntity);
        }
    }
    /**
     * 根据dict_id查询字典值
     */
    @Override
    public List<AuthDictItemVM> getItemsById(Long dictId) {
      List<AuthDictItemVM> list = authDictItemRepository.getItemById(dictId);
        return list;
    }
    /**
     * 根据dict_code查询字典值
     */
    @Override
    public List<AuthDictItemVM> getItemsByCode(String dictCode) {
      List<AuthDictItemVM> list = authDictItemRepository.getItemByCode(dictCode);
        return list;
    }

    /**
     * 根据dict_code和字典名获取对应字典值
     */
    @Override
    public String getDictName(Long dictId,String dictCode){
        String dictName = authDictItemRepository.getDictValue(dictId, dictCode);
        return dictName;
    }

    @Override
    public String getDictCode(int dictId, String dictName) {
        String dictCode = authDictItemRepository.getDictCode(dictId, dictName);
        return dictCode;
    }

    @Override
    public List<AuthDictItemVM> getEntryByIdList(List<Long> collect) {
       return authDictItemRepository.getEntryByIdList(collect);
    }

    @Override
    public Long getItemsBy(AuthDictItemVM authDictItemVM) {
        return authDictItemRepository.getItemsBy(authDictItemVM);
    }

}
