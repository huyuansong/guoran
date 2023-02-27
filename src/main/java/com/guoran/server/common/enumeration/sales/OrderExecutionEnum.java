package com.guoran.server.common.enumeration.sales;

public enum OrderExecutionEnum {
    /**
     * 订单执行情况
     * 1:未执行
     * 2:执行中
     * 3:执行完毕
     */
    NON_EXECUTION("未执行", 1),
    ZO_EXECUTION("执行中", 2),
    YES_EXECUTION("执行完毕", 3);
    /**
     * 名称
     */
    private String text;
    /**
     * ID
     */
    private int value;

    OrderExecutionEnum(String text, int value) {
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
        for (OrderExecutionEnum c : OrderExecutionEnum.values()) {
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
