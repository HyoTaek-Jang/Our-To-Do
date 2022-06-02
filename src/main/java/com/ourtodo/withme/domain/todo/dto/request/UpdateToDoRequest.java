package com.ourtodo.withme.domain.todo.dto.request;

import static com.ourtodo.withme.domain.tag.constants.TagValidationConstants.*;
import static com.ourtodo.withme.domain.todo.constants.ToDoValidationConstants.*;

import javax.validation.constraints.NotNull;

import org.springframework.lang.Nullable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UpdateToDoRequest {
	@NotNull(message = NOT_EXIST_CONTENT)
	private String content;

	@NotNull(message = NOT_EXIST_TODO)
	private Long todoId;

	@NotNull(message = NOT_EXIST_TAG)
	private Long tagId;
}
