package com.ajou.hertz.common.exception;

import org.springframework.http.HttpStatus;

import com.ajou.hertz.common.exception.constant.CustomExceptionType;

public abstract class ConflictException extends CustomException {

    public ConflictException(CustomExceptionType exceptionType) {
        super(HttpStatus.CONFLICT, exceptionType);
    }

    public ConflictException(CustomExceptionType exceptionType, String optionalMessage) {
        super(HttpStatus.CONFLICT, exceptionType, optionalMessage);
    }

    public ConflictException(CustomExceptionType exceptionType, Throwable cause) {
        super(HttpStatus.CONFLICT, exceptionType, cause);
    }

    public ConflictException(CustomExceptionType exceptionType, String optionalMessage, Throwable cause) {
        super(HttpStatus.CONFLICT, exceptionType, optionalMessage, cause);
    }
}
