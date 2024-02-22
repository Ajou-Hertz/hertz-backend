package com.ajou.hertz.domain.user.exception;

import com.ajou.hertz.common.exception.NotFoundException;
import com.ajou.hertz.common.exception.constant.CustomExceptionType;

public class UserNotFoundByPhoneException extends NotFoundException {
	public UserNotFoundByPhoneException(String phone) {
		super(CustomExceptionType.USER_NOT_FOUND_BY_PHONE, phone);
	}
}
