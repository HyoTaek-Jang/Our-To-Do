package com.ourtodo.withme.domain.user.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ourtodo.withme.global.dto.BaseResponse;
import com.ourtodo.withme.global.security.util.SecurityUtil;

@RestController
@RequestMapping("/test")
public class TokenTestController {

	@GetMapping("/token")
	public ResponseEntity<BaseResponse> tokenTest(HttpServletRequest request) {
		Long currentMemberId = SecurityUtil.getCurrentMemberId();
		return ResponseEntity.ok(new BaseResponse(String.valueOf(currentMemberId)));
	}
}
