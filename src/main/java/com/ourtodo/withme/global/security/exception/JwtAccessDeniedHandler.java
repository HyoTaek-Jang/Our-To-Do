package com.ourtodo.withme.global.security.exception;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import com.ourtodo.withme.global.dto.BaseResponse;
import com.ourtodo.withme.global.util.ResponseBodyWriteUtil;

@Component
public class JwtAccessDeniedHandler implements AccessDeniedHandler {

	@Override
	public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws
		IOException {
		// 필요한 권한이 없이 접근하려 할때 403
		ResponseBodyWriteUtil.sendApiResponse(response, 403, new BaseResponse("접근할 수 없는 권한입니다."));
	}
}
