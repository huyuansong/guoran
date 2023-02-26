package com.guoran.server.sys.resource;

import com.fasterxml.jackson.databind.ObjectMapper;
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
import com.guoran.server.sys.service.WarehouseService;
import com.guoran.server.sys.vmodel.WarehouseVM;
import com.guoran.server.sys.vo.AuditVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * <p>
 * 仓库信息表 rest接口
 * </p>
 *
 * @author zhangjx
 * @create 2020-08-20
 * @Modify By
 */
@Api(tags = {"仓库信息表"})
@RestController
@RequestMapping("/sys/warehouses")
public class WarehouseResource {
    @Autowired
    ObjectMapper objectMapper;
    @Autowired
    MenuService menuService;
    @Autowired
    private WarehouseService warehouseService;

    /**
     * 根据id查询
     */
    @ApiOperation(value = "根据id查询数据")
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public String getEntry(@PathVariable long id) {
        String result = null;
        try {
            WarehouseVM warehouseVM = warehouseService.getEntryBy(id);
            result = JsonResult.success(ImErrorCode.MSG_SUCCESS, MessageUtils.get(ImErrorCode.MSG_SUCCESS), warehouseVM);
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
            Page<WarehouseVM> pageInfo = warehouseService.findEntrysByPage(pageQuery);
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
     * 查询所有仓库数据
     */
    @ApiOperation(value = "查询所有仓库数据")
    @RequestMapping(value = "/getAllWarehouse", method = RequestMethod.POST)
    public String findAllWarehouse() {
        String result = null;
        try {
            List<WarehouseVM> list = warehouseService.findAllWarehouse();
            result = JsonResult.success(ImErrorCode.MSG_SUCCESS, MessageUtils.get(ImErrorCode.MSG_SUCCESS), list);
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
    String createEntry(@RequestBody WarehouseVM warehouseVM) {
        try {
            String resultMess = warehouseService.createEntry(warehouseVM);
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
    public String updateEntry(@RequestBody WarehouseVM warehouseVM) {
        try {
            WarehouseVM entryBy = warehouseService.getEntryBy(warehouseVM.getId());
            if (entryBy.getAuditStatus() != 3 && entryBy.getAuditStatus() != 7) {
                return JsonResult.failed("没有数据权限！");
            }
            String resultMess = warehouseService.updateEntry(warehouseVM);
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
     * 批量删除
     */
    @ApiOperation(value = "删除数据")
    @RequestMapping(value = "/delete", method = RequestMethod.DELETE)
    String deleteEntry(String ids) {
        try {
            WarehouseVM entryBy = warehouseService.getEntryBy(Long.parseLong(ids));
            if (entryBy.getAuditStatus() != 3 && entryBy.getAuditStatus() != 7) {
                return JsonResult.failed("没有数据权限！");
            }
            warehouseService.deleteById(ids);
            return JsonResult.success(ImErrorCode.MSG_SUCCESS, MessageUtils.get(ImErrorCode.MSG_SUCCESS), null);
        } catch (ServiceException serviceException) {
            throw serviceException;
        } catch (Exception e) {
            ServiceException se = new ServiceException(ImErrorCode.MSG_FAIL, MessageUtils.get(ImErrorCode.MSG_FAIL));
            throw se;
        }
    }

    /**
     * 审核
     */
    @ApiOperation(value = "审核")
    @RequestMapping(value = "/changeReview", method = RequestMethod.POST)
    public String checkProduct(@RequestBody AuditVo auditVo, HttpServletRequest request) {
        try {
            List<String> linkList = menuService.getLinkList();
            AuditCheckController auditCheckUtil = new AuditCheckController();
            boolean b = auditCheckUtil.checkAudit(request, auditVo.getState(), linkList);
            if (!b) {
                return JsonResult.failed("没有权限进行审核操作！");
            }
            String[] split = auditVo.getIds().split(",");
            for (String sid : split) {
                WarehouseVM entryBy = warehouseService.getEntryBy(Long.parseLong(sid));
                if ((auditVo.getState() != 2 && auditVo.getState() != 3) || (entryBy.getAuditStatus() != 3 && entryBy.getAuditStatus() != 2 && entryBy.getAuditStatus() != 7)) {
                    return JsonResult.failed("没有数据权限！");
                }
            }

            String resultMess = warehouseService.checkProduct(auditVo.getIds(), auditVo.getState(), auditVo.getDismissReason());
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
    @RequestMapping(value = "/exportWarehouse", method = RequestMethod.POST)
    public String exportWarehouse(HttpServletResponse response, HttpServletRequest request) {
       /* try {
            warehouseService.explortWarehouse(response, request);
            return JsonResult.success(ImErrorCode.MSG_SUCCESS, MessageUtils.get(ImErrorCode.MSG_SUCCESS), null);
        } catch (ServiceException serviceException) {
            throw serviceException;
        } catch (Exception e) {
            ServiceException se = new ServiceException(ImErrorCode.MSG_FAIL, MessageUtils.get(ImErrorCode.MSG_FAIL));
            throw se;
        }*/

        return "";
    }

//    /**
//     * 同步MQ仓库
//     */
//    @ApiOperation(value = "同步MQ仓库")
//    @RequestMapping(value = "/synToGRW")
//    public String synToGRW(@RequestBody SynToGrVO meterialVO){
//        WarehouseVM warehouseVM = warehouseService.synToGRW(meterialVO.getData(),meterialVO.getOp());
//        return JsonResult.success(warehouseVM);
//    }
}
