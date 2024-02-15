package com.ajou.hertz.common.exception;

import org.springframework.http.HttpStatus;

import com.ajou.hertz.common.exception.constant.CustomExceptionType;

public abstract class UnauthorizedException extends CustomException {

    public UnauthorizedException(CustomExceptionType exceptionType) {
        super(HttpStatus.UNAUTHORIZED, exceptionType);
    }

    public UnauthorizedException(CustomExceptionType exceptionType, String optionalMessage) {
        super(HttpStatus.UNAUTHORIZED, exceptionType, optionalMessage);
    }

    public UnauthorizedException(CustomExceptionType exceptionType, Throwable cause) {
        super(HttpStatus.UNAUTHORIZED, exceptionType, cause);
    }

    public UnauthorizedException(CustomExceptionType exceptionType, String optionalMessage, Throwable cause) {
        super(HttpStatus.UNAUTHORIZED, exceptionType, optionalMessage, cause);
    }
}
