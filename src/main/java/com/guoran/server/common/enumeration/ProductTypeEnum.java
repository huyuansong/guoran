package com.guoran.server.common.enumeration;

/**
 * 产品类型枚举
 * 产成品
 * 半成品
 * 原果
 */
public enum ProductTypeEnum {

    FINISHEDPRODUCTS("产成品", 1),
    SEMIMANUFACTURES("半成品", 2),
    PRIMARYFRUIT("原果", 3);


    /**
     * 名称
     */
    private String text;
    /**
     * ID
     */
    private int value;


    private ProductTypeEnum(String text, int value) {
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
        for (ProductTypeEnum c : ProductTypeEnum.values()) {
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
