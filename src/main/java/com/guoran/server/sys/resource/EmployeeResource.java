package com.guoran.server.sys.resource;


import com.github.pagehelper.Page;
import com.guoran.server.auth.service.MenuService;
import com.guoran.server.auth.service.PositionService;
import com.guoran.server.auth.vo.PositionVO;
import com.guoran.server.common.JsonResult;
import com.guoran.server.common.exception.ImErrorCode;
import com.guoran.server.common.exception.ServiceException;
import com.guoran.server.common.i18n.MessageUtils;
import com.guoran.server.common.resource.AuditCheckController;
import com.guoran.server.common.search.PageQuery;
import com.guoran.server.common.search.PageResult;
import com.guoran.server.common.utils.CheckInputUtil;
import com.guoran.server.common.utils.StringUtils;
import com.guoran.server.liu.financial.vmodel.WagesVO;
import com.guoran.server.sys.service.EmployeeService;
import com.guoran.server.sys.vmodel.EmployeeVM;
import com.guoran.server.sys.vo.AuditVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * <p>
 * 员工信息表 rest接口
 * </p>
 *
 * @author zhangjx
 * @create 2020-08-19
 * @Modify By
 */
@Api(tags = {"员工信息表"})
@RestController
@RequestMapping("/sys/employees")
public class EmployeeResource {
    @Autowired
    MenuService menuService;
    @Autowired
    PositionService positionService;
    @Autowired
    private EmployeeService employeeService;

    /**
     * 根据id查询
     */
    @ApiOperation(value = "根据id查询数据")
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public String getEntry(@PathVariable long id) {
        String result = null;
        try {
            EmployeeVM employeeVM = employeeService.getEntryBy(id);
            result = JsonResult.success(ImErrorCode.MSG_SUCCESS, MessageUtils.get(ImErrorCode.MSG_SUCCESS), employeeVM);
        } catch (ServiceException serviceException) {
            throw serviceException;
        } catch (Exception e) {
            ServiceException se = new ServiceException(ImErrorCode.MSG_FAIL, MessageUtils.get(ImErrorCode.MSG_FAIL));
            throw se;
        }
        return result;
    }

    /**
     * 查询分页数据
     */
    @ApiOperation(value = "查询分页数据")
    @RequestMapping(value = "/page", method = RequestMethod.POST)
    public String getEntrysByPage(@RequestBody PageQuery pageQuery, @Param("query") String query) {
        String result = null;
        try {
            CheckInputUtil checkInputUtil = new CheckInputUtil();
            if (checkInputUtil.chikcInput(pageQuery)) {
                return JsonResult.failed("请勿输入特殊字符");
            }
            Page<EmployeeVM> pageInfo = employeeService.findEntrysByPage(pageQuery, query);
            PageResult pageResult = new PageResult();
            pageResult.setPageNum(pageQuery.getPageNum());
            pageResult.setRows(pageInfo);
            pageResult.setTotal(pageInfo.getTotal());
            pageResult.setPages(pageInfo.getPages());
            return JsonResult.success(ImErrorCode.MSG_SUCCESS, MessageUtils.get(ImErrorCode.MSG_SUCCESS), pageResult);
        } catch (ServiceException serviceException) {
            throw serviceException;
        } catch (Exception e) {
            ServiceException se = new ServiceException(ImErrorCode.MSG_FAIL, MessageUtils.get(ImErrorCode.MSG_FAIL));
            throw se;
        }

    }

    /**
     * 新增
     */
    @ApiOperation(value = "新增数据")
    @RequestMapping(method = RequestMethod.POST)
    String createEntry(@RequestBody EmployeeVM employeeVM) {
        try {
            String resultMess = employeeService.createEntry(employeeVM);
            if (StringUtils.isNotEmpty(resultMess)) {
                return resultMess;
            }
            return JsonResult.success(ImErrorCode.MSG_SUCCESS, MessageUtils.get(ImErrorCode.MSG_SUCCESS), null);
        } catch (ServiceException serviceException) {
            throw serviceException;
        } catch (Exception e) {
            ServiceException se = new ServiceException(ImErrorCode.MSG_FAIL, MessageUtils.get(ImErrorCode.MSG_FAIL));
            throw se;
        }
    }

