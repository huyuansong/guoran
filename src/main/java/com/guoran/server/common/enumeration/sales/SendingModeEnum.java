package com.guoran.server.common.enumeration.sales;


public enum SendingModeEnum {
    /**
     * 发出方式：物流承运/自提
     */
    LOGISTICS("物流承运", 1),
    PICK_UP("自提", 2);
    /**
     * 名称
     */
    private String text;
    /**
     * 账号状态
     */
    private Integer value;

    SendingModeEnum(String text, Integer value) {
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
        for (SendingModeEnum c : SendingModeEnum.values()) {
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
