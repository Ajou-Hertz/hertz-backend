package com.ajou.hertz.common.validator;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

/**
 * <p>
 * The string has to be a well-formed password.
 *
 * <p>
 * {@code null} elements are considered valid.
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = PasswordValidator.class)
public @interface Password {

	String message() default "잘못된 비밀번호입니다. 비밀번호 양식을 확인하고 다시 시도해주세요.";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};
}
