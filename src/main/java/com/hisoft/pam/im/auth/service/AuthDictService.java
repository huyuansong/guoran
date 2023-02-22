package com.hisoft.pam.im.auth.service;

import com.github.pagehelper.Page;
import com.hisoft.pam.im.auth.vmodel.AuthDictVM;
import com.hisoft.pam.im.common.search.PageQuery;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author wangyongtao
 * @create 2022-3-11
 * @Modify By
 * */
public interface AuthDictService{
/**
     *  根据id获取
     * @param id
     */
    AuthDictVM getEntryBy(long id);

    /**
     * 创建
     * @param authDictVM
     */
    String createEntry(AuthDictVM authDictVM);
    /**
     * 修改
     * @param authDictVM
     */
    boolean updateEntry(AuthDictVM authDictVM);
    /**
     * 删除
     * @param id
     */
    boolean deleteById(long id);

    /**
     * 分页
     * @param pageQuery
     * @return
     */
    Page<AuthDictVM> findEntrysByPage(PageQuery pageQuery);

    /**
    * 批量删除
    * @param ids
    */
    void deleteIds(String ids);

    /**
    * 根据id删除 做修改操作 修改 is_delete
    * @param authDictVM
    * @return
    */
    boolean updateIsDelete(AuthDictVM authDictVM);

    /**
     * 根据code查询
     * @return
     */
    List<AuthDictVM> findByCode(String code);
}
