package com.guoran.server.common.enumeration;

/**
 * 题目类型枚举
 */
public enum QuestionTypeEnum implements BaseEnum {

    SINGLE_CHOICE("单选题", 1),
    MULTIPLE_CHOICE("多选题", 2),
    TRUE_OR_FALSE("判断题", 3),
    MULTIPLE_CHOICE_EXPANSION("多选扩充题", 4),
    ESSAY_QUESTION("问答题", 5);

    /**
     * 名称
     */
    private String text;
    /**
     * 账号状态
     */
    private int value;


    private QuestionTypeEnum(String text, int value) {
        this.text = text;
        this.value = value;
    }

    /**
     * 通过账号类型来获取名称
     *
     * @param value
     * @return
     */
    public static String getText(int value) {
        for (QuestionTypeEnum c : QuestionTypeEnum.values()) {
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
