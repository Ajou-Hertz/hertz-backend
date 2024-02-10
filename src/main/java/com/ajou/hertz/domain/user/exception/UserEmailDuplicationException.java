package com.ajou.hertz.domain.user.exception;

import com.ajou.hertz.global.common.exception.ConflictException;
import com.ajou.hertz.global.exception.constant.CustomExceptionType;

public class UserEmailDuplicationException extends ConflictException {

	public UserEmailDuplicationException(String email) {
		super(CustomExceptionType.USER_EMAIL_DUPLICATION, String.format("email=%s", email));
	}
}