    /**
     * 修改
     */
    @ApiOperation(value = "修改数据")
    @RequestMapping(method = RequestMethod.PUT)
    public String updateEntry(@RequestBody EmployeeVM employeeVM) {
/*        try {
            String resultMess = employeeService.updateEntry(employeeVM);
            if (StringUtils.isNotEmpty(resultMess)) {
                return resultMess;
            }
            return JsonResult.success(ImErrorCode.MSG_SUCCESS, MessageUtils.get(ImErrorCode.MSG_SUCCESS), null);
        } catch (ServiceException serviceException) {
            throw serviceException;
        } catch (Exception e) {
            ServiceException se = new ServiceException(ImErrorCode.MSG_FAIL, MessageUtils.get(ImErrorCode.MSG_FAIL));
            throw se;
        }*/
        return "";

    }

    /**
     * 删除
     */
    @ApiOperation(value = "删除数据")
    @RequestMapping(value = "/{ids}", method = RequestMethod.DELETE)
    String deleteEntry(@PathVariable String ids) {
        /*try {
            String resultMess = employeeService.deleteById(ids);
            if (StringUtils.isNotEmpty(resultMess)) {
                return resultMess;
            }
            return JsonResult.success(ImErrorCode.MSG_SUCCESS, MessageUtils.get(ImErrorCode.MSG_SUCCESS), null);
        } catch (ServiceException serviceException) {
            throw serviceException;
        } catch (Exception e) {
            ServiceException se = new ServiceException(ImErrorCode.MSG_FAIL, MessageUtils.get(ImErrorCode.MSG_FAIL));
            throw se;
        }*/

        return "";
    }

    /**
     * 改变审核状态
     */
    @ApiOperation(value = "改变审核状态")
    @RequestMapping(value = "/changeReview", method = RequestMethod.POST)
    String changeReview(@RequestBody AuditVo auditVo, HttpServletRequest request) {
        try {
            List<String> linkList = menuService.getLinkList();
            AuditCheckController auditCheckUtil = new AuditCheckController();
            boolean b = auditCheckUtil.checkAudit(request, auditVo.getState(), linkList);
            if (!b) {
                return JsonResult.failed("没有权限进行审核操作！");
            }
            String resultMess = employeeService.changeReview(auditVo.getIds(), auditVo.getState(), auditVo.getDismissReason());
            if (StringUtils.isNotEmpty(resultMess)) {
                return resultMess;
            }
            return JsonResult.success(ImErrorCode.MSG_SUCCESS, MessageUtils.get(ImErrorCode.MSG_SUCCESS), null);
        } catch (ServiceException serviceException) {
            throw serviceException;
        } catch (Exception e) {
            ServiceException se = new ServiceException(ImErrorCode.MSG_FAIL, MessageUtils.get(ImErrorCode.MSG_FAIL));
            throw se;
        }
    }

    /**
     * 导出
     */
    @ApiOperation(value = "导出")
    @RequestMapping(value = "/exportEmployee", method = RequestMethod.POST)
    public String exportEmployee(@Param(value = "ids") String ids, HttpServletResponse response, HttpServletRequest request) {
/*        try {
            employeeService.explort(ids, response, request);
            return JsonResult.success(ImErrorCode.MSG_SUCCESS, MessageUtils.get(ImErrorCode.MSG_SUCCESS), null);
        } catch (ServiceException serviceException) {
            throw serviceException;
        } catch (Exception e) {
            ServiceException se = new ServiceException(ImErrorCode.MSG_FAIL, MessageUtils.get(ImErrorCode.MSG_FAIL));
            throw se;
        }*/

        return "";
    }

    /**
     * 获取所有员工
     */
    @ApiOperation(value = "获取所有员工")
    @RequestMapping(value = "/getAllEmployee", method = RequestMethod.POST)
    public String findAllEmployee() {
        try {
            List<EmployeeVM> allEmployee = employeeService.findAllEmployee();
            return JsonResult.success(ImErrorCode.MSG_SUCCESS, MessageUtils.get(ImErrorCode.MSG_SUCCESS), allEmployee);
        } catch (ServiceException serviceException) {
            throw serviceException;
        } catch (Exception e) {
            ServiceException se = new ServiceException(ImErrorCode.MSG_FAIL, MessageUtils.get(ImErrorCode.MSG_FAIL));
            throw se;
        }
    }

