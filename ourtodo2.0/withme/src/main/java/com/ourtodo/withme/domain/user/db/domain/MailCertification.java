package com.ourtodo.withme.domain.user.db.domain;

import static com.ourtodo.withme.domain.user.constants.MailCertificationConstants.*;
import static com.ourtodo.withme.domain.user.constants.SignupValidationConstants.*;
import static com.ourtodo.withme.global.constants.CommonValidationConstants.*;

import java.sql.Timestamp;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

import com.ourtodo.withme.global.entity.BaseEntity;

import lombok.Getter;

@Entity
@Getter
public class MailCertification extends BaseEntity {
	@NotNull(message = IS_NULL)
	private String code;

	@NotNull(message = IS_NULL)
	@Temporal(TemporalType.TIMESTAMP)
	private Date expiredTime;

	@NotNull(message = IS_NULL)
	@Email(message = NOT_MATCH_EMAIL)
	private String email;

	public void updateCertification(String code, String email) {
		this.code = code;
		this.email = email;
		this.expiredTime = new Timestamp(System.currentTimeMillis() + MAIL_EXPIRED_TIME);
	}
}
