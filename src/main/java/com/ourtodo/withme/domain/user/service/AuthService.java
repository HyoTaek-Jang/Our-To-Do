package com.ourtodo.withme.domain.user.service;

import static com.ourtodo.withme.domain.user.constants.AuthControllerConstants.*;
import static com.ourtodo.withme.domain.user.constants.MailCertificationConstants.*;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ourtodo.withme.domain.user.db.domain.Member;
import com.ourtodo.withme.domain.user.db.repository.MemberRepository;
import com.ourtodo.withme.global.exception.custom.ValidationException;
import com.ourtodo.withme.global.security.domain.RefreshToken;
import com.ourtodo.withme.global.security.token.TokenDto;
import com.ourtodo.withme.global.security.token.TokenProvider;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {
	private final AuthenticationManagerBuilder authenticationManagerBuilder;
	private final TokenProvider tokenProvider;
	private final MemberRepository memberRepository;
	private final PasswordEncoder passwordEncoder;

	@Transactional(readOnly = true)
	public TokenDto login(String email, String password) {
		Member byEmail = memberRepository.findByEmail(email)
			.orElseThrow(() -> new ValidationException(IS_NOT_EXIST_EMAIL, 409));
		if (!passwordEncoder.matches(password, byEmail.getPassword())) {
			throw new ValidationException(NOT_MATCH_PASSWORD, 409);
		}

		// 1. Login ID/PW 를 기반으로 AuthenticationToken 생성
		UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(email, password);


		// 2. 실제로 검증 (사용자 비밀번호 체크) 이 이루어지는 부분
		//    authenticate 메서드가 실행이 될 때 CustomUserDetailsService 에서 만들었던 loadUserByUsername 메서드가 실행됨
		Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);

		// 3. 인증 정보를 기반으로 JWT 토큰 생성
		TokenDto tokenDto = tokenProvider.generateTokenDto(authentication);

		// 4. RefreshToken 저장
		// RefreshToken refreshToken = RefreshToken.builder()
		// 	.key(authentication.getName())
		// 	.value(tokenDto.getRefreshToken())
		// 	.build();

		// refreshTokenRepository.save(refreshToken);

		// 5. 토큰 발급
		return tokenDto;
	}
}
