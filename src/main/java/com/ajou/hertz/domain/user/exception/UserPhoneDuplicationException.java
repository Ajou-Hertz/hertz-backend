package com.ajou.hertz.domain.user.exception;

import com.ajou.hertz.common.exception.ConflictException;
import com.ajou.hertz.common.exception.constant.CustomExceptionType;

public class UserPhoneDuplicationException extends ConflictException {

	public UserPhoneDuplicationException(String phone) {
		super(CustomExceptionType.USER_PHONE_DUPLICATION, "phone=" + phone);
	}
}
