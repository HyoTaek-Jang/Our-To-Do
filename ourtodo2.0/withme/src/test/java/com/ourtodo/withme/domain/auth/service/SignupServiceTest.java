package com.ourtodo.withme.domain.auth.service;

import static org.junit.jupiter.api.Assertions.*;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class SignupServiceTest {
	@Test
	@DisplayName("회원가입 Request 유효성 검사")
	void signupValidationTest() {
		// name, email, password, confirm password
		// notnull
		// password 8자리 이상
		// password, confirm equal
		// email 중복체크

		// pass case
		// given
		SignupService signupService = new SignupService();
		SignupRequest signupRequest = new SignupRequest("장효택", "hyotaek9812@gmail.com", "a123456", "a123456");

		// when
		boolean expectTrue = signupService.signupVaild(signupRequest);

		// then
		Assertions.assertThat(expectTrue).isTrue();
	}

	private class SignupRequest {
		private String name;
		private String email;
		private String password;
		private String confirmPassword;

		public SignupRequest(String name, String email, String password, String confirmPassword) {
			this.name = name;
			this.email = email;
			this.password = password;
			this.confirmPassword = confirmPassword;
		}
	}
}