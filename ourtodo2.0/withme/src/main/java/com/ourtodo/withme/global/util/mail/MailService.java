package com.ourtodo.withme.global.util.mail;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MailService {

	private final JavaMailSender javaMailSender;

	@Async
	public void sendSignUpCertificationMail(String email, String code) throws MessagingException {
		MimeMessage mimeMessage = javaMailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");

		MailForm mailForm = new MailForm();
		String mailContent = mailForm.getCertificationCodeMail(code);

		helper.setFrom("OurToDo"); //보내는사람
		helper.setTo(email); //받는사람
		helper.setSubject("[OurToDo] OurToDo 회원가입 이메일 인증코드입니다."); //메일제목
		helper.setText(mailContent, true); //ture넣을경우 html

		javaMailSender.send(mimeMessage);
	}

}
