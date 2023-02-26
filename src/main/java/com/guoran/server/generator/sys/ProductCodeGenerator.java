package com.guoran.server.generator.sys;

import com.guoran.server.common.i18n.MessageUtils;
import com.guoran.server.generator.CommonCodeGenerator;
import com.guoran.server.generator.sequence.service.SequenceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @description: 商品编码生成器
 * SPBM+6位序号
 **/
@Component(value = "PRODUCT")
public class ProductCodeGenerator extends CommonCodeGenerator {

    public ProductCodeGenerator(@Autowired MessageUtils messageUtils, @Autowired SequenceService sequenceService) {
        this.messageUtils = messageUtils;
        this.sequenceService = sequenceService;
    }

    /**
     * 生成编号
     *
     * @return
     */
    @Override
    public String generateCode() {
        return this.generateCode(6, "PRODUCT");
    }

}
