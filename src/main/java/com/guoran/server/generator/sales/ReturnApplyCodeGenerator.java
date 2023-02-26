package com.guoran.server.generator.sales;

import com.guoran.server.common.i18n.MessageUtils;
import com.guoran.server.generator.CommonCodeGenerator;
import com.guoran.server.generator.sequence.service.SequenceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @description: 退回申请单号生成器
 * XXTHSQ+日期+4位序号
 **/
@Component(value = "RETURNAPPLY")
public class ReturnApplyCodeGenerator extends CommonCodeGenerator {

    public ReturnApplyCodeGenerator(@Autowired MessageUtils messageUtils, @Autowired SequenceService sequenceService) {
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
        return this.generateCode(4, "RETURNAPPLY");
    }


    /**
     * 获取退回申请单号中的建立日期
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
