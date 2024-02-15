package com.ajou.hertz.common.exception;

import org.springframework.http.HttpStatus;

import com.ajou.hertz.common.exception.constant.CustomExceptionType;

public abstract class BadRequestException extends CustomException {

    public BadRequestException(CustomExceptionType exceptionType) {
        super(HttpStatus.BAD_REQUEST, exceptionType);
    }

    public BadRequestException(CustomExceptionType exceptionType, String optionalMessage) {
        super(HttpStatus.BAD_REQUEST, exceptionType, optionalMessage);
    }

    public BadRequestException(CustomExceptionType exceptionType, Throwable cause) {
        super(HttpStatus.BAD_REQUEST, exceptionType, cause);
    }

    public BadRequestException(CustomExceptionType exceptionType, String optionalMessage, Throwable cause) {
        super(HttpStatus.BAD_REQUEST, exceptionType, optionalMessage, cause);
    }
}
