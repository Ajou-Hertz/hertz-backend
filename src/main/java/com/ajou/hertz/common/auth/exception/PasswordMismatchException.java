package com.ajou.hertz.common.auth.exception;

import com.ajou.hertz.common.exception.BadRequestException;
import com.ajou.hertz.common.exception.constant.CustomExceptionType;

public class PasswordMismatchException extends BadRequestException {

	public PasswordMismatchException() {
		super(CustomExceptionType.PASSWORD_MISMATCH);
	}
}
