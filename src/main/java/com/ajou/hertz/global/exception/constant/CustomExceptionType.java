package com.ajou.hertz.global.exception.constant;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Error code 목록
 *
 * <ul>
 *     <li>1001 ~ 1999: 일반 예외. 아래 항목에 해당하지 않는 대부분의 예외가 여기에 해당한다.</li>
 *     <li>2000 ~ 2199: 인증 관련 예외</li>
 *     <li>2200 ~ 2399: 유저 관련 예외</li>
 * </ul>
 */
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public enum CustomExceptionType {

	/**
	 * 로그인, 인증 관련 예외
	 */
	ACCESS_DENIED(2000, "접근이 거부되었습니다."),
	UNAUTHORIZED(2001, "유효하지 않은 인증 정보로 인해 인증 과정에서 문제가 발생하였습니다."),
	TOKEN_VALIDATE(2002, "유효하지 않은 token입니다. Token 값이 잘못되었거나 만료되어 유효하지 않은 경우로 token 갱신이 필요합니다."),
	ACCESS_TOKEN_VALIDATE(2003, "유효하지 않은 access token입니다. Token 값이 잘못되었거나 만료되어 유효하지 않은 경우로 token 갱신이 필요합니다."),

	/**
	 * 유저 관련 예외
	 */
	USER_EMAIL_DUPLICATION(2200, "이미 다른 회원이 사용 중인 이메일입니다."),
	;

	private final Integer code;
	private final String message;
}
