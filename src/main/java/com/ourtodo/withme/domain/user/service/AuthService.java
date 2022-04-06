package com.ourtodo.withme.domain.user.service;

import static com.ourtodo.withme.domain.user.constants.AuthControllerConstants.*;
import static com.ourtodo.withme.domain.user.constants.MailCertificationConstants.*;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.ourtodo.withme.domain.user.db.domain.Member;
import com.ourtodo.withme.domain.user.db.repository.MemberRepository;
import com.ourtodo.withme.global.exception.custom.ValidationException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {

	private final MemberRepository memberRepository;
	private final PasswordEncoder passwordEncoder;

	public void login(String email, String password) {
		Member byEmail = memberRepository.findByEmail(email)
			.orElseThrow(() -> new ValidationException(IS_NOT_EXIST_EMAIL, 409));
		if (!passwordEncoder.matches(password, byEmail.getPassword())) {
			throw new ValidationException(NOT_MATCH_PASSWORD, 409);
		}
	}
}
