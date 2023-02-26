package com.guoran.server.common;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.guoran.server.common.exception.ImErrorCode;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Result {
	private String code;
	private String msg;
	private Object data;
	private Boolean isSuccess;

	public static Result success(Object data) {
		return Result.builder().code("200").msg("success").data(data).build();
	}

	public static Result error() {
		return Result.builder().code("500").msg("error").build();
	}

	public static String success(String code, String message, Object data) {
		return JSON.toJSONStringWithDateFormat(new Result(code, message, data, true), "yyyy-MM-dd HH:mm:ss", SerializerFeature.DisableCircularReferenceDetect, SerializerFeature.WriteNullStringAsEmpty);

    }
    /**
     * 失败的结果
     * @return
     */
    public static String failed() {
        return failed(ImErrorCode.MSG_FAIL);
    }

    /**
     * 自定义消息的失败结果
     * @param msg
     * @return
     */
    public static String failed(String msg) {
        return failed(ImErrorCode.MSG_FAIL, msg);
    }

    /**
     * 自定义消息编号的失败结果
     * @param code
     * @param msg
     * @return
     */
    public static String failed(String code, String msg) {
        return JSON.toJSONString(new Result(code, msg, null, false));
    }


}
