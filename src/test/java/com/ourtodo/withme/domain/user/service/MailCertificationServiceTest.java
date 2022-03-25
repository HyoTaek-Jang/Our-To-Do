package com.ourtodo.withme.domain.user.service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.ourtodo.withme.BaseTest;
import com.ourtodo.withme.global.exception.custom.ValidationException;

class MailCertificationServiceTest extends BaseTest {

	private final MailCertificationService certificationService;

	@Autowired
	MailCertificationServiceTest(MailCertificationService certificationService) {
		this.certificationService = certificationService;
	}

	@Test
	@DisplayName("이메일 코드 인증 검증 테스트")
	void verifyEmailCertificationCodeTest() {
		String email = "test@naver.com";

		String code = certificationService.saveMailCertification(email);
		boolean result = certificationService.verifyCertification(email, code);

		Assertions.assertThat(result).isTrue();

		email = "notExist@naver.com";

		String finalEmail = email;
		Assertions.assertThatThrownBy(() -> certificationService.verifyCertification(finalEmail, code)).isInstanceOf(
			ValidationException.class);
	}
}