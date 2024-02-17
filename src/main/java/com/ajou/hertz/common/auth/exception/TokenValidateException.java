package com.ajou.hertz.common.auth.exception;

import com.ajou.hertz.common.exception.UnauthorizedException;
import com.ajou.hertz.common.exception.constant.CustomExceptionType;

public class TokenValidateException extends UnauthorizedException {

	public TokenValidateException(String optionalMessage, Throwable cause) {
		super(CustomExceptionType.TOKEN_VALIDATE, optionalMessage, cause);
	}
}
