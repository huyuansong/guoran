package com.hisoft.pam.im.common.enumeration;
/**
 * 登录类型枚举
 */
public enum LoginTypeEnum {
    /* 登录类型  状态
     *
      * */
    EMAIL("email",1 ),
    TEL("tel",2),
    CODE("code",3);

    /**
     * 名称
     */
    private String text;
    /**
     * 账号状态
     */
    private int value;


    private LoginTypeEnum(String text, int value ){
        this.text = text;
        this.value =value;
    }

    /**
     * 通过账号类型来获取名称
     * @param value
     * @return
     */
    public static String getText(int value) {
        for (LoginTypeEnum c : LoginTypeEnum.values()) {
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
