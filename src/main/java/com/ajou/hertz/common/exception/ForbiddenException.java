package com.ajou.hertz.common.exception;

import org.springframework.http.HttpStatus;

import com.ajou.hertz.common.exception.constant.CustomExceptionType;

public abstract class ForbiddenException extends CustomException {

    public ForbiddenException(CustomExceptionType exceptionType) {
        super(HttpStatus.FORBIDDEN, exceptionType);
    }

    public ForbiddenException(CustomExceptionType exceptionType, String optionalMessage) {
        super(HttpStatus.FORBIDDEN, exceptionType, optionalMessage);
    }

    public ForbiddenException(CustomExceptionType exceptionType, Throwable cause) {
        super(HttpStatus.FORBIDDEN, exceptionType, cause);
    }

    public ForbiddenException(CustomExceptionType exceptionType, String optionalMessage, Throwable cause) {
        super(HttpStatus.FORBIDDEN, exceptionType, optionalMessage, cause);
    }
}
