package com.hisoft.pam.im.auth.service;

import com.github.pagehelper.Page;
import com.hisoft.pam.im.auth.vmodel.CompanyVM;
import com.hisoft.pam.im.auth.vo.CompanyVO;
import com.hisoft.pam.im.common.search.PageQuery;

import java.io.IOException;
import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author zhangjx
 * @create 2020-09-10
 * @Modify By
 * */
public interface CompanyService{
/**
     *  根据id获取
     * @param id
     */
    CompanyVM getEntryBy(long id);

    /**
     * 创建
     * @param companyVM
     */
    String createEntry(CompanyVM companyVM);
    /**
     * 修改
     * @param companyVM
     */
    String updateEntry(CompanyVM companyVM);
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
    Page<CompanyVM> findEntrysByPage(PageQuery pageQuery);

    /**
     * 同步
     * @return
     */
    String synInfos() throws IOException;


    List<CompanyVO> getAllCompany();

    List company();
    List companyCopy();

}
