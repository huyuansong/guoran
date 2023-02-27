package com.guoran.server.common.enumeration;

/**
 * 主计量单位枚举
 * 件
 */
public enum MainMeasurementEnum {

    PIECE("件", 1),
    OX("箱", 2),
    TONG("桶", 3),
    DAI("袋", 4),
    KG("Kg", 5),
    GE("个", 6),
    JUAN("卷", 7),
    BAO("包", 8),
    HE("盒", 9),
    SHUANG("双", 10),
    BA("把", 11),
    GEN("根", 12),
    MI("米", 13),
    PAN("盘", 14),
    DUN("吨", 15);

    /**
     * 名称
     */
    private String text;
    /**
     * ID
     */
    private int value;


    private MainMeasurementEnum(String text, int value) {
        this.text = text;
        this.value = value;
    }


    /**
     * 通过ID来获取名称
     *
     * @param value
     * @return
     */
    public static String getText(int value) {
        for (MainMeasurementEnum c : MainMeasurementEnum.values()) {
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
