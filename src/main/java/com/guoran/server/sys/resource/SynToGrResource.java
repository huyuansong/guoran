package com.guoran.server.sys.resource;

import com.guoran.server.sys.service.impl.ProductServiceImpl;
import com.guoran.server.sys.service.impl.WarehouseServiceImpl;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author zhangmengyu
 * @create 2020/11/14 18:44
 * @Modify By
 */
@Api(tags = {"同步到GR"})
@RestController
@RequestMapping("/sys/syn")
public class SynToGrResource {

    /*    @Autowired
        CustomerInfoService customerInfoService;*/
/*    @Autowired
    CustomerBankServiceImpl customerBankService;*/
    @Autowired
    ProductServiceImpl productService;
    @Autowired
    WarehouseServiceImpl warehouseService;

    /**
     * 同步MQ
     */
   /* @ApiOperation(value = "同步mq")
    @RequestMapping(value = "/synToGR")
    public String synToGR(@RequestBody SynToGrVO synToGrVO) {

        String dataType = synToGrVO.getDataType();

        Map[] data = synToGrVO.getData();

        String op = synToGrVO.getOp();
        String result = null;
        if (dataType.equals("supplier")) {
            result = customerInfoService.synToGR(data, op);
        } else if (dataType.equals("custbank")) {
            result = customerBankService.synToGR(data, op);
        } else if (dataType.equals("material")) {
            result = productService.synToGRM(data, op);
        } else if (dataType.equals("marbasclass")) {
            result = productService.synToGRMC(data, op);
        } else if (dataType.equals("stordoc")) {
            result = warehouseService.synToGRW(data, op);
        } else {
            return JsonResult.failed(ImErrorCode.MSG_FAIL, "无效的档案类型");
        }
        if (StringUtils.isNotEmpty(result)) {
            return JsonResult.failed(ImErrorCode.MSG_FAIL, result);
        }
        return JsonResult.success(ImErrorCode.MSG_SUCCESS, MessageUtils.get(ImErrorCode.MSG_SUCCESS), null);
    }
*/
}
