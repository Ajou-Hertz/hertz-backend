package com.ajou.hertz.common.kakao.exception;

import org.springframework.http.HttpStatus;

import com.ajou.hertz.common.exception.CustomException;
import com.ajou.hertz.common.exception.constant.CustomExceptionType;
import com.ajou.hertz.common.kakao.dto.response.KakaoErrorResponse;

public class KakaoClientException extends CustomException {

	public KakaoClientException(HttpStatus httpStatus, KakaoErrorResponse kakaoErrorResponse) {
		super(httpStatus, CustomExceptionType.KAKAO_CLIENT, createErrorMessage(kakaoErrorResponse));
	}

	private static String createErrorMessage(KakaoErrorResponse kakaoErrorResponse) {
		return String.format(
			"ErrorInfo=[errorCode=%s, error=%s, errorMessage=%s]",
			kakaoErrorResponse.errorCode(),
			kakaoErrorResponse.error(),
			kakaoErrorResponse.errorDescription()
		);
	}
}
