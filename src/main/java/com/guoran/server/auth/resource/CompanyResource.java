package com.guoran.server.auth.resource;

import com.github.pagehelper.Page;
import com.guoran.server.auth.service.CompanyService;
import com.guoran.server.auth.service.PositionService;
import com.guoran.server.auth.vmodel.CompanyVM;
import com.guoran.server.auth.vmodel.PositionVM;
import com.guoran.server.auth.vo.CompanyVO;
import com.guoran.server.auth.vo.PositionVO;
import com.guoran.server.common.JsonResult;
import com.guoran.server.common.exception.ImErrorCode;
import com.guoran.server.common.exception.ServiceException;
import com.guoran.server.common.i18n.MessageUtils;
import com.guoran.server.common.search.PageQuery;
import com.guoran.server.common.search.PageResult;
import com.guoran.server.common.utils.CheckInputUtil;
import com.guoran.server.common.utils.StringUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * rest接口
 * </p>
 *
 * @author zhangjx
 * @create 2020-09-10
 * @Modify By
 */
@Api(tags = {""})
@RestController
@RequestMapping("/auth/companys")
public class CompanyResource {
    @Autowired
    private CompanyService companyService;
    @Autowired
    private PositionService positionService;

    /**
     * 根据id查询
     */
    @ApiOperation(value = "根据id查询数据")
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public String getEntry(@PathVariable long id) {
        String result = null;
        try {
            CompanyVM companyVM = companyService.getEntryBy(id);
            result = JsonResult.success(ImErrorCode.MSG_SUCCESS, MessageUtils.get(ImErrorCode.MSG_SUCCESS), companyVM);
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
            Page<CompanyVM> pageInfo = companyService.findEntrysByPage(pageQuery);
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
    public String createEntry(@RequestBody CompanyVM companyVM) {
        try {
            String message = companyService.createEntry(companyVM);
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
    public String updateEntry(@RequestBody CompanyVM companyVM) {
        try {
            String message = companyService.updateEntry(companyVM);
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
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    String deleteEntry(@PathVariable long id) {
        try {
            boolean b = companyService.deleteById(id);
            if (!b) {
                return JsonResult.failed(ImErrorCode.MSG_FAIL, "数据不存在或发生错误！删除失败！");
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
     * 新增
     */
    @ApiOperation(value = "新增数据")
    @RequestMapping(value = "/synInfos", method = RequestMethod.POST)
    public String synInfos() {
        try {
            String message = companyService.synInfos();
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
     * 获取所有公司信息
     */
    @ApiOperation(value = "获取所有公司信息")
    @RequestMapping(value = "/getAllCompany", method = RequestMethod.POST)
    public String getAllCompany() {
        String result = null;
        try {
            List<CompanyVO> companyVOS = companyService.getAllCompany();
            result = JsonResult.success(ImErrorCode.MSG_SUCCESS, MessageUtils.get(ImErrorCode.MSG_SUCCESS), companyVOS);
        } catch (ServiceException serviceException) {
            throw serviceException;
        } catch (Exception e) {
            ServiceException se = new ServiceException(ImErrorCode.MSG_FAIL, MessageUtils.get(ImErrorCode.MSG_FAIL));
            throw se;
        }
        return result;
    }

    @ApiOperation(value = "获取公司、部门")
    @RequestMapping(value = "/alltest", method = RequestMethod.POST)
    public String company() {
        List company = companyService.company();
        return JsonResult.success(company);
    }

    @ApiOperation(value = "获取公司、部门")
    @RequestMapping(value = "/alltestCopy", method = RequestMethod.POST)
    public String companyCopy() {
        List company = companyService.companyCopy();
        return JsonResult.success(company);
    }

    /**
     * 同步
     */
    @ApiOperation(value = "同步岗位信息")
    @RequestMapping(value = "/position/synInfos", method = RequestMethod.POST)
    public String synPositionInfos() {
        try {
            String message = positionService.synInfos();
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
     * 获取所有岗位
     */
    @ApiOperation(value = "获取所有岗位")
    @RequestMapping(value = "/position/getAllPostition", method = RequestMethod.POST)
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

    /**
     * 新增
     */
    @ApiOperation(value = "新增数据")
    @RequestMapping(value = "/positon/add", method = RequestMethod.POST)
    public String createEntry(@RequestBody PositionVM positionVM) {
        try {
            String message = positionService.createEntry(positionVM);
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
    @RequestMapping(value = "/positon/update", method = RequestMethod.PUT)
    public String updateEntry(@RequestBody PositionVM positionVM) {
        try {
            String message = positionService.updateEntry(positionVM);
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
    @RequestMapping(value = "/positon/{id}", method = RequestMethod.DELETE)
    String deletePositionEntry(@PathVariable long id) {
        try {
            String message = positionService.deleteById(id);
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
}
