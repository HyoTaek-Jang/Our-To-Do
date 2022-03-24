package com.ourtodo.withme.domain.user.constants;

public class MailCertificationConstants {
	public static final int MAIL_EXPIRED_TIME = 1000 * 60 * 5; // 5분
	public static final int CODE_LENGTH = 6;

	public static final String IS_NOT_EXIST_EMAIL = "존재하지 않는 이메일입니다.";
	public static final String AFTER_EXPIRED_TIME = "인증시간이 지났습니다.";
}
