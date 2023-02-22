package com.hisoft.pam.im.auth.resource;

import com.github.pagehelper.Page;
import com.hisoft.pam.im.auth.vo.CompanyVO;
import com.hisoft.pam.im.auth.vo.DepartmentVO;
import com.hisoft.pam.im.common.JsonResult;
import com.hisoft.pam.im.common.exception.ImErrorCode;
import com.hisoft.pam.im.common.i18n.MessageUtils;
import com.hisoft.pam.im.common.search.PageQuery;
import com.hisoft.pam.im.common.search.PageResult;
import com.hisoft.pam.im.common.utils.CheckInputUtil;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import com.hisoft.pam.im.auth.service.DepartmentService;
import com.hisoft.pam.im.auth.vmodel.DepartmentVM;

import com.hisoft.pam.im.common.exception.ServiceException;
import com.hisoft.pam.im.common.utils.StringUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import java.util.ArrayList;
import java.util.List;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
    *  rest接口
    * </p>
 *
 * @author zhangjx
 * @create 2020-09-10
 * @Modify By
 */
@Api(tags = {"部门审核"})
@RestController
@RequestMapping("/auth/departments")
public class DepartmentResource {
    @Autowired
    private DepartmentService departmentService;

    /**
    * 根据id查询
    */
    @ApiOperation(value = "根据id查询数据")
    @RequestMapping(value = "/{id}",method = RequestMethod.GET)
    public String getEntry(@PathVariable long id){
        String result = null;
        try {
            DepartmentVM departmentVM = departmentService.getEntryBy(id);
            result = JsonResult.success(ImErrorCode.MSG_SUCCESS, MessageUtils.get(ImErrorCode.MSG_SUCCESS),departmentVM);
        } catch (ServiceException serviceException){
            throw serviceException;
        } catch (Exception e){
            ServiceException se = new ServiceException(ImErrorCode.MSG_FAIL,MessageUtils.get(ImErrorCode.MSG_FAIL));
            throw se;
        }
        return result;
    }
    /**
     * 查询分页数据
     */
    @ApiOperation(value = "查询分页数据")
   @RequestMapping(value = "/page",method = RequestMethod.POST)
    public String getEntrysByPage(@RequestBody PageQuery pageQuery){
        String result = null;
        try {
            CheckInputUtil checkInputUtil = new CheckInputUtil();
            if(checkInputUtil.chikcInput(pageQuery)){
                return JsonResult.failed("请勿输入特殊字符");
            }
            Page<DepartmentVM> pageInfo = departmentService.findEntrysByPage(pageQuery);
            PageResult pageResult=new PageResult();
            pageResult.setPageNum(pageQuery.getPageNum());
            pageResult.setRows(pageInfo);
            pageResult.setTotal(pageInfo.getTotal());
            pageResult.setPages(pageInfo.getPages());
            result = JsonResult.success(ImErrorCode.MSG_SUCCESS,MessageUtils.get(ImErrorCode.MSG_SUCCESS),pageResult);
        } catch (ServiceException serviceException){
            throw serviceException;
        } catch (Exception e){
            ServiceException se = new ServiceException(ImErrorCode.MSG_FAIL,MessageUtils.get(ImErrorCode.MSG_FAIL));
            throw se;
        }
        return result;
    }
    /**
    * 新增
    */
    @ApiOperation(value = "新增数据")
    @RequestMapping(method = RequestMethod.POST)
    public String createEntry(@RequestBody DepartmentVM departmentVM) {
        try {
            String message = departmentService.createEntry(departmentVM);
            if(StringUtils.isNotEmpty(message)){
                return message;
            }
            return JsonResult.success(ImErrorCode.MSG_SUCCESS,MessageUtils.get(ImErrorCode.MSG_SUCCESS),null);
        }catch (ServiceException serviceException){
            throw serviceException;
        } catch (Exception e){
            ServiceException se = new ServiceException(ImErrorCode.MSG_FAIL,MessageUtils.get(ImErrorCode.MSG_FAIL));
            throw se;
        }
    }
    /**
    * 修改
    */
    @ApiOperation(value = "修改数据")
    @RequestMapping(method = RequestMethod.PUT)
    public String updateEntry(@RequestBody DepartmentVM departmentVM) {
        try {
            String message = departmentService.updateEntry(departmentVM);
            if(StringUtils.isNotEmpty(message)){
                return message;
            }
            return JsonResult.success(ImErrorCode.MSG_SUCCESS,MessageUtils.get(ImErrorCode.MSG_SUCCESS),null);
        }catch (ServiceException serviceException){
            throw serviceException;
        } catch (Exception e){
            ServiceException se = new ServiceException(ImErrorCode.MSG_FAIL,MessageUtils.get(ImErrorCode.MSG_FAIL));
            throw se;
        }
    }

