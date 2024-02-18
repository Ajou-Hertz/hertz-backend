package com.ajou.hertz.domain.user.exception;

import com.ajou.hertz.common.exception.ConflictException;
import com.ajou.hertz.common.exception.constant.CustomExceptionType;

public class UserKakaoUidDuplicationException extends ConflictException {
	public UserKakaoUidDuplicationException(String kakaoUid) {
		super(CustomExceptionType.USER_KAKAO_UID_DUPLICATION, "kakaoUid=" + kakaoUid);
	}
}
