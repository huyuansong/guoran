package com.hisoft.pam.im.common.enumeration;
/**
 * 水表电表类型枚举
 */
public enum MeterTypeEnum {

    WATER("水表",1),
    ELECTRICITY("电表",2),
    GAS("气表",3);

    /**
     * 名称
     */
    private String text;
    /**
     * ID
     */
    private int value;


    private MeterTypeEnum(String text, int value ){
        this.text = text;
        this.value =value;
    }


    /**
     * 通过ID来获取名称
     * @param value
     * @return
     */
    public static String getText(int value) {
        for (MeterTypeEnum c : MeterTypeEnum.values()) {
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
