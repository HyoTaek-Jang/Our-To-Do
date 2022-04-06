package com.ourtodo.withme.domain.user.service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.ourtodo.withme.BaseTest;
import com.ourtodo.withme.domain.user.db.domain.Member;
import com.ourtodo.withme.domain.user.db.repository.MemberRepository;
import com.ourtodo.withme.domain.user.dto.request.SignupRequest;
import com.ourtodo.withme.global.exception.custom.ValidationException;

class SignupServiceTest extends BaseTest {

	private final SignupService signupService;
	private final MemberRepository memberRepository;
	private final PasswordEncoder passwordEncoder;

	@Autowired
	public SignupServiceTest(SignupService signupService,
		MemberRepository memberRepository, PasswordEncoder passwordEncoder) {
		this.signupService = signupService;
		this.memberRepository = memberRepository;
		this.passwordEncoder = passwordEncoder;
	}

	@Test
	@DisplayName("회원가입 Request 유효성 검사 - 서비스로직")
	void signupServiceValidationTest(){
		//비밀번호 일치 확인
		//given
		String name = "장효택";
		String email = "hyotaek9812@gmail.com";
		String password = "a1234567";
		String nonConfirmPassword = "a123457";

		//when, that
		Assertions.assertThatThrownBy(() -> signupService.signupValid(new SignupRequest(name, email, password, nonConfirmPassword)))
			.isInstanceOf(ValidationException.class);

		// email 중복체크
		//given
		String confirmPassword = "a1234567";
		SignupRequest signupRequest = new SignupRequest(name, email, password, confirmPassword);
		//when
		memberRepository.save(signupRequest.toEntity(confirmPassword));

		//that
		Assertions.assertThatThrownBy(() -> signupService.signupValid(signupRequest))
			.isInstanceOf(ValidationException.class);
	}

	@Test
	@DisplayName("비밀번호 암호화 검사")
	void encryptPasswordTest(){
		//given
		String name = "장효택";
		String email = "hyotaek9812@gmail.com";
		String password = "a1234567";
		String confirmPassword = "a1234567";
		SignupRequest signupRequest = new SignupRequest(name, email, password, confirmPassword);

		//when
		Member member = signupService.saveUser(signupRequest);

		//that
		Assertions.assertThat(passwordEncoder.matches(password, member.getPassword())).isTrue();
	}
}