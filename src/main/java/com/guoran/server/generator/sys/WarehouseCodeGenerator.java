package com.guoran.server.generator.sys;

import com.guoran.server.common.i18n.MessageUtils;
import com.guoran.server.generator.CommonCodeGenerator;
import com.guoran.server.generator.sequence.service.SequenceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @description: 仓库编码生成器
 * CK+4位序号
 **/
@Component(value = "WAREHOUSE")
public class WarehouseCodeGenerator extends CommonCodeGenerator {

    public WarehouseCodeGenerator(@Autowired MessageUtils messageUtils, @Autowired SequenceService sequenceService) {
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
        return this.generateCode(4, "WAREHOUSE");
    }


}
