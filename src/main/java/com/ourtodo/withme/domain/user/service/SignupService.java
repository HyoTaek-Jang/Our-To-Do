package com.ourtodo.withme.domain.user.service;

import static com.ourtodo.withme.domain.tag.constants.TagConstants.*;
import static com.ourtodo.withme.domain.user.constants.SignupValidationConstants.*;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ourtodo.withme.domain.tag.service.TagService;
import com.ourtodo.withme.domain.user.db.domain.Member;
import com.ourtodo.withme.domain.user.db.repository.MemberRepository;
import com.ourtodo.withme.domain.user.dto.request.SignupRequest;
import com.ourtodo.withme.global.exception.custom.BaseException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SignupService {

	private final MemberRepository memberRepository;
	private final PasswordEncoder passwordEncoder;
	private final TagService tagService;

	@Transactional(readOnly = true)
	public void signupValid(SignupRequest signupRequest) {
		if (!signupRequest.getPassword().equals(signupRequest.getConfirmPassword()))
			throw new BaseException(NOT_SAME_PASSWORD, 400);

		if (memberRepository.findByEmail(signupRequest.getEmail()).isPresent())
			throw new BaseException(IS_EXIST_EMAIL, 409);
	}

	@Transactional
	public Member saveUser(SignupRequest signupRequest) {
		Member member = signupRequest.toEntity(passwordEncoder.encode(signupRequest.getPassword()));
		Member savedMember = memberRepository.save(member);
		tagService.addTag(savedMember, COMMON_TAG, COMMON_COLOR);
		return savedMember;
	}
}
