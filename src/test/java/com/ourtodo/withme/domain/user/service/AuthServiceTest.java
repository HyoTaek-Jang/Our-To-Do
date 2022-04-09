package com.ourtodo.withme.domain.user.service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.ourtodo.withme.BaseTest;
import com.ourtodo.withme.domain.user.db.domain.Member;
import com.ourtodo.withme.domain.user.db.repository.MemberRepository;
import com.ourtodo.withme.global.security.token.Authority;
import com.ourtodo.withme.global.security.token.TokenDto;
import com.ourtodo.withme.global.security.token.TokenProvider;

class AuthServiceTest extends BaseTest {

	private final MemberRepository memberRepository;
	private final AuthService authService;
	private final TokenProvider tokenProvider;
	private final PasswordEncoder passwordEncoder;

	@Autowired
	public AuthServiceTest(MemberRepository memberRepository,
		AuthService authService, TokenProvider tokenProvider,
		PasswordEncoder passwordEncoder) {
		this.memberRepository = memberRepository;
		this.authService = authService;
		this.tokenProvider = tokenProvider;
		this.passwordEncoder = passwordEncoder;
	}

	@Test
	@DisplayName("로그인 테스트 - 토큰 검증")
	void loginTestWithToken() {
		String email = "test@test.com";
		String password = "a123123123";

		Member member = memberRepository.save(
			Member.builder().authority(Authority.ROLE_USER).email(email).password(passwordEncoder.encode(password)).build());
		TokenDto tokenDto = authService.login(email, password);

		Authentication authentication = tokenProvider.getAuthentication(tokenDto.getAccessToken());

		Assertions.assertThat(authentication.getName()).isEqualTo(String.valueOf(member.getId()));

		password += "dirty";
		String finalPassword = password;
		Assertions.assertThatThrownBy(() -> authService.login(email, finalPassword)).isInstanceOf(
			BadCredentialsException.class);
	}

	@Test
	@DisplayName("토큰 리프래시 테스트")
	void tokenRefreshTest() {
		String email = "test@test.com";
		String password = "a123123123";
		memberRepository.save(
			Member.builder().authority(Authority.ROLE_USER).email(email).password(passwordEncoder.encode(password)).build());

		TokenDto tokenDto = authService.login(email, password);
		TokenDto reissueToken = authService.reissueToken(tokenDto.getAccessToken(), tokenDto.getRefreshToken());

		Assertions.assertThat(tokenProvider.getAuthentication(tokenDto.getAccessToken()).getName())
			.isEqualTo(tokenProvider.getAuthentication(reissueToken.getAccessToken()).getName());

	}
}