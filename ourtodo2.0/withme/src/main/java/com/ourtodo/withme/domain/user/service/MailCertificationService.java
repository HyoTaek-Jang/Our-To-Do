package com.ourtodo.withme.domain.user.service;

import static com.ourtodo.withme.domain.user.constants.MailCertificationConstants.*;

import java.util.UUID;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.ourtodo.withme.domain.user.db.domain.MailCertification;
import com.ourtodo.withme.domain.user.db.repository.MailCertificationRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MailCertificationService {

	private final PasswordEncoder passwordEncoder;
	private final MailCertificationRepository mailCertificationRepository;

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
}
