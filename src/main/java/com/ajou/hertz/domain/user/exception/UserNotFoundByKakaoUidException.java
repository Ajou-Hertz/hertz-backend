package com.ajou.hertz.domain.user.exception;

import com.ajou.hertz.common.exception.NotFoundException;
import com.ajou.hertz.common.exception.constant.CustomExceptionType;

public class UserNotFoundByKakaoUidException extends NotFoundException {
	public UserNotFoundByKakaoUidException(String kakaoUid) {
		super(CustomExceptionType.USER_KAKAO_UID_DUPLICATION, "kakaoUid=" + kakaoUid);
	}
}
