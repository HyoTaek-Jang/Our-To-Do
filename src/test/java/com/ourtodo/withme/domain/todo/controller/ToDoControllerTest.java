package com.ourtodo.withme.domain.todo.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.ourtodo.withme.BaseTest;
import com.ourtodo.withme.domain.todo.dto.request.AddToDoRequest;

class ToDoControllerTest extends BaseTest {

	final String USER_ID = "2";

	@Test
	@DisplayName("ToDo 생성 테스트 - 성공")
	@WithMockUser(value = USER_ID)
	void addTodoSuccessTest() throws Exception {
		AddToDoRequest request = new AddToDoRequest("content", null);
		mockMvc.perform(MockMvcRequestBuilders.post("/todo").contentType(MediaType.APPLICATION_JSON).content(this.objectMapper.writeValueAsString(request))).andExpect(MockMvcResultMatchers.status().is2xxSuccessful());
	}

	@Test
	@DisplayName("ToDo 생성 테스트 - 실패")
	@WithMockUser(value = USER_ID)
	void addTodoFailTest() throws Exception {
		AddToDoRequest request = new AddToDoRequest(null, null);
		mockMvc.perform(MockMvcRequestBuilders.post("/todo").contentType(MediaType.APPLICATION_JSON).content(this.objectMapper.writeValueAsString(request))).andExpect(MockMvcResultMatchers.status().is4xxClientError());
	}

}