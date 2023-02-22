package com.hisoft.pam.im.common.enumeration.sales;

import com.hisoft.pam.im.common.enumeration.BaseEnum;

/**
 * 审核类型枚举
 */
public enum ReturnAuditStatusEnum implements BaseEnum{
    /*
     * 退回申请单：审核状态
     * 1:已审核
     * 2:审核中
     * 3:未提交
     * 4:撤回
     *  名称
     * 简写*/
    AUDITED("已审核",1),
    AUDITiNG("审核中",2),
    UNSUBMIT("未提交",3),
    WITHDRAWAL_APPLY("撤回",4);

    /**
     * 文本
     */
    private String text;
    /**
     *  简称
     */
    private int value;

    private ReturnAuditStatusEnum(String text, int value){
        this.text = text;
        this.value =value;
    }

    /**
     * 通过值来获取名称
     * @param value
     * @return
     */
    public static String getText(int value) {
        for (ReturnAuditStatusEnum c : ReturnAuditStatusEnum.values()) {
            if (c.getValue() == value) {
                return c.text;
            }
        }
        return null;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }


}
