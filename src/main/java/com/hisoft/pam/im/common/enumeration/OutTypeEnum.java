package com.hisoft.pam.im.common.enumeration;
/**
 * 水表电表类型枚举
 */
public enum OutTypeEnum {

    WATER("形态转换出库",1),
    ELECTRICITY("招待领用",2),
    GAS("损耗出库",3);

    /**
     * 名称
     */
    private String text;
    /**
     * ID
     */
    private int value;


    private OutTypeEnum(String text, int value ){
        this.text = text;
        this.value =value;
    }


    /**
     * 通过ID来获取名称
     * @param value
     * @return
     */
    public static String getText(int value) {
        for (OutTypeEnum c : OutTypeEnum.values()) {
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