    /**
     * 同步信息
     */
    @ApiOperation(value = "同步")
    @RequestMapping(value = "/synInfos", method = RequestMethod.POST)
    public String synInfos() throws IOException {
        try {
            String resultMess = employeeService.synInfos();
            if (StringUtils.isNotEmpty(resultMess)) {
                return resultMess;
            }
            return JsonResult.success(ImErrorCode.MSG_SUCCESS, MessageUtils.get(ImErrorCode.MSG_SUCCESS), null);
        } catch (ServiceException serviceException) {
            throw serviceException;
        } catch (Exception e) {
            ServiceException se = new ServiceException(ImErrorCode.MSG_FAIL, MessageUtils.get(ImErrorCode.MSG_FAIL));
            throw se;
        }
    }


    /**
     * 获取当前登录员工信息
     */
    @ApiOperation(value = "获取当前登录员工信息")
    @RequestMapping(value = "/getEmployee", method = RequestMethod.GET)
    public String getEmployee() {
     /*   try {
            EmployeeVM employee = employeeService.getEntryInfo();
            return JsonResult.success(ImErrorCode.MSG_SUCCESS, MessageUtils.get(ImErrorCode.MSG_SUCCESS), employee);
        } catch (ServiceException serviceException) {
            throw serviceException;
        } catch (Exception e) {
            ServiceException se = new ServiceException(ImErrorCode.MSG_FAIL, MessageUtils.get(ImErrorCode.MSG_FAIL));
            throw se;
        }*/

        return "";
    }

    /**
     * 获取当前登录员工信息
     */
    @ApiOperation(value = "获取工资管理员工信息")
    @RequestMapping(value = "/getWagesEmployees", method = RequestMethod.POST)
    public String getWagesEmployees(@RequestBody WagesVO wagesVO) {
      /*  try {
            PageResult pageResult = new PageResult();
            List<EmployeeVM> pageList = employeeService.getWagesEmployees(wagesVO);
            ;
            List<EmployeeVM> pageList1 = new ArrayList<>();
            int pageSize = wagesVO.getPageSize();
            if (wagesVO.getPageNum() == ((pageList.size() / pageSize) + 1)) {
                for (int i = 0; i < pageList.size() % pageSize; i++) {
                    pageList1.add(pageList.get(pageSize * (wagesVO.getPageNum() - 1) + i));
                }
            } else {
                for (int i = 0; i < pageSize; i++) {
                    pageList1.add(pageList.get(pageSize * (wagesVO.getPageNum() - 1) + i));
                }
            }
            pageResult.setRows(pageList1);
            pageResult.setTotal(pageList.size());
            pageResult.setPageNum(wagesVO.getPageNum());
            if (pageList.size() % pageSize == 0) {
                pageResult.setPages(pageList.size() / pageSize);
            } else {
                pageResult.setPages((pageList.size() / pageSize) + 1);
            }
            return JsonResult.success(ImErrorCode.MSG_SUCCESS, MessageUtils.get(ImErrorCode.MSG_SUCCESS), pageResult);
        } catch (ServiceException serviceException) {
            throw serviceException;
        } catch (Exception e) {
            ServiceException se = new ServiceException(ImErrorCode.MSG_FAIL, MessageUtils.get(ImErrorCode.MSG_FAIL));
            throw se;
        }*/

        return "";
    }

    /**
     * 获取所有岗位
     */
    @ApiOperation(value = "获取所有岗位")
    @RequestMapping(value = "/getAllPostition", method = RequestMethod.POST)
    public String getAllPosition(@Param("code") String code) {
        try {
            List<PositionVO> positionVOS = positionService.getAllPostion(code);
            return JsonResult.success(ImErrorCode.MSG_SUCCESS, MessageUtils.get(ImErrorCode.MSG_SUCCESS), positionVOS);
        } catch (ServiceException serviceException) {
            throw serviceException;
        } catch (Exception e) {
            ServiceException se = new ServiceException(ImErrorCode.MSG_FAIL, MessageUtils.get(ImErrorCode.MSG_FAIL));
            throw se;
        }
    }
}
