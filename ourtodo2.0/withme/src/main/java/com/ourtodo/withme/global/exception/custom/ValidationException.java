package com.ourtodo.withme.global.exception.custom;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ValidationException extends RuntimeException{
	private String message;
	private int code;
}
