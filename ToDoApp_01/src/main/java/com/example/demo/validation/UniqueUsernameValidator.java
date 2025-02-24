package com.example.demo.validation;

import com.example.demo.repository.UserAccountRepository;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class UniqueUsernameValidator implements ConstraintValidator<UniqueUsername, String> {

	private final UserAccountRepository repository;

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		return repository.findByUsername(value).isEmpty();
	}

}
