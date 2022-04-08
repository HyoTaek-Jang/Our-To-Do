package com.ourtodo.withme.domain.user.controller;

import static com.ourtodo.withme.domain.user.constants.AuthControllerConstants.*;

import javax.mail.MessagingException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.xml.bind.ValidationException;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ourtodo.withme.domain.user.dto.request.LoginRequest;
import com.ourtodo.withme.domain.user.dto.request.SendCertificationMailRequest;
import com.ourtodo.withme.domain.user.dto.request.SignupRequest;
import com.ourtodo.withme.domain.user.dto.request.VerifyCertificationMailRequest;
import com.ourtodo.withme.domain.user.dto.response.LoginResponse;
import com.ourtodo.withme.domain.user.service.AuthService;
import com.ourtodo.withme.domain.user.service.MailCertificationService;
import com.ourtodo.withme.domain.user.service.SignupService;
import com.ourtodo.withme.global.dto.BaseResponse;
import com.ourtodo.withme.global.security.token.TokenDto;
import com.ourtodo.withme.global.security.util.SecurityUtil;
import com.ourtodo.withme.global.util.mail.MailService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

	private final SignupService signupService;
	private final MailCertificationService certificationService;
	private final MailService mailService;
	private final AuthService authService;

	@PostMapping("/signup")
	public ResponseEntity<? extends BaseResponse> signupUser(@Valid @RequestBody SignupRequest signupRequest) {
		signupService.signupValid(signupRequest);
		signupService.saveUser(signupRequest);
		return ResponseEntity.status(201).body(new BaseResponse(SUCCESS_SIGNUP));
	}

	@PostMapping("/certification/signup")
	public ResponseEntity<? extends BaseResponse> sendSignupCertificationMail(
		@Valid @RequestBody SendCertificationMailRequest sendCertificationMailRequest) throws
		MessagingException {
		String code = certificationService.saveMailCertification(sendCertificationMailRequest.getEmail());
		mailService.sendSignUpCertificationMail(sendCertificationMailRequest.getEmail(), code);
		return ResponseEntity.status(201).body(new BaseResponse(SUCCESS_SEND_MAIL));
	}

	@PostMapping("/certification/verification/signup")
	public ResponseEntity<? extends BaseResponse> verifySignupCertificationMail(
		@Valid @RequestBody VerifyCertificationMailRequest verifyCertificationMailRequest) {
		boolean result = certificationService.verifyCertification(verifyCertificationMailRequest.getEmail(),
			verifyCertificationMailRequest.getCode());
		if (result)
			return ResponseEntity.status(201).body(new BaseResponse(SUCCESS_VERIFY));
		return ResponseEntity.status(409).body(new BaseResponse(FAIL_VERIFY));
	}

	@PostMapping("/login")
	public ResponseEntity<? extends BaseResponse> login(@Valid @RequestBody LoginRequest loginRequest, HttpServletResponse response) {
		TokenDto token = authService.login(loginRequest.getEmail(), loginRequest.getPassword());
		authService.setRefreshToken(response, token.getRefreshToken());
		return ResponseEntity.status(201)
			.body(new LoginResponse(SUCCESS_LOGIN, token.getAccessToken()));
	}

	@PostMapping("/refresh")
	public ResponseEntity<? extends BaseResponse> reissueToken(HttpServletRequest request, HttpServletResponse response, @CookieValue(name = "refreshToken") String refreshToken) {
		String accessToken = SecurityUtil.resolveToken(request);
		TokenDto tokenDto = authService.reissueToken(accessToken, refreshToken);
		authService.setRefreshToken(response, tokenDto.getRefreshToken());
		return ResponseEntity.status(201)
			.body(new LoginResponse(SUCCESS_REFRESH, tokenDto.getAccessToken()));
	}
}
