package com.ourtodo.withme.domain.user.db.domain;

import static com.ourtodo.withme.domain.user.constants.MailCertificationConstants.*;
import static com.ourtodo.withme.domain.user.constants.SignupValidationConstants.*;

import java.sql.Timestamp;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

import com.ourtodo.withme.global.entity.BaseEntity;

@Entity
public class MailCertification extends BaseEntity {
	@NotNull
	private String code;

	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	private Date expiredTime;

	@NotNull
	@Email(message = NOT_MATCH_EMAIL)
	private String email;

	public void updateCertification(String code, String email) {
		this.code = code;
		this.email = email;
		this.expiredTime = new Timestamp(System.currentTimeMillis() + MAIL_EXPIRED_TIME);
	}
}
