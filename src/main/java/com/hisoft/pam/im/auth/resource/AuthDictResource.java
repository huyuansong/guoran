package com.hisoft.pam.im.auth.resource;

import com.github.pagehelper.Page;
import com.hisoft.pam.im.auth.service.AuthDictItemService;
import com.hisoft.pam.im.auth.vmodel.AuthDictItemVM;
import com.hisoft.pam.im.common.JsonResult;
import com.hisoft.pam.im.common.exception.ImErrorCode;
import com.hisoft.pam.im.common.exception.ServiceException;
import com.hisoft.pam.im.common.i18n.MessageUtils;
import com.hisoft.pam.im.common.search.PageQuery;
import com.hisoft.pam.im.common.search.PageResult;
import com.hisoft.pam.im.common.utils.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import com.hisoft.pam.im.auth.service.AuthDictService;
import com.hisoft.pam.im.auth.vmodel.AuthDictVM;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
    *  rest接口
    * </p>
 *
 * @author wangyongtao
 * @create 2022-3-11
 * @Modify By
 */
@Api(tags = {"数据字典"})
@RestController
@RequestMapping("/auth/AuthDict")
public class AuthDictResource {
    @Autowired
    private AuthDictService authDictService;
    @Autowired
    private AuthDictItemService authDictItemService;

    /**
    * 根据id查询
    */
    @ApiOperation(value = "根据id查询数据")
    @RequestMapping(value = "/{id}",method = RequestMethod.GET)
    public String getEntry(@PathVariable long id){
        String result = null;
        try {
            AuthDictVM authDictVM = authDictService.getEntryBy(id);
            result = JsonResult.success(ImErrorCode.MSG_SUCCESS, MessageUtils.get(ImErrorCode.MSG_SUCCESS),authDictVM);
        } catch (ServiceException serviceException){
            throw serviceException;
        } catch (Exception e){
            e.printStackTrace();
            ServiceException se = new ServiceException(ImErrorCode.MSG_FAIL,MessageUtils.get(ImErrorCode.MSG_FAIL));
            throw se;
        }
        return result;
    }
    /**
     * 查询分页数据
     */
    @ApiOperation(value = "查询分页数据")
   @RequestMapping(value = "/page",method = RequestMethod.POST)
    public String getEntrysByPage(@RequestBody PageQuery pageQuery){
        String result = null;
        try {
            Page<AuthDictVM> pageInfo = authDictService.findEntrysByPage(pageQuery);
            PageResult pageResult=new PageResult();
            pageResult.setPageNum(pageQuery.getPageNum());
            pageResult.setRows(pageInfo);
            pageResult.setTotal(pageInfo.getTotal());
            pageResult.setPages(pageInfo.getPages());
            List<Long> collect = pageInfo.stream().map(x -> x.getId()).collect(Collectors.toList());
            if (!collect.isEmpty()) {
                List<AuthDictItemVM> authDictItemVMS = authDictItemService.getEntryByIdList(collect);
                Map<Long, List<AuthDictItemVM>> collect1 = authDictItemVMS.stream().collect(Collectors.groupingBy(AuthDictItemVM::getDictId));
                for (AuthDictVM authDictVM : pageInfo) {
                    if (collect1.get(authDictVM.getId()) != null) {
                        authDictVM.setHasChildren("true");
                    } else {
                        authDictVM.setHasChildren("false");
                    }
                }
            }
            result = JsonResult.success(ImErrorCode.MSG_SUCCESS,MessageUtils.get(ImErrorCode.MSG_SUCCESS),pageResult);
        } catch (ServiceException serviceException){
            throw serviceException;
        } catch (Exception e){
            e.printStackTrace();
            ServiceException se = new ServiceException(ImErrorCode.MSG_FAIL,MessageUtils.get(ImErrorCode.MSG_FAIL));
            throw se;
        }
        return result;
    }
    /**
    * 新增
    */
    @ApiOperation(value = "新增数据")
    @RequestMapping(method = RequestMethod.POST)
    String createEntry(@RequestBody AuthDictVM authDictVM) {
        try {
            String msg = authDictService.createEntry(authDictVM);
            if(StringUtils.isNotEmpty(msg)){
                return JsonResult.failed(msg);
            }
            return JsonResult.success(ImErrorCode.MSG_SUCCESS,MessageUtils.get(ImErrorCode.MSG_SUCCESS),null);
        }catch (ServiceException serviceException){
            throw serviceException;
        } catch (Exception e){
            e.printStackTrace();
            ServiceException se = new ServiceException(ImErrorCode.MSG_FAIL,MessageUtils.get(ImErrorCode.MSG_FAIL));
            throw se;
        }
    }
    /**
    * 修改
    */
    @ApiOperation(value = "修改数据")
    @RequestMapping(method = RequestMethod.PUT)
    public String updateEntry(@RequestBody AuthDictVM authDictVM) {
        try {
            authDictService.updateEntry(authDictVM);
            return JsonResult.success(ImErrorCode.MSG_SUCCESS,MessageUtils.get(ImErrorCode.MSG_SUCCESS),null);
        }catch (ServiceException serviceException){
            throw serviceException;
        } catch (Exception e){
            e.printStackTrace();
            ServiceException se = new ServiceException(ImErrorCode.MSG_FAIL,MessageUtils.get(ImErrorCode.MSG_FAIL));
            throw se;
        }
    }

    /**
    * 删除
    */
    @ApiOperation(value = "删除数据")
    @RequestMapping(value = "/{id}",method = RequestMethod.DELETE)
    String deleteEntry(@PathVariable long id) {
        try {
            authDictService.deleteById(id);
            return JsonResult.success(ImErrorCode.MSG_SUCCESS,MessageUtils.get(ImErrorCode.MSG_SUCCESS),null);
        }catch (ServiceException serviceException){
            throw serviceException;
        } catch (Exception e){
            e.printStackTrace();
            ServiceException se = new ServiceException(ImErrorCode.MSG_FAIL,MessageUtils.get(ImErrorCode.MSG_FAIL));
            throw se;
        }
    }



    /**
    * 批量删除
    */
    @ApiOperation(value = "批量删除")
    @RequestMapping(value = "/deleteIds",method = RequestMethod.DELETE)
    String deleteBatchEntry(String ids) {
        try {
            authDictService.deleteIds(ids);
            return JsonResult.success(ImErrorCode.MSG_SUCCESS,MessageUtils.get(ImErrorCode.MSG_SUCCESS),null);
        }catch (ServiceException serviceException){
            throw serviceException;
        } catch (Exception e){
            e.printStackTrace();
            ServiceException se = new ServiceException(ImErrorCode.MSG_FAIL,MessageUtils.get(ImErrorCode.MSG_FAIL));
            throw se;
        }
    }

    /**
    * 删除 做修改操作 根据id修改 is_delete 参数{"id":id,"isDelete":"1"}
    */
    @ApiOperation(value = "修改数据")
    @RequestMapping(value = "/delete",method = RequestMethod.PUT)
    public String updateIsDelete(@RequestBody AuthDictVM authDictVM) {
        try {
            authDictService.updateIsDelete(authDictVM);
            return JsonResult.success(ImErrorCode.MSG_SUCCESS,MessageUtils.get(ImErrorCode.MSG_SUCCESS),null);
        }catch (ServiceException serviceException){
            throw serviceException;
        } catch (Exception e){
            e.printStackTrace();
            ServiceException se = new ServiceException(ImErrorCode.MSG_FAIL,MessageUtils.get(ImErrorCode.MSG_FAIL));
            throw se;
        }
    }
}
