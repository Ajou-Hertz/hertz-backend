package com.ajou.hertz.common.exception;

import java.io.IOException;

import com.ajou.hertz.common.exception.constant.CustomExceptionType;

/**
 * <p>I/O 작업에서 문제가 발생했을 때 사용하는 일반적인 exception class.
 * <p>Checked exception을 {@link IOException}을 감싸 처리하는 용도로도 사용한다.
 *
 * @see IOException
 */
public class IOProcessingException extends InternalServerException {

	public IOProcessingException(Throwable cause) {
		super(CustomExceptionType.IO_PROCESSING, cause);
	}
}