    /**
    * 删除
    */
    @ApiOperation(value = "删除数据")
    @RequestMapping(value = "/{id}",method = RequestMethod.DELETE)
    String deleteEntry(@PathVariable long id) {
        try {
            departmentService.deleteById(id);
            return JsonResult.success(ImErrorCode.MSG_SUCCESS,MessageUtils.get(ImErrorCode.MSG_SUCCESS),null);
        }catch (ServiceException serviceException){
            throw serviceException;
        } catch (Exception e){
            ServiceException se = new ServiceException(ImErrorCode.MSG_FAIL,MessageUtils.get(ImErrorCode.MSG_FAIL));
            throw se;
        }
    }
    /**
     * 新增
     */
    @ApiOperation(value = "新增数据")
    @RequestMapping(value = "/synInfos",method = RequestMethod.POST)
    public String synInfos() {
        try {
            List<CompanyVO> allCompany = new ArrayList<>();
            String message = departmentService.synInfos(allCompany);
            if(StringUtils.isNotEmpty(message)){
                return message;
            }
            return JsonResult.success(ImErrorCode.MSG_SUCCESS,MessageUtils.get(ImErrorCode.MSG_SUCCESS),null);
        }catch (ServiceException serviceException){
            throw serviceException;
        } catch (Exception e){
            ServiceException se = new ServiceException(ImErrorCode.MSG_FAIL,MessageUtils.get(ImErrorCode.MSG_FAIL));
            throw se;
        }
    }
    /**
     * 根据公司code获取所有部门信息
     */
    @ApiOperation(value = "根据公司code获取所有")
    @RequestMapping(value = "/getAllDepartment",method = RequestMethod.POST)
    public String getAllDepartment(@Param(value = "companyCode")String companyCode){
        try {
            List<DepartmentVO> departmentVOS = departmentService.getAllDepartment(companyCode);

            return JsonResult.success(ImErrorCode.MSG_SUCCESS,MessageUtils.get(ImErrorCode.MSG_SUCCESS),departmentVOS);
        }catch (ServiceException serviceException){
            throw serviceException;
        } catch (Exception e){
            ServiceException se = new ServiceException(ImErrorCode.MSG_FAIL,MessageUtils.get(ImErrorCode.MSG_FAIL));
            throw se;
        }
    }
    /**
     * 获取部门列表
     */
    @ApiOperation(value = "获取部门列表")
    @RequestMapping(value = "/getList",method = RequestMethod.POST)
    public String getList(){
        try {
            List<DepartmentVM> list = departmentService.getList();
            return JsonResult.success(ImErrorCode.MSG_SUCCESS,MessageUtils.get(ImErrorCode.MSG_SUCCESS),list);
        }catch (ServiceException serviceException){
            throw serviceException;
        } catch (Exception e){
            ServiceException se = new ServiceException(ImErrorCode.MSG_FAIL,MessageUtils.get(ImErrorCode.MSG_FAIL));
            throw se;
        }
    }
}
