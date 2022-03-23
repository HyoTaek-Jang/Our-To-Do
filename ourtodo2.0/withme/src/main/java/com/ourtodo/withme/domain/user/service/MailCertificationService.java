package com.ourtodo.withme.domain.user.service;

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

	public void saveMailCertification(String email) {
		MailCertification byEmail = mailCertificationRepository.findByEmail(email).orElse(new MailCertification());
	}
}
