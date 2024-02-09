package com.ajou.hertz.global.common.exception;

import com.ajou.hertz.global.exception.constant.CustomExceptionType;
import org.springframework.http.HttpStatus;

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
