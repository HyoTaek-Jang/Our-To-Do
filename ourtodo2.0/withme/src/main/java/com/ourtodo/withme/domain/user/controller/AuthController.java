package com.ourtodo.withme.domain.user.controller;

import javax.mail.MessagingException;
import javax.validation.Valid;
import javax.xml.bind.ValidationException;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ourtodo.withme.domain.user.dto.request.SendCertificationMailRequest;
import com.ourtodo.withme.domain.user.dto.request.SignupRequest;
import com.ourtodo.withme.domain.user.dto.request.VerifyCertificationMailRequest;
import com.ourtodo.withme.domain.user.service.MailCertificationService;
import com.ourtodo.withme.domain.user.service.SignupService;
import com.ourtodo.withme.global.dto.BaseResponse;
import com.ourtodo.withme.global.util.mail.MailService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

	private final SignupService signupService;
	private final MailCertificationService certificationService;
	private final MailService mailService;

	@PostMapping("/signup")
	public ResponseEntity<? extends BaseResponse> signupUser(@Valid @RequestBody SignupRequest signupRequest){
		// ToDo 어떻게 인증유저를 확인할 것인지.. 인증코드를 받을까? 아니면 토큰?
		signupService.signupValid(signupRequest);
		signupService.saveUser(signupRequest);
		return ResponseEntity.status(201).body(new BaseResponse("회원가입을 완료했습니다."));
	}


	@PostMapping("/certification/signup")
	public ResponseEntity<? extends BaseResponse> sendSignupCertificationMail(@Valid @RequestBody SendCertificationMailRequest sendCertificationMailRequest) throws
		MessagingException {
		String code = certificationService.saveMailCertification(sendCertificationMailRequest.getEmail());
		mailService.sendSignUpCertificationMail(sendCertificationMailRequest.getEmail(), code);
		return ResponseEntity.status(201).body(new BaseResponse("인증코드 이메일 발송을 완료했습니다."));
	}

	@PostMapping("/certification/verification/signup")
	public ResponseEntity<? extends BaseResponse> verifySignupCertificationMail(@Valid @RequestBody VerifyCertificationMailRequest verifyCertificationMailRequest){
		boolean result = certificationService.verifyCertification(verifyCertificationMailRequest.getEmail(),
			verifyCertificationMailRequest.getCode());
		if (result)
			return ResponseEntity.status(201).body(new BaseResponse("인증을 성공했습니다."));
		return ResponseEntity.status(409).body(new BaseResponse("인증번호가 잘못 됐습니다."));
	}
}
