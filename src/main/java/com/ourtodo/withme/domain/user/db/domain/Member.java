package com.ourtodo.withme.domain.user.db.domain;

import static com.ourtodo.withme.domain.user.constants.SignupValidationConstants.*;
import static com.ourtodo.withme.global.constants.CommonValidationConstants.*;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.ourtodo.withme.domain.tag.db.domain.Tag;
import com.ourtodo.withme.global.entity.BaseEntityWithDate;
import com.ourtodo.withme.global.security.token.Authority;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class Member extends BaseEntityWithDate {
	@NotNull
	private String email;

	@Enumerated(EnumType.STRING)
	private Authority authority;

	@NotNull
	@Size(min = PASSWORD_MIN_LENGTH, message = LESS_THAN_MIN_LENGTH)
	private String password;

	@OneToOne(mappedBy = "member", fetch = FetchType.LAZY)
	private RefreshToken refreshToken;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "member")
	private List<Tag> tagList;
}
