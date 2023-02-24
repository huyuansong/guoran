package com.guoran.server.psj.equipment.resource;


import com.guoran.server.common.JsonResult;
import com.guoran.server.common.exception.ImErrorCode;
import com.guoran.server.common.i18n.MessageUtils;
import com.guoran.server.common.search.PageQuery;
import com.guoran.server.common.search.PageResult;
import com.guoran.server.psj.equipment.model.vto.DrinksCansFillingVM;
import com.guoran.server.psj.equipment.service.DrinksCansFillingService;
import com.guoran.server.psj.equipment.utils.CheckInputUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * <p>
 * 设备管理-设备运行记录-饮料车间-易拉罐灌装记录 rest接口
 * </p>
 */
@Api(tags = {"设备管理-设备运行记录-饮料车间-易拉罐灌装记录"})
@RestController
@RequestMapping("/equipment/drinksCansFillings")
public class DrinksCansFillingResource {

    @Resource
    private DrinksCansFillingService drinksCansFillingService;

    /**
     * 根据id查询
     */
    @ApiOperation(value = "根据id查询数据")
    @GetMapping(value = "/{id}")
    public String getEntry(@PathVariable long id) {
        if (id == 0) {
            return null;
        }
        DrinksCansFillingVM drinksCansFillingVM = drinksCansFillingService.getEntryBy(id);
        return JsonResult.success(ImErrorCode.MSG_SUCCESS, MessageUtils.get(ImErrorCode.MSG_SUCCESS), drinksCansFillingVM);
    }

    /**
     * 查询分页数据
     */
    @ApiOperation(value = "查询分页数据")
    @RequestMapping(value = "/page", method = RequestMethod.POST)
    public String getEntrysByPage(@RequestBody PageQuery pageQuery) {
        if (CheckInputUtil.chikcInput(pageQuery)) {
            return JsonResult.failed("请勿输入特殊字符");
        }
        PageResult pageResult = drinksCansFillingService.findEntrysByPage(pageQuery);

        return JsonResult.success(ImErrorCode.MSG_SUCCESS, MessageUtils.get(ImErrorCode.MSG_SUCCESS), pageResult);
    }
}
