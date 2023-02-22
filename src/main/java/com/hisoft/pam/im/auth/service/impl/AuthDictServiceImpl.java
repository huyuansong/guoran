package com.hisoft.pam.im.auth.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.hisoft.pam.im.auth.model.AuthDictEntity;
import com.hisoft.pam.im.auth.repository.AuthDictItemRepository;
import com.hisoft.pam.im.auth.vmodel.AuthDictVM;
import com.hisoft.pam.im.auth.repository.AuthDictRepository;
import com.hisoft.pam.im.auth.service.AuthDictService;
import com.hisoft.pam.im.common.exception.ServiceException;
import com.hisoft.pam.im.common.search.DynamicSearch;
import com.hisoft.pam.im.common.search.FilterGroup;
import com.hisoft.pam.im.common.search.PageQuery;
import com.hisoft.pam.im.security.JwtUserUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

@Transactional
@Service
public class AuthDictServiceImpl  implements AuthDictService {
    @Autowired
    JwtUserUtil jwtUserUtil;
    @Autowired
    AuthDictRepository authDictRepository;
    @Autowired
    AuthDictItemRepository authDictItemRepository;
    /**
     * 根据id获取
     *
     * @param id
     * @return
     */
    @Override
    public AuthDictVM getEntryBy(long id) {
        AuthDictEntity authDictEntity = authDictRepository.findById(id);
        AuthDictVM authDictVM = new AuthDictVM();
        BeanUtils.copyProperties(authDictEntity,authDictVM);
        return authDictVM ;
    }

    /**
     * 创建
     *
     * @param authDictVM
     * @return entity的id
     */
    @Override
    public String createEntry(AuthDictVM authDictVM) {
        AuthDictEntity authDictEntity=new AuthDictEntity();
        BeanUtils.copyProperties(authDictVM,authDictEntity);
        authDictEntity.setCreateBy(jwtUserUtil.getUserName());
        authDictEntity.setCreateTime(new Date());
        List<AuthDictVM> list = authDictRepository.getDictsByCode(authDictVM.getDictCode());
        if (list.size()>0){
            return "类型编码重复,请修改！";
        }
        authDictRepository.insert(authDictEntity);
        return null;
    }

    /**
     * 修改
     *
     * @param authDictVM
     * @return 是否成功
     */
    @Override
    public boolean updateEntry(AuthDictVM authDictVM) throws ServiceException {
        AuthDictEntity authDictEntity=authDictRepository.findById(authDictVM.getId());
        authDictEntity.failWhenConcurrencyViolation(authDictVM.getConcurrencyVersion());
        BeanUtils.copyProperties(authDictVM,authDictEntity);
        authDictEntity.setUpdateBy(jwtUserUtil.getUserName());
        authDictEntity.setUpdateTime(new Date());
        return authDictRepository.update(authDictEntity);
    }

    /**
     * 删除
     *
     * @param id
     * @return 是否成功
     */
    @Override
    public boolean deleteById(long id) {
        boolean b = false;
        try {
            authDictItemRepository.deleteBanch(id);
            b = authDictRepository.deleteById(id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return b;
    }

    /**
     * 分页
     *
     * @param pageQuery
     * @return
     */
    @Override
    public Page<AuthDictVM> findEntrysByPage(PageQuery pageQuery) {
        FilterGroup filterGroup=pageQuery.getWhere();
        //自动转字符串
        String where= DynamicSearch.getInstance().buildWhere(filterGroup);
        PageHelper.startPage(pageQuery.getPageNum(),pageQuery.getPageSize(),pageQuery.getOrderBy());
        return authDictRepository.findEntrysByPage(where);
    }

    /**
    *批量删除
    * @param ids
    */
    @Override
    public void deleteIds(String ids) {
        String[] idf = ids.split(",");
        for(String id:idf){
            authDictRepository.deleteById(Long.valueOf(id));
        }
    }

    /**
    * 删除 做修改操作
    * @param authDictVM
    * @return
    */
    @Override
    public boolean updateIsDelete(AuthDictVM authDictVM) {
        AuthDictEntity authDictEntity=new AuthDictEntity();
        BeanUtils.copyProperties(authDictVM,authDictEntity);
        return authDictRepository.updateIsDelete(authDictEntity);
    }

    @Override
    public List<AuthDictVM> findByCode(String code) {
        return authDictRepository.getDictsByCode(code);
    }
}
