package com.ourtodo.withme.domain.user.service;

import static com.ourtodo.withme.domain.user.constants.SignupValidationConstants.*;

import javax.xml.bind.ValidationException;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ourtodo.withme.domain.user.db.entity.User;
import com.ourtodo.withme.domain.user.db.repository.UserRepository;
import com.ourtodo.withme.domain.user.dto.request.SignupRequest;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class SignupService {

	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;

	public void signupValid(SignupRequest signupRequest) throws ValidationException {
		if (!signupRequest.getPassword().equals(signupRequest.getConfirmPassword()))
			throw new ValidationException(NOT_SAME_PASSWORD, "400");

		if (userRepository.findByEmail(signupRequest.getEmail()) != null)
			throw new ValidationException(IS_EXIST_EMAIL, "409");
	}

	public User saveUser(SignupRequest signupRequest) {
		User user = signupRequest.toEntity(passwordEncoder.encode(signupRequest.getPassword()));
		return userRepository.save(user);
	}
}