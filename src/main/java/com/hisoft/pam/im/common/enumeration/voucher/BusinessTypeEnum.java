package com.hisoft.pam.im.common.enumeration.voucher;

/**
 * 业务类型
 * 销售明细
 * 销售退回
 */
public enum BusinessTypeEnum {
    SALDETAL("销售明细",0 ),
    BACKDETAL("销售退回",1);

    /**
     * 名称
     */
    private String text;
    /**
     * 状态
     */
    private int value;


    private BusinessTypeEnum(String text, int value ){
        this.text = text;
        this.value =value;
    }

    /**
     * 通过类型来获取名称
     * @param value
     * @return
     */
    public static String getText(int value) {
        for (BusinessTypeEnum c : BusinessTypeEnum.values()) {
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
