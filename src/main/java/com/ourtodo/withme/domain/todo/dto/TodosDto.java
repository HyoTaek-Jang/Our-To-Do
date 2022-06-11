package com.ourtodo.withme.domain.todo.dto;

import java.util.List;

import com.ourtodo.withme.domain.tag.db.domain.Tag;

import lombok.Getter;

@Getter
public class TodosDto {
	private String tagName;
	private String tagColor;
	private List<TodoDto> todos;

	public TodosDto(Tag tag, List<TodoDto> todos) {
		this.tagName = tag.getName();
		this.tagColor = tag.getColor();
		this.todos = todos;
	}
}
