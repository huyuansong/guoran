package com.guoran.server.sys.service;

import com.github.pagehelper.Page;
import com.guoran.server.common.exception.ServiceException;
import com.guoran.server.common.search.PageQuery;
import com.guoran.server.sys.vmodel.EmployeeVM;

import java.io.IOException;
import java.util.List;

/**
 * <p>
 * 员工信息表 服务类
 * </p>
 *
 * @author zhangjx
 * @create 2020-08-19
 * @Modify By
 */
public interface EmployeeService {
    /**
     * 根据id获取
     *
     * @param id
     */
    EmployeeVM getEntryBy(long id);

    /**
     * 创建
     *
     * @param employeeVM
     */
    String createEntry(EmployeeVM employeeVM);

    /*    *//**
     * 修改
     *
     * @param employeeVM
     *//*
    String updateEntry(EmployeeVM employeeVM);*/


    /**
     * 修改职员已经绑定登录 用户
     *
     * @param employeeVM
     * @return
     * @throws ServiceException
     */
    boolean updateBinding(EmployeeVM employeeVM) throws ServiceException;

    /**
     * 删除
     *
     * @param ids
     */
/*
    String deleteById(String ids);
*/

    /**
     * 改变审核状态
     *
     * @param ids
     * @return
     */
    String changeReview(String ids, Integer state, String dismissReason);

    /**
     * 分页
     *
     * @param pageQuery
     * @return
     */
    Page<EmployeeVM> findEntrysByPage(PageQuery pageQuery, String query);

    /**
     * 导出
     *
     * @param ids
     */
/*
    void explort(String ids, HttpServletResponse response, HttpServletRequest request);
*/

    /**
     * 获取所有员工
     */
    List<EmployeeVM> findAllEmployee();

    /**
     * 同步
     *
     * @return
     */
    String synInfos() throws IOException;

    /**
     * 获取登录人对应的公司，部门及岗位信息
     */
/*
    EmployeeVM getEntryInfo();
*/

    /**
     * 获取工资管理页面显示的员工列表
     *
     * @return
    /*     *//*
    List<EmployeeVM> getWagesEmployees(WagesVO wagesVO);*/
}
