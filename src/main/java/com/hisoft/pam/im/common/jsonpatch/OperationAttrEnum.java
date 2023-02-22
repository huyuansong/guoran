package com.hisoft.pam.im.common.jsonpatch;

/**
 * JSON文档关键字
 */
public enum OperationAttrEnum {
	/**
	 * 操作
	 */
	OP("op"),
	/**
	 * 值
	 */
	VALUE("value"),
	/**
	 * 资料路径
	 */
	PATH("path"),
	/**
	 * 源
	 */
	FROM("from");
	private String value;
	OperationAttrEnum(String value){
		this.value=value;
	}
	public String value(){
		return value;
	}
}
