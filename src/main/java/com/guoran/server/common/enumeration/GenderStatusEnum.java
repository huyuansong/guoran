package com.guoran.server.common.enumeration;

/**
 * 账号类型枚举
 */
public enum GenderStatusEnum {
    /* 账号  状态
     *
     * */
    NORMAL("男", "F"),
    CANCEL("女", "M");

    /**
     * 名称
     */
    private String text;
    /**
     * 账号状态
     */
    private String value;


    private GenderStatusEnum(String text, String value) {
        this.text = text;
        this.value = value;
    }

    /**
     * 通过账号类型来获取名称
     *
     * @param value
     * @return
     */
    public static String getText(String value) {
        for (GenderStatusEnum c : GenderStatusEnum.values()) {
            if (c.getValue().equals(value)) {
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

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

}
