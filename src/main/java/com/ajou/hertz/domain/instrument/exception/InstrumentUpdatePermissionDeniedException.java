package com.ajou.hertz.domain.instrument.exception;

import com.ajou.hertz.common.exception.ForbiddenException;
import com.ajou.hertz.common.exception.constant.CustomExceptionType;

public class InstrumentUpdatePermissionDeniedException extends ForbiddenException {
	public InstrumentUpdatePermissionDeniedException(Long userId, Long instrumentId) {
		super(
			CustomExceptionType.INSTRUMENT_UPDATE_PERMISSION_DENIED,
			String.format("userId=%s instrumentId=%s", userId, instrumentId)
		);
	}
}
