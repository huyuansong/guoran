package com.hisoft.pam.im.common.enumeration;
/**
 * 客户类型枚举
 * 内部客户
 * 外部客户
 */
public enum CustomerTypeEnum {
    CONFIRMED("内部客户",0 ),
    UNCONFIRMED("外部客户",1);

    /**
     * 名称
     */
    private String text;
    /**
     * 状态
     */
    private int value;


    private CustomerTypeEnum(String text, int value ){
        this.text = text;
        this.value =value;
    }

    /**
     * 通过类型来获取名称
     * @param value
     * @return
     */
    public static String getText(int value) {
        for (CustomerTypeEnum c : CustomerTypeEnum.values()) {
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
