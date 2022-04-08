package com.ourtodo.withme.global.security.util;

import static com.ourtodo.withme.domain.user.constants.TokenConstants.*;
import static com.ourtodo.withme.global.security.filter.JwtFilter.*;

import javax.servlet.http.HttpServletRequest;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;

import com.ourtodo.withme.global.exception.custom.BaseException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SecurityUtil {
	// SecurityContext 에 유저 정보가 저장되는 시점
	// Request 가 들어올 때 JwtFilter 의 doFilter 에서 저장
	public static Long getCurrentMemberId() {
		final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

		if (authentication == null || authentication.getName() == null) {
			throw new BaseException(NOT_EXIST_SECURITY_CONTEXT, 409);
		}

		return Long.parseLong(authentication.getName());
	}

	public static String resolveToken(HttpServletRequest request) {
		String bearerToken = request.getHeader(AUTHORIZATION_HEADER);
		if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(BEARER_PREFIX)) {
			return bearerToken.substring(7);
		}
		return null;
	}
}