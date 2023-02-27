package com.guoran.server.common.enumeration;

/**
 * 账号类型枚举
 */
public enum AccountStatusEnum {
    /* 账号  状态
     *
     * */
    NORMAL("正常", 0),
    CANCEL("注销", 1);

    /**
     * 名称
     */
    private String text;
    /**
     * 账号状态
     */
    private int value;


    private AccountStatusEnum(String text, int value) {
        this.text = text;
        this.value = value;
    }

    /**
     * 通过账号类型来获取名称
     *
     * @param value
     * @return
     */
    public static String getText(int value) {
        for (AccountStatusEnum c : AccountStatusEnum.values()) {
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
