package com.ajou.hertz.common.exception;

import org.springframework.http.HttpStatus;

import com.ajou.hertz.common.exception.constant.CustomExceptionType;

public abstract class InternalServerException extends CustomException {

    public InternalServerException(CustomExceptionType exceptionType) {
        super(HttpStatus.INTERNAL_SERVER_ERROR, exceptionType);
    }

    public InternalServerException(CustomExceptionType exceptionType, String optionalMessage) {
        super(HttpStatus.INTERNAL_SERVER_ERROR, exceptionType, optionalMessage);
    }

    public InternalServerException(CustomExceptionType exceptionType, Throwable cause) {
        super(HttpStatus.INTERNAL_SERVER_ERROR, exceptionType, cause);
    }

    public InternalServerException(CustomExceptionType exceptionType, String optionalMessage, Throwable cause) {
        super(HttpStatus.INTERNAL_SERVER_ERROR, exceptionType, optionalMessage, cause);
    }
}
