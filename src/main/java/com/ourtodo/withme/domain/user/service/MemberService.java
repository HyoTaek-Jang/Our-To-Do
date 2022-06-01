package com.ourtodo.withme.domain.user.service;

import static com.ourtodo.withme.domain.user.constants.MemberConstants.*;

import org.springframework.stereotype.Service;

import com.ourtodo.withme.domain.user.db.domain.Member;
import com.ourtodo.withme.domain.user.db.repository.MemberRepository;
import com.ourtodo.withme.global.exception.custom.BaseException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MemberService {

	private final MemberRepository memberRepository;

	public Member findMemberById(Long memberId) {
		return memberRepository.findById(memberId).orElseThrow(() -> new BaseException(NOT_EXIST_MEMBER, 401));
	}
}
