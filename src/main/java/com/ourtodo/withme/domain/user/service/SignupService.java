package com.ourtodo.withme.domain.user.service;

import static com.ourtodo.withme.domain.user.constants.SignupValidationConstants.*;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.ourtodo.withme.domain.user.db.domain.Member;
import com.ourtodo.withme.domain.user.db.repository.MemberRepository;
import com.ourtodo.withme.domain.user.dto.request.SignupRequest;
import com.ourtodo.withme.global.exception.custom.ValidationException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SignupService {

	private final MemberRepository memberRepository;
	private final PasswordEncoder passwordEncoder;

	public void signupValid(SignupRequest signupRequest) {
		if (!signupRequest.getPassword().equals(signupRequest.getConfirmPassword()))
			throw new ValidationException(NOT_SAME_PASSWORD, 400);

		if (memberRepository.findByEmail(signupRequest.getEmail()).isPresent())
			throw new ValidationException(IS_EXIST_EMAIL, 409);
	}

	public Member saveUser(SignupRequest signupRequest) {
		Member member = signupRequest.toEntity(passwordEncoder.encode(signupRequest.getPassword()));
		return memberRepository.save(member);
	}
}
