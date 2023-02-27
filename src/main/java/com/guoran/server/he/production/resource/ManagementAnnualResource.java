package com.guoran.server.he.production.resource;

import com.guoran.server.common.JsonResult;
import com.guoran.server.common.exception.ImErrorCode;
import com.guoran.server.common.exception.ServiceException;
import com.guoran.server.common.i18n.MessageUtils;
import com.guoran.server.he.production.service.ManagementAnnualService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @time 2023/2/2714:15
 * @outhor zhou
 */
@Api(tags = {"年度生产计划表"})
@RestController
@RequestMapping("/production/managementAnnuals")
public class ManagementAnnualResource {
    @Autowired
    private ManagementAnnualService managementAnnualService;


    /**
     * 导出
     */
    @ApiOperation(value = "导出")
    @RequestMapping(value = "/electricity", method = RequestMethod.POST)
    public String electricity(@RequestBody Long[] ids, HttpServletResponse response, HttpServletRequest request) {
        try {
            managementAnnualService.explort(ids, response, request);
            return JsonResult.success(ImErrorCode.MSG_SUCCESS, MessageUtils.get(ImErrorCode.MSG_SUCCESS), null);
        } catch (ServiceException serviceException) {
            throw serviceException;
        } catch (Exception e) {
            ServiceException se = new ServiceException(ImErrorCode.MSG_FAIL, MessageUtils.get(ImErrorCode.MSG_FAIL));
            throw se;
        }
    }
}
