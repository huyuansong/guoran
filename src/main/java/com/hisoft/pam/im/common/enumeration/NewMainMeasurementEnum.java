package com.hisoft.pam.im.common.enumeration;
/**
 * 主计量单位枚举
 * 件
 */
public enum NewMainMeasurementEnum {

    PIECE("件","PIECE"),
    BOX("箱", "BOX"),
    TONG("桶","TONG"),
    DAI("袋","DAI"),
    KG("Kg","KG"),
    GE("个","GE"),
    JUAN("卷","JUAN"),
    BAO("包","BAO"),
    HE("盒","HE"),
    SHUANG("双","SHUANG"),
    BA("把","BA"),
    GEN("根","GEN"),
    MI("米","MI"),
    PAN("盘","PAN"),
    DUN("吨","DUN"),
    KUAI("块","KUAI");

    /**
     * 名称
     */
    private String text;
    /**
     * ID
     */
    private String value;


    private NewMainMeasurementEnum(String text, String value ){
        this.text = text;
        this.value =value;
    }


    /**
     * 通过ID来获取名称
     * @param value
     * @return
     */
    public static String getText(String value) {
        for (NewMainMeasurementEnum c : NewMainMeasurementEnum.values()) {
            if (c.getValue().equals(value)) {
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

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

}
