package com.guoran.server.auth.resource;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.guoran.server.auth.model.MenuEntity;
import com.guoran.server.auth.service.MenuService;
import com.guoran.server.auth.vmodel.MenuVM;
import com.guoran.server.auth.vmodel.NewMenuVM;
import com.guoran.server.common.JsonResult;
import com.guoran.server.common.exception.ImErrorCode;
import com.guoran.server.common.i18n.MessageUtils;
import com.guoran.server.common.search.FilterGroup;
import com.guoran.server.common.search.FilterRule;
import com.guoran.server.common.search.PageQuery;
import com.guoran.server.common.search.PageResult;
import com.guoran.server.common.utils.CheckInputUtil;
import com.guoran.server.common.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/auth/menus")
public class MenuResource {
    @Autowired
    ObjectMapper objectMapper;
    @Autowired
    MenuService menuService;

    /**
     * 获取全量的菜单
     *
     * @return
     */
    @PreAuthorize("true")
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public String getMenuEntity() {
        String result = null;
        JsonResult jsonResult = new JsonResult();
        try {
            List<NewMenuVM> menuVMList = menuService.getMenuEntity();
            jsonResult.setIsSuccess(true);
            String message = "获取成功。";
            jsonResult.setMessage(message);
            jsonResult.setData(menuVMList);
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
     * 根据角色获取菜单
     *
     * @return
     */
    @PreAuthorize("true")
    @RequestMapping(value = "/rolemenuList/{roleId}", method = RequestMethod.GET)
    public String getUserMenuEntity(@PathVariable long roleId) {
        String result = null;
        JsonResult jsonResult = new JsonResult();
        try {
            //根据用户名获取用户信息。
            List<NewMenuVM> menuVMList = menuService.getMenusByRole(roleId);
            jsonResult.setIsSuccess(true);
            String message = "获取成功。";
            jsonResult.setMessage(message);
            jsonResult.setData(menuVMList);
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
     * 获取用户对应角色的菜单
     *
     * @return
     */
    @PreAuthorize("true")
    @RequestMapping(value = "/userRoleList", method = RequestMethod.GET)
    public String getUserRoleMenuEntity() {
        String result = null;
        JsonResult jsonResult = new JsonResult();
        try {
            List<MenuVM> menuVMList = menuService.getUserRoleMenuEntity();
            jsonResult.setIsSuccess(true);
            String message = "获取成功。";
            jsonResult.setMessage(message);
            jsonResult.setData(menuVMList);
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
            MenuVM menuVM = menuService.getEntryBy(id);
            jsonResult.setIsSuccess(true);
            String message = "获取成功。";
            jsonResult.setMessage(message);
            jsonResult.setData(menuVM);
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
            PageHelper.startPage(pageQuery.getPageNum(), pageQuery.getPageSize(), pageQuery.getOrderBy());
            FilterGroup filterGroup = pageQuery.getWhere();
            List<FilterRule> filterRuleList = filterGroup.getRules();
            StringBuilder where = new StringBuilder();
            //查询条件
            for (FilterRule rule : filterGroup.getRules()) {
                where.append(rule.getField());
                where.append(" " + rule.getOp() + "'%" + rule.getValue() + "%'");
            }
            Page<MenuVM> pageInfo = menuService.findMenusByPage(where.toString());
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
     * @param menuVM
     * @return
     */
    @RequestMapping(method = RequestMethod.POST)
    String createEntry(@RequestBody MenuVM menuVM) {
        String result = null;
        JsonResult jsonResult = new JsonResult();
        try {
            String messgae = menuService.createEntry(menuVM);
            if (StringUtils.isNotEmpty(messgae)) {
                return messgae;
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
     * @param menuVM
     * @return
     */
    @RequestMapping(method = RequestMethod.PUT)
    public String updateEntry(@RequestBody MenuVM menuVM) {
        String result = null;
        JsonResult jsonResult = new JsonResult();
        try {
            menuService.updateEntry(menuVM);
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
            menuService.deleteById(id);
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
     * 获取全量菜单，无树形结构
     */
    @RequestMapping(value = "/getAllMenu", method = RequestMethod.POST)
    String getAllMenu() {
        try {
            List<MenuEntity> menuVMS = menuService.getAllMenu();
            return JsonResult.success(ImErrorCode.MSG_SUCCESS, MessageUtils.get(ImErrorCode.MSG_SUCCESS), menuVMS);
        } catch (Exception e) {
            return JsonResult.failed(ImErrorCode.MSG_FAIL, MessageUtils.get(ImErrorCode.MSG_FAIL));
        }
    }
}
