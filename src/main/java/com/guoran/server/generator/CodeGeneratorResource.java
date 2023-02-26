package com.guoran.server.generator;

import com.guoran.server.common.WordToPinYinUtil;
import com.guoran.server.generator.water.GasGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @description:
 * @author: machao
 * @create: 2019-12-11 14:49
 * @Modify By
 **/
@RestController
@RequestMapping(value = "/codes")
public class CodeGeneratorResource {
    @Autowired
    WordToPinYinUtil wordToPinYinUtil;

    /**
     * 水表编号:GRSB+4位序号
     */
    @Autowired
    @Qualifier(value = "WATER")
    CodeGenerator waterGenerator;

    /**
     * 电表编号:GRDB+4位序号
     */
    @Autowired
    @Qualifier(value = "ELECTRICITY")
    CodeGenerator electricityGenerator;

    /**
     * 销售订单编号:XSDD+建立日期+4位序号
     */
    @Autowired
    @Qualifier(value = "SALES")
    CodeGenerator salesOrderGenerator;

    /**
     * 销售订单编号:XSDD+建立日期+4位序号
     */
    @Autowired
    @Qualifier(value = "ACCEPTANCE")
    CodeGenerator acceptanceCodeGenerator;

    /**
     * 入库单编号:DJCP+日期+序号
     */
    @Autowired
    @Qualifier(value = "ACCEPTANCE")
    CodeGenerator warehousingCodeGenerator;


    /**
     * 商品编码:SPBM+6位序号
     */
    @Autowired
    @Qualifier(value = "PRODUCT")
    CodeGenerator productCodeGenerator;


    /**
     * 仓库编码:CK+4位序号
     */
    @Autowired
    @Qualifier(value = "WAREHOUSE")
    CodeGenerator warehouseCodeGenerator;

    /**
     * 生产计划单号:SCJH+日期+序号
     */
    @Autowired
    @Qualifier(value = "PRODUCTIONPLAN")
    CodeGenerator productionplanCodeGenerator;

    /**
     * 零工编号:LG+六位序号
     */
    @Autowired
    @Qualifier(value = "SCATTERED")
    CodeGenerator scatteredCodeGenerator;


    /**
     * 退回申请单号
     * XXTHSQ+日期+4位序号
     */
    @Autowired
    @Qualifier(value = "SCATTERED")
    CodeGenerator returnApplyCodeGenerator;

    /**
     * 退款流水号
     * XXTK+日期+4位序号
     */
    @Autowired
    @Qualifier(value = "SCATTERED")
    CodeGenerator refundCodeGenerator;

    /**
     * 出库单号
     * EX+日期+5位序号
     */
    @Autowired
    @Qualifier(value = "EX-WAREHOUSE")
    CodeGenerator exwarehouseCodeGenerator;

    /**
     * 调拨商品批次号
     * DB+日期+4位序号
     */
    @Autowired
    @Qualifier(value = "PRODUCTALLOCATION")
    CodeGenerator productAllocationCodeGenerator;

    @Autowired
    @Qualifier(value = "GAS")
    GasGenerator gasGenerator;

    @RequestMapping(method = RequestMethod.GET)
    public String genearteMoeCode() {
        //水表编号:GRSB+4位序号
        waterGenerator.setPre("GRSB");
        String waterCode = waterGenerator.generateCode();

        //电表编号:GRSB+4位序号
        electricityGenerator.setPre("GRDB");
        String electricityCode = electricityGenerator.generateCode();

        //销售订单编号:XSDD+建立日期+4位序号
        salesOrderGenerator.setPre("XSDD");
        String salelOrderCode = salesOrderGenerator.generateCode();

        //验收单据号:SCYS+日期+序号
        acceptanceCodeGenerator.setPre("SCYS");
        String acceptanceCode = acceptanceCodeGenerator.generateCode();

        //入库单号:DJCP+日期+序号
        warehousingCodeGenerator.setPre("DJCP");
        String warehousingCode = warehousingCodeGenerator.generateCode();


        //商品编码:SPBM+6位序号
        productCodeGenerator.setPre("SPBM");
        String productCode = productCodeGenerator.generateCode();

        //仓库编码:CK+4位序号
        warehouseCodeGenerator.setPre("CK");
        String warehouseCode = warehouseCodeGenerator.generateCode();

        //生产计划单号:SCJH+日期+序号
        productionplanCodeGenerator.setPre("SCJH");
        String productionplanCode = productionplanCodeGenerator.generateCode();

        //零工编号:LG+六位序号
        scatteredCodeGenerator.setPre("LG");
        String scatteredCode = scatteredCodeGenerator.generateCode();


        //退回申请单号生成器
        // XXTHSQ+日期+4位序号
        returnApplyCodeGenerator.setPre("XXTHSQ");
        String returnApplyCode = returnApplyCodeGenerator.generateCode();

        //退款流水号
        // XXTK+日期+4位序号
        refundCodeGenerator.setPre("XXTK");
        String refundCode = refundCodeGenerator.generateCode();

        // 出库单号 EX+日期+5位序号
        exwarehouseCodeGenerator.setPre("EX");
        String exwarehouseCode = exwarehouseCodeGenerator.generateCode();

        // 调拨商品批次号 DB+日期+4位序号
        productAllocationCodeGenerator.setPre("DB");
        String productAllocationCode = productAllocationCodeGenerator.generateCode();

        //气表编号:GAS+4位序号
        gasGenerator.setPre("GAS");
        String gasCode = gasGenerator.generateCode();

        return "水表编号：" + waterCode + " 电表编号:" + electricityCode + " 销售订单编号:" + salelOrderCode
                + " 验收单据号:" + acceptanceCode + " 入库单号:" + warehousingCode
                + " 商品编码:" + productCode + " 仓库编码:" + warehouseCode
                + " 生产计划单号:" + productionplanCode
                + " 零工编号:" + scatteredCode
                + " 退回申请单号:" + returnApplyCode
                + " 退款流水号:" + refundCode
                + " 出库单号:" + exwarehouseCode
                + " 调拨商品批次号:" + productAllocationCode
                + " 气表编号:" + gasCode;
    }
}
