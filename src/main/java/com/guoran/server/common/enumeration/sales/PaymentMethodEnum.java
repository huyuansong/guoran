package com.guoran.server.common.enumeration.sales;

public enum PaymentMethodEnum {
    /**
     * 支付方式
     */
    WECHAT("现金回款", 1),
    ALIPAY("冲预回款", 2);

    private String text;
    private Integer value;

    PaymentMethodEnum(String text, Integer value) {
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
        for (PaymentMethodEnum c : PaymentMethodEnum.values()) {
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
