package com.hisoft.pam.im.common.enumeration.sales;

public enum InvoiceStatus {
    /**
     * 开票状态：未开具，已开具，作废,全部开具完成
     */
    INVOICE_STATUS_NO("未开具",1),
    INVOICE_STATUS_YES("已开具",2),
    ZUOFEI("作废",3),
    YES_KAIJU("已全部开具",4);
    /**
     * 名称
     */
    private String text;
    /**
     * 账号状态
     */
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

    InvoiceStatus(String text, Integer value) {
        this.text = text;
        this.value = value;
    }
    /**
     * 通过值来获取名称
     * @param value
     * @return
     */
    public static String getText(Integer value) {
        for (InvoiceStatus c : InvoiceStatus.values()) {
            if (c.getValue().equals(value)) {
                return c.text;
            }
        }
        return null;
    }
}
