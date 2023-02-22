package com.hisoft.pam.im.common.enumeration;
/**
 * 生产渠道枚举
 * 外购
 * 自产
 */
public enum ProductionChannelsEnum {

    OUTSOURCING("外购",1),
    SELFPRODUCED("自产",2);

    /**
     * 名称
     */
    private String text;
    /**
     * ID
     */
    private int value;


    private ProductionChannelsEnum(String text, int value ){
        this.text = text;
        this.value =value;
    }


    /**
     * 通过ID来获取名称
     * @param value
     * @return
     */
    public static String getText(int value) {
        for (ProductionChannelsEnum c : ProductionChannelsEnum.values()) {
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
