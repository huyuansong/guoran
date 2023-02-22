package com.hisoft.pam.im.common.enumeration.voucher;

/**
 * 上传状态枚举
 * 已上传
 * 未上传
 * 上传失败
 */
public enum UploadStateEnum {
    UPLOADED("已上传",0 ),
    UNUPLOAD("未上传",1),
    UPLOADFAIL("上传失败",2);

    /**
     * 名称
     */
    private String text;
    /**
     * 状态
     */
    private int value;


    private UploadStateEnum(String text, int value ){
        this.text = text;
        this.value =value;
    }

    /**
     * 通过类型来获取名称
     * @param value
     * @return
     */
    public static String getText(int value) {
        for (UploadStateEnum c : UploadStateEnum.values()) {
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
