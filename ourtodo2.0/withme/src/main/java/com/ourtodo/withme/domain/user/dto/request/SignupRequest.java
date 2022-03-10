package com.ourtodo.withme.domain.user.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class SignupRequest {
	private String name;
	private String email;
	private String password;
	private String confirmPassword;
}
