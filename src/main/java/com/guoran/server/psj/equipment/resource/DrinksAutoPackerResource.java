package com.guoran.server.psj.equipment.resource;

import com.hisoft.pam.im.common.JsonResult;
import com.hisoft.pam.im.equipment.service.DrinksAutoPackerService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;


/**
 * <p>
 * 设备管理-设备运行记录-饮料车间-自动包装机运行记录 rest接口
 * </p>
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
    public JsonResult getEntry(@PathVariable long id) {
        if (id == 0) {
            return null;
        }
        return JsonResult.success(drinksAutoPackerService.getById(id));

    }


    /**
     * 删除
     */
    @ApiOperation(value = "删除数据")
    @DeleteMapping(value = "/delete")
    public JsonResult deleteEntry(String ids) {
        if (StringUtils.isEmpty(ids)) {
            return null;
        }
        return JsonResult.success(drinksAutoPackerService.deleteByIds(ids));
    }


}
