package com.ourtodo.withme.domain.tag.dto.request;

import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ChangeTagName {
	@NotNull
	private Long tagId;
	@NotNull
	private String name;
}
