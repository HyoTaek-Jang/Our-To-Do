package com.ourtodo.withme.domain.user.service;

import javax.xml.bind.ValidationException;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.ourtodo.withme.BaseTest;
import com.ourtodo.withme.domain.user.db.repository.UserRepository;
import com.ourtodo.withme.domain.user.dto.request.SignupRequest;

class SignupServiceTest extends BaseTest {

	private final SignupService signupService;
	private final UserRepository userRepository;

	@Autowired
	public SignupServiceTest(SignupService signupService,
		UserRepository userRepository) {
		this.signupService = signupService;
		this.userRepository = userRepository;
	}

	@Test
	@DisplayName("회원가입 Request 유효성 검사 - service logic")
	void signupServiceValidationTest(){
		// notnull - @Valid
		// password, confirm 8자리 이상 - @Valid

		//비밀번호 일치 확인
		//given
		String name = "장효택";
		String email = "hyotaek9812@gmail.com";
		String password = "a123456";
		String nonConfirmPassword = "a123457";

		//when, that
		Assertions.assertThatThrownBy(() -> signupService.signupValid(new SignupRequest(name, email, password, nonConfirmPassword)))
			.isInstanceOf(ValidationException.class);

		// email 중복체크
		//given
		String confirmPassword = "a123456";
		SignupRequest signupRequest = new SignupRequest(name, email, password, confirmPassword);
		//when
		userRepository.save(signupRequest.toEntity());

		//that
		Assertions.assertThatThrownBy(() -> signupService.signupValid(signupRequest))
			.isInstanceOf(ValidationException.class);
	}

	@Test
	@DisplayName("회원가입 Request 유효성 검사 - @Valid logic")
	void signupValidationTest(){
		// notnull - @Valid
		// password, confirm 8자리 이상 - @Valid

		//비밀번호 일치 확인
		//given
		String name = "장효택";
		String email = "hyotaek9812@gmail.com";
		String password = "a123456";
		String nonConfirmPassword = "a123457";

		//when, that
		Assertions.assertThatThrownBy(() -> signupService.signupValid(new SignupRequest(name, email, password, nonConfirmPassword)))
			.isInstanceOf(ValidationException.class);

		// email 중복체크
		//given
		String confirmPassword = "a123456";
		SignupRequest signupRequest = new SignupRequest(name, email, password, confirmPassword);
		//when
		userRepository.save(signupRequest.toEntity());

		//that
		Assertions.assertThatThrownBy(() -> signupService.signupValid(signupRequest))
			.isInstanceOf(ValidationException.class);
	}
}