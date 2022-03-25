package com.ourtodo.withme.global.util.mail;

import javax.mail.MessagingException;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.ourtodo.withme.BaseTest;

class MailServiceTest extends BaseTest {

	private final MailService mailService;

	@Autowired
	public MailServiceTest(MailService mailService) {
		this.mailService = mailService;
	}

	@Test
	@DisplayName("이메일 발송 테스트")
	@Disabled
	void sendMailTest() throws MessagingException {
		mailService.sendSignUpCertificationMail("gyxor8582@naver.com", "test code");
	}
}