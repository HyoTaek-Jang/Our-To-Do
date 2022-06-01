package com.ourtodo.withme.domain.tag.dto.request;

import javax.validation.constraints.NotNull;

import lombok.Getter;

@Getter
public class ChangeTagColor {
	@NotNull
	private Long tagId;
	@NotNull
	private String color;
}
