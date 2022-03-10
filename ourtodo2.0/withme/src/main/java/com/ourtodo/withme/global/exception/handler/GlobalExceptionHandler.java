package com.ourtodo.withme.global.exception.handler;

import java.util.Arrays;
import java.util.Objects;

import javax.xml.bind.ValidationException;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.ourtodo.withme.global.dto.BaseResponse;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {
	@ExceptionHandler(value = MethodArgumentNotValidException.class)
	private ResponseEntity<BaseResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
		String defaultMessage = Objects.requireNonNull(e.getBindingResult().getAllErrors().get(0).getDefaultMessage());;
		System.out.println("defaultMessage = " + defaultMessage);
		return ResponseEntity.status(400).body(new BaseResponse(defaultMessage));
	}

	@ExceptionHandler(value = ValidationException.class)
	private ResponseEntity<BaseResponse> handleValidationException(ValidationException e) {
		return ResponseEntity.status(Integer.parseInt(e.getErrorCode())).body(new BaseResponse(e.getMessage()));
	}

	@ExceptionHandler(value = Exception.class)
	private ResponseEntity<BaseResponse> handleException(Exception e) {
		log.error("Internal Error Trace : {} ", Arrays.toString(e.getStackTrace()));
		log.error("Error Message : {}",e.getMessage());
		return ResponseEntity.status(500).body(new BaseResponse(e.getMessage()));
	}

}
