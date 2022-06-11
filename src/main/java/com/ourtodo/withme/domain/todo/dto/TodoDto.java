package com.ourtodo.withme.domain.todo.dto;

import com.ourtodo.withme.domain.todo.db.domain.ToDo;

import lombok.Getter;

@Getter
public class TodoDto {
	private Long id;
	private String content;

	public TodoDto(ToDo toDo) {
		this.id = toDo.getId();
		this.content = toDo.getContent();
	}
}
