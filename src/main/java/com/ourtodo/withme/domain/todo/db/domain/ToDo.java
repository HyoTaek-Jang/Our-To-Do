package com.ourtodo.withme.domain.todo.db.domain;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import com.ourtodo.withme.domain.tag.db.domain.Tag;
import com.ourtodo.withme.global.entity.BaseEntityWithDate;

@Entity
public class ToDo extends BaseEntityWithDate {
	@NotNull
	private String content;

	@NotNull
	private Boolean isCompleted;

	@ManyToOne
	@JoinColumn
	private Tag tag;
}
