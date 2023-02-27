package com.guoran.server.common.enumeration;

/**
 * 是否类型枚举
 * 客户表使用到的地方如下：
 * 1.是否可赊销
 * 2.是否个人
 * 3.是否默认账户
 * <p>
 * 是  &  否
 */
public enum WhetherEnum {
    CONFIRMED("是", 0),
    UNCONFIRMED("否", 1);

    /**
     * 名称
     */
    private String text;
    /**
     * 状态
     */
    private int value;


    private WhetherEnum(String text, int value) {
        this.text = text;
        this.value = value;
    }

    /**
     * 通过类型来获取名称
     *
     * @param value
     * @return
     */
    public static String getText(int value) {
        for (WhetherEnum c : WhetherEnum.values()) {
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
