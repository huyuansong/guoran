package com.hisoft.pam.im.common.enumeration.customer;

/**
 * 账号类型枚举
 */
public enum CustomerFileEnum {
    /* 客户文件标识  状态
     *
      * */
    ID_CARD_FRONT("身份证正面",1 ),
    ID_CARD_BEHIND("身份证反面",2),
    BANK_CARD_FRONT("银行卡正面",3 ),
    BANK_CARD_BEHIND("银行卡反面",4),
    THE_BISNESS_LICENSE("营业执照",5 ),
    ACCOUNT_OPENING_PERMIT("开户许可证",6);

    /**
     * 名称
     */
    private String text;
    /**
     * 账号状态
     */
    private int value;


    private CustomerFileEnum(String text, int value ){
        this.text = text;
        this.value =value;
    }

    /**
     * 通过账号类型来获取名称
     * @param value
     * @return
     */
    public static String getText(int value) {
        for (CustomerFileEnum c : CustomerFileEnum.values()) {
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
