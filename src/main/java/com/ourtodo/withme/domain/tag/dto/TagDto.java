package com.ourtodo.withme.domain.tag.dto;

import com.ourtodo.withme.domain.tag.db.domain.Tag;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
public class TagDto {
	private Long id;
	private String name;
	private String color;

	public TagDto(Tag tag) {
		this.id = tag.getId();
		this.name = tag.getName();
		this.color = tag.getColor();
	}
}
