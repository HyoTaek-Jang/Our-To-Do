package com.ourtodo.withme.global.security.service;

import static com.ourtodo.withme.domain.user.constants.MailCertificationConstants.*;

import java.util.Collections;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ourtodo.withme.domain.user.db.domain.Member;
import com.ourtodo.withme.domain.user.db.repository.MemberRepository;
import com.ourtodo.withme.global.exception.custom.BaseException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
	private final MemberRepository memberRepository;

	@Override
	@Transactional(readOnly = true)
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		return memberRepository.findByEmail(username)
			.map(this::createUserDetails)
			.orElseThrow(() -> new BaseException(IS_NOT_EXIST_EMAIL, 409));
	}

	// DB 에 User 값이 존재한다면 UserDetails 객체로 만들어서 리턴
	private UserDetails createUserDetails(Member member) {
		GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(member.getAuthority().toString());

		return new User(
			String.valueOf(member.getId()),
			member.getPassword(),
			Collections.singleton(grantedAuthority)
		);
	}
}
