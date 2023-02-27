package com.guoran.server.generator.scattered;

import com.guoran.server.common.i18n.MessageUtils;
import com.guoran.server.generator.CommonCodeGenerator;
import com.guoran.server.generator.sequence.service.SequenceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @description: 零工编号生成器
 * LG+六位序号
 **/
@Component(value = "SCATTERED")
public class ScatteredCodeGenerator extends CommonCodeGenerator {

    public ScatteredCodeGenerator(@Autowired MessageUtils messageUtils, @Autowired SequenceService sequenceService) {
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
        return this.generateCode(6, "SCATTERED");
    }

}
