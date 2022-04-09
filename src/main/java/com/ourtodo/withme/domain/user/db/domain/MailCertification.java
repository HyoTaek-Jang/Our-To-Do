package com.ourtodo.withme.domain.user.db.domain;

import static com.ourtodo.withme.domain.user.constants.SignupValidationConstants.*;
import static com.ourtodo.withme.global.constants.CommonValidationConstants.*;

import javax.persistence.Entity;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

import com.ourtodo.withme.global.entity.BaseEntityWithDate;

import lombok.Getter;

@Entity
@Getter
public class MailCertification extends BaseEntityWithDate {
	@NotNull(message = IS_NULL)
	private String code;

	@NotNull(message = IS_NULL)
	@Email(message = NOT_MATCH_EMAIL)
	private String email;

	public void updateCertification(String code, String email) {
		this.code = code;
		this.email = email;
	}
}
