package com.ourtodo.withme.domain.tag.db.domain;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import com.ourtodo.withme.domain.user.db.domain.Member;
import com.ourtodo.withme.global.entity.BaseEntityWithDate;

@Entity
public class Tag extends BaseEntityWithDate {
	@NotNull
	private String name;

	@NotNull
	private String color;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn
	private Member member;
}
