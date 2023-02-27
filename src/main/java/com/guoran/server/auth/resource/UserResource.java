package com.guoran.server.auth.resource;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.guoran.server.auth.b.repository.UserRepository;
import com.guoran.server.auth.model.UserEntity;
import com.guoran.server.auth.service.RoleService;
import com.guoran.server.auth.service.UserRoleService;
import com.guoran.server.auth.service.UserService;
import com.guoran.server.auth.vmodel.RoleVM;
import com.guoran.server.auth.vmodel.UserInfoVM;
import com.guoran.server.auth.vmodel.UserRoleVM;
import com.guoran.server.auth.vmodel.UserVM;
import com.guoran.server.auth.vo.UserEmployeeVO;
import com.guoran.server.common.JsonResult;
import com.guoran.server.common.exception.ImErrorCode;
import com.guoran.server.common.exception.ServiceException;
import com.guoran.server.common.i18n.MessageUtils;
import com.guoran.server.common.search.FilterGroup;
import com.guoran.server.common.search.FilterRule;
import com.guoran.server.common.search.PageQuery;
import com.guoran.server.common.search.PageResult;
import com.guoran.server.common.utils.AesEncryptUtil;
import com.guoran.server.common.utils.CheckInputUtil;
import com.guoran.server.common.utils.StringUtils;
import com.guoran.server.sys.service.EmployeeService;
import com.guoran.server.sys.vmodel.EmployeeVM;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RestController
@RequestMapping("/auth/users")
public class UserResource {
    @Autowired
    ObjectMapper objectMapper;
    @Autowired
    UserService userService;
    @Autowired
    UserRepository userRepository;
    @Autowired
    UserRoleService userRoleService;
    @Autowired
    EmployeeService employeeService;
    @Autowired
    RoleService roleService;
    // @Value("${server.url}")
    private String serverUrl;
    /*    @Value("${jwt.header}")*/
    private String tokenHeader;

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
            UserVM userVM = userService.getEntryBy(id);
            jsonResult.setIsSuccess(true);
            String message = "获取成功。";
            jsonResult.setMessage(message);
            jsonResult.setData(userVM);
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

