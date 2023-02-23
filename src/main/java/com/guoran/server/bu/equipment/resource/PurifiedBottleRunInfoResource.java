package com.guoran.server.bu.equipment.resource;


import com.github.pagehelper.Page;
import com.guoran.server.bu.equipment.service.PurifiedBottleRunInfoService;
import com.guoran.server.bu.equipment.vmodel.PurifiedBottleRunInfoVM;
import com.guoran.server.common.Result;
import com.guoran.server.common.exception.ImErrorCode;
import com.guoran.server.common.exception.ServiceException;
import com.guoran.server.common.i18n.MessageUtils;
import com.guoran.server.common.search.PageQuery;
import com.guoran.server.common.search.PageResult;
import com.guoran.server.common.utils.CheckInputUtil;
import com.guoran.server.common.utils.StringUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * w
 * 2023/1/26  19:07
 * 01-pro
 **/
@Api(tags = {"设备管理-设备运行记录-纯净水车间-吹瓶机运行记录"})
@RestController
@RequestMapping("/equipment/PurifiedBottleRunInfos")
public class PurifiedBottleRunInfoResource {
    @Resource
    private PurifiedBottleRunInfoService purifiedBottleRunInfoService;

    /**
     * 根据id查询
     */
    @ApiOperation(value = "根据id查询数据")
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public String getEntry(@PathVariable long id) {
        String result = null;
        try {
            PurifiedBottleRunInfoVM purifiedBottleRunInfoVM = purifiedBottleRunInfoService.getEntryBy(id);
            result = Result.success(ImErrorCode.MSG_SUCCESS, MessageUtils.get(ImErrorCode.MSG_SUCCESS), purifiedBottleRunInfoVM);
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
                return Result.failed("请勿输入特殊字符");
            }
            Page<PurifiedBottleRunInfoVM> pageInfo = purifiedBottleRunInfoService.findEntrysByPage(pageQuery);
            PageResult pageResult = new PageResult();
            pageResult.setPageNum(pageQuery.getPageNum());
            pageResult.setRows(pageInfo);
            pageResult.setTotal(pageInfo.getTotal());
            pageResult.setPages(pageInfo.getPages());
            result = Result.success(ImErrorCode.MSG_SUCCESS, MessageUtils.get(ImErrorCode.MSG_SUCCESS), pageResult);
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
    String createEntry(@RequestBody PurifiedBottleRunInfoVM purifiedBottleRunInfoVM) {
        try {
            String message = purifiedBottleRunInfoService.createEntry(purifiedBottleRunInfoVM);
            if (StringUtils.isNotEmpty(message)) {
                return message;
            }
            return Result.success(ImErrorCode.MSG_SUCCESS, MessageUtils.get(ImErrorCode.MSG_SUCCESS), null);
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
    public String updateEntry(@RequestBody PurifiedBottleRunInfoVM purifiedBottleRunInfoVM) {
        try {
            String message = purifiedBottleRunInfoService.updateEntry(purifiedBottleRunInfoVM);
            if (StringUtils.isNotEmpty(message)) {
                return message;
            }
            return Result.success(ImErrorCode.MSG_SUCCESS, MessageUtils.get(ImErrorCode.MSG_SUCCESS), null);
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
    @RequestMapping(value = "/delete", method = RequestMethod.DELETE)
    String deleteEntry(String ids) {
        try {
            purifiedBottleRunInfoService.deleteById(ids);
            return Result.success(ImErrorCode.MSG_SUCCESS, MessageUtils.get(ImErrorCode.MSG_SUCCESS), null);
        } catch (ServiceException serviceException) {
            throw serviceException;
        } catch (Exception e) {
            ServiceException se = new ServiceException(ImErrorCode.MSG_FAIL, MessageUtils.get(ImErrorCode.MSG_FAIL));
            throw se;
        }
    }
}

