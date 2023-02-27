package com.guoran.server.sys.resource;

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
import com.guoran.server.sys.service.ElectricityMeterService;
import com.guoran.server.sys.vmodel.ElectricityMeterVM;
import com.guoran.server.sys.vo.AuditVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * <p>
 * 电表信息 rest接口
 * </p>
 *
 * @author zhangjx
 * @create 2020-08-20
 * @Modify By
 */
@Api(tags = {"电表信息"})
@RestController
@RequestMapping("/sys/electricityMeters")
public class ElectricityMeterResource {
    @Autowired
    MenuService menuService;
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
            ElectricityMeterVM electricityMeterVM = electricityMeterService.getEntryBy(id);
            result = JsonResult.success(ImErrorCode.MSG_SUCCESS, MessageUtils.get(ImErrorCode.MSG_SUCCESS), electricityMeterVM);
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

    /**
     * 新增
     */
    @ApiOperation(value = "新增数据")
    @RequestMapping(method = RequestMethod.POST)
    String createEntry(@RequestBody ElectricityMeterVM electricityMeterVM) {
        try {
            electricityMeterService.createEntry(electricityMeterVM);
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
    public String updateEntry(@RequestBody ElectricityMeterVM electricityMeterVM) {
        try {
            ElectricityMeterVM entryBy = electricityMeterService.getEntryBy(electricityMeterVM.getId());
            if (entryBy.getAuditStatus() != 3 && entryBy.getAuditStatus() != 7) {
                return JsonResult.failed("没有数据权限！");
            }
            electricityMeterService.updateEntry(electricityMeterVM);
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
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    String deleteEntry(@PathVariable long id) {
        try {
            ElectricityMeterVM entryBy = electricityMeterService.getEntryBy(id);
            if (entryBy.getAuditStatus() != 3 && entryBy.getAuditStatus() != 7) {
                return JsonResult.failed("没有数据权限！");
            }
            electricityMeterService.deleteById(id);

            return JsonResult.success(ImErrorCode.MSG_SUCCESS, MessageUtils.get(ImErrorCode.MSG_SUCCESS), null);
        } catch (ServiceException serviceException) {
            throw serviceException;
        } catch (Exception e) {
            ServiceException se = new ServiceException(ImErrorCode.MSG_FAIL, MessageUtils.get(ImErrorCode.MSG_FAIL));
            throw se;
        }
    }

    /**
     * 多项删除
     */
    @ApiOperation(value = "删除数据")
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
    String deleteEntry(@PathVariable("id") String id) {
        try {
            electricityMeterService.deleteByIds(id);
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
//    @ApiOperation(value = "导出")
//    @RequestMapping(value = "/electricity", method = RequestMethod.POST)
//    public String electricity(@RequestParam("ids") String ids, HttpServletResponse response, HttpServletRequest request) {
//        try {
//            electricityMeterService.explort(ids, response, request);
//            return JsonResult.success(ImErrorCode.MSG_SUCCESS, MessageUtils.get(ImErrorCode.MSG_SUCCESS), null);
//        } catch (ServiceException serviceException) {
//            throw serviceException;
//        } catch (Exception e) {
//            ServiceException se = new ServiceException(ImErrorCode.MSG_FAIL, MessageUtils.get(ImErrorCode.MSG_FAIL));
//            throw se;
//        }
//    }

    /**
     * 审核状态
     */
    @ApiOperation(value = "审核状态")
    @RequestMapping(value = "/checkelecticity", method = RequestMethod.POST)
    String changeReview(@RequestBody AuditVo auditVo, HttpServletRequest request) {
        try {
            List<String> linkList = menuService.getLinkList();
            AuditCheckController auditCheckUtil = new AuditCheckController();
            boolean b = auditCheckUtil.checkAudit(request, auditVo.getState(), linkList);
            if (!b) {
                return JsonResult.failed("没有权限进行审核操作！");
            }
            String[] split = auditVo.getIds().split(",");
            for (String sid : split) {
                ElectricityMeterVM entryBy = electricityMeterService.getEntryBy(Long.parseLong(sid));
                if ((auditVo.getState() != 2 && auditVo.getState() != 3) || (entryBy.getAuditStatus() != 3 && entryBy.getAuditStatus() != 2 && entryBy.getAuditStatus() != 7)) {
                    return JsonResult.failed("没有数据权限！");
                }
            }

//            String resultMess = electricityMeterService.checkelecticity(auditVo.getIds(), auditVo.getState(), auditVo.getDismissReason());
//            if (StringUtils.isNotEmpty(resultMess)) {
//                return resultMess;
//            }
            return JsonResult.success(ImErrorCode.MSG_SUCCESS, MessageUtils.get(ImErrorCode.MSG_SUCCESS), null);
        } catch (ServiceException serviceException) {
            throw serviceException;
        } catch (Exception e) {
            ServiceException se = new ServiceException(ImErrorCode.MSG_FAIL, MessageUtils.get(ImErrorCode.MSG_FAIL));
            throw se;
        }
    }


}
