package com.guoran.server.psj.equipment.resource;


import com.guoran.server.common.JsonResult;
import com.guoran.server.common.exception.ImErrorCode;
import com.guoran.server.common.i18n.MessageUtils;
import com.guoran.server.common.search.PageQuery;
import com.guoran.server.common.search.PageResult;
import com.guoran.server.psj.equipment.model.vto.DrinksAutoPackerVM;
import com.guoran.server.psj.equipment.service.DrinksAutoPackerService;
import com.guoran.server.psj.equipment.utils.CheckInputUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;


/**
 * 设备管理-设备运行记录-饮料车间-自动包装机运行记录 rest接口
 */
@Api(tags = {"设备管理-设备运行记录-饮料车间-自动包装机运行记录"})
@RestController
@RequestMapping("/equipment/DrinksAutoPackers")
public class DrinksAutoPackerResource {

    @Resource
    private DrinksAutoPackerService drinksAutoPackerService;

    /**
     * 根据id查询
     */
    @ApiOperation(value = "根据id查询数据")
    @GetMapping(value = "/{id}")
    public String getEntry(@PathVariable long id) {
        if (id == 0) {
            return null;
        }
        DrinksAutoPackerVM drinksAutoPackerVM = drinksAutoPackerService.getEntryBy(id);
        return JsonResult.success(drinksAutoPackerVM);
    }


    /**
     * 查询分页数据
     */
    @ApiOperation(value = "查询分页数据")
    @PostMapping(value = "/page")
    public String getEntrysByPage(@RequestBody PageQuery pageQuery) {

        if (CheckInputUtil.chikcInput(pageQuery)) {
            return JsonResult.failed("请勿输入特殊字符");
        }
        PageResult pageResult = drinksAutoPackerService.findEntrysByPage(pageQuery);

        return JsonResult.success(ImErrorCode.MSG_SUCCESS, MessageUtils.get(ImErrorCode.MSG_SUCCESS), pageResult);
    }


    /**
     * 新增
     */
    @ApiOperation(value = "新增数据")
    @PostMapping
    public String createEntry(@RequestBody DrinksAutoPackerVM drinksAutoPackerVM) {

        if (drinksAutoPackerVM == null) {
            return null;
        }
        drinksAutoPackerService.createEntry(drinksAutoPackerVM);
        return JsonResult.success(ImErrorCode.MSG_SUCCESS, MessageUtils.get(ImErrorCode.MSG_SUCCESS), null);

    }


    /**
     * 删除
     */
    @ApiOperation(value = "删除数据")
    @DeleteMapping(value = "/delete")
    public String deleteEntry(String ids) {
        if (ids != null) {
            drinksAutoPackerService.deleteByIds(ids);
        }
        return JsonResult.success(ImErrorCode.MSG_SUCCESS, MessageUtils.get(ImErrorCode.MSG_SUCCESS), null);
    }


    /**
     * 修改
     */
    @ApiOperation(value = "修改数据")
    @PutMapping
    public String updateEntry(@RequestBody DrinksAutoPackerVM drinksAutoPackerVM) {

        if (drinksAutoPackerVM == null) {
            return null;
        }
        drinksAutoPackerService.updateEntry(drinksAutoPackerVM);
        return JsonResult.success(ImErrorCode.MSG_SUCCESS, MessageUtils.get(ImErrorCode.MSG_SUCCESS), null);

    }

}
