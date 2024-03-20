package com.ajou.hertz.domain.user.exception;

import com.ajou.hertz.common.exception.ForbiddenException;
import com.ajou.hertz.common.exception.constant.CustomExceptionType;

public class UserIdForbiddenException extends ForbiddenException {

	public UserIdForbiddenException(Long userId, Long loginUserId) {
		super(CustomExceptionType.USER_ID_FORBIDDEN, "userid와" + userId + "userid가" + loginUserId + "다릅니다");
	}
}
