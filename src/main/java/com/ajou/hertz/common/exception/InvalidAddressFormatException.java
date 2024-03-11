package com.ajou.hertz.common.exception;

import com.ajou.hertz.common.exception.constant.CustomExceptionType;

public class InvalidAddressFormatException extends BadRequestException {
	public InvalidAddressFormatException(String address) {
		super(CustomExceptionType.INVALID_ADDRESS_FORMAT, "address=" + address);
	}
}
