package com.ourtodo.withme.domain.user.db.entity;

import static com.ourtodo.withme.domain.user.constants.SignupValidationConstants.*;
import static com.ourtodo.withme.global.constants.CommonValidationConstants.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotNull
	private String name;

	@NotNull
	private String email;

	@NotNull
	@Size(min = PASSWORD_MIN_LENGTH, message = LESS_THAN_MIN_LENGTH)
	private String password;
}
