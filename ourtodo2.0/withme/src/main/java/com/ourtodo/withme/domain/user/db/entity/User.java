package com.ourtodo.withme.domain.user.db.entity;

import static com.ourtodo.withme.domain.user.constants.SignupValidationConstants.*;
import static com.ourtodo.withme.global.constants.CommonValidationConstants.*;

import javax.persistence.Entity;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.ourtodo.withme.global.entity.BaseEntity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class User extends BaseEntity {
	@NotNull
	private String name;

	@NotNull
	private String email;

	@NotNull
	@Size(min = PASSWORD_MIN_LENGTH, message = LESS_THAN_MIN_LENGTH)
	private String password;
}
