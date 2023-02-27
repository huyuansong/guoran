package com.guoran.server.common.enumeration.voucher;

/**
 * 借贷方向
 * 借款
 * 贷款
 */
public enum borrowingDirectionEnum {
    BORROWER("借方", 1),
    LENDER("贷方", 2);

    /**
     * 名称
     */
    private String text;
    /**
     * 状态
     */
    private int value;


    private borrowingDirectionEnum(String text, int value) {
        this.text = text;
        this.value = value;
    }

    /**
     * 通过类型来获取名称
     *
     * @param value
     * @return
     */
    public static String getText(int value) {
        for (borrowingDirectionEnum c : borrowingDirectionEnum.values()) {
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
