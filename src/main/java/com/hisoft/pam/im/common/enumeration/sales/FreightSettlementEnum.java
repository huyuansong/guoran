package com.hisoft.pam.im.common.enumeration.sales;


public enum FreightSettlementEnum {
    /**
     * 运费结算方式:我方承担，买方承担
     */
    WE_UNDERTAKE("我方承担",1),
    THE_BUYER_FOR("买方承担",2);

    private String text;
    private Integer value;

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

    FreightSettlementEnum(String text, Integer value) {
        this.text = text;
        this.value = value;
    }
    /**
     * 通过值来获取名称
     * @param value
     * @return
     */
    public static String getText(Integer value) {
        for (FreightSettlementEnum c : FreightSettlementEnum.values()) {
            if (c.getValue().equals(value)) {
                return c.text;
            }
        }
        return null;
    }
}
