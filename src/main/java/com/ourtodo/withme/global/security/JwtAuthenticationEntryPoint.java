package com.ourtodo.withme.global.security;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import com.ourtodo.withme.global.dto.BaseResponse;
import com.ourtodo.withme.global.util.ResponseBodyWriteUtil;

@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws
		IOException {
		// 유효한 자격증명을 제공하지 않고 접근하려 할때 401
		ResponseBodyWriteUtil.sendApiResponse(response, 401, new BaseResponse("유효하지 않은 인증입니다."));
	}
}
