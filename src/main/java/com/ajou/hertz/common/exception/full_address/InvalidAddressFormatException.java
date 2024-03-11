package com.ajou.hertz.common.exception.full_address;

import com.ajou.hertz.common.exception.BadRequestException;
import com.ajou.hertz.common.exception.constant.CustomExceptionType;

public class InvalidAddressFormatException extends BadRequestException {
	public InvalidAddressFormatException(CustomExceptionType exceptionType) {
		super(exceptionType);
	}

	public InvalidAddressFormatException(CustomExceptionType exceptionType, String optionalMessage) {
		super(exceptionType, optionalMessage);
	}

	public InvalidAddressFormatException(CustomExceptionType exceptionType, Throwable cause) {
		super(exceptionType, cause);
	}

	public InvalidAddressFormatException(CustomExceptionType exceptionType, String optionalMessage, Throwable cause) {
		super(exceptionType, optionalMessage, cause);
	}
}
