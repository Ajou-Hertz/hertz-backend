package com.ajou.hertz.domain.user.exception;

import com.ajou.hertz.common.exception.NotFoundException;
import com.ajou.hertz.common.exception.constant.CustomExceptionType;

public class UserNotFoundByIdException extends NotFoundException {
	public UserNotFoundByIdException(Long userId) {
		super(CustomExceptionType.USER_NOT_FOUND_BY_ID, "userId=" + userId);
	}
}
