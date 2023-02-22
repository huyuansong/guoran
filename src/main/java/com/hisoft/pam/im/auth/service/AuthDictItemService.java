package com.hisoft.pam.im.auth.service;

import com.github.pagehelper.Page;
import com.hisoft.pam.im.auth.model.AuthDictItemEntity;
import com.hisoft.pam.im.auth.vmodel.AuthDictItemVM;
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
public interface AuthDictItemService{
/**
     *  根据id获取
     * @param id
     */
    AuthDictItemVM getEntryBy(long id);

    /**
     * 创建
     * @param authDictItemVM
     */
    Long createEntry(AuthDictItemVM authDictItemVM);
    /**
     * 修改
     * @param authDictItemVM
     */
    boolean updateEntry(AuthDictItemVM authDictItemVM);
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
    Page<AuthDictItemVM> findEntrysByPage(PageQuery pageQuery);

    /**
    * 批量删除
    * @param ids
    */
    void deleteIds(String ids);

    /**
     * 批量新增
     * @param list
     */
    void addBanch(List<AuthDictItemVM> list);

    /**
     * 根据dictId查询字典值
     * @param dictCode
     * @return
     */
    List<AuthDictItemVM> getItemsByCode(String dictCode);

    List<AuthDictItemVM> getItemsById(Long dictId);

    /**
     * 根据dictId和dictCode查询字典值
     * @param dictId
     * @param dictCode
     * @return
     */
    public String getDictName(Long dictId,String dictCode);

    /**
     * 根据dictId和dictCode查询字典值
     * @param dictId
     * @param dictName
     * @return
     */
    public String getDictCode(int dictId,String dictName);

    /**
     * 根据dictIdsList查询
     */
    List<AuthDictItemVM> getEntryByIdList(List<Long> collect);

    Long getItemsBy(AuthDictItemVM authDictItemVM);
}
