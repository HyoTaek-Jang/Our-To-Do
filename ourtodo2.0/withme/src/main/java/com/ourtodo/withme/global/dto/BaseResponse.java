package com.ourtodo.withme.global.dto;

import lombok.Getter;

@Getter
public class BaseResponse {
	String message;

	public BaseResponse(String msg) {
		this.message = msg;
	}
}
