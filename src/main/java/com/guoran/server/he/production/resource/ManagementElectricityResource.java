package com.guoran.server.he.production.resource;

import com.github.pagehelper.Page;
import com.guoran.server.auth.service.MenuService;
import com.guoran.server.common.JsonResult;
import com.guoran.server.common.exception.ImErrorCode;
import com.guoran.server.common.exception.ServiceException;
import com.guoran.server.common.i18n.MessageUtils;
import com.guoran.server.common.resource.AuditCheckController;
import com.guoran.server.common.search.PageQuery;
import com.guoran.server.common.search.PageResult;
import com.guoran.server.common.utils.CheckInputUtil;
import com.guoran.server.common.utils.StringUtils;
import com.guoran.server.he.production.service.ManagementElectricityService;
import com.guoran.server.he.production.vmodel.ManagementElectricityVM;
import com.guoran.server.he.production.vo.ProductionQualityVo;
import com.guoran.server.sys.service.ElectricityMeterService;
import com.guoran.server.sys.vmodel.ElectricityMeterVM;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @time 2023/2/2714:25
 * @outhor zhou
 */
@Api(tags = {"生产电费记录表"})
@RestController
@RequestMapping("/production/managementElectricitys")
public class ManagementElectricityResource {
    @Autowired
    MenuService menuService;
    @Autowired
    private ManagementElectricityService managementElectricityService;
    @Autowired
    private ElectricityMeterService electricityMeterService;

