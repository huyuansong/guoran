package com.guoran.server.auth.resource;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.pagehelper.Page;
import com.guoran.server.auth.service.RoleMenuService;
import com.guoran.server.auth.service.RoleService;
import com.guoran.server.auth.vmodel.RoleMenuVM;
import com.guoran.server.auth.vmodel.RoleVM;
import com.guoran.server.common.JsonResult;
import com.guoran.server.common.exception.ImErrorCode;
import com.guoran.server.common.i18n.MessageUtils;
import com.guoran.server.common.search.PageQuery;
import com.guoran.server.common.search.PageResult;
import com.guoran.server.common.utils.CheckInputUtil;
import com.guoran.server.common.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/auth/roles")
public class RoleResource {
    @Autowired
    ObjectMapper objectMapper;
    @Autowired
    RoleService roleService;
    @Autowired
    RoleMenuService roleMenuService;

    /**
     * 根据类型Id获取实体
     *
     * @param id
     * @return
     */
    @PreAuthorize("true")
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public String getEntry(@PathVariable long id) {
        String result = null;
        JsonResult jsonResult = new JsonResult();
        try {
            RoleVM roleVM = roleService.getEntryBy(id);
            jsonResult.setIsSuccess(true);
            String message = "获取成功。";
            jsonResult.setMessage(message);
            jsonResult.setData(roleVM);
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
     * 分页
     *
     * @param pageQuery
     * @return
     */
    @RequestMapping(value = "/page", method = RequestMethod.POST)
    public String getEntrysByPage(@RequestBody PageQuery pageQuery) {
        String result = null;
        JsonResult jsonResult = new JsonResult();
        try {
            CheckInputUtil checkInputUtil = new CheckInputUtil();
            if (checkInputUtil.chikcInput(pageQuery)) {
                return JsonResult.failed("请勿输入特殊字符");
            }
            Page<RoleVM> pageInfo = roleService.findRolesByPage(pageQuery);
            PageResult pageResult = new PageResult();
            pageResult.setPageNum(pageQuery.getPageNum());
            pageResult.setRows(pageInfo);
            pageResult.setTotal(pageInfo.getTotal());
            pageResult.setPages(pageInfo.getPages());
            jsonResult.setIsSuccess(true);
            jsonResult.setData(pageResult);
            result = objectMapper.writeValueAsString(jsonResult);
        } catch (Exception e) {
            jsonResult.setIsSuccess(false);
            jsonResult.setMessage(MessageUtils.get(ImErrorCode.MSG_SUCCESS));
        }
        try {
            result = objectMapper.writeValueAsString(jsonResult);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 角色下拉框
     *
     * @return
     */
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public String getEntrysByList() {
        String result = null;
        JsonResult jsonResult = new JsonResult();
        try {
            List<RoleVM> roleVMList = roleService.findAllRoles();
            jsonResult.setIsSuccess(true);
            jsonResult.setData(roleVMList);
            result = objectMapper.writeValueAsString(jsonResult);
        } catch (Exception e) {
            jsonResult.setIsSuccess(false);
            jsonResult.setMessage(e.getMessage());
        }
        try {
            result = objectMapper.writeValueAsString(jsonResult);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 新增项
     *
     * @param roleVM
     * @return
     */
    @RequestMapping(method = RequestMethod.POST)
    String createEntry(@RequestBody RoleVM roleVM) {
        String result = null;
        JsonResult jsonResult = new JsonResult();
        try {
            String message = roleService.createEntry(roleVM);
            if (StringUtils.isNotEmpty(message)) {
                return message;
            }

            jsonResult.setIsSuccess(true);
            jsonResult.setMessage(MessageUtils.get(ImErrorCode.MSG_SUCCESS));
            result = objectMapper.writeValueAsString(jsonResult);
        } catch (Exception e) {
            jsonResult.setIsSuccess(false);
            jsonResult.setMessage(e.getMessage());
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
     * @param roleVM
     * @return
     */
    @RequestMapping(method = RequestMethod.PUT)
    public String updateEntry(@RequestBody RoleVM roleVM) {
        String result = null;
        JsonResult jsonResult = new JsonResult();
        try {
            String message = roleService.updateEntry(roleVM);
            if (StringUtils.isNotEmpty(message)) {
                return message;
            }
            jsonResult.setIsSuccess(true);
            jsonResult.setMessage("修改成功.");
        } catch (Exception e) {
            jsonResult.setIsSuccess(false);
            jsonResult.setMessage(e.getMessage());
        }
        try {
            result = objectMapper.writeValueAsString(jsonResult);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 根据id删除项
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    String deleteEntry(@PathVariable long id) {
        String result = null;
        JsonResult jsonResult = new JsonResult();
        try {
            String resultStr = roleService.deleteById(id);
            if (StringUtils.isNotEmpty(resultStr)) {
                return resultStr;
            }
            jsonResult.setIsSuccess(true);
            jsonResult.setMessage("删除成功.");
        } catch (Exception e) {
            jsonResult.setIsSuccess(false);
            jsonResult.setMessage(e.getMessage());
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
    @RequestMapping(value = "/roleMenu", method = RequestMethod.PUT)
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
