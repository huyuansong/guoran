package com.hisoft.pam.im.auth.service;

import com.github.pagehelper.Page;
import com.hisoft.pam.im.auth.vmodel.DepartmentVM;
import com.hisoft.pam.im.auth.vo.CompanyVO;
import com.hisoft.pam.im.auth.vo.DepartmentVO;
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
public interface DepartmentService{
/**
     *  根据id获取
     * @param id
     */
    DepartmentVM getEntryBy(long id);

    /**
     * 创建
     * @param departmentVM
     */
    String createEntry(DepartmentVM departmentVM);
    /**
     * 修改
     * @param departmentVM
     */
    String updateEntry(DepartmentVM departmentVM);
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
    Page<DepartmentVM> findEntrysByPage(PageQuery pageQuery);

    /**
     * 同步
     * @return
     */
    String synInfos(List<CompanyVO> allCompany) throws IOException;

    /**
     * 获取所有部门信息
     * @return
     */
    List<DepartmentVO> getAllDepartment(String companyCode);

    List<DepartmentVM> getList();

    String getCompanyCode(String departmentCode);

    String getCompanyNameByCode(String departmentCode);

    DepartmentVO findEntrysByCode(String productionWorkshopId);


}
