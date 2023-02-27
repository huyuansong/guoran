package com.guoran.server.common.enumeration.sales;

public enum YesNoEnum {
    /**
     * 发出方式：物流承运/自提
     */
    YES("否", 1),
    NO("是", 2);
    /**
     * 名称
     */
    private String text;
    /**
     * 账号状态
     */
    private Integer value;

    YesNoEnum(String text, Integer value) {
        this.text = text;
        this.value = value;
    }

    /**
     * 通过值来获取名称
     *
     * @param value
     * @return
     */
    public static String getText(Integer value) {
        for (YesNoEnum c : YesNoEnum.values()) {
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

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }
}
