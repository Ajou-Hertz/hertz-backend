package com.ajou.hertz.common.exception.constant;

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
 *     <li>2400 ~ 2599: 주소 관련 예외</li>
 *     <li>2600 ~ 2799: 악기 관련 예외</li>
 * </ul>
 */
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public enum CustomExceptionType {

	MULTIPART_FILE_NOT_READABLE(1000, "파일을 읽을 수 없습니다. 올바른 파일인지 다시 확인 후 요청해주세요."),
	IO_PROCESSING(1001, "입출력 처리 중 오류가 발생했습니다."),

	/**
	 * 로그인, 인증 관련 예외
	 */
	ACCESS_DENIED(2000, "접근이 거부되었습니다."),
	UNAUTHORIZED(2001, "유효하지 않은 인증 정보로 인해 인증 과정에서 문제가 발생하였습니다."),
	TOKEN_VALIDATE(2002, "유효하지 않은 token입니다. Token 값이 잘못되었거나 만료되어 유효하지 않은 경우로 token 갱신이 필요합니다."),
	PASSWORD_MISMATCH(2003, "비밀번호가 일치하지 않습니다."),

	/**
	 * 유저 관련 예외
	 */
	USER_EMAIL_DUPLICATION(2200, "이미 사용 중인 이메일입니다."),
	USER_NOT_FOUND_BY_ID(2201, "일치하는 회원을 찾을 수 없습니다."),
	USER_NOT_FOUND_BY_EMAIL(2202, "일치하는 회원을 찾을 수 없습니다."),
	USER_PHONE_DUPLICATION(2203, "이미 사용 중인 전화번호입니다."),
	USER_KAKAO_UID_DUPLICATION(2204, "이미 가입한 계정입니다."),
	USER_NOT_FOUND_BY_KAKAO_UID(2205, "일치하는 회원을 찾을 수 없습니다."),
	USER_NOT_FOUND_BY_PHONE(2206, "일치하는 회원을 찾을 수 없습니다."),

	KAKAO_CLIENT(10000, "카카오 서버와의 통신 중 오류가 발생했습니다."),

	/**
	 * 주소 관련 예외
	 */
	INVALID_ADDRESS_FORMAT(2400, "주소 형식이 올바르지 않습니다."),

	/**
	 * 악기 관련 예외
	 */
	INSTRUMENT_NOT_FOUND_BY_ID(2600, "일치하는 매물 정보를 찾을 수 없습니다.")
	;

	private final Integer code;
	private final String message;
}
