package com.hisoft.pam.im.auth.resource;

import com.github.pagehelper.Page;
import com.hisoft.pam.im.auth.model.AuthDictItemEntity;
import com.hisoft.pam.im.auth.service.AuthDictItemService;
import com.hisoft.pam.im.common.JsonResult;
import com.hisoft.pam.im.common.exception.ImErrorCode;
import com.hisoft.pam.im.common.exception.ServiceException;
import com.hisoft.pam.im.common.i18n.MessageUtils;
import com.hisoft.pam.im.common.search.PageQuery;
import com.hisoft.pam.im.common.search.PageResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.hisoft.pam.im.auth.vmodel.AuthDictItemVM;

import java.util.List;

/**
 * <p>
    *  rest接口
    * </p>
 *
 * @author wangyongtao
 * @create 2022-3-11
 * @Modify By
 */
@Api(tags = {"数据字典审核表"})
@RestController
@RequestMapping("/auth/authDictItem")
public class AuthDictItemResource {
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
            AuthDictItemVM authDictItemVM = authDictItemService.getEntryBy(id);
            result = JsonResult.success(ImErrorCode.MSG_SUCCESS, MessageUtils.get(ImErrorCode.MSG_SUCCESS),authDictItemVM);
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
            Page<AuthDictItemVM> pageInfo = authDictItemService.findEntrysByPage(pageQuery);
            PageResult pageResult=new PageResult();
            pageResult.setPageNum(pageQuery.getPageNum());
            pageResult.setRows(pageInfo);
            pageResult.setTotal(pageInfo.getTotal());
            pageResult.setPages(pageInfo.getPages());
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
    String createEntry(@RequestBody AuthDictItemVM authDictItemVM) {
        try {
            Long id = authDictItemService.getItemsBy(authDictItemVM);
            if (id != null){
                return JsonResult.failed("状态值已存在");
            }
            authDictItemService.createEntry(authDictItemVM);
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
    public String updateEntry(@RequestBody AuthDictItemVM authDictItemVM) {
        try {
            Long id = authDictItemService.getItemsBy(authDictItemVM);
            if (id != null && !id.equals(authDictItemVM.getId())){
                return JsonResult.failed("状态值已存在");
            }
            authDictItemService.updateEntry(authDictItemVM);
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
            authDictItemService.deleteById(id);
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
            authDictItemService.deleteIds(ids);
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
     * 批量新增
     */
    @ApiOperation(value = "批量新增")
    @PostMapping(value = "/addBanch")
    String addBanchEntry(@RequestBody List<AuthDictItemVM> list){
        try {
            authDictItemService.addBanch(list);
            return JsonResult.success(ImErrorCode.MSG_SUCCESS,MessageUtils.get(ImErrorCode.MSG_SUCCESS),null);
        } catch (ServiceException serviceException){
            throw serviceException;
        } catch (Exception e){
            e.printStackTrace();
            ServiceException se = new ServiceException(ImErrorCode.MSG_FAIL,MessageUtils.get(ImErrorCode.MSG_FAIL));
            throw se;
        }
    }
    /**
     * 根据dict_id查询字典值
     */
    @ApiOperation(value = "根据dict_id查询字典值")
    @RequestMapping(value = "/getItemsByDictId/{dictId}",method = RequestMethod.GET)
    String getItemsByCode(@PathVariable Long dictId){
       List<AuthDictItemVM> list = authDictItemService.getItemsById(dictId);
       return JsonResult.success(ImErrorCode.MSG_SUCCESS,MessageUtils.get(ImErrorCode.MSG_SUCCESS),list);
    }
    /**
     * 根据dict_code查询字典值
     */
    @ApiOperation(value = "根据dict_id查询字典值")
    @RequestMapping(value = "/getItemsByDictCode/{dictCode}",method = RequestMethod.GET)
    String getItemsByCode(@PathVariable String dictCode){
       List<AuthDictItemVM> list = authDictItemService.getItemsByCode(dictCode);
       return JsonResult.success(ImErrorCode.MSG_SUCCESS,MessageUtils.get(ImErrorCode.MSG_SUCCESS),list);
    }
}
