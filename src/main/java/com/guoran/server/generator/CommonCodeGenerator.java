package com.guoran.server.generator;

import com.guoran.server.common.assertor.CustomeAssert;
import com.guoran.server.common.exception.ImErrorCode;
import com.guoran.server.common.exception.ServiceException;
import com.guoran.server.common.i18n.MessageUtils;
import com.guoran.server.generator.sequence.service.SequenceService;
import com.guoran.server.generator.sequence.vmodel.SequenceVM;

/**
 * @description: 通用编号生成抽象类
 * @author: machao
 * @create: 2019-12-12 14:12
 * @Modify By
 **/
public abstract class CommonCodeGenerator implements CodeGenerator {
    /**
     * 消息处理
     */
    protected MessageUtils messageUtils;
    /**
     * 编号序号
     */
    protected SequenceService sequenceService;

    protected String pre;

    /**
     * 获取先决条件
     *
     * @return
     */
    @Override
    public String getPre() {
        return this.pre;
    }

    /**
     * 设置先决条件
     *
     * @param pre
     * @return
     */
    @Override
    public void setPre(String pre) {
        this.pre = pre;
    }

    /**
     * 生成代码
     *
     * @param seqLength 序号长度
     * @param typeCode  编号类型
     * @return
     * @throws ServiceException
     */
    protected String generateCode(int seqLength, String typeCode) throws ServiceException {
        CustomeAssert.isEmpty(this.pre, ImErrorCode.MSG_STR_ISEMPTY, "pre " + MessageUtils.get(ImErrorCode.MSG_STR_ISEMPTY));
        String code = null;
        //获取序号
        SequenceVM sequenceVM = sequenceService.findEntryByTypeCode(typeCode);
        if (sequenceVM != null) {
            int seq = sequenceVM.getSeq();
            seq++;
            //补全数字前面的0
            String seqStr = String.format("%0" + seqLength + "d", seq);
            code = this.pre + "" + seqStr;
            sequenceVM.setSeq(seq);
            sequenceService.updateEntry(sequenceVM);
        }
        return code;
    }


}
