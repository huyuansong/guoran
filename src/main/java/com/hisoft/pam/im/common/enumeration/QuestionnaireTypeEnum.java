package com.hisoft.pam.im.common.enumeration;

public enum QuestionnaireTypeEnum implements BaseEnum {

    SINGLE_CHOICE("单选题",1 ),
    MULTIPLE_CHOICE("多选题",2),
    FILL_IN_BLANK("填空题",3),
    MULTIPLE_ENTRY("多项填空",4),
    HORIZONTAL_FILL("横向填空",5),
    IMG_CHOICE("图片选择",6),
    ESSAY_QUESTION("打分题",7),
    GRADING_QUESTION("评价题",8),
    ADMINISTRATIVE("城市地址",9);


    /**
     * 题型名称
     */
    private String text;
    /**
     * 类型code
     */

    private int value;

    QuestionnaireTypeEnum(String text, int value) {
        this.text = text;
        this.value = value;
    }

    /**
     * 获取题目类型
     * @param value
     */
    public static String getText(int value){
        for(QuestionnaireTypeEnum c : QuestionnaireTypeEnum.values()){
            if(c.getValue() == value){
                return c.text;
            }
        }
        return null;
    }

    @Override
    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}
