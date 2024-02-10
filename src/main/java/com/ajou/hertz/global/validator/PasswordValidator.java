package com.ajou.hertz.global.validator;

import java.util.regex.Pattern;

import org.springframework.stereotype.Component;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

@Component
public class PasswordValidator implements ConstraintValidator<Password, String> {

	@Override
	public void initialize(Password constraintAnnotation) {
		ConstraintValidator.super.initialize(constraintAnnotation);
	}

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		if (value == null) {
			return true;
		}
		return Pattern.matches("^(?=.*[A-Za-z])(?=.*\\d)(?=.*[!@#$%^*+=-]).{8,16}$", value);
	}
}
