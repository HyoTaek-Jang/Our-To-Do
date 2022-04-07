package com.ourtodo.withme.global.dto;

import lombok.Getter;

@Getter
public class BaseExceptionResponse extends BaseResponse{
	private final String trace;

	public BaseExceptionResponse(String msg, String trace) {
		super(msg);
		this.trace = trace;
	}
}
