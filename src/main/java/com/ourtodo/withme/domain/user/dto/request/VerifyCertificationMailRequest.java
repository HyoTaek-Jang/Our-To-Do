package com.ourtodo.withme.domain.user.dto.request;

import static com.ourtodo.withme.domain.user.constants.SignupValidationConstants.*;
import static com.ourtodo.withme.global.constants.CommonValidationConstants.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

import lombok.Getter;

@Getter
public class VerifyCertificationMailRequest {
	@Email(message = NOT_MATCH_EMAIL)
	@NotNull(message = IS_NULL)
	private String email;

	@NotNull(message = IS_NULL)
	private String code;
}
