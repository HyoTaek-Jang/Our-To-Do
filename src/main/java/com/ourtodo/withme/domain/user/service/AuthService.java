package com.ourtodo.withme.domain.user.service;

import static com.ourtodo.withme.domain.user.constants.MailCertificationConstants.*;
import static com.ourtodo.withme.domain.user.constants.TokenConstants.*;
import static com.ourtodo.withme.global.security.token.TokenProvider.*;

import java.util.Date;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ourtodo.withme.domain.user.db.domain.Member;
import com.ourtodo.withme.domain.user.db.repository.MemberRepository;
import com.ourtodo.withme.domain.user.db.repository.RefreshTokenRepository;
import com.ourtodo.withme.global.exception.custom.BaseException;
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
		UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(email,
			password);

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

	@Transactional
	public void updateRefreshToken(Long memberId, String refreshToken) {
		Member member = memberRepository.findById(memberId)
			.orElseThrow(() -> new BaseException(IS_NOT_EXIST_EMAIL, 409));
		RefreshToken refreshTokenEntity = refreshTokenRepository.findByMember(member).orElse(null);
		if (refreshTokenEntity == null) {
			refreshTokenEntity = new RefreshToken(refreshToken, member);
		} else {
			refreshTokenEntity.updateRefreshToken(refreshToken);
		}
		refreshTokenRepository.save(refreshTokenEntity);
	}

	@Transactional
	public TokenDto reissueToken(String accessToken, String refreshToken) {
		// 1. Refresh Token 검증
		if (!tokenProvider.validateToken(refreshToken)) {
			throw new BaseException(NOT_VALID_TOKEN, 409);
		}

		// 2. Access Token 에서 Member ID 가져오기
		Authentication authentication = tokenProvider.getAuthentication(accessToken);

		// 3. 저장소에서 Member ID 를 기반으로 Refresh Token 값 가져옴
		Member member = memberRepository.findById(Long.parseLong(authentication.getName()))
			.orElseThrow(() -> new BaseException(IS_NOT_EXIST_EMAIL, 409));
		RefreshToken refreshTokenEntity = refreshTokenRepository.findByMember(member)
			.orElseThrow(() -> new BaseException(NOT_VALID_TOKEN, 409));

		// 4. Refresh Token 일치하는지 검사
		if (!refreshTokenEntity.getRefreshToken().equals(refreshToken)) {
			throw new BaseException(NOT_VALID_TOKEN, 409);
		}

		TokenDto tokenDto = tokenProvider.generateTokenDto(authentication);
		updateRefreshToken(Long.parseLong(authentication.getName()), tokenDto.getRefreshToken());

		return tokenDto;
	}

	public void setRefreshToken(HttpServletResponse response, String refreshToken) {
		Cookie cookie = new Cookie(COOKIE_REFRESH_TOKEN, refreshToken);
		cookie.setHttpOnly(true);
		cookie.setMaxAge((int)REFRESH_TOKEN_EXPIRE_TIME / 1000);
		response.addCookie(cookie);
	}
}