    @RequestMapping(value = "/currentUserInfo", method = RequestMethod.GET)
    public String getLoginUserInfo() {
        String result = null;
        JsonResult jsonResult = new JsonResult();
        try {
            UserVM userVM = userService.getLoginUserInfo();
            jsonResult.setIsSuccess(true);
            String message = "获取成功。";
            jsonResult.setMessage(message);
            jsonResult.setData(userVM);
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

    @RequestMapping(value = "/getCurrentUserInfo", method = RequestMethod.GET)
    public String getCurrentUserInfo() {
        String result = null;
        JsonResult jsonResult = new JsonResult();
        try {
            UserEmployeeVO userEmployeeVO = userService.getCurrentUserInfo();
            jsonResult.setIsSuccess(true);
            String message = "获取成功。";
            jsonResult.setMessage(message);
            jsonResult.setData(userEmployeeVO);
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
            PageHelper.startPage(pageQuery.getPageNum(), pageQuery.getPageSize(), pageQuery.getOrderBy());
            FilterGroup filterGroup = pageQuery.getWhere();
//            List<FilterRule> filterRuleList =filterGroup.getRules();
            StringBuilder where = new StringBuilder();
            //查询条件
            for (FilterRule rule : filterGroup.getRules()) {
                where.append(" and " + rule.getField());
                where.append(" " + rule.getOp() + "'%" + rule.getValue() + "%'");
            }
            Page<UserVM> pageInfo = userService.findUsersByPage(where.toString());
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
     * 新增项
     *
     * @param userVM
     * @return
     */
    @RequestMapping(method = RequestMethod.POST)
    String createEntry(@RequestBody UserVM userVM) {
        String result = null;
        JsonResult jsonResult = new JsonResult();
        try {
            //判断用户名是否存在
            UserVM dto = userService.getEntryByName(userVM.getName());
            if (dto != null) {
                return JsonResult.failed("用户名已经存在！");
            }
            String jobNo = userService.getEntryByJobNo(userVM.getUserCode());
            if (StringUtils.isNotEmpty(jobNo)) {
                return jobNo;
            }
            Long id = userService.createEntry(userVM);
            if (id > 0) {
                UserRoleVM userRoleVM = new UserRoleVM();
                userRoleVM.setUserId(id);
                userRoleVM.setUserName(userVM.getName());
                userRoleVM.setRoleVMList(userVM.getRoleVMList());
                String message = userRoleService.createEntry(userRoleVM);
                if (StringUtils.isNotEmpty(message)) {
                    return message;
                }
                //修改用户绑定登录账号
                EmployeeVM employeeVM = new EmployeeVM();
                employeeVM.setUserCode(userVM.getUserCode());
                employeeVM.setBinding(1);
                boolean flag = employeeService.updateBinding(employeeVM);
                if (!flag) {
                    return JsonResult.failed("创建用户修改用户绑定登录账号失败！");
                }
            }
            jsonResult.setIsSuccess(true);
            jsonResult.setData(id);
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
     * @param userVM
     * @return
     */
    @RequestMapping(method = RequestMethod.PUT)
    public String updateEntry(@RequestBody UserVM userVM) {
        String result = null;
        JsonResult jsonResult = new JsonResult();
        try {

            if (userVM.getId() != 1) {
                userService.updateEntry(userVM);
                jsonResult.setIsSuccess(true);
                jsonResult.setMessage("修改成功.");
            } else {
                if (userVM.getName().equals("admin")) {
                    userService.updateEntry(userVM);
                    jsonResult.setIsSuccess(true);
                    jsonResult.setMessage("修改成功.");
                } else {
                    jsonResult.setIsSuccess(false);
                    jsonResult.setMessage("不能修改超级管理员admin的用户名.");
                }
            }
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
            if (id != 1) {
                userService.deleteById(id);
                jsonResult.setIsSuccess(true);
                jsonResult.setMessage("删除成功.");
            } else {
                jsonResult.setIsSuccess(false);
                jsonResult.setMessage("删除失败.");
            }
        } catch (Exception e) {
            jsonResult.setIsSuccess(false);
//            jsonResult.setMessage(MessageUtils.get(ImErrorCode.MSG_FAIL));
            jsonResult.setMessage("删除失败.");
        }
        try {
            result = objectMapper.writeValueAsString(jsonResult);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 初始化密码
     *
     * @param userVM
     * @return
     */
    @RequestMapping(value = "/initializePassWord", method = RequestMethod.PUT)
    public String updateInitializePassWord(@RequestBody UserVM userVM) {
        String result = null;
        JsonResult jsonResult = new JsonResult();
        try {
            userService.updateInitializePassWord(userVM.getId());
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


    /**
     * 修改密码
     *
     * @param userVM
     * @return
     */
    @RequestMapping(value = "/updatePassWord", method = RequestMethod.PUT)
    public String updatePassWord(@RequestBody UserVM userVM) {
        String result = null;
        JsonResult jsonResult = new JsonResult();
        try {
            String message = userService.updatePassWord(userVM);
            if (StringUtils.isNotEmpty(message)) {
                return message;
            }
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

    /**
     * 重置密码
     *
     * @param userVM
     * @return
     */
    @RequestMapping(value = "/resetPassword", method = RequestMethod.POST)
    public String resetPassword(@RequestBody UserVM userVM) {
        String result = null;
        JsonResult jsonResult = new JsonResult();
        try {
            //解密密码
            String newPassword = AesEncryptUtil.aesDecrypt(userVM.getNewPassword());
            String confirmPassword = AesEncryptUtil.aesDecrypt(userVM.getConfirmPassword());
            userVM.setNewPassword(newPassword);
            userVM.setConfirmPassword(confirmPassword);
            String message = userService.resetPassword(userVM);
            if (StringUtils.isNotEmpty(message)) {
                return message;
            }
            jsonResult.setIsSuccess(true);
            jsonResult.setMessage("重置成功.");
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
     * admin
     * 用户管理
     *
     * @param pageQuery
     * @return
     */
    @RequestMapping(value = "/userInfoPage", method = RequestMethod.POST)
    public String getUserInfoAndRoleByPage(@RequestBody PageQuery pageQuery) {
        String result = null;
        try {
            Page<UserInfoVM> pageInfo = userService.findUserInfoAndRoleByPage(pageQuery);
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
     * 注销账号
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/updateAccount/{id}", method = RequestMethod.GET)
    public String updateAccount(@PathVariable long id) {
        String result = null;
        JsonResult jsonResult = new JsonResult();
        try {
            if (id != 1) {
                String message = userService.updateAccount(id);
                if (StringUtils.isNotEmpty(message)) {
                    return message;
                }
                jsonResult.setIsSuccess(true);
                jsonResult.setMessage("注销成功.");
            } else {
                jsonResult.setIsSuccess(false);
                jsonResult.setMessage("不能注销超级管理员.");
            }
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
     * 注销账号
     *
     * @param
     * @return
     */
    @RequestMapping(value = "/updateAccountByCode/{code}", method = RequestMethod.GET)
    public String updateAccountByCode(@PathVariable String code) {
        String result = null;
        JsonResult jsonResult = new JsonResult();
        UserEntity byUserCode = userRepository.findByUserCode(code);
        Long id = byUserCode.getId();
        try {
            if (id != 1) {
                String message = userService.updateAccountByCode(code);
                if (StringUtils.isNotEmpty(message)) {
                    return message;
                }
                jsonResult.setIsSuccess(true);
                jsonResult.setMessage("注销成功.");
            } else {
                jsonResult.setIsSuccess(false);
                jsonResult.setMessage("不能注销超级管理员.");
            }
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
     * 启用账号
     */
    @RequestMapping(value = "/updateAccountUse/{id}", method = RequestMethod.GET)
    public String updateAccountUse(@PathVariable long id) {
        String result = null;
        JsonResult jsonResult = new JsonResult();
        try {
            String message = userService.updateAccountUse(id);
            if (StringUtils.isNotEmpty(message)) {
                return message;
            }
            jsonResult.setIsSuccess(true);
            jsonResult.setMessage("启用成功.");

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
     * 绑定账号
     */
    @RequestMapping(value = "/bindWeiXin", method = RequestMethod.POST)
    public String bindWeiXin(@RequestBody UserVM userVM) {
        String result = null;

        try {
            String message = userService.updateOpenId(userVM);
            result = JsonResult.success(ImErrorCode.MSG_SUCCESS, MessageUtils.get(ImErrorCode.MSG_SUCCESS), message);

        } catch (ServiceException serviceException) {
            throw serviceException;
        } catch (Exception e) {
            ServiceException se = new ServiceException(ImErrorCode.MSG_FAIL, MessageUtils.get(ImErrorCode.MSG_FAIL));
            throw se;
        }
        return result;
    }

    /**
     * 绑定
     */
    @RequestMapping(value = "/bindWeiXinUrl", method = RequestMethod.GET)
    public String bindWeiXinUrl(HttpServletResponse response, HttpServletRequest request) {
        String result = null;

        try {
            String message = serverUrl + "/api/s/bindWeixin?access_token=" + request.getHeader(this.tokenHeader);
            result = JsonResult.success(ImErrorCode.MSG_SUCCESS, MessageUtils.get(ImErrorCode.MSG_SUCCESS), message);

        } catch (ServiceException serviceException) {
            throw serviceException;
        } catch (Exception e) {
            ServiceException se = new ServiceException(ImErrorCode.MSG_FAIL, MessageUtils.get(ImErrorCode.MSG_FAIL));
            throw se;
        }
        return result;
    }

    /**
     * 根据用户id获取关联员工信息
     */
    @RequestMapping(value = "/getEmpInfo/{id}", method = RequestMethod.GET)
    public String getEmpInfo(@PathVariable long id) {
        String result = null;
        try {
            EmployeeVM employeeVM = userService.getEmpInfo(id);
            result = JsonResult.success(ImErrorCode.MSG_SUCCESS, MessageUtils.get(ImErrorCode.MSG_SUCCESS), employeeVM);
        } catch (ServiceException serviceException) {
            throw serviceException;
        } catch (Exception e) {
            ServiceException se = new ServiceException(ImErrorCode.MSG_FAIL, MessageUtils.get(ImErrorCode.MSG_FAIL));
            throw se;
        }
        return result;
    }


    /**
     * 根据用户ID返回对应角色信息
     *
     * @param userId
     * @return
     */
    @PreAuthorize("true")
    @RequestMapping(value = "/userRole/list/{userId}", method = RequestMethod.GET)
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
     * 角色下拉框
     *
     * @return
     */
    @RequestMapping(value = "/userRole/list", method = RequestMethod.GET)
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


}
