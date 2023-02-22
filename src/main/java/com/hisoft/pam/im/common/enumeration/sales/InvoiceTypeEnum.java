package com.hisoft.pam.im.common.enumeration.sales;


public enum InvoiceTypeEnum {
    /**
     * 1:增值税专用发票/2增值税普通发票/3电子发票
     */
    SPECIAL_PURPOSE_INVOICE("增值税专用发票",1),
    ORDINARY_INVOICE("增值税普通发票",2),
    ELECTRONIC_INVOICE("电子发票",3);

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

    InvoiceTypeEnum(String text, Integer value) {
        this.text = text;
        this.value = value;
    }
    /**
     * 通过值来获取名称
     * @param value
     * @return
     */
    public static String getText(Integer value) {
        for (InvoiceTypeEnum c : InvoiceTypeEnum.values()) {
            if (c.getValue().equals(value)) {
                return c.text;
            }
        }
        return null;
    }
}
