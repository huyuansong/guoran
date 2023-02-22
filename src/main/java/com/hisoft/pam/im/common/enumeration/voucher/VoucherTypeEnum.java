package com.hisoft.pam.im.common.enumeration.voucher;

/**
 * 上传状态枚举
 * 已上传
 * 未上传
 * 上传失败
 */
public enum VoucherTypeEnum {
    COLLECTION_VOUCHER("收款凭证",0 ),
    PAYMENT_VOUCHER("付款凭证",1),
    TRANSFER_VOUCHER("转账凭证",2);

    /**
     * 名称
     */
    private String text;
    /**
     * 状态
     */
    private int value;


    private VoucherTypeEnum(String text, int value ){
        this.text = text;
        this.value =value;
    }

    /**
     * 通过类型来获取名称
     * @param value
     * @return
     */
    public static String getText(int value) {
        for (VoucherTypeEnum c : VoucherTypeEnum.values()) {
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
