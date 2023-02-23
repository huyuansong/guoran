package com.guoran.server.common.em;


/**
 * @author Wei
 */

/*
*   @Description: 返回结果枚举类
*   100-199登录业务异常
*   200-299用户业务异常
*   300-399权限业务异常
*   400-499系统业务异常
*   500-599其他业务异常
* */
public enum ResultCodeEnum {
	/*
	* 成功
	* */
	SUCCESS("200", "success"),
	/*
	 * 失败
	 * */
	ERROR("500", "error")
	;



	private String code;
	private String msg;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	ResultCodeEnum(String s, String success) {
		this.code = s;
		this.msg = success;
	}
}
