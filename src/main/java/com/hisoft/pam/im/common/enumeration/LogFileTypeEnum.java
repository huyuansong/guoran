package com.hisoft.pam.im.common.enumeration;

/**
 * 日志文件类型
 */
public enum LogFileTypeEnum implements BaseEnum{
    IMAGE("图片",1),
    FILE("文件",2);
    /**
     * 文本
     */
    private String text;
    /**
     *  key
     */
    private int value;

    private LogFileTypeEnum(String text, int value){
        this.text = text;
        this.value =value;
    }

    /**
     * 通过值来获取名称
     * @param value
     * @return
     */
    public static String getText(int value) {
        for (LogFileTypeEnum c : LogFileTypeEnum.values()) {
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
