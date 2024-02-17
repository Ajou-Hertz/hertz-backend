package com.ajou.hertz.domain.user.exception;

import com.ajou.hertz.common.exception.NotFoundException;
import com.ajou.hertz.common.exception.constant.CustomExceptionType;

public class UserNotFoundByEmailException extends NotFoundException {
	public UserNotFoundByEmailException(String email) {
		super(CustomExceptionType.USER_NOT_FOUND_BY_EMAIL, "email=" + email);
	}
}
