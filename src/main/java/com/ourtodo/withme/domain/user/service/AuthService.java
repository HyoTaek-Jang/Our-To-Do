package com.ourtodo.withme.domain.user.service;

import static com.ourtodo.withme.domain.user.constants.MailCertificationConstants.*;

import java.util.Optional;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ourtodo.withme.domain.user.db.domain.Member;
import com.ourtodo.withme.domain.user.db.repository.MemberRepository;
import com.ourtodo.withme.domain.user.db.repository.RefreshTokenRepository;
import com.ourtodo.withme.global.exception.custom.ValidationException;
import com.ourtodo.withme.domain.user.db.domain.RefreshToken;
import com.ourtodo.withme.global.security.token.TokenDto;
import com.ourtodo.withme.global.security.token.TokenProvider;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {
	private final AuthenticationManagerBuilder authenticationManagerBuilder;
	private final TokenProvider tokenProvider;
	private final MemberRepository memberRepository;
	private final RefreshTokenRepository refreshTokenRepository;

	@Transactional
	public TokenDto login(String email, String password) {
		// 1. Login ID/PW 를 기반으로 AuthenticationToken 생성
		UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(email, password);

		// 2. 실제로 검증 (사용자 비밀번호 체크) 이 이루어지는 부분
		//    authenticate 메서드가 실행이 될 때 CustomUserDetailsService 에서 만들었던 loadUserByUsername 메서드가 실행됨
		Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);

		// 3. 인증 정보를 기반으로 JWT 토큰 생성
		TokenDto tokenDto = tokenProvider.generateTokenDto(authentication);

		// 4. RefreshToken 저장
		updateRefreshToken(Long.parseLong(authentication.getName()), tokenDto.getRefreshToken());

		// 5. 토큰 발급
		return tokenDto;
	}

	public void updateRefreshToken(Long memberId, String refreshToken) {
		Member member = memberRepository.findById(memberId).orElseThrow(() -> new ValidationException(IS_NOT_EXIST_EMAIL ,409));
		RefreshToken refreshTokenEntity = refreshTokenRepository.findByMember(member).orElse(null);
		if (refreshTokenEntity == null) {
			refreshTokenEntity = new RefreshToken(refreshToken, member);
		} else {
			refreshTokenEntity.updateRefreshToken(refreshToken);
		}
		refreshTokenRepository.save(refreshTokenEntity);
	}
}
