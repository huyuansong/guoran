package com.guoran.server.common;

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

	public static Result success(Object data) {
		return Result.builder().code("200").msg("success").data(data).build();
	}

	public static Result error() {
		return Result.builder().code("500").msg("error").build();
	}

}
