package com.ourtodo.withme.domain.user.db.domain;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;

import com.ourtodo.withme.domain.user.db.domain.Member;
import com.ourtodo.withme.global.entity.BaseEntity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class RefreshToken extends BaseEntity {
	@NotNull
	private String refreshToken;
	@OneToOne
	@JoinColumn
	private Member member;

	public void updateRefreshToken(String refreshToken) {
		this.refreshToken = refreshToken;
	}
}
