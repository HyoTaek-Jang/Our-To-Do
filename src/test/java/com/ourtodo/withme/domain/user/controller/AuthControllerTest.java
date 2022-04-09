package com.ourtodo.withme.domain.user.controller;

import static com.ourtodo.withme.domain.user.constants.SignupValidationConstants.*;
import static com.ourtodo.withme.global.constants.CommonValidationConstants.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;

import com.ourtodo.withme.BaseTest;
import com.ourtodo.withme.domain.user.dto.request.SignupRequest;

class AuthControllerTest extends BaseTest {
	@Test
	@DisplayName("회원가입 Request 유효성 검사 - @Valid 로직")
	void signupValidationTest() throws Exception {

		//email 형식 검사
		//given
		String email = "hyotaek9812";
		String password = "a12345678";
		SignupRequest signupRequest = new SignupRequest(email, password, password);

		//when
		ResultActions perform = this.mockMvc.perform(post("/auth/signup").contentType(MediaType.APPLICATION_JSON)
			.content(this.objectMapper.writeValueAsString(signupRequest)));

		//then
		perform.andExpect(status().is4xxClientError()).andExpect(jsonPath("message", String.class).value(NOT_MATCH_EMAIL));

		//password 자릿수 검사
		//given
		email = "hyotaek9812@gmail.com";
		password = "a12345";
		signupRequest = new SignupRequest(email, password, password);

		//when
		perform = this.mockMvc.perform(post("/auth/signup").contentType(MediaType.APPLICATION_JSON)
			.content(this.objectMapper.writeValueAsString(signupRequest)));

		//then
		perform.andExpect(status().is4xxClientError()).andExpect(jsonPath("message", String.class).value(LESS_THAN_MIN_LENGTH));
	}

	@Test
	@DisplayName("회원가입 성공 테스트")
	void signupSuccessTest() throws Exception {
		//given
		String email = "a123@naver.com";
		String password = "a12345678";
		SignupRequest signupRequest = new SignupRequest(email, password, password);

		//when
		ResultActions perform = this.mockMvc.perform(post("/auth/signup").contentType(MediaType.APPLICATION_JSON)
			.content(this.objectMapper.writeValueAsString(signupRequest)));

		//then
		perform.andExpect(status().isCreated());
	}
}