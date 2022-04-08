package com.ourtodo.withme.domain.user.dto.response;

import com.ourtodo.withme.global.dto.BaseResponse;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
public class LoginResponse extends BaseResponse {
	private String accessToken;
	private String refreshToken;

	public LoginResponse(String msg, String accessToken, String refreshToken) {
		super(msg);
		this.accessToken = accessToken;
		this.refreshToken = refreshToken;
	}
}
