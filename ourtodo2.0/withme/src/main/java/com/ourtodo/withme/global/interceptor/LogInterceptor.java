package com.ourtodo.withme.global.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.AsyncHandlerInterceptor;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
	public class LogInterceptor implements AsyncHandlerInterceptor {
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
		log.info("Method : {} Req URL : {}", request.getMethod(), request.getRequestURL());
		return true;
	}
}
