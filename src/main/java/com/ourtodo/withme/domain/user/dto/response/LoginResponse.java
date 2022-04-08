package com.ourtodo.withme.domain.user.dto.response;

import com.ourtodo.withme.global.dto.BaseResponse;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
public class LoginResponse extends BaseResponse {
	private String accessToken;

	public LoginResponse(String msg, String accessToken) {
		super(msg);
		this.accessToken = accessToken;
	}
}
