package com.ourtodo.withme.domain.todo.db.domain;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import com.ourtodo.withme.domain.tag.db.domain.Tag;
import com.ourtodo.withme.global.entity.BaseEntityWithDate;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class ToDo extends BaseEntityWithDate {
	@NotNull
	private String content;

	@NotNull
	private Boolean isCompleted;

	@ManyToOne
	@JoinColumn
	@NotNull
	private Tag tag;

	@NotNull
	private Long ordering;

	public void updateTag(Tag tag) {
		this.tag = tag;
	}

	public void updateContent(String content) {
		this.content = content;
	}

	public void toggleIsCompleted() {
		this.isCompleted = !this.isCompleted;
	}
}
