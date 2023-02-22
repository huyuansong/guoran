package com.hisoft.pam.im.common.enumeration.sales;

public enum InvoiceUploadEnum {
    /**
     * 发票上传状态:未上传，已上传，作废
     */
    NOT_UPLOADE("未上传","1"),
    YES_UPLOADE("已上传","2"),
    DELETE_YES("作废","3");
    private String text;
    private String value;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    InvoiceUploadEnum(String text, String value) {
        this.text = text;
        this.value = value;
    }
    /**
     * 通过值来获取名称
     * @param value
     * @return
     */
    public static String getText(String value) {
        for (InvoiceUploadEnum c : InvoiceUploadEnum.values()) {
            if (c.getValue().equals(value)) {
                return c.text;
            }
        }
        return null;
    }
}
