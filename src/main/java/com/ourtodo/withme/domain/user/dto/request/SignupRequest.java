package com.ourtodo.withme.domain.user.dto.request;

import static com.ourtodo.withme.domain.user.constants.SignupValidationConstants.*;
import static com.ourtodo.withme.global.constants.CommonValidationConstants.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.ourtodo.withme.global.security.token.Authority;
import com.ourtodo.withme.domain.user.db.domain.Member;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class SignupRequest {
	@Email(message = NOT_MATCH_EMAIL)
	@NotNull(message = IS_NULL)
	private String email;

	@NotNull(message = IS_NULL)
	@Size(min = PASSWORD_MIN_LENGTH, message = LESS_THAN_MIN_LENGTH)
	private String password;

	@NotNull(message = IS_NULL)
	private String confirmPassword;

	public Member toEntity(String encryptedPassword) {
		return Member.builder()
			.email(this.email)
			.password(encryptedPassword)
			.authority(Authority.ROLE_USER)
			.build();
	}
}
