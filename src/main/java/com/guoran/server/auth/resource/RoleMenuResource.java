package com.guoran.server.auth.resource;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.guoran.server.auth.service.RoleMenuService;
import com.guoran.server.auth.vmodel.RoleMenuVM;
import com.guoran.server.common.JsonResult;
import com.guoran.server.common.exception.ImErrorCode;
import com.guoran.server.common.i18n.MessageUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * 角色菜单分配
 */
@RestController
@RequestMapping("/auth/roleMenus")
public class RoleMenuResource {
    @Autowired
    ObjectMapper objectMapper;
    @Autowired
    RoleMenuService roleMenuService;


    /**
     * 新增项
     *
     * @param roleMenuVM
     * @return
     */
    @RequestMapping(method = RequestMethod.POST)
    String createEntry(@RequestBody RoleMenuVM roleMenuVM) {
        String result = null;
        JsonResult jsonResult = new JsonResult();
        try {
            roleMenuService.createEntry(roleMenuVM);
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
     * @param roleMenuVM
     * @return
     */
    @RequestMapping(method = RequestMethod.PUT)
    public String updateEntry(@RequestBody RoleMenuVM roleMenuVM) {
        String result = null;
        JsonResult jsonResult = new JsonResult();
        try {
            roleMenuService.updateEntry(roleMenuVM);
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
