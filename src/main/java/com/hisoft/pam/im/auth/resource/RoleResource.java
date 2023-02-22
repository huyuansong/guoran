package com.hisoft.pam.im.auth.resource;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.pagehelper.Page;
import com.hisoft.pam.im.auth.service.RoleMenuService;
import com.hisoft.pam.im.auth.vmodel.RoleMenuVM;
import com.hisoft.pam.im.auth.vmodel.RoleVM;
import com.hisoft.pam.im.common.JsonResult;
import com.hisoft.pam.im.common.exception.ImErrorCode;
import com.hisoft.pam.im.common.search.PageQuery;
import com.hisoft.pam.im.common.search.PageResult;
import com.hisoft.pam.im.common.utils.CheckInputUtil;
import com.hisoft.pam.im.common.utils.StringUtils;
import com.hisoft.pam.im.auth.service.RoleService;
import com.hisoft.pam.im.common.i18n.MessageUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@Api(tags = {"角色"})
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
     * @param id
     * @return
     */
    @PreAuthorize("true")
    @ApiOperation(value = "根据类型Id获取实体")
    @RequestMapping(value = "/{id}",method = RequestMethod.GET)
    public String getEntry(@PathVariable long id){
        String result = null;
        JsonResult jsonResult=new JsonResult();
        try {
            RoleVM roleVM = roleService.getEntryBy(id);
            jsonResult.setIsSuccess(true);
            String message="获取成功。";
            jsonResult.setMessage(message);
            jsonResult.setData(roleVM);
            result = objectMapper.writeValueAsString(jsonResult);
        } catch (Exception e){
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
     * @param pageQuery
     * @return
     */
    @ApiOperation(value = "分页")
    @RequestMapping(value = "/page",method = RequestMethod.POST)
    public String getEntrysByPage(@RequestBody PageQuery pageQuery){
        String result = null;
        JsonResult jsonResult=new JsonResult();
        try {
            CheckInputUtil checkInputUtil = new CheckInputUtil();
            if(checkInputUtil.chikcInput(pageQuery)){
                return JsonResult.failed("请勿输入特殊字符");
            }
            Page<RoleVM> pageInfo = roleService.findRolesByPage(pageQuery);
            PageResult pageResult=new PageResult();
            pageResult.setPageNum(pageQuery.getPageNum());
            pageResult.setRows(pageInfo);
            pageResult.setTotal(pageInfo.getTotal());
            pageResult.setPages(pageInfo.getPages());
            jsonResult.setIsSuccess(true);
            jsonResult.setData(pageResult);
            result = objectMapper.writeValueAsString(jsonResult);
        } catch (Exception e){
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
     * @return
     */
    @ApiOperation(value = "角色下拉框")
    @RequestMapping(value = "/list",method = RequestMethod.GET)
    public String getEntrysByList(){
        String result = null;
        JsonResult jsonResult=new JsonResult();
        try {
            List<RoleVM> roleVMList =roleService.findAllRoles();
            jsonResult.setIsSuccess(true);
            jsonResult.setData(roleVMList);
            result = objectMapper.writeValueAsString(jsonResult);
        } catch (Exception e){
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
     * @param roleVM
     * @return
     */
    @ApiOperation(value = "新增项")
    @RequestMapping(method = RequestMethod.POST)
    String createEntry(@RequestBody RoleVM roleVM) {
        String result = null;
        JsonResult jsonResult=new JsonResult();
        try {
            String message=roleService.createEntry(roleVM);
            if(StringUtils.isNotEmpty(message)){
                return message;
            }

            jsonResult.setIsSuccess(true);
            jsonResult.setMessage(MessageUtils.get(ImErrorCode.MSG_SUCCESS));
            result = objectMapper.writeValueAsString(jsonResult);
        }catch (Exception e){
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
     * @param roleVM
     * @return
     */
    @ApiOperation(value = "修改")
    @RequestMapping(method = RequestMethod.PUT)
    public String updateEntry(@RequestBody RoleVM roleVM) {
        String result = null;
        JsonResult jsonResult=new JsonResult();
        try {
            String message =roleService.updateEntry(roleVM);
            if(StringUtils.isNotEmpty(message)){
                return message;
            }
            jsonResult.setIsSuccess(true);
            jsonResult.setMessage("修改成功.");
        }catch (Exception e){
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
     * @param id
     * @return
     */
    @ApiOperation(value = "根据id删除项")
    @RequestMapping(value = "/{id}",method = RequestMethod.DELETE)
    String deleteEntry(@PathVariable long id) {
        String result = null;
        JsonResult jsonResult=new JsonResult();
        try {
            String resultStr=roleService.deleteById(id);
            if(StringUtils.isNotEmpty(resultStr)){
                return resultStr;
            }
            jsonResult.setIsSuccess(true);
            jsonResult.setMessage("删除成功.");
        }catch (Exception e){
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
     * @param roleMenuVM
     * @return
     */
    @ApiOperation(value = "修改")
    @RequestMapping(value = "/roleMenu",method = RequestMethod.PUT)
    public String updateEntry(@RequestBody RoleMenuVM roleMenuVM) {
        String result = null;
        JsonResult jsonResult=new JsonResult();
        try {
            roleMenuService.updateEntry(roleMenuVM);
            jsonResult.setIsSuccess(true);
            jsonResult.setMessage("修改成功.");
        }catch (Exception e){
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
