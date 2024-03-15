package com.ajou.hertz.domain.instrument.exception;

import com.ajou.hertz.common.exception.ForbiddenException;
import com.ajou.hertz.common.exception.constant.CustomExceptionType;

public class InstrumentDeletePermissionDeniedException extends ForbiddenException {

	public InstrumentDeletePermissionDeniedException() {
		super(CustomExceptionType.INSTRUMENT_DELETE_PERMISSION_DENIED);
	}
}
