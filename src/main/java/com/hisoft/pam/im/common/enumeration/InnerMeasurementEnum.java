package com.hisoft.pam.im.common.enumeration;
/**
 * 内部计量单位枚举
 * 瓶，罐，桶
 */
public enum InnerMeasurementEnum {

    BOTTLE("瓶",1),
    POT("罐",2),
    BUCKET("桶",3),
    DAI("袋",4),
    KG("Kg",5),
    GE("个",6),
    JUAN("卷",7),
    BAO("包",8),
    HE("盒",9),
    SHUANG("双",10),
    BA("把",11),
    GEN("根",12),
    MI("米",13),
    PAN("盘",14),
    DUN("吨",15);

    /**
     * 名称
     */
    private String text;
    /**
     * ID
     */
    private int value;


    private InnerMeasurementEnum(String text, int value ){
        this.text = text;
        this.value =value;
    }


    /**
     * 通过ID来获取名称
     * @param value
     * @return
     */
    public static String getText(int value) {
        for (InnerMeasurementEnum c : InnerMeasurementEnum.values()) {
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
