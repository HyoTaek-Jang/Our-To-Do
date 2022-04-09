package com.ourtodo.withme.domain.user.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.ourtodo.withme.BaseTest;

class TokenTestControllerTest extends BaseTest {

	final String USER_ID = "1";

	@Test
	@DisplayName("스프링 시큐리티 인증 테스트 - 성공")
	@WithMockUser(value = USER_ID)
	void tokenSuccessTest() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/test/token")).andExpect(MockMvcResultMatchers.status().isOk());
	}

	@Test
	@DisplayName("스프링 시큐리티 인증 테스트 - 실패")
	@WithAnonymousUser
	void tokenFailTest() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/test/token")).andExpect(MockMvcResultMatchers.status().is4xxClientError());
	}
}