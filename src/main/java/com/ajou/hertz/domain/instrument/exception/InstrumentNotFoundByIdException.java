package com.ajou.hertz.domain.instrument.exception;

import com.ajou.hertz.common.exception.NotFoundException;
import com.ajou.hertz.common.exception.constant.CustomExceptionType;

public class InstrumentNotFoundByIdException extends NotFoundException {

	public InstrumentNotFoundByIdException(Long id) {
		super(CustomExceptionType.INSTRUMENT_NOT_FOUND_BY_ID, "id=" + id);
	}
}
