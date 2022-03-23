package com.ourtodo.withme.domain.user.db.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import com.ourtodo.withme.global.entity.BaseEntity;

@Entity
public class MailCertification extends BaseEntity {
	@NotNull
	private String code;

	@Temporal(TemporalType.TIMESTAMP)
	private Date expiredTime;

	@OneToOne
	@JoinColumn
	private User user;
}
