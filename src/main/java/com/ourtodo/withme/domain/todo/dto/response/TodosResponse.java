package com.ourtodo.withme.domain.todo.dto.response;

import java.util.List;

import com.ourtodo.withme.domain.todo.dto.TodosDto;
import com.ourtodo.withme.global.dto.BaseResponse;

import lombok.Getter;

@Getter
public class TodosResponse extends BaseResponse {
	List<TodosDto> todos;

	public TodosResponse(String msg, List<TodosDto> todos) {
		super(msg);
		this.todos = todos;
	}
}
