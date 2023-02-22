package com.hisoft.pam.im.common.enumeration;
/**
 * 审核类型枚举
 */
public enum AuditStatusEnum implements BaseEnum{
    /* 未提交   审核中  撤回审核中  已审核  名称   简写*/
    UNSUBMIT("未提交",3),
    INAUDITED("待审核",2),
    WITHDRAWAL_APPLY("撤回",4),
    AUDITED("已提交",1),
    TRUN_DOWN("已驳回",7),
    AUDIT_OFFCE("仓管确认通过",5),
    AUDIT_OFFCE_YES("仓管确认驳回",6),
    AUDIT_OFFCE_LAST("最后通过",8),
    AUDIT_OFFCE_COMMIT("仓管待提交",9),
    FALIURE("作废",10);
//    MONTH_CHANGE("月度计划调整后",15);
//    MONTH_CHANGE_submit("月度计划调整后待审核",16),
//    MONTH_CHANGE_back("月度计划调整后驳回",17);

//    QUAILTY_NO_SUBMIT("质检未提交",11),
//    QUAILTY_REVIEWING("质检审核中",12),
//    QUALITY_PASS("质检通过",13),
//    QUALITY_REJECTED("质检驳回",14);

    /**
     * 文本
     */
    private String text;
    /**
     *  简称
     */
    private int value;

    private AuditStatusEnum(String text,  int value){
        this.text = text;
        this.value =value;
    }

    /**
     * 通过值来获取名称
     * @param value
     * @return
     */
    public static String getText(int value) {
        for (AuditStatusEnum c : AuditStatusEnum.values()) {
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
