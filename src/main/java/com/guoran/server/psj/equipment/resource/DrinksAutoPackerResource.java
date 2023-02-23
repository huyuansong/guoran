package com.guoran.server.psj.equipment.resource;


import com.guoran.server.common.JsonResult;
import com.guoran.server.psj.equipment.model.vto.DrinksAutoPackerVM;
import com.guoran.server.psj.equipment.service.DrinksAutoPackerService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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


}
