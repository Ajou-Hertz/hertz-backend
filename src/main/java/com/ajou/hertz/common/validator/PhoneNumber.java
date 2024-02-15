package com.ajou.hertz.common.validator;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

/**
 * <p>
 * The string has to be a well-formed phone number.
 *
 * <p>
 * {@code null} elements are considered valid.
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = PhoneNumberValidator.class)
public @interface PhoneNumber {

	String message() default "잘못된 번호입니다. 전화번호 양식을 확인 후 다시 시도해주세요.";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};
}
