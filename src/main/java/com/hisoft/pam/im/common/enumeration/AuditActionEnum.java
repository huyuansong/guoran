package com.hisoft.pam.im.common.enumeration;
/**
 * 审核动作枚举
 */
public enum AuditActionEnum implements BaseEnum{
    /* 提交   通过  驳回  撤回审核 */
    SUBMIT("提交",1),
    PASS("通过",2),
    REJECT("驳回",3),
    REVOKE("撤回审核",4);
    /**
     * 文本
     */
    private String text;
    /**
     *  简称
     */
    private int value;

    private AuditActionEnum(String text, int value){
        this.text = text;
        this.value =value;
    }

    /**
     * 通过值来获取名称
     * @param value
     * @return
     */
    public static String getText(int value) {
        for (AuditActionEnum c : AuditActionEnum.values()) {
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
