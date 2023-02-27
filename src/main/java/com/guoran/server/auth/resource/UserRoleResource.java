package com.guoran.server.auth.resource;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.guoran.server.auth.service.UserRoleService;
import com.guoran.server.auth.vmodel.UserRoleVM;
import com.guoran.server.common.JsonResult;
import com.guoran.server.common.exception.ImErrorCode;
import com.guoran.server.common.i18n.MessageUtils;
import com.guoran.server.common.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/auth/userRoles")
public class UserRoleResource {
    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    UserRoleService userRoleService;

    /**
     * 根据用户ID返回对应角色信息
     *
     * @param userId
     * @return
     */
    @PreAuthorize("true")
    @RequestMapping(value = "/list/{userId}", method = RequestMethod.GET)
    public String findEntityByUserId(@PathVariable long userId) {
        {
            String result = null;
            JsonResult jsonResult = new JsonResult();
            try {
                List<UserRoleVM> userRoleVMList = userRoleService.findEntityByUserId(userId);
                jsonResult.setIsSuccess(true);
                String message = "获取成功。";
                jsonResult.setMessage(message);
                jsonResult.setData(userRoleVMList);
                result = objectMapper.writeValueAsString(jsonResult);
            } catch (Exception e) {
                jsonResult.setIsSuccess(false);
                jsonResult.setMessage(MessageUtils.get(ImErrorCode.MSG_FAIL));
            }
            try {
                result = objectMapper.writeValueAsString(jsonResult);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
            return result;
        }
    }

    /**
     * 新增项
     *
     * @param userRoleVM
     * @return
     */
    @RequestMapping(method = RequestMethod.POST)
    String createEntry(@RequestBody UserRoleVM userRoleVM) {
        String result = null;
        JsonResult jsonResult = new JsonResult();
        try {
            String message = userRoleService.createEntry(userRoleVM);
            if (StringUtils.isNotEmpty(message)) {
                return message;
            }
            jsonResult.setIsSuccess(true);
            jsonResult.setMessage(MessageUtils.get(ImErrorCode.MSG_SUCCESS));
            result = objectMapper.writeValueAsString(jsonResult);
        } catch (Exception e) {
            jsonResult.setIsSuccess(false);
            jsonResult.setMessage(MessageUtils.get(ImErrorCode.MSG_FAIL));
        }
        try {
            result = objectMapper.writeValueAsString(jsonResult);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 修改
     *
     * @param userRoleVM
     * @return
     */
    @RequestMapping(method = RequestMethod.PUT)
    public String updateEntry(@RequestBody UserRoleVM userRoleVM) {
        String result = null;
        JsonResult jsonResult = new JsonResult();
        try {
            userRoleService.updateEntry(userRoleVM);
            jsonResult.setIsSuccess(true);
            jsonResult.setMessage("修改成功.");
        } catch (Exception e) {
            jsonResult.setIsSuccess(false);
            jsonResult.setMessage(MessageUtils.get(ImErrorCode.MSG_FAIL));
        }
        try {
            result = objectMapper.writeValueAsString(jsonResult);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return result;
    }
}
