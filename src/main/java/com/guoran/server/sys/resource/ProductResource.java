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
import com.guoran.server.common.utils.StringUtils;
import com.guoran.server.sys.service.ProductService;
import com.guoran.server.sys.vmodel.ProductVM;
import com.guoran.server.sys.vo.AuditVo;
import com.guoran.server.sys.vo.SynToGrVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * <p>
 * 商品信息表 rest接口
 * </p>
 *
 * @author zhangjx
 * @create 2020-08-20
 * @Modify By
 */
@Api(tags = {"商品信息表"})
@RestController
@RequestMapping("/sys/products")
public class ProductResource {
    @Autowired
    MenuService menuService;
    @Autowired
    private ProductService productService;

    /**
     * 根据id查询
     */
    @ApiOperation(value = "根据id查询数据")
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public String getEntry(@PathVariable long id) {
        String result = null;
        try {
            ProductVM productVM = productService.getEntryBy(id);
            result = JsonResult.success(ImErrorCode.MSG_SUCCESS, MessageUtils.get(ImErrorCode.MSG_SUCCESS), productVM);
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
            Page<ProductVM> pageInfo = productService.findEntrysByPage(pageQuery);
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
    String createEntry(@RequestBody ProductVM productVM) {
        try {
            String resultMess = productService.createEntry(productVM);
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
    public String updateEntry(@RequestBody ProductVM productVM) {
        try {
            ProductVM entryBy = productService.getEntryBy(productVM.getId());
            if (entryBy.getAuditStatus() != 3 && entryBy.getAuditStatus() != 7) {
                return JsonResult.failed("没有数据权限！");
            }
            String resultMess = productService.updateEntry(productVM);
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
     * 删除
     */
    @ApiOperation(value = "删除数据")
    @RequestMapping(value = "/{ids}", method = RequestMethod.DELETE)
    String deleteEntry(@PathVariable String ids) {
        try {
            ProductVM entryBy = productService.getEntryBy(Long.parseLong(ids));
            if (entryBy.getAuditStatus() != 3 && entryBy.getAuditStatus() != 7) {
                return JsonResult.failed("没有数据权限！");
            }
            productService.deleteById(ids);
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
    @ApiOperation(value = "导出数据")
    @RequestMapping(value = "/exportProduct", method = RequestMethod.POST)
    public String exportProduct(@RequestParam(value = "ids") String ids, HttpServletResponse response, HttpServletRequest request) {
/*        try {
            productService.explort(ids, response, request);
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
     * 审核
     */
    @ApiOperation(value = "审核")
    @RequestMapping(value = "/checkProduct", method = RequestMethod.POST)
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
                ProductVM entryBy = productService.getEntryBy(Long.parseLong(sid));
                if ((auditVo.getState() != 2 && auditVo.getState() != 3) || (entryBy.getAuditStatus() != 3 && entryBy.getAuditStatus() != 2 && entryBy.getAuditStatus() != 7)) {
                    return JsonResult.failed("没有数据权限！");
                }
            }
            String resultMess = productService.checkProduct(auditVo.getIds(), auditVo.getState(), auditVo.getDismissReason());
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
     * 获取所有商品
     */
    @ApiOperation(value = "获取所有商品")
    @RequestMapping(value = "/getAllProduct", method = RequestMethod.POST)
    public String getAllProduct() {
        try {
            List<ProductVM> data = productService.getAllProduct();
            return JsonResult.success(ImErrorCode.MSG_SUCCESS, MessageUtils.get(ImErrorCode.MSG_SUCCESS), data);
        } catch (ServiceException serviceException) {
            throw serviceException;
        } catch (Exception e) {
            ServiceException se = new ServiceException(ImErrorCode.MSG_FAIL, MessageUtils.get(ImErrorCode.MSG_FAIL));
            throw se;
        }
    }

    /**
     * 产品规格接口
     */
    @ApiOperation(value = "获取产品规格")
    @RequestMapping(value = "/getSpecifications", method = RequestMethod.POST)
    public String getSpecifications() {
        try {
            List<String> data = productService.getSpecifications();
            return JsonResult.success(ImErrorCode.MSG_SUCCESS, MessageUtils.get(ImErrorCode.MSG_SUCCESS), data);
        } catch (ServiceException serviceException) {
            throw serviceException;
        } catch (Exception e) {
            ServiceException se = new ServiceException(ImErrorCode.MSG_FAIL, MessageUtils.get(ImErrorCode.MSG_FAIL));
            throw se;
        }
    }

    /**
     * 同步MQ物料分类
     */
    @ApiOperation(value = "同步mq物料分类")
    @RequestMapping(value = "/synToGRMC")
    public String synToGRMC(@RequestBody SynToGrVO meterialVO) {
        String s = productService.synToGRMC(meterialVO.getData(), meterialVO.getOp());
        return JsonResult.success(s);
    }

    /**
     * 同步MQ物料
     */
    @ApiOperation(value = "同步mq物料")
    @RequestMapping(value = "/synToGRM")
    public String synToGRM(@RequestBody SynToGrVO meterialVO) {
        String s = productService.synToGRM(meterialVO.getData(), meterialVO.getOp());
        return JsonResult.success(s);
    }
}
