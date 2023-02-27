package com.guoran.server.common.enumeration;

/**
 * 预收余额 类型枚举
 * 大于0
 * 小于0
 * 全部
 */
public enum BalanceEnum {
    ALL("全部", 0),
    GREATER_THAN("大于0", 1),
    LESS_THAN("小于0", 2);

    /**
     * 名称
     */
    private String text;
    /**
     * 状态
     */
    private int value;


    private BalanceEnum(String text, int value) {
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
        for (BalanceEnum c : BalanceEnum.values()) {
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
