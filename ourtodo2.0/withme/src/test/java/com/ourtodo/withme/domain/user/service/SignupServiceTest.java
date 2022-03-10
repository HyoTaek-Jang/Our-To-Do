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
	void signupValidationTest() throws Exception {

		//email 형식 검사
		//given
		String name = "장효택";
		String email = "hyotaek9812@gmail.com";
		String password = "a123456";
		SignupRequest signupRequest = new SignupRequest(name, email, password, password);

		//when
		ResultActions perform = this.mockMvc.perform(post("/auth/signup").contentType(MediaType.APPLICATION_JSON)
			.content(this.objectMapper.writeValueAsString(signupRequest)));

		//then
		perform.andExpect(status().is4xxClientError()).andExpect(jsonPath("message", String.class).value(notMatchEmail));

		//password 자릿수 검사
		//given
		password = "a12345";
		signupRequest = new SignupRequest(name, email, password, password);

		//when
		perform = this.mockMvc.perform(post("/auth/signup").contentType(MediaType.APPLICATION_JSON)
			.content(this.objectMapper.writeValueAsString(signupRequest)));

		//then
		perform.andExpect(status().is4xxClientError()).andExpect(jsonPath("message", String.class).value(lessThanMinLength));
	}
}