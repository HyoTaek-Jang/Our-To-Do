package com.ourtodo.withme.domain.user.dto.request;

import com.ourtodo.withme.domain.user.db.entity.User;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class SignupRequest {
	private String name;
	private String email;
	private String password;
	private String confirmPassword;

	public User toEntity() {
		return new User(null, this.name, this.email, this.password);
	}
}
