package com.hisoft.pam.im.common.search;

/**
 * 查询过滤操作类型
 */
public enum FileterOperatorEnum {
    /**
     * 模糊查询
     */
    LIKE("模糊查询"," like '%{}%'"),
    /**
     * 模糊匹配开始
     */
    STARTWITH("模糊匹配开始"," like '{}%'"),
    /**
     * 模糊匹配结尾
     */
    ENDWITH("模糊匹配结尾"," like '%{}'"),
    /**
     * 相等
     */
    EQUAL("相等","="),
    /**
     * 不相等
     */
    NOTEQUALEQUAL("不相等","<>"),
    /**
     * 大于>
     */
    GREATER("大于",">"),
    /**
     * 大于等于>=
     */
    GREATEROREQUAL("大于等于",">="),
    /**
     * 小于
     */
    LESS("小于","<"),
    /**
     *小于等于 <=
     */
    LESSOREQUAL("小于等于","<="),
    /**
     * 数字或日期之间
     */
    BETWEEN("数字或日期之间"," between {} and {} "),
    /**
     * 在范围内
     */
    IN("在范围内"," in ({})"),
    /**
     * 不在范围内
     */
    NOTIN("不在范围内"," not in ({}) ");
    /**
     * 名称
     */
    private String text;
    /**
     * 账号状态
     */
    private String value;


    private FileterOperatorEnum(String text, String value ){
        this.text = text;
        this.value =value;
    }

    /**
     * 通过值来获取名称
     * @param value
     * @return
     */
    public static String getText(int value) {
        for (FileterOperatorEnum c : FileterOperatorEnum.values()) {
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
