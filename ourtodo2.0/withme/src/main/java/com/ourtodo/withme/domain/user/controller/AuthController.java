package com.ourtodo.withme.domain.user.controller;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ourtodo.withme.domain.user.dto.request.SignupRequest;
import com.ourtodo.withme.domain.user.service.SignupService;
import com.ourtodo.withme.global.dto.BaseResponse;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

	private final SignupService signupService;

	@PostMapping("/signup")
	public ResponseEntity<? extends BaseResponse> signupUser(@Valid @RequestBody SignupRequest signupRequest) {
		return null;
	}

}
