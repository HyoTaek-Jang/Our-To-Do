package com.ourtodo.withme.global.exception.handler;

import static com.ourtodo.withme.domain.user.constants.AuthControllerConstants.*;
import static com.ourtodo.withme.global.constants.CommonValidationConstants.*;

import java.util.Arrays;
import java.util.Objects;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingRequestCookieException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.ourtodo.withme.global.dto.BaseExceptionResponse;
import com.ourtodo.withme.global.dto.BaseResponse;
import com.ourtodo.withme.global.exception.custom.BaseException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {
	@ExceptionHandler(value = MethodArgumentNotValidException.class)
	private ResponseEntity<BaseResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
		String defaultMessage = Objects.requireNonNull(e.getBindingResult().getAllErrors().get(0).getDefaultMessage());;
		return ResponseEntity.status(400).body(new BaseResponse(defaultMessage));
	}

	@ExceptionHandler(value = {BaseException.class, InternalAuthenticationServiceException.class})
	private ResponseEntity<BaseResponse> handleValidationException(BaseException e) {
		return ResponseEntity.status(e.getCode()).body(new BaseResponse(e.getMessage()));
	}

	@ExceptionHandler(value = {BadCredentialsException.class})
	private ResponseEntity<BaseResponse> handleBadCredentialException(BadCredentialsException e) {
		return ResponseEntity.status(409).body(new BaseResponse(NOT_MATCH_PASSWORD));
	}

	@ExceptionHandler(value = {MissingRequestCookieException.class})
	private ResponseEntity<BaseResponse> handleMissingCookieException(MissingRequestCookieException e) {
		return ResponseEntity.status(400).body(new BaseResponse(e.getCookieName() + MISSING_COOKIE));
	}

	@ExceptionHandler(value = Exception.class)
	private ResponseEntity<? extends BaseResponse> handleException(Exception e) {
		log.error("Internal Error Trace : {} ", Arrays.toString(e.getStackTrace()));
		log.error("Error Message : {}",e.getMessage());
		return ResponseEntity.status(500).body(new BaseExceptionResponse(e.getMessage(),
			Arrays.toString(e.getStackTrace())));
	}

}
