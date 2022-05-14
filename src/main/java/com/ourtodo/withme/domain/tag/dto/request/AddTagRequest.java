package com.ourtodo.withme.domain.tag.dto.request;

import static com.ourtodo.withme.domain.tag.constants.TagValidationConstants.*;

import javax.validation.constraints.NotNull;

import lombok.Getter;

@Getter
public class AddTagRequest {
	@NotNull(message = NOT_EXIST_NAME)
	private String name;
	@NotNull(message = NOT_EXIST_COLOR)
	private String color;
}
