package com.ourtodo.withme.domain.todo.dto.request;

import static com.ourtodo.withme.domain.todo.constants.ToDoValidationConstants.*;

import javax.validation.constraints.NotNull;

import org.springframework.lang.Nullable;

import lombok.Getter;

@Getter
public class AddToDoRequest {
	@NotNull(message = NOT_EXIST_CONTENT)
	private String content;
	@Nullable
	private Long tagId;
}
