package com.ourtodo.withme.domain.user.service;

import static com.ourtodo.withme.global.constants.ValidationConstants.*;

import javax.xml.bind.ValidationException;

import org.springframework.stereotype.Service;

import com.ourtodo.withme.domain.user.db.repository.UserRepository;
import com.ourtodo.withme.domain.user.dto.request.SignupRequest;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SignupService {

	private final UserRepository userRepository;

	public void signupValid(SignupRequest signupRequest) throws ValidationException {
		if (!signupRequest.getPassword().equals(signupRequest.getConfirmPassword()))
			throw new ValidationException(notSamePassword, "400");

		if (userRepository.findByEmail(signupRequest.getEmail()) != null)
			throw new ValidationException(existEmail, "409");
	}
}
