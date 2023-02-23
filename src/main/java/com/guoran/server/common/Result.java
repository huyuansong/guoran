package com.guoran.server.common;

import com.guoran.server.common.em.ResultCodeEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Wei
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Result {
	private String code;
	private String msg;
	private Object data;

	public static Result success(Object data) {
		return Result.builder().code(ResultCodeEnum.SUCCESS.getCode()).msg(ResultCodeEnum.SUCCESS.getMsg()).data(data).build();
	}

	public static Result error() {
		return Result.builder().code(ResultCodeEnum.ERROR.getCode()).msg(ResultCodeEnum.ERROR.getMsg()).build();
	}

}
