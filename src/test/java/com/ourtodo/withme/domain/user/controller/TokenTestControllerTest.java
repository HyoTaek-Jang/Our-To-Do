package com.ourtodo.withme.domain.user.controller;

import org.junit.jupiter.api.Test;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.ourtodo.withme.BaseTest;

class TokenTestControllerTest extends BaseTest {

	final String USER_ID = "1";

	@Test
	@WithMockUser(value = USER_ID)
	void tokenSuccessTest() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/test/token")).andExpect(MockMvcResultMatchers.status().isOk());
	}

	@Test
	@WithAnonymousUser
	void tokenFailTest() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/test/token")).andExpect(MockMvcResultMatchers.status().is4xxClientError());
	}
}