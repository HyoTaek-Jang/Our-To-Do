package com.ourtodo.withme.domain.user.service;

import static com.ourtodo.withme.domain.user.constants.MailCertificationConstants.*;

import java.util.Date;
import java.util.UUID;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ourtodo.withme.domain.user.db.domain.MailCertification;
import com.ourtodo.withme.domain.user.db.repository.MailCertificationRepository;
import com.ourtodo.withme.global.exception.custom.ValidationException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MailCertificationService {

	private final PasswordEncoder passwordEncoder;
	private final MailCertificationRepository mailCertificationRepository;

	@Transactional
	public String saveMailCertification(String email) {
			MailCertification mailCertification = mailCertificationRepository.findByEmail(email).orElse(new MailCertification());
		String randomCode = createRandomCode(CODE_LENGTH);
		mailCertification.updateCertification(passwordEncoder.encode(randomCode), email);
		mailCertificationRepository.save(mailCertification);
		return randomCode;
	}

	public String createRandomCode(int length) {
		int start = (int) (Math.random() * 27);
		return UUID.randomUUID().toString().replace("-", "").substring(start, start + length);
	}

	@Transactional(readOnly = true)
	public boolean verifyCertification(String email, String code) {
		MailCertification mailCertification = mailCertificationRepository.findByEmail(email)
			.orElseThrow(() -> new ValidationException(IS_NOT_EXIST_EMAIL, 409));
		if (!mailCertification.getCreatedAt().before(new Date(System.currentTimeMillis() + MAIL_EXPIRED_TIME)))
			throw new ValidationException(AFTER_EXPIRED_TIME, 409);
		return passwordEncoder.matches(code, mailCertification.getCode());
	}
}
