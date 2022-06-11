package com.ourtodo.withme.domain.tag.db.domain;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;

import com.ourtodo.withme.domain.todo.db.domain.ToDo;
import com.ourtodo.withme.domain.user.db.domain.Member;
import com.ourtodo.withme.global.entity.BaseEntityWithDate;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class Tag extends BaseEntityWithDate {
	@NotNull
	private String name;

	@NotNull
	private String color;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn
	private Member member;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "tag")
	private List<ToDo> toDoList;

	public void updateName(String name) {
		this.name = name;
	}

	public void updateColor(String color) {
		this.color = color;
	}
}