    /**
     * 根据id查询
     */
    @ApiOperation(value = "根据id查询数据")
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public String getEntry(@PathVariable long id) {
        String result = null;
        try {
            ManagementElectricityVM managementElectricityVM = managementElectricityService.getEntryBy(id);
            result = JsonResult.success(ImErrorCode.MSG_SUCCESS, MessageUtils.get(ImErrorCode.MSG_SUCCESS), managementElectricityVM);
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
    public String getEntrysByPage(@RequestBody PageQuery pageQuery) {
        String result = null;
        try {
            CheckInputUtil checkInputUtil = new CheckInputUtil();
            if (checkInputUtil.chikcInput(pageQuery)) {
                return JsonResult.failed("请勿输入特殊字符");
            }
            Page<ManagementElectricityVM> pageInfo = managementElectricityService.findEntrysByPage(pageQuery);
            PageResult pageResult = new PageResult();
            pageResult.setPageNum(pageQuery.getPageNum());
            pageResult.setRows(pageInfo);
            pageResult.setTotal(pageInfo.getTotal());
            pageResult.setPages(pageInfo.getPages());
            result = JsonResult.success(ImErrorCode.MSG_SUCCESS, MessageUtils.get(ImErrorCode.MSG_SUCCESS), pageResult);
        } catch (ServiceException serviceException) {
            throw serviceException;
        } catch (Exception e) {
            ServiceException se = new ServiceException(ImErrorCode.MSG_FAIL, MessageUtils.get(ImErrorCode.MSG_FAIL));
            throw se;
        }
        return result;
    }

    /**
     * 新增
     */
    @ApiOperation(value = "新增数据")
    @RequestMapping(method = RequestMethod.POST)
    public String createEntry(@RequestBody ManagementElectricityVM managementElectricityVM) {
        try {
            String message = managementElectricityService.createEntry(managementElectricityVM);
            if (StringUtils.isNotEmpty(message)) {
                return message;
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
    public String updateEntry(@RequestBody ManagementElectricityVM managementElectricityVM) {
        try {
            ManagementElectricityVM entryBy = managementElectricityService.getEntryBy(managementElectricityVM.getId());
            if (entryBy.getAuditStatus() != 3 && entryBy.getAuditStatus() != 7) {
                return JsonResult.failed("没有数据权限！");
            }
            String message = managementElectricityService.updateEntry(managementElectricityVM);
            if (StringUtils.isNotEmpty(message)) {
                return message;
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
     * 删除
     */
    @ApiOperation(value = "删除数据")
    @RequestMapping(value = "/{ids}", method = RequestMethod.DELETE)
    String deleteEntry(@PathVariable String ids) {
        try {
            ManagementElectricityVM entryBy = managementElectricityService.getEntryBy(Long.parseLong(ids));
            if (entryBy.getAuditStatus() != 3 && entryBy.getAuditStatus() != 7) {
                return JsonResult.failed("没有数据权限！");
            }
            managementElectricityService.deleteById(ids);
            return JsonResult.success(ImErrorCode.MSG_SUCCESS, MessageUtils.get(ImErrorCode.MSG_SUCCESS), null);
        } catch (ServiceException serviceException) {
            throw serviceException;
        } catch (Exception e) {
            ServiceException se = new ServiceException(ImErrorCode.MSG_FAIL, MessageUtils.get(ImErrorCode.MSG_FAIL));
            throw se;
        }
    }

    /*
     *审核
     */
    @ApiOperation(value = "审核")
    @PostMapping(value = "/audit")
    String auditEntry(@RequestBody ProductionQualityVo auditUpdate, HttpServletRequest request) {
        try {

            List<String> linkList = menuService.getLinkList();
            AuditCheckController auditCheckUtil = new AuditCheckController();
            boolean b = auditCheckUtil.checkAudit(request, auditUpdate.getStatus(), linkList);
            if (!b) {
                return JsonResult.failed("没有权限进行审核操作！");
            }
            Long[] ids = auditUpdate.getIds();
            for (Long sid : ids) {
                ManagementElectricityVM entryBy = managementElectricityService.getEntryBy(sid);
                if ((auditUpdate.getStatus() != 2 && auditUpdate.getStatus() != 3) || (entryBy.getAuditStatus() != 3 && entryBy.getAuditStatus() != 2 && entryBy.getAuditStatus() != 7)) {
                    return JsonResult.failed("没有数据权限！");
                }
            }

            managementElectricityService.auditById(auditUpdate);
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
    @RequestMapping(value = "/electricity", method = RequestMethod.POST)
    public String electricity(@RequestBody Long[] ids, HttpServletResponse response, HttpServletRequest request) {
        try {
            managementElectricityService.explort(ids, response, request);
            return JsonResult.success(ImErrorCode.MSG_SUCCESS, MessageUtils.get(ImErrorCode.MSG_SUCCESS), null);
        } catch (ServiceException serviceException) {
            throw serviceException;
        } catch (Exception e) {
            ServiceException se = new ServiceException(ImErrorCode.MSG_FAIL, MessageUtils.get(ImErrorCode.MSG_FAIL));
            throw se;
        }
    }

    /**
     * 获取上次读数
     */
    @ApiOperation(value = "获取上次读数")
    @RequestMapping(value = "/getLastReading", method = RequestMethod.GET)
    public String getLastReading(@Param(value = "code") String code) {
        try {
            ManagementElectricityVM lastReading = managementElectricityService.getLastReading(code);
            return JsonResult.success(ImErrorCode.MSG_SUCCESS, MessageUtils.get(ImErrorCode.MSG_SUCCESS), lastReading);
        } catch (ServiceException serviceException) {
            throw serviceException;
        } catch (Exception e) {
            ServiceException se = new ServiceException(ImErrorCode.MSG_FAIL, MessageUtils.get(ImErrorCode.MSG_FAIL));
            throw se;
        }
    }

    /**
     * 查询分页数据
     */
    @ApiOperation(value = "查询分页数据")
    @RequestMapping(value = "/electricityMeters/page", method = RequestMethod.POST)
    public String getElectricityMetersEntrysByPage(@RequestBody PageQuery pageQuery) {
        String result = null;
        try {
            CheckInputUtil checkInputUtil = new CheckInputUtil();
            if (checkInputUtil.chikcInput(pageQuery)) {
                return JsonResult.failed("请勿输入特殊字符");
            }
            Page<ElectricityMeterVM> pageInfo = electricityMeterService.findEntrysByPage(pageQuery);
            PageResult pageResult = new PageResult();
            pageResult.setPageNum(pageQuery.getPageNum());
            pageResult.setRows(pageInfo);
            pageResult.setTotal(pageInfo.getTotal());
            pageResult.setPages(pageInfo.getPages());
            result = JsonResult.success(ImErrorCode.MSG_SUCCESS, MessageUtils.get(ImErrorCode.MSG_SUCCESS), pageResult);
        } catch (ServiceException serviceException) {
            throw serviceException;
        } catch (Exception e) {
            ServiceException se = new ServiceException(ImErrorCode.MSG_FAIL, MessageUtils.get(ImErrorCode.MSG_FAIL));
            throw se;
        }
        return result;
    }
}
