package com.guoran.server.sys.resource;

import com.github.pagehelper.Page;
import com.guoran.server.auth.service.MenuService;
import com.guoran.server.common.JsonResult;
import com.guoran.server.common.exception.ServiceException;
import com.guoran.server.common.i18n.MessageUtils;
import com.guoran.server.common.resource.AuditCheckController;
import com.guoran.server.common.search.PageQuery;
import com.guoran.server.common.search.PageResult;
import com.guoran.server.common.utils.CheckInputUtil;
import com.guoran.server.common.utils.StringUtils;
import com.guoran.server.sys.service.GasMeterService;
import com.guoran.server.sys.vmodel.GasMeterVM;
import com.guoran.server.sys.vo.AuditVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

import static com.guoran.server.common.exception.ImErrorCode.MSG_FAIL;
import static com.guoran.server.common.exception.ImErrorCode.MSG_SUCCESS;

/**
 * <p>
 * rest接口
 * </p>
 *
 * @author zcq
 * @create 2020-11-27
 * @Modify By
 */
@Api(tags = {""})
@RestController
@RequestMapping("/sys/GasMeter")
public class GasMeterResource {
    @Autowired
    MenuService menuService;
    @Autowired
    private GasMeterService gasMeterService;

    /**
     * 根据id查询
     */
    @ApiOperation(value = "根据id查询数据")
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public String getEntry(@PathVariable long id) {
        String result = null;
        try {
            GasMeterVM gasMeterVM = gasMeterService.getEntryBy(id);
            result = JsonResult.success(MSG_SUCCESS, MessageUtils.get(MSG_SUCCESS), gasMeterVM);
        } catch (ServiceException serviceException) {
            throw serviceException;
        } catch (Exception e) {
            ServiceException se = new ServiceException(MSG_FAIL, MessageUtils.get(MSG_FAIL));
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
            Page<GasMeterVM> pageInfo = gasMeterService.findEntrysByPage(pageQuery);
            PageResult pageResult = new PageResult();
            pageResult.setPageNum(pageQuery.getPageNum());
            pageResult.setRows(pageInfo);
            pageResult.setTotal(pageInfo.getTotal());
            pageResult.setPages(pageInfo.getPages());
            result = JsonResult.success(MSG_SUCCESS, MessageUtils.get(MSG_SUCCESS), pageResult);
        } catch (ServiceException serviceException) {
            throw serviceException;
        } catch (Exception e) {
            ServiceException se = new ServiceException(MSG_FAIL, MessageUtils.get(MSG_FAIL));
            throw se;
        }
        return result;
    }

    /**
     * 新增
     */
    @ApiOperation(value = "新增数据")
    @RequestMapping(method = RequestMethod.POST)
    String createEntry(@RequestBody GasMeterVM gasMeterVM) {
        try {
            gasMeterService.createEntry(gasMeterVM);
            return JsonResult.success(MSG_SUCCESS, MessageUtils.get(MSG_SUCCESS), null);
        } catch (ServiceException serviceException) {
            throw serviceException;
        } catch (Exception e) {
            ServiceException se = new ServiceException(MSG_FAIL, MessageUtils.get(MSG_FAIL));
            throw se;
        }
    }

    /**
     * 修改
     */
    @ApiOperation(value = "修改数据")
    @RequestMapping(method = RequestMethod.PUT)
    public String updateEntry(@RequestBody GasMeterVM gasMeterVM) {
        try {
            GasMeterVM entryBy = gasMeterService.getEntryBy(gasMeterVM.getId());
            if (entryBy.getAuditStatus() != 3 && entryBy.getAuditStatus() != 7) {
                return JsonResult.failed("没有数据权限！");
            }
            gasMeterService.updateEntry(gasMeterVM);
            return JsonResult.success(MSG_SUCCESS, MessageUtils.get(MSG_SUCCESS), null);
        } catch (ServiceException serviceException) {
            throw serviceException;
        } catch (Exception e) {
            ServiceException se = new ServiceException(MSG_FAIL, MessageUtils.get(MSG_FAIL));
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
            GasMeterVM entryBy = gasMeterService.getEntryBy(id);
            if (entryBy.getAuditStatus() != 3 && entryBy.getAuditStatus() != 7) {
                return JsonResult.failed("没有数据权限！");
            }
            gasMeterService.deleteById(id);
            return JsonResult.success(MSG_SUCCESS, MessageUtils.get(MSG_SUCCESS), null);
        } catch (ServiceException serviceException) {
            throw serviceException;
        } catch (Exception e) {
            ServiceException se = new ServiceException(MSG_FAIL, MessageUtils.get(MSG_FAIL));
            throw se;
        }
    }

    /**
     * 审核状态
     */
    @ApiOperation(value = "审核状态")
    @RequestMapping(value = "/checkGasMeter", method = RequestMethod.POST)
    String changeReview(@RequestBody AuditVo auditVo
            , HttpServletRequest request) {
        try {
            List<String> linkList = menuService.getLinkList();
            AuditCheckController auditCheckUtil = new AuditCheckController();
            boolean b = auditCheckUtil.checkAudit(request, auditVo.getState(), linkList);
            if (!b) {
                return JsonResult.failed("没有权限进行审核操作！");
            }
            String[] split = auditVo.getIds().split(",");
            for (String sid : split) {
                GasMeterVM entryBy = gasMeterService.getEntryBy(Long.parseLong(sid));
                if ((auditVo.getState() != 2 && auditVo.getState() != 3) || (entryBy.getAuditStatus() != 3 && entryBy.getAuditStatus() != 2 && entryBy.getAuditStatus() != 7)) {
                    return JsonResult.failed("没有数据权限！");
                }
            }
            String resultMess = gasMeterService.checkGasMeter(auditVo.getIds(), auditVo.getState(), auditVo.getDismissReason());
            if (StringUtils.isNotEmpty(resultMess)) {
                return resultMess;
            }
            return JsonResult.success(MSG_SUCCESS, MessageUtils.get(MSG_SUCCESS), null);
        } catch (ServiceException serviceException) {
            throw serviceException;
        } catch (Exception e) {
            ServiceException se = new ServiceException(MSG_FAIL, MessageUtils.get(MSG_FAIL));
            throw se;
        }
    }

    /**
     * 导出
     */
    @ApiOperation(value = "导出")
    @RequestMapping(value = "/gasMeter", method = RequestMethod.POST)
    public String electricity(HttpServletResponse response, HttpServletRequest request) {
       /* try {
            gasMeterService.explort(response, request);
            return JsonResult.success(MSG_SUCCESS, MessageUtils.get(MSG_SUCCESS), null);
        } catch (ServiceException serviceException) {
            throw serviceException;
        } catch (Exception e) {
            ServiceException se = new ServiceException(MSG_FAIL, MessageUtils.get(MSG_FAIL));
            throw se;
        }*/

        return "";
    }
}
