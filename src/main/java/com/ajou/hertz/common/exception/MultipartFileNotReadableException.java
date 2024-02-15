package com.ajou.hertz.common.exception;

import com.ajou.hertz.common.exception.constant.CustomExceptionType;

public class MultipartFileNotReadableException extends BadRequestException {

	public MultipartFileNotReadableException(Throwable cause) {
		super(CustomExceptionType.MULTIPART_FILE_NOT_READABLE, cause);
	}
}
