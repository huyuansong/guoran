package com.guoran.server.common.enumeration;

/**
 * 档案所在公司类型枚举
 */
public enum FileCompanyTypeEnum {

    GUORAN("果然风情", 1),
    TIANRUN("郑铁天润", 2);

    /**
     * 名称
     */
    private String text;
    /**
     * key
     */
    private int value;


    private FileCompanyTypeEnum(String text, int value) {
        this.text = text;
        this.value = value;
    }

    /**
     * 通过ID来获取名称
     *
     * @param value
     * @return
     */
    public static String getText(int value) {
        for (FileCompanyTypeEnum c : FileCompanyTypeEnum.values()) {
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
