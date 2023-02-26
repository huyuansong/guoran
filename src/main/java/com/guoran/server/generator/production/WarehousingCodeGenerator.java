package com.guoran.server.generator.production;

import com.guoran.server.common.i18n.MessageUtils;
import com.guoran.server.generator.CommonCodeGenerator;
import com.guoran.server.generator.sequence.service.SequenceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @description: 入库单号生成器
 * DJCP+日期+序号
 **/
@Component(value = "WAREHOUSING")
public class WarehousingCodeGenerator extends CommonCodeGenerator {

    public WarehousingCodeGenerator(@Autowired MessageUtils messageUtils, @Autowired SequenceService sequenceService) {
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
        String date = this.getDate();
        this.pre = this.pre + date;
        return this.generateCode(5, "WAREHOUSING");
    }


    /**
     * 获取入库单号中的建立日期
     *
     * @return
     */
    private String getDate() {
        String date = null;
        Calendar startTime = Calendar.getInstance();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
        formatter.setLenient(false);
        formatter.set2DigitYearStart(startTime.getTime());
        try {
            date = formatter.format(new Date());
        } catch (Exception e) {
        }
        return date;
    }
}
