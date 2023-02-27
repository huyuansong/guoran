package com.guoran.server.weChat.resource;

import com.github.pagehelper.Page;
import com.guoran.server.common.JsonResult;
import com.guoran.server.common.exception.ImErrorCode;
import com.guoran.server.common.exception.ServiceException;
import com.guoran.server.common.i18n.MessageUtils;
import com.guoran.server.common.search.PageQuery;
import com.guoran.server.common.search.PageResult;
import com.guoran.server.common.utils.StringUtils;
import com.guoran.server.weChat.service.AccessTokenService;
import com.guoran.server.weChat.vmodel.AccessTokenVM;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * <p>
 * 企业微信推荐验证码及token临时存储表 rest接口
 * </p>
 *
 * @author zhangjx
 * @create 2020-09-16
 * @Modify By
 */
@Api(tags = {"企业微信推送验证码及token临时存储表"})
@RestController
@RequestMapping("/weChat/accessTokens")
public class AccessTokenResource {
    @Autowired
    private AccessTokenService accessTokenService;


    /**
     * 输入员工账号
     * 获取验证码
     */
    @RequestMapping(value = "/send/{userId}", method = RequestMethod.GET)
    public String send(HttpServletResponse response, HttpServletRequest request, @PathVariable String userId) {
        String result = null;
        try {

            //发送信息
            String message = accessTokenService.send(userId);
            if (StringUtils.isNotEmpty(message)) {
                return JsonResult.failed(message);
            }
            result = JsonResult.success(ImErrorCode.MSG_SUCCESS, MessageUtils.get(ImErrorCode.MSG_SUCCESS));

        } catch (ServiceException serviceException) {
            throw serviceException;
        } catch (Exception e) {
            ServiceException se = new ServiceException(ImErrorCode.MSG_FAIL, MessageUtils.get(ImErrorCode.MSG_FAIL));
            throw se;
        }
        return result;
    }


    /**
     * 根据id查询
     */
    @ApiOperation(value = "根据id查询数据")
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public String getEntry(@PathVariable long id) {
        String result = null;
        try {
            AccessTokenVM accessTokenVM = accessTokenService.getEntryBy(id);
            result = JsonResult.success(ImErrorCode.MSG_SUCCESS, MessageUtils.get(ImErrorCode.MSG_SUCCESS), accessTokenVM);
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
            Page<AccessTokenVM> pageInfo = accessTokenService.findEntrysByPage(pageQuery);
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
    public String createEntry(@RequestBody AccessTokenVM accessTokenVM) {
        try {
            String message = accessTokenService.createEntry(accessTokenVM);
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
    public String updateEntry(@RequestBody AccessTokenVM accessTokenVM) {
        try {
            String message = accessTokenService.updateEntry(accessTokenVM);
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
            accessTokenService.deleteById(id);
            return JsonResult.success(ImErrorCode.MSG_SUCCESS, MessageUtils.get(ImErrorCode.MSG_SUCCESS), null);
        } catch (ServiceException serviceException) {
            throw serviceException;
        } catch (Exception e) {
            ServiceException se = new ServiceException(ImErrorCode.MSG_FAIL, MessageUtils.get(ImErrorCode.MSG_FAIL));
            throw se;
        }
    }
}
