package com.ourtodo.withme.domain.tag.controller;

import static com.ourtodo.withme.global.constants.CommonValidationConstants.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.ResultActions;

import com.ourtodo.withme.BaseTest;
import com.ourtodo.withme.domain.tag.dto.request.AddTagRequest;

class TagControllerTest extends BaseTest {
	final String USER_ID = "1";

	@Test
	@DisplayName("태그 생성 테스트 - 성공")
	@WithMockUser(value = USER_ID)
	void addTagSuccessTest() throws Exception {
		//given
		String name = "테스트 태그 이름";
		String color = "#FFFFFF";
		AddTagRequest tagRequest = new AddTagRequest(name, color);

		//when
		ResultActions perform = this.mockMvc.perform(post("/tag").contentType(MediaType.APPLICATION_JSON)
			.content(this.objectMapper.writeValueAsString(tagRequest)));

		//then
		perform.andExpect(status().isCreated());
	}

	@Test
	@DisplayName("태그 생성 테스트 - 실패")
	@WithMockUser(value = USER_ID)
	void addTagFailTest() throws Exception {
		//given
		String name = "테스트 태그 이름";
		String color = null;
		AddTagRequest tagRequest = new AddTagRequest(name, color);

		//when
		ResultActions perform = this.mockMvc.perform(post("/tag").contentType(MediaType.APPLICATION_JSON)
			.content(this.objectMapper.writeValueAsString(tagRequest)));

		//then
		perform.andExpect(status().is4xxClientError());

		name = null;
		color = "#FFFFF";
		tagRequest = new AddTagRequest(name, color);

		//when
		perform = this.mockMvc.perform(post("/tag").contentType(MediaType.APPLICATION_JSON)
			.content(this.objectMapper.writeValueAsString(tagRequest)));

		//then
		perform.andExpect(status().is4xxClientError());
	}

	@Test
	@DisplayName("태그 조회 테스트 - 비어있는 리스트")
	@WithMockUser(value = USER_ID)
	void findTagListTest() throws Exception {
		//when
		ResultActions perform = this.mockMvc.perform(get("/tag/list"));

		//then
		perform.andExpect(status().isOk()).andExpect(jsonPath("tagList", List.class).isEmpty());
	}
}