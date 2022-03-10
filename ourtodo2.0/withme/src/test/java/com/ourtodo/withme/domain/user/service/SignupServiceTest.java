package com.ourtodo.withme.domain.user.service;

import static com.ourtodo.withme.domain.user.constants.SignupValidationConstants.*;
import static com.ourtodo.withme.global.constants.CommonValidationConstants.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import javax.xml.bind.ValidationException;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;

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
		//비밀번호 일치 확인
		//given
		String name = "장효택";
		String email = "hyotaek9812@gmail.com";
		String password = "a1234567";
		String nonConfirmPassword = "a123457";

		//when, that
		Assertions.assertThatThrownBy(() -> signupService.signupValid(new SignupRequest(name, email, password, nonConfirmPassword)))
			.isInstanceOf(ValidationException.class);

		// email 중복체크
		//given
		String confirmPassword = "a1234567";
		SignupRequest signupRequest = new SignupRequest(name, email, password, confirmPassword);
		//when
		userRepository.save(signupRequest.toEntity());

		//that
		Assertions.assertThatThrownBy(() -> signupService.signupValid(signupRequest))
			.isInstanceOf(ValidationException.class);
	}
}