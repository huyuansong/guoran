package com.guoran.server.generator.water;

import com.guoran.server.common.i18n.MessageUtils;
import com.guoran.server.generator.CommonCodeGenerator;
import com.guoran.server.generator.sequence.service.SequenceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @description: 学生编号生成器
 * @author: machao
 * @create: 2019-12-11 14:10
 * @Modify By
 **/
@Component(value = "ELECTRICITY")
public class ElectricityGenerator extends CommonCodeGenerator {

    public ElectricityGenerator(@Autowired MessageUtils messageUtils, @Autowired SequenceService sequenceService) {
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
        return this.generateCode(4, "ELECTRICITY");
    }
}
